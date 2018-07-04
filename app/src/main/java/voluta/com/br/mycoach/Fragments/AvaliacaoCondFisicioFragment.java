package voluta.com.br.mycoach.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import voluta.com.br.mycoach.Adapters.AvaliacaoCondFisicoExpandableListAdapter;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.Model.FrequenciaMetragem;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.Model.AvaliacaoCondFisico;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.ServerConnection;

public class AvaliacaoCondFisicioFragment extends Fragment implements  View.OnClickListener {

    private AvaliacaoCondFisico ultimaAvaliacao;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.title_activity_fragment_avaliacao_cond_fisico));

        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
        {
            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            fab.setImageResource(R.drawable.ic_note_add_white_24dp);
            fab.setOnClickListener(this);
            fab.show();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ultimaAvaliacao = ((Aluno)MyCoachApp.alunoVisualizado).getAvaliacaoCondFisicoList().get(0);
        }
        catch (Exception ex){}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.avaliacao_cond_fisico_fragment, null);
        loadList(view);
        return view;
    }

    private void loadList(View view) {
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        expandableListView.setAdapter(new AvaliacaoCondFisicoExpandableListAdapter(getActivity(), ((Aluno)MyCoachApp.alunoVisualizado).getAvaliacaoCondFisicoList()));
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(getActivity().findViewById(R.id.main_fragment_container).getId(),new AddAvaliacaoCondFisicoFragment())
                .addToBackStack(null)
                .commit();
    }
}
