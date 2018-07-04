package voluta.com.br.mycoach.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.Model.User;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.JsonParserRequest;
import voluta.com.br.mycoach.Services.ServerConnection;

public class LoginActivity extends AppCompatActivity {

    private static final String URI_LOGIN = "http://www.hshpersonal.com.br/api/users.php";

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;
    private ImageView imageViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mProgressView = findViewById(R.id.progressBar);
        mLoginFormView = findViewById(R.id.mLoginFormView);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        imageViewLogo = (ImageView) findViewById(R.id.imageViewLogo);

        // imageViewLogo
        Animation animationFadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        imageViewLogo.startAnimation(animationFadeIn);

        // Splash time
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkAutomaticLogin();
                    }
                });

            }
        }, 1000);
    }

    private void checkAutomaticLogin() {

        //checks if automatic login is set
        Map automaticLoginData = MyCoachApp.getAutomaticLoginData(this);
        if (automaticLoginData.entrySet().size() != 0){
            String email = (String) automaticLoginData.get("email");
            String senha = (String) automaticLoginData.get("senha");
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)){
                mEmailView.setText(email);
                mPasswordView.setText(senha);
                mEmailSignInButton.performClick();
            }
        }
        else
        {
            imageViewLogo.animate().translationY(-400f);
            mLoginFormView.setVisibility(View.VISIBLE);
        }


    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            doLogin(email, password);
        }
    }

    private void doLogin(String email, String password) {
        final Map headers = new HashMap<String, String>();
        String base64userpass = Base64.encodeToString((mEmailView.getText().toString() + ':' + mPasswordView.getText().toString()).getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", "Basic " + base64userpass);
        ServerConnection.getInstance(this).getRequestQueue().add(new JsonParserRequest<User>(URI_LOGIN, User.class, headers, new Response.Listener<User>() {
            @Override
            public void onResponse(User response)
            {
                if (response.categoria == 0) // request coach data
                {
                    ServerConnection.getInstance(getApplicationContext()).getRequestQueue().add(new JsonParserRequest<Coach>(URI_LOGIN, Coach.class, headers, new Response.Listener<Coach>() {
                        @Override
                        public void onResponse(Coach response) {
                            initiateApp(response);
                        }}, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.getClass().equals(NoConnectionError.class))
                            {
                                showProgress(false);
                                Snackbar.make((View)mLoginFormView.getParent(),R.string.error_no_connection,Snackbar.LENGTH_LONG).show();
                            }
                            else
                            {
                                showProgress(false);
                                mEmailView.setError(getString(R.string.error_incorrect_password));
                                mEmailView.requestFocus();
                            }
                        }
                    }));
                }
                else // request aluno data
                {
                    ServerConnection.getInstance(getApplicationContext()).getRequestQueue().add(new JsonParserRequest<Aluno>(URI_LOGIN, Aluno.class, headers, new Response.Listener<Aluno>() {
                        @Override
                        public void onResponse(Aluno response) {
                            initiateApp(response);
                        }}, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.getClass().equals(NoConnectionError.class))
                                {
                                    showProgress(false);
                                    Snackbar.make((View)mLoginFormView.getParent(),R.string.error_no_connection,Snackbar.LENGTH_LONG).show();
                                }
                                else
                                {
                                    showProgress(false);
                                    mEmailView.setError(getString(R.string.error_incorrect_password));
                                    mEmailView.requestFocus();
                                }
                            }
                        }));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getClass().equals(NoConnectionError.class))
                {
                    showProgress(false);
                    Snackbar.make((View)mLoginFormView.getParent(),R.string.error_no_connection,Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    showProgress(false);
                    mEmailView.setError(getString(R.string.error_incorrect_password));
                    mEmailView.requestFocus();
                }
            }
        }));
    }

    private void initiateApp(User response) {
        Map dadosLoginAutomatico = new HashMap();
        dadosLoginAutomatico.put("email", mEmailView.getText().toString());
        dadosLoginAutomatico.put("senha", mPasswordView.getText().toString());
        MyCoachApp.userLogado = response;
        MyCoachApp.modality = Modality.swimming;
        MyCoachApp.setAutomaticLoginData(LoginActivity.this, dadosLoginAutomatico);
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        mLoginFormView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }
}
