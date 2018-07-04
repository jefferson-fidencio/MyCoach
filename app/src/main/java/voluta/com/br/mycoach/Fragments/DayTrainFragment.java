package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.Model.Treino;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;

/**
 * Created by jdfid on 19/10/2017.
 */

public class DayTrainFragment extends Fragment {

    public EditText editText;
    private TextView textView;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        Treino treino = (Treino) args.getSerializable("treino");
        String treinoText = treino.getDescricao();

        View convertView = inflater.inflate(R.layout.fragment_day_train, container, false);
        textView = (TextView) convertView.findViewById(R.id.textViewTreino);
        editText = (EditText) convertView.findViewById(R.id.editTextTreino);

        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
        {
            textView.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            editText.setText(treinoText.equals("") ? "Não existem treinos cadastrados para este dia": treinoText);
        }
        else
        {
            textView.setText(treinoText.equals("") ? "Não existem treinos cadastrados para este dia": treinoText);
        }

        return convertView;
    }
}
