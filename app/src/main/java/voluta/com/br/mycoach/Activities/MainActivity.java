package voluta.com.br.mycoach.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import voluta.com.br.mycoach.Adapters.DeprecTitleNavigAdapter;
import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.Fragments.AlunoFragment;
import voluta.com.br.mycoach.Fragments.CoachFragment;
import voluta.com.br.mycoach.Fragments.PersonalDialogFragment;
import voluta.com.br.mycoach.Fragments.SobreDialogFragment;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.ServerConnection;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActionBar.OnNavigationListener {

    private FragmentManager fragmentManager;
    private DeprecTitleNavigAdapter adapter;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private View navHeader;
    private AlunoFragment mainFragment;

    //HACK para acesso ao menu com drawer a fim de mostrar ou nao botao de voltar e menu a partir do fragment
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide the action bar title
        actionBar = getSupportActionBar();

        fragmentManager = getSupportFragmentManager();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        navHeader = navigationView.getHeaderView(0);
        TextView textViewNome = (TextView) navHeader.findViewById(R.id.textViewNome);
        textViewNome.setText(MyCoachApp.userLogado.getNome());
        TextView textViewEmail = (TextView) navHeader.findViewById(R.id.textViewEmail);
        textViewEmail.setText(MyCoachApp.userLogado.getEmail());
        final ImageView imageViewAluno = (ImageView) navHeader.findViewById(R.id.imageViewAlunoContainer).findViewById(R.id.imageViewAluno);
        ServerConnection.getInstance(this).getImageLoader().get(ServerConnection.getInstance(this).getBaseURI() +  MyCoachApp.userLogado.getImg().substring(2), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageViewAluno.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                imageViewAluno.setImageDrawable(getResources().getDrawable(R.drawable.ic_swimmer_badge_72, null));
            }
        }, 256, 256);

        setAppTheme();

    }

    private void setAppTheme() {

        TextView textViewTitle = (TextView) navHeader.findViewById(R.id.textViewTitle);
        if (MyCoachApp.userLogado.getClass () == Coach.class) // coach
        {
            navigationView.getMenu().findItem(R.id.nav_personal).setVisible(false);
            textViewTitle.setText("Coach");
            fragmentManager.beginTransaction()
                    .add(R.id.main_fragment_container, new CoachFragment())
                    .commit();

            toolbar.setBackgroundColor(getResources().getColor(R.color.colorAppBodyWorkoutPrimary));
            navHeader.setBackground(getResources().getDrawable(R.mipmap.bg_nav_coach_xxxhdpi));
        }
        else // aluno
        {
            // Enabling Spinner dropdown navigation
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

            // Spinner title navigation data
            ArrayList navSpinner = new ArrayList();
            for (Modality modality: Modality.values()) {
                navSpinner.add(modality);
            }

            // title drop down adapter
            adapter = new DeprecTitleNavigAdapter(getApplicationContext(), navSpinner);

            // assigning the spinner navigation
            actionBar.setListNavigationCallbacks(adapter, this);

            // check if user is aluno or coach
            MyCoachApp.alunoVisualizado = (Aluno) MyCoachApp.userLogado; //hack
            textViewTitle.setText("Aluno");
            mainFragment = new AlunoFragment();
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, mainFragment).commit();

            Modality modality = MyCoachApp.modality;
            if (modality == Modality.swimming)
            {
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorAppPrimary));
                navHeader.setBackground(getResources().getDrawable(R.mipmap.bg_nav_drawer_xxxhdpi_new));
            }
            else if (modality == Modality.running)
            {
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorAppRunningPrimary));
                navHeader.setBackground(getResources().getDrawable(R.mipmap.bg_nav_drawer_running_xxxhdpi));
            }
            else
            {
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorAppBodyWorkoutPrimary));
                navHeader.setBackground(getResources().getDrawable(R.mipmap.bg_nav_drawer_bodyworkout_xxxhdpi));
            }

            adapter.notifyDataSetChanged();

            if (mainFragment != null)
                mainFragment.loadOptionsListView();
        }
    }

    protected void sendEmail() {
        String[] TO = {"bertellipersonal@hotmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato a partir do Home Swim Home");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Digite aqui a mensagem ao seu personal");

        try {
            startActivity(Intent.createChooser(emailIntent, "E-mail para seu personal"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "Não existe um aplicativo para envio de email.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sobre) {
            SobreDialogFragment sobreDialogFragment = new SobreDialogFragment();
            sobreDialogFragment.show(getSupportFragmentManager(), "sobreDialog");
        } else if (id == R.id.nav_sair) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getString(R.string.sair_dialog_message));
            alertDialogBuilder.setTitle(getString(R.string.sair_dialog_title));
            alertDialogBuilder.setNegativeButton(getString(R.string.sair_dialog_negative_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.setPositiveButton(getString(R.string.sair_dialog_positive_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    MyCoachApp.clearAutomaticLoginData(MainActivity.this);
                    MainActivity.this.finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if (id == R.id.nav_personal){
            PersonalDialogFragment personalDialogFragment = new PersonalDialogFragment();
            personalDialogFragment.show(getSupportFragmentManager(), "personalDialog");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        MyCoachApp.modality = (Modality) adapter.getItem(itemPosition);
        setAppTheme();
        return false;
    }
}
