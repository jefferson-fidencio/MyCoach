package voluta.com.br.mycoach.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import voluta.com.br.mycoach.Adapters.AvaliacaoTecnicaListViewAdapter;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.Model.FrequenciaMetragem;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.Model.AvaliacaoTecnica;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.ServerConnection;

public class AvaliacaoTecnicaFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private List avaliacaoTecnicas;
    private Fragment avaliacaoTecnicaDetalheFragment;
    private AlertDialog creationDialog;
    private ProgressDialog progressDialog;
    private EditText editTextNameNew;
    private static final String URI_ADD = "http://www.hshpersonal.com.br/api/avaliacoes/tecnicas/add.php";

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(getResources().getString(R.string.title_activity_fragment_avaliacao_tecnica));

        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
        {
            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            fab.setImageResource(R.drawable.ic_note_add_white_24dp);
            fab.setOnClickListener(this);
            fab.show();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        avaliacaoTecnicas = ((Aluno)MyCoachApp.alunoVisualizado).getAvaliacaoTecnicaList();

        editTextNameNew = new EditText(getContext());
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Digite o nome da avaliação")
                .setView(editTextNameNew)
                .setPositiveButton("Criar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        creationDialog.cancel();
                        validadeFields();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        creationDialog.cancel();
                    }
                });
        creationDialog = alertDialogBuilder.create();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Criando Avaliação");
    }

    private void validadeFields() {

        //reseta erro
        editTextNameNew.setError(null);

        //recupera novo nome
        String name = editTextNameNew.getText().toString();

        if (TextUtils.isEmpty(name)) {
            editTextNameNew.setError(getString(R.string.error_field_required));
            editTextNameNew.requestFocus();
        }
        else
        {
            showProgress(true);
            doAdd(name);
        }
    }

    private void showProgress(boolean show) {
        if (show){
            progressDialog.show();
        }
        else
        {
            progressDialog.hide();
        }
    }

    private void doAdd(final String name) {
        ServerConnection.getInstance(getContext()).getRequestQueue().add(new StringRequest(Request.Method.POST, URI_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                AvaliacaoTecnica avaliacaoTecnica = new AvaliacaoTecnica(Integer.valueOf(response), name,null);
                EditAvaliacaoTecnicaFragment editAvaliacaoTecnicaFragment = new EditAvaliacaoTecnicaFragment();
                Bundle args = new Bundle();
                args.putSerializable("avaliacaoTecnica", avaliacaoTecnica);
                editAvaliacaoTecnicaFragment.setArguments(args);

                showProgress(false);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(getActivity().findViewById(R.id.main_fragment_container).getId(),editAvaliacaoTecnicaFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false);
                Snackbar.make(getView(), "Falha ao criar avaliação.",Snackbar.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idAluno", String.valueOf(MyCoachApp.alunoVisualizado.getId()));
                params.put("name", name);
                return params;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avaliacao_tecnica_fragment, null);
        loadList(view);
        return view;
    }

    private void loadList(View view) {

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new AvaliacaoTecnicaListViewAdapter(getActivity(), avaliacaoTecnicas));
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AvaliacaoTecnica avaliacaoTecnica = (AvaliacaoTecnica) avaliacaoTecnicas.get(position);
        Bundle args = new Bundle();
        args.putSerializable("avaliacaoTecnica", avaliacaoTecnica);
        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
            avaliacaoTecnicaDetalheFragment = new EditAvaliacaoTecnicaFragment();
        else
            avaliacaoTecnicaDetalheFragment = new AvaliacaoTecnicaDetalheFragment();
        avaliacaoTecnicaDetalheFragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.main_fragment_container, avaliacaoTecnicaDetalheFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        creationDialog.show();
    }
}
