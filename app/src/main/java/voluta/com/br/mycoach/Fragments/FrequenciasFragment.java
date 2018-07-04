package voluta.com.br.mycoach.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import voluta.com.br.mycoach.Adapters.FrequenciasMetragensListAdapter;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.Model.FrequenciaMetragem;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.MultiPartRequest;
import voluta.com.br.mycoach.Services.ServerConnection;

public class FrequenciasFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String KEY_FREQUENCY_OBJ = "frequency";
    private FrequencyDetailFragment frequencyDetailFragment;
    private AlertDialog creationDialog;
    private ProgressDialog progressDialog;
    private EditText editTextNameNewFrequency;
    private static final String URI_ADD_FREQUENCY = "http://www.hshpersonal.com.br/api/frequency/add.php";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        editTextNameNewFrequency = new EditText(getContext());

        alertDialogBuilder.setMessage("Digite o nome do registro")
            .setView(editTextNameNewFrequency)
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
        creationDialog =alertDialogBuilder.create();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Criando Frequência - Metragem");
    }

    private void validadeFields() {

        //reseta erro
        editTextNameNewFrequency.setError(null);

        //recupera novo nome
        String name = editTextNameNewFrequency.getText().toString();

        if (TextUtils.isEmpty(name)) {
            editTextNameNewFrequency.setError(getString(R.string.error_field_required));
            editTextNameNewFrequency.requestFocus();
        }
        else
        {
            showProgress(true);
            doAddFrequency(name);
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

    private void doAddFrequency(final String name) {
        ServerConnection.getInstance(getContext()).getRequestQueue().add(new StringRequest(Request.Method.POST, URI_ADD_FREQUENCY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                FrequenciaMetragem frequenciaMetragem = new FrequenciaMetragem(Integer.valueOf(response), name,"","");
                frequencyDetailFragment = new FrequencyDetailFragment();
                Bundle args = new Bundle();
                args.putSerializable(KEY_FREQUENCY_OBJ, frequenciaMetragem);
                frequencyDetailFragment.setArguments(args);

                showProgress(false);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(getActivity().findViewById(R.id.main_fragment_container).getId(),frequencyDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false);
                Snackbar.make(getView(), "Falha ao criar frequência.",Snackbar.LENGTH_LONG).show();
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

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.title_activity_fragment_frequencias));
        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
        {
            //getActivity().findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorAppBodyWorkoutPrimary));
            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            fab.setImageResource(R.drawable.ic_note_add_white_24dp);
            fab.setOnClickListener(this);
            fab.show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frequencia_fragment, null);
        LoadListView(view);
        return view;
    }

    private void LoadListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new FrequenciasMetragensListAdapter(getActivity(), ((Aluno)MyCoachApp.alunoVisualizado).getFrequenciaMetragemList()));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        frequencyDetailFragment = new FrequencyDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_FREQUENCY_OBJ, (FrequenciaMetragem) parent.getItemAtPosition(position));
        frequencyDetailFragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.
                beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.main_fragment_container, frequencyDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        creationDialog.show();
    }
}
