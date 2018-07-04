package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import voluta.com.br.mycoach.Adapters.TrainListAdapter;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.Model.Treino;
import voluta.com.br.mycoach.Model.TreinoSemanal;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.JsonParserRequest;
import voluta.com.br.mycoach.Services.ServerConnection;

public class TrainFragment extends Fragment {

    private FragmentManager fragmentManager;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    private TrainListAdapter trainListAdapter;
    private static final String URI_EDIT_TREINO = "http://www.hshpersonal.com.br/api/treino/edit.php";
    private int updateCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getChildFragmentManager();
        getActivity().setTitle(getString(R.string.title_activity_fragment_treino));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_train,container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        setHasOptionsMenu(true);
        loadListView(v);
        return v;
    }

    private void loadListView(View v) {
        trainListAdapter = new TrainListAdapter(fragmentManager,((Aluno)MyCoachApp.alunoVisualizado).getTreino_semanalList().get(0));
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        viewPager.setAdapter(trainListAdapter);
        View pager_title_strip = v.findViewById(R.id.pager_title_strip);
        pager_title_strip.setBackgroundColor(getResources().getColor(MyCoachApp.getAppPrimaryColorId()));

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (Calendar.MONDAY == dayOfWeek) viewPager.setCurrentItem(0);
        else if (Calendar.TUESDAY == dayOfWeek) viewPager.setCurrentItem(1);
        else if (Calendar.WEDNESDAY == dayOfWeek) viewPager.setCurrentItem(2);
        else if (Calendar.THURSDAY == dayOfWeek) viewPager.setCurrentItem(3);
        else if (Calendar.FRIDAY == dayOfWeek) viewPager.setCurrentItem(4);
        else if (Calendar.SATURDAY == dayOfWeek) viewPager.setCurrentItem(5);
        else if (Calendar.SUNDAY == dayOfWeek) viewPager.setCurrentItem(6);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (MyCoachApp.userLogado.getClass().equals(Coach.class)){
        inflater.inflate(R.menu.aluno_add_action_menu, menu);}
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save)
        {
            showProgressBar(true);
            updateCount = 0;

            TreinoSemanal treinoSemanal = ((Aluno)MyCoachApp.alunoVisualizado).getTreino_semanalList().get(0);

            for (int i = 0 ; i < trainListAdapter.getCount() ; i++) {
                DayTrainFragment fragment = (DayTrainFragment) trainListAdapter.getItem(i);
                EditText editText = (EditText)fragment.editText;
                if (editText != null)
                {
                    String desc = editText.getText().toString();

                    //procura treino alterado por este fragment
                    Treino treinoAlterado = trainListAdapter.getTreino(i);
                    for (int j = 0 ; j < treinoSemanal.gettreino().size(); j ++)
                    {
                        Treino treino = treinoSemanal.gettreino().get(j);
                        if (treino.getId() == treinoAlterado.getId())
                        {
                            treino.setDescricao(desc);
                            updateCount = updateCount + 1;
                            doUpdate(treino);
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void showProgressBar(boolean show) {
        if (show)
        {
            setHasOptionsMenu(false);
            viewPager.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            setHasOptionsMenu(true);
            viewPager.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void doUpdate(final Treino treino) {
        ServerConnection.getInstance(getContext()).getRequestQueue().add(
                new StringRequest(Request.Method.POST, URI_EDIT_TREINO, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updateCount = updateCount - 1;

                        if (updateCount == 0)
                        {
                            showProgressBar(false);
                            Snackbar.make(getView(), "Treinos atualizados com sucesso", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        updateCount = updateCount - 1;

                        if (updateCount == 0)
                        {
                            showProgressBar(false);
                            Snackbar.make(getView(), "Falha ao atualizar treinos", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map params = new HashMap();
                        params.put("id", String.valueOf(treino.getId()));
                        params.put("descricao", treino.getDescricao());
                        return params;
                    }
                }
        );
    }
}
