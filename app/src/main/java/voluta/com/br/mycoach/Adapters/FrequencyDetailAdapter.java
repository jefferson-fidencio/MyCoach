package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import voluta.com.br.mycoach.Fragments.ImageFragment;
import voluta.com.br.mycoach.Fragments.NetworkImageFragment;
import voluta.com.br.mycoach.Model.FrequenciaMetragem;
import voluta.com.br.mycoach.R;

/**
 * Created by jdfid on 27/10/2017.
 */

public class FrequencyDetailAdapter extends FragmentStatePagerAdapter {

    public static final String KEY_NETWORK_IMAGE_SRC = "imgSrc";

    private FrequenciaMetragem frequenciaMetragem;
    private List<Map> fragments;
    private List<Map> bitmaps;

    public FrequencyDetailAdapter(FragmentManager fragmentManager, FrequenciaMetragem frequenciaMetragem){
        super(fragmentManager);
        this.frequenciaMetragem = frequenciaMetragem;
        fragments = new ArrayList();
    }

    @Override
    public int getCount() {
        return 2;
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
        String imgSrc = "";
        Fragment fragment = null;
            if (position == 0) //frequencia
            {
                if (frequenciaMetragem.getfrequenciaImg().equals("")) //nao tem imagem no server ainda
                {
                    fragment = new ImageFragment();
                }
                else {
                    fragment = new NetworkImageFragment();
                    imgSrc = frequenciaMetragem.getfrequenciaImg().substring(2);
                    Bundle args = new Bundle();
                    args.putString(KEY_NETWORK_IMAGE_SRC, imgSrc);
                    fragment.setArguments(args);
                }
            }else if (position == 1){
                if (frequenciaMetragem.getmetragemImg().equals("")) //nao tem imagem no server ainda
                {
                    fragment = new ImageFragment();
                }
                else {
                    fragment = new NetworkImageFragment();
                    imgSrc = frequenciaMetragem.getmetragemImg().substring(2);
                    Bundle args = new Bundle();
                    args.putString(KEY_NETWORK_IMAGE_SRC, imgSrc);
                    fragment.setArguments(args);
                }
            }

        //salvando fragment associado com a posicao do adapter
        Map map = new HashMap<Integer,Fragment>();
        map.put(position,fragment);
        fragments.add(map);

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String titleStr = "Frequencias e Metragens";
        if (position == 0) {
            titleStr = "Frequências";
        }else if (position == 1){
            titleStr = "Metragens";
        }
        return titleStr.toUpperCase();
    }
}
