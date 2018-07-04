package voluta.com.br.mycoach.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import voluta.com.br.mycoach.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jdfid on 30/11/2017.
 */

public class ImageFragment extends Fragment implements View.OnClickListener {

    public ImageView imageView;
    private TextView textViewClick;
    private static final int PICK_IMAGE_REQUEST = 111;
    public Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, null);
        textViewClick = (TextView) view.findViewById(R.id.textViewSelectPhoto);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textViewClick.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Selecione a imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                imageView.setImageTintMode(null);
                imageView.setImageURI(data.getData());
                textViewClick.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
