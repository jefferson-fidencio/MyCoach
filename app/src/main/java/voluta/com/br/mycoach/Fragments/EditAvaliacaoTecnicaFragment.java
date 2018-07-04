package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import voluta.com.br.mycoach.Adapters.TecnicasListAdapter;
import voluta.com.br.mycoach.Model.AvaliacaoTecnica;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.Model.SubTecnica;
import voluta.com.br.mycoach.Model.Tecnica;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;

/**
 * Created by jdfid on 03/12/2017.
 */

public class EditAvaliacaoTecnicaFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private AvaliacaoTecnica avaliacaoTecnica;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(avaliacaoTecnica.getNome());
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_note_add_white_24dp);
        fab.setOnClickListener(this);
        fab.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avaliacaoTecnica = (AvaliacaoTecnica) getArguments().get("avaliacaoTecnica");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_avaliacao_tecnica, null);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setEmptyView(view.findViewById(R.id.empty_list_item));
        listView.setOnItemClickListener(this);
        loadList(listView);
        return view;
    }

    private void loadList(ListView listView) {
        TecnicasListAdapter tecnicasListAdapter = new TecnicasListAdapter(getContext(), avaliacaoTecnica.getTecnicas());
        listView.setAdapter(tecnicasListAdapter);
    }

    @Override
    public void onClick(View v) {

        AddTecnicaFragment addTecnicaFragment = new AddTecnicaFragment();
        Bundle args = new Bundle();
        args.putSerializable("AvaliacaoTecnica", avaliacaoTecnica);
        // nova tecnica
        args.putSerializable("tecnica", new Tecnica(0,0,"", "", new ArrayList<SubTecnica>(), "",""));
        addTecnicaFragment.setArguments(args);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(getActivity().findViewById(R.id.main_fragment_container).getId(), addTecnicaFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tecnica tecnica = (Tecnica) parent.getItemAtPosition(position);
        Bundle args = new Bundle();
        args.putSerializable("tecnica", tecnica);
        AddTecnicaFragment tecnicaDetalheFragment = new AddTecnicaFragment();
        tecnicaDetalheFragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.main_fragment_container, tecnicaDetalheFragment)
                .addToBackStack(null)
                .commit();
    }
}
