package voluta.com.br.mycoach.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import voluta.com.br.mycoach.Fragments.TecnicaDetalheFragment;
import voluta.com.br.mycoach.Model.AvaliacaoTecnica;
import voluta.com.br.mycoach.Model.Tecnica;

public class AvaliacaoTecnicaDetalhePagerAdapter extends FragmentStatePagerAdapter {

    private AvaliacaoTecnica avaliacaoTecnica;
    public static String KEY_TECNICA = "tecnica";

    public AvaliacaoTecnicaDetalhePagerAdapter(FragmentManager fm, AvaliacaoTecnica avaliacaoTecnica) {
        super(fm);
        this.avaliacaoTecnica = avaliacaoTecnica;
    }

    @Override
    public CharSequence getPageTitle(int position){
        Tecnica tecnica = avaliacaoTecnica.getTecnicas().get(position);
        return tecnica.getNome().toUpperCase();
    }

    public int getCount() {
        return avaliacaoTecnica.getTecnicas().size();
    }

    @Override
    public Fragment getItem(int position) {
        Tecnica tecnica = avaliacaoTecnica.getTecnicas().get(position);
        Fragment fragment = new TecnicaDetalheFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_TECNICA, tecnica);
        fragment.setArguments(args);
        return fragment;
    }
}
