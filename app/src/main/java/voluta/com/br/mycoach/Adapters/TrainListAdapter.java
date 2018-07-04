package voluta.com.br.mycoach.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import voluta.com.br.mycoach.Fragments.DayTrainFragment;
import voluta.com.br.mycoach.Model.Treino;
import voluta.com.br.mycoach.Model.TreinoSemanal;

/**
 * Created by jdfid on 19/10/2017.
 */

public class TrainListAdapter extends FragmentStatePagerAdapter {

    private TreinoSemanal _treinoSemanal;
    private List<Map> fragments;
    private List<Map> treinos;

    public TrainListAdapter(FragmentManager fm, TreinoSemanal treinoSemanal) {
        super(fm);
        this._treinoSemanal = treinoSemanal;
        fragments = new ArrayList();
        treinos = new ArrayList();
    }

    @Override
    public Fragment getItem(int position) {

        // gambiarra pra nao instanciar fragment toda vez
        for (int i = 0 ; i < fragments.size() ; i++) {
            Map<Integer,Fragment> map = fragments.get(i);
            if (map.containsKey(position))
                return map.get(position);
        }

        // se nao existir =, cria
        DayTrainFragment fragment = new DayTrainFragment();
        Bundle args = new Bundle();
        Treino treino = _treinoSemanal.gettreino().get(position);
        args.putSerializable("treino", treino);
        fragment.setArguments(args);
        //salvando fragment associado com a posicao do adapter
        Map map = new HashMap<Integer,Fragment>();
        map.put(position,fragment);
        fragments.add(map);
        //salvando treino associado com a posicao do adapter
        map = new HashMap<Integer,Treino>();
        map.put(position,treino);
        treinos.add(map);
        return fragment;
    }

    @Override
    public int getCount() {
        return _treinoSemanal.gettreino().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Treino treino = _treinoSemanal.gettreino().get(position);
        return treino.getdia().toUpperCase();
    }

    public Treino getTreino (int position) {
        for (int i = 0 ; i < treinos.size() ; i++) {
            Map<Integer,Treino> map = treinos.get(i);
            if (map.containsKey(position))
                return map.get(position);
        }
        return null;
    }

}
