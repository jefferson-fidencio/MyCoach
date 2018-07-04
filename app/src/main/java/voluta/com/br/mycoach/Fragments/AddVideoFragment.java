package voluta.com.br.mycoach.Fragments;

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
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import voluta.com.br.mycoach.Activities.MainActivity;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.ServerConnection;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jdfid on 20/11/2017.
 */

public class AddVideoFragment extends Fragment {

    private boolean mToolBarNavigationListenerIsRegistered = false;
    private static final String URI_ADD= "http://www.hshpersonal.com.br/api/videos/add.php";
    private EditText editTextName;
    private EditText editTextUrl;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_video,null);
        editTextName = (EditText) v.findViewById(R.id.editTextName);
        editTextUrl = (EditText) v.findViewById(R.id.editTextUrl);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Adicionar vídeo");
        enableBackButton(true);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();
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
        editTextUrl.setError(null);

        // Store values at the time of the login attempt.
        String name = editTextName.getText().toString();
        String url = editTextUrl.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            editTextName.setError(getString(R.string.error_field_required));
            focusView = editTextName;
            cancel = true;
        }

        if (TextUtils.isEmpty(url)) {
            editTextUrl.setError(getString(R.string.error_field_required));
            focusView = editTextUrl;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            doUpload(name, url);
        }
    }

    private void doUpload(final String name, final String url) {
        ServerConnection.getInstance(getContext()).getRequestQueue().add(new StringRequest(Request.Method.POST, URI_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Snackbar.make(getView(), "Vídeo criado com sucesso.",Snackbar.LENGTH_LONG).show();
                getFragmentManager().popBackStack();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView(), "Falha ao criar vídeo.",Snackbar.LENGTH_LONG).show();
                getFragmentManager().popBackStack();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idAluno", String.valueOf(MyCoachApp.alunoVisualizado.getId()));
                params.put("name", name);
                params.put("url", url);
                return params;
            }
        });
    }

    private void showProgress(boolean visible) {
        if (visible) {
            editTextName.setVisibility(View.GONE);
            editTextUrl.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            editTextName.setVisibility(View.VISIBLE);
            editTextUrl.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
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
}
