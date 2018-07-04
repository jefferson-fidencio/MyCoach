package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import voluta.com.br.mycoach.Adapters.AvaliacaoTecnicaDetalhePagerAdapter;
import voluta.com.br.mycoach.Model.SubTecnica;
import voluta.com.br.mycoach.Model.Tecnica;
import voluta.com.br.mycoach.Model.Video;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;

/**
 * Created by jdfid on 30/10/2017.
 */

public class TecnicaDetalheFragment extends Fragment implements View.OnClickListener {

    private FrameLayout frameLayout;
    private CardView cardView;
    private YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    private Video video;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Tecnica tecnica = (Tecnica) getArguments().getSerializable(AvaliacaoTecnicaDetalhePagerAdapter.KEY_TECNICA);
        video = new Video(0,"",tecnica.getvideoURI());
        View convertView = inflater.inflate(R.layout.expandable_list_item_expanded_avaliacao_tecnica, null);
        TextView textViewConceito = (TextView) convertView.findViewById(R.id.textViewConceito);
        textViewConceito.setText(tecnica.getConceito().toString());
        TextView textViewObs = (TextView) convertView.findViewById(R.id.textViewObservacao);
        textViewObs.setText(tecnica.getObservacao());
        LinearLayout listView = (LinearLayout) convertView.findViewById(R.id.subTecnicaContainer);
        for (SubTecnica subTecnica:tecnica.getSubTecnicas()) {
            View subTecnicaView = inflater.inflate(R.layout.list_item_sub_tecnica, null);
            TextView textViewNome = (TextView) subTecnicaView.findViewById(R.id.textViewNome);
            textViewNome.setText(subTecnica.getNome());
            RadioButton radioButton;
            switch (subTecnica.getExecucao()){
                case E:
                    radioButton = (RadioButton) subTecnicaView.findViewById(R.id.radioButtonE);
                    break;
                case EP:
                    radioButton = (RadioButton) subTecnicaView.findViewById(R.id.radioButtonEP);
                    break;
                case NE:
                    radioButton = (RadioButton) subTecnicaView.findViewById(R.id.radioButtonNE);
                    break;
                default:
                    radioButton = (RadioButton) subTecnicaView.findViewById(R.id.radioButtonE);
                    break;
            }
            radioButton.setChecked(true);
            listView.addView(subTecnicaView);
        }

        frameLayout = (FrameLayout) convertView.findViewById(R.id.youtube_fragment);
        cardView = (CardView) convertView.findViewById(R.id.cardVideo);
        cardView.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        recoverLastState();
    }
    @Override
    public void onResume() {
        super.onResume();
        recoverLastState();
    }

    private void recoverLastState() {
        if (youTubePlayerSupportFragment != null){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.remove(youTubePlayerSupportFragment).commit();
        cardView.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        recoverLastState();

        cardView.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);

        youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(frameLayout.getId(), youTubePlayerSupportFragment).commit();

        youTubePlayerSupportFragment.initialize(MyCoachApp.getGoogleApiKey(), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(video.getvideo().substring(video.getvideo().indexOf('=') + 1));
                youTubePlayer.setShowFullscreenButton(false);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getActivity(), "Falha ao reproduzir vídeo", Toast.LENGTH_LONG).show();
            }
        });
    }
}
