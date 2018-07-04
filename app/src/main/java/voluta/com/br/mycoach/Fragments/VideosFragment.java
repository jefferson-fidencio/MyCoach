package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import voluta.com.br.mycoach.Adapters.VideosListAdapter;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.Model.Video;
import voluta.com.br.mycoach.R;

public class VideosFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private View lastVideoViewShown;
    private int lastPosition;
    private YouTubePlayerSupportFragment youTubePlayerSupportFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.title_activity_fragment_meus_videos));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meus_videos_fragment, null);
        loadList(view);
        return view;
    }

    private void loadList(View view) {

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new VideosListAdapter(getActivity(), ((Aluno)MyCoachApp.alunoVisualizado).getVideos()));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //HACK por definicao, no adapter cada view da lista tem um id setado de "posicao + 100"

        //altera ultima view para estado original e remove fragment de video
        recoverLastState();

        final Video video = (Video) parent.getItemAtPosition(position);
        CardView cardView = (CardView) view.findViewById(R.id.cardVideo);
        cardView.setVisibility(View.GONE);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(position + 100);
        frameLayout.setVisibility(View.VISIBLE);

        youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(position + 100, youTubePlayerSupportFragment).commit();

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

        //salva ultimo layout editado para restaurar ao estado original antes de abrir proximo video
        lastVideoViewShown = view;
        lastPosition = position;
    }

    private void recoverLastState() {

        if (lastVideoViewShown != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.remove(youTubePlayerSupportFragment).commit();

            CardView cardView = (CardView) lastVideoViewShown.findViewById(R.id.cardVideo);
            cardView.setVisibility(View.VISIBLE);
            FrameLayout frameLayout = (FrameLayout) lastVideoViewShown.findViewById(lastPosition + 100);
            frameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
        {
            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            fab.setImageResource(R.drawable.ic_note_add_white_24dp);
            fab.setOnClickListener(this);
            fab.show();
        }
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(getActivity().findViewById(R.id.main_fragment_container).getId(),new AddVideoFragment())
                .addToBackStack(null)
                .commit();
    }
}
