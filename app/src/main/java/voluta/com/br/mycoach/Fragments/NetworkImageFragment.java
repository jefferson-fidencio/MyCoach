package voluta.com.br.mycoach.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import voluta.com.br.mycoach.Adapters.FrequencyDetailAdapter;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.ServerConnection;

/**
 * Created by jdfid on 27/10/2017.
 */

public class NetworkImageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        String imgSrc = getArguments().getString(FrequencyDetailAdapter.KEY_NETWORK_IMAGE_SRC);
        View view = inflater.inflate(R.layout.imagefragment, null);
        NetworkImageView networkImageView = (NetworkImageView) view.findViewById(R.id.networkImageView);
        networkImageView.setImageUrl(ServerConnection.getInstance(getContext()).getBaseURI() + imgSrc, ServerConnection.getInstance(getContext()).getImageLoader());
        return view;
    }
}
