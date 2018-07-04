package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import voluta.com.br.mycoach.Activities.MainActivity;
import voluta.com.br.mycoach.Adapters.AlunoListAdapter;
import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;

public class AlunoFragment extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentManager _fragmentManager;
    private Fragment treinoFragment;
    private Fragment frequenciasFragment;
    private Fragment avaliacaoCondFisicioFragment;
    private Fragment avaliacaoTecnicaFragment;
    private Fragment meusVideosFragment;
    private Fragment outrosVideosFragment;
    private FloatingActionButton floatingActionButton;
    private boolean mToolBarNavigationListenerIsRegistered = false;
    private View fragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _fragmentManager = getActivity().getSupportFragmentManager();
        inicializarFragmentsUnicos();
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();
    }

    private void inicializarFragmentsUnicos() {
        treinoFragment = new TrainFragment();
        frequenciasFragment = new FrequenciasFragment();
        avaliacaoCondFisicioFragment = new AvaliacaoCondFisicioFragment();
        avaliacaoTecnicaFragment = new AvaliacaoTecnicaFragment();
        meusVideosFragment = new VideosFragment();
        outrosVideosFragment = new OutrosVideosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_main,null);
        loadOptionsListView();
        return fragment;
    }

    public void loadOptionsListView() {
        ListView listView = (ListView) fragment.findViewById(R.id.listview_principal);
        listView.setAdapter(new AlunoListAdapter(getActivity()));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
        {
            getActivity().findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(MyCoachApp.modality == Modality.swimming ? R.color.colorAppPrimary : MyCoachApp.modality == Modality.running ? R.color.colorAppRunningPrimary : R.color.colorAppBodyWorkoutPrimary));
            getActivity().setTitle(MyCoachApp.alunoVisualizado.getNome());
            enableBackButton(true);
        }
        else {
            getActivity().setTitle(getString(R.string.title_activity_fragment_opcoes));
            enableBackButton(false);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        floatingActionButton.hide();
        enableBackButton(true);

        switch (position){
            case 0:
                _fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.main_fragment_container, new TrainFragment()) //TODO ver se melhor criar sempre ou como manter unico aqui
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                _fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.main_fragment_container, frequenciasFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                _fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.main_fragment_container, avaliacaoCondFisicioFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                _fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.main_fragment_container, avaliacaoTecnicaFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                _fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.main_fragment_container, meusVideosFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 5:
                _fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.main_fragment_container, outrosVideosFragment)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
