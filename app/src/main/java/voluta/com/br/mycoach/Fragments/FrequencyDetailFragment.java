package voluta.com.br.mycoach.Fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import voluta.com.br.mycoach.Adapters.FrequencyDetailAdapter;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.Model.FrequenciaMetragem;
import voluta.com.br.mycoach.Model.Treino;
import voluta.com.br.mycoach.Model.TreinoSemanal;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.MultiPartRequest;
import voluta.com.br.mycoach.Services.ServerConnection;

/**
 * Created by jdfid on 27/10/2017.
 */

public class FrequencyDetailFragment extends Fragment {

    private FrequenciaMetragem frequenciaMetragem;
    private FrequencyDetailAdapter frequencyDetailAdapter;
    private ViewPager viewPager;
    private static final String URI_EDIT_FREQUENCY = "http://www.hshpersonal.com.br/api/frequency/edit.php";
    private int updateCount;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // sempre chega uma frequencia metragem
        frequenciaMetragem = (FrequenciaMetragem) getArguments().getSerializable(FrequenciasFragment.KEY_FREQUENCY_OBJ);
        getActivity().setTitle(frequenciaMetragem.getNome());

        //getActivity().findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorAppBodyWorkoutPrimary));
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_detail_frequency, null);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Alterando Frequência - Metragem");

        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
            setHasOptionsMenu(true);

        loadTabs(view);
        return view;
    }

    private void loadTabs(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        frequencyDetailAdapter = new FrequencyDetailAdapter(getChildFragmentManager(), frequenciaMetragem);
        viewPager.setAdapter(frequencyDetailAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (MyCoachApp.userLogado.getClass().equals(Coach.class)){
            inflater.inflate(R.menu.aluno_add_action_menu, menu);}
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save)
        {
            showProgressBar(true);
            updateCount = 0;

            for (int i = 0 ; i < frequencyDetailAdapter.getCount() ; i++) {
                Fragment fragment = frequencyDetailAdapter.getItem(i);
                if (fragment.getClass().equals(ImageFragment.class))
                {
                    Bitmap bitmap = ((ImageFragment)fragment).bitmap;
                    if (bitmap != null){
                        updateCount = updateCount + 1;
                        doUpdate(bitmap, i);
                    }
                }
            }
        }
        return true;
    }

    private void showProgressBar(boolean show) {
        if (show)
        {
            //setHasOptionsMenu(false);
            //viewPager.setVisibility(View.GONE);
            progressDialog.show();
        }
        else
        {
            //setHasOptionsMenu(true);
            //viewPager.setVisibility(View.VISIBLE);
            progressDialog.hide();
        }
    }

    private void doUpdate(final Bitmap bitmap, final int tipo) {
        MultiPartRequest multipartRequest = new MultiPartRequest(Request.Method.POST, URI_EDIT_FREQUENCY, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                updateCount = updateCount - 1;

                if (updateCount == 0)
                {
                    showProgressBar(false);
                    Snackbar.make(getView(), "Frequências e Metragens atualizadas com sucesso", Snackbar.LENGTH_LONG).show();
                    getFragmentManager().popBackStack();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateCount = updateCount - 1;

                if (updateCount == 0)
                {
                    showProgressBar(false);
                    Snackbar.make(getView(), "Falha ao atualizar frequências e metragens", Snackbar.LENGTH_LONG).show();
                }
                getFragmentManager().popBackStack();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(frequenciaMetragem.getId()));
                params.put("idAluno", String.valueOf(MyCoachApp.alunoVisualizado.getId()));
                params.put("tipo", String.valueOf(tipo));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                byte[] data = byteArrayOutputStream.toByteArray();
                params.put("fileToUpload", new DataPart("fileToUpload.jpg", data, "image/jpeg"));

                return params;
            }
        };

        ServerConnection.getInstance(getContext()).getRequestQueue().add(multipartRequest);
    }
}
