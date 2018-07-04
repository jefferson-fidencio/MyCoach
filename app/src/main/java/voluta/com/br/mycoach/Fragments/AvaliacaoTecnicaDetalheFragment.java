package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import voluta.com.br.mycoach.Adapters.AvaliacaoTecnicaDetalhePagerAdapter;
import voluta.com.br.mycoach.Model.AvaliacaoTecnica;
import voluta.com.br.mycoach.R;

public class AvaliacaoTecnicaDetalheFragment extends Fragment {

    private AvaliacaoTecnica avaliacaoTecnica;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avaliacao_tecnica_detalhe_fragment, null);
        loadTabs(view);
        return view;
    }

    private void loadTabs(View view) {
        Bundle arguments = getArguments();
        avaliacaoTecnica = (AvaliacaoTecnica) arguments.getSerializable("avaliacaoTecnica");
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new AvaliacaoTecnicaDetalhePagerAdapter(getChildFragmentManager(), avaliacaoTecnica));
    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        AvaliacaoTecnicaDetalhePagerAdapter adapter = (AvaliacaoTecnicaDetalhePagerAdapter) expandableListView.getExpandableListAdapter();
        adapter.destroy();

    }*/
}
