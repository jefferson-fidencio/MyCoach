package voluta.com.br.mycoach.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import voluta.com.br.mycoach.Activities.MainActivity;
import voluta.com.br.mycoach.Adapters.TitleSpinnerAdapter;
import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.MultiPartRequest;
import voluta.com.br.mycoach.Services.ServerConnection;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jdfid on 20/11/2017.
 */

public class AddAlunoFragment extends Fragment implements View.OnClickListener {

    private boolean mToolBarNavigationListenerIsRegistered = false;
    private static final String URI_ADD_ALUNO = "http://www.hshpersonal.com.br/api/users/add.php";
    private static final int PICK_IMAGE_REQUEST = 111;
    private ImageView imageView;
    private TextView textViewClick;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressBar progressBar;
    private Spinner spinner;
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_aluno,null);
        textViewClick = (TextView) v.findViewById(R.id.textViewSelectPhoto);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        editTextName = (EditText) v.findViewById(R.id.editTextName);
        editTextEmail = (EditText) v.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) v.findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayList navSpinner = new ArrayList();
        for (Modality modality: Modality.values()) {
            navSpinner.add(modality);
        }
        spinner.setAdapter(new TitleSpinnerAdapter(getContext(), navSpinner) );
        textViewClick.setOnClickListener(this);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Adicionar aluno");
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
        editTextEmail.setError(null);
        editTextPassword.setError(null);

        // Store values at the time of the login attempt.
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        int idModalidade = spinner.getSelectedItemPosition();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            editTextName.setError(getString(R.string.error_field_required));
            focusView = editTextName;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError(getString(R.string.error_field_required));
            focusView = editTextEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            editTextEmail.setError(getString(R.string.error_invalid_email));
            focusView = editTextEmail;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError(getString(R.string.error_field_required));
            focusView = editTextPassword;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            doUpload(name,email,password,idModalidade);
        }
    }

    private void doUpload(final String name, final String email, final String password, final int idModalidade) {
        MultiPartRequest multipartRequest = new MultiPartRequest(Request.Method.POST, URI_ADD_ALUNO, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Snackbar.make(getView(), "Aluno criado com sucesso.",Snackbar.LENGTH_LONG).show();
                getFragmentManager().popBackStack();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getView(), "Falha ao criar aluno.",Snackbar.LENGTH_LONG).show();
                getFragmentManager().popBackStack();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idCoach", String.valueOf(MyCoachApp.userLogado.getId()));
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("modalidade", String.valueOf(idModalidade));
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

    private void showProgress(boolean visible) {
        if (visible) {
            editTextName.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            editTextPassword.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            textViewClick.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            editTextName.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            textViewClick.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
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
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Selecione a imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                imageView.setImageTintMode(null);
                imageView.setImageURI(data.getData());
                textViewClick.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
