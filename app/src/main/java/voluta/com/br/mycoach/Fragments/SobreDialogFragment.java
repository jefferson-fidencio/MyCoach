package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import voluta.com.br.mycoach.BuildConfig;
import voluta.com.br.mycoach.R;

public class SobreDialogFragment extends AppCompatDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_sobre_dialog_fragment, null);
        TextView txtVersion = (TextView) v.findViewById(R.id.textViewVersao);
        txtVersion.setText("Versão: " + BuildConfig.VERSION_NAME);
        return v;
    }
}
