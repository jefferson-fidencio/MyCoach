package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import voluta.com.br.mycoach.Activities.MainActivity;
import voluta.com.br.mycoach.Adapters.AlunosListAdapter;
import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;

/**
 * Created by jdfid on 17/11/2017.
 */

public class CoachFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView listView;
    private boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_coach, null);
        listView = (ListView) v.findViewById(R.id.listView);
        loadList();
        return v;
    }

    private void loadList() {
        //ordena lista por nome
        Collections.sort(((Coach)MyCoachApp.userLogado).getAlunos(), new Comparator<Aluno>() {
            @Override
            public int compare(Aluno o1, Aluno o2) {
                return o1.getNome().compareTo(o2.getNome());
            }
        });

        AlunosListAdapter alunosListAdapter = new AlunosListAdapter(getContext(), ((Coach)MyCoachApp.userLogado).getAlunos());
        listView.setAdapter(alunosListAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Aluno alunoVisualizado = (Aluno) parent.getItemAtPosition(position);
        MyCoachApp.alunoVisualizado = alunoVisualizado;
        MyCoachApp.modality = alunoVisualizado.getIdModalidade() == 0 ? Modality.swimming : (alunoVisualizado.getIdModalidade() == 1 ? Modality.running : Modality.body_workout);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.main_fragment_container, new AlunoFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
        {
            getActivity().findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorAppBodyWorkoutPrimary));
            getActivity().setTitle("Meus Alunos");
            enableBackButton(false);
            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            fab.setImageResource(R.drawable.ic_person_add_white_24dp);
            fab.setOnClickListener(this);
            fab.show();
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
    public void onClick(View v) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(getActivity().findViewById(R.id.main_fragment_container).getId(),new AddAlunoFragment())
                .addToBackStack(null)
                .commit();
    }
}
