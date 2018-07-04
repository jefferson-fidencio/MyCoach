package voluta.com.br.mycoach.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import voluta.com.br.mycoach.Activities.MainActivity;
import voluta.com.br.mycoach.Adapters.SubTecnicaListAdapter;
import voluta.com.br.mycoach.Adapters.TitleSpinnerAdapter;
import voluta.com.br.mycoach.Enum.Conceito;
import voluta.com.br.mycoach.Enum.Execucao;
import voluta.com.br.mycoach.Model.AvaliacaoTecnica;
import voluta.com.br.mycoach.Model.SubTecnica;
import voluta.com.br.mycoach.Model.Tecnica;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.ServerConnection;

/**
 * Created by jdfid on 03/12/2017.
 */

public class AddTecnicaFragment extends Fragment implements View.OnClickListener {

    private boolean mToolBarNavigationListenerIsRegistered = false;
    private AvaliacaoTecnica avaliacaoTecnica;
    private Tecnica tecnica;
    private static final String URI_ADD_TECNICA = "http://www.hshpersonal.com.br/api/avaliacoes/tecnicas/tecnicas/add.php";
    private static final String URI_ADD_SUB_TECNICA = "http://www.hshpersonal.com.br/api/avaliacoes/tecnicas/tecnicas/subtecnicas/add.php";
    private EditText editTextName;
    private EditText editTextObs;
    private EditText editTextVideo;
    private Spinner spinnerConceito;
    private ProgressBar progressBar;
    private TextView empty;
    private ListView listView;
    private boolean editing = false;
    private AlertDialog dialogCriacaoSubTecnica;
    private SubTecnicaListAdapter subTecnicaListAdapter;
    private int addCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_tecnica, null);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextObs = (EditText) view.findViewById(R.id.editTextObs);
        editTextVideo = (EditText) view.findViewById(R.id.editTextVideo);
        spinnerConceito = (Spinner) view.findViewById(R.id.spinnerConceito);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        listView = (ListView)view.findViewById(R.id.listView);
        empty = (TextView) view.findViewById(R.id.empty_list_item);

        ArrayList navSpinner = new ArrayList();
        for (Conceito conceito: Conceito.values()) {
            navSpinner.add(conceito);
        }
        spinnerConceito.setAdapter(new TitleSpinnerAdapter(getContext(), navSpinner) );


        if (tecnica != null)
        {
            editing = true;
            editTextName.setText(tecnica.getNome());
            editTextObs.setText(tecnica.getObservacao());
            editTextVideo.setText(tecnica.getvideoURI());

        }

        loadList(inflater);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        View dialogView = inflater.inflate(R.layout.fragment_add_sub_tecnica, null);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerExecucao = (Spinner) dialogView.findViewById(R.id.spinnerExecucao);
        final ArrayList spinnerData = new ArrayList();
        for (Execucao execucao: Execucao.values()) {
            spinnerData.add(execucao);
        }
        spinnerExecucao.setAdapter(new TitleSpinnerAdapter(getContext(), spinnerData) );
        alertBuilder.setView(dialogView)
                .setTitle("Adicionar sub-técnica")
                .setPositiveButton("Criar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SubTecnica subTecnica = new SubTecnica(0, editTextName.getText().toString(), new voluta.com.br.mycoach.Model.Execucao(spinnerExecucao.getSelectedItemId(), spinnerExecucao.getSelectedItem().toString()));
                        tecnica.getSubTecnicas().add(subTecnica);
                        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialogCriacaoSubTecnica = alertBuilder.create();

        setHasOptionsMenu(true);
        return view;
    }

    private void loadList(LayoutInflater inflater) {
        listView.setEmptyView(empty);
        //listView.addHeaderView(inflater.inflate(R.layout.list_header_sub_tecnica, null));
        subTecnicaListAdapter = new SubTecnicaListAdapter(getContext(),tecnica);
        listView.setAdapter(subTecnicaListAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            avaliacaoTecnica = (AvaliacaoTecnica) getArguments().getSerializable("AvaliacaoTecnica");
        }catch (Exception ex){}

        try {
            tecnica = (Tecnica) getArguments().getSerializable("tecnica");
        }catch (Exception ex){

        }

        getActivity().setTitle("Adicionar técnica");
        enableBackButton(true);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void enableBackButton(boolean enable) {
        final MainActivity parentActivity = (MainActivity) getActivity();
        if(enable) {
            parentActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(!mToolBarNavigationListenerIsRegistered) {
                parentActivity.actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parentActivity.onBackPressed();
                    }
                });
                mToolBarNavigationListenerIsRegistered = true;
            }
        } else {
            parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            parentActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            parentActivity.actionBarDrawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.aluno_add_action_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save)
        {
            validadeFields();
        }
        return true;
    }

    private void validadeFields() {
        // Reset errors.
        editTextName.setError(null);
        editTextObs.setError(null);
        editTextVideo.setError(null);

        // Store values at the time of the login attempt.
        String name = editTextName.getText().toString();
        String observacao = editTextObs.getText().toString();
        String videoUrl = editTextVideo.getText().toString();
        int idConceito = spinnerConceito.getSelectedItemPosition();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            editTextName.setError(getString(R.string.error_field_required));
            focusView = editTextName;
            cancel = true;
        }
        if (TextUtils.isEmpty(observacao)) {
            editTextObs.setError(getString(R.string.error_field_required));
            focusView = editTextObs;
            cancel = true;
        }
        if (TextUtils.isEmpty(videoUrl)) {
            editTextVideo.setError(getString(R.string.error_field_required));
            focusView = editTextVideo;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            doAdd(name,observacao,videoUrl,idConceito);
    }
    }

    private void showProgress(boolean visible) {
        if (visible) {
            editTextName.setVisibility(View.GONE);
            editTextObs.setVisibility(View.GONE);
            editTextVideo.setVisibility(View.GONE);
            spinnerConceito.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            editTextName.setVisibility(View.VISIBLE);
            editTextObs.setVisibility(View.VISIBLE);
            editTextVideo.setVisibility(View.VISIBLE);
            spinnerConceito.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void doAdd(final String nome, final String observacao, final String videoUrl, final int idConceito) {

        addCount = 1 + subTecnicaListAdapter.getCount();

        ServerConnection.getInstance(getContext()).getRequestQueue().add(new StringRequest(Request.Method.POST, URI_ADD_TECNICA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                tecnica.setId(Integer.valueOf(response));

                addCount = addCount - 1;
                if (addCount == 0)
                {
                    showProgress(false);
                    getFragmentManager().popBackStack();
                    Snackbar.make(getView(), "Técnica criada com sucesso.",Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    //starts adding sub techniques
                    for (int i = 0; i < subTecnicaListAdapter.getCount() ; i++)
                    {
                        SubTecnica subTecnica = (SubTecnica) subTecnicaListAdapter.getItem(i);
                        doAddSubTecnica(subTecnica, tecnica);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    showProgress(false);
                    getFragmentManager().popBackStack();
                    Snackbar.make(getView(), "Falha ao criar técnica.",Snackbar.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idAvaliacao", String.valueOf(avaliacaoTecnica.getId()));
                params.put("nome", nome);
                params.put("observacao", observacao);
                params.put("videoUrl", videoUrl);
                params.put("conceito", String.valueOf(idConceito));
                return params;
            }
        });
    }

    private void doAddSubTecnica(final SubTecnica subTecnica, final Tecnica tecnica) {
        ServerConnection.getInstance(getContext()).getRequestQueue().add(new StringRequest(Request.Method.POST, URI_ADD_SUB_TECNICA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                addCount = addCount - 1;
                if (addCount == 0)
                {
                    showProgress(false);
                    getFragmentManager().popBackStack();
                    Snackbar.make(getView(), "Técnica criada com sucesso.",Snackbar.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addCount = addCount - 1;
                if (addCount == 0)
                {
                    showProgress(false);
                    getFragmentManager().popBackStack();
                    Snackbar.make(getView(), "Falha ao criar sub-técnica.",Snackbar.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idTecnica", String.valueOf(tecnica.getId()));
                params.put("nome", subTecnica.getNome());
                params.put("idExecucao", subTecnica.getExecucao().toString());
                return params;
            }
        });
    }

    @Override
    public void onClick(View v) {
        dialogCriacaoSubTecnica.show();
    }
}
