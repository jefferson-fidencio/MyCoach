package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.ServerConnection;

/**
 * Created by jdfid on 17/11/2017.
 */

public class AlunosListAdapter extends BaseAdapter {

    private Context context;
    private List<Aluno> alunosList;
    public AlunosListAdapter(Context context, List<Aluno> alunosList) {
        this.context = context;
        this.alunosList = alunosList;
    }

    @Override
    public int getCount() {
        return alunosList.size();
    }

    @Override
    public Object getItem(int position) {
        return alunosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunosList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_aluno, null);
        TextView textViewNome = (TextView) v.findViewById(R.id.textViewTitle);
        textViewNome.setText(alunosList.get(position).getNome());
        TextView textViewDesc = (TextView) v.findViewById(R.id.textViewDesc);
        final ImageView imageViewAluno = (ImageView) v.findViewById(R.id.imageViewAlunoContainer).findViewById(R.id.imageView2);
        if (alunosList.get(position).getImg() == null || alunosList.get(position).getImg().equals(""))
        {
            imageViewAluno.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_perm_identity_black_48dp));
            imageViewAluno.setImageTintList(ColorStateList.valueOf(Color.LTGRAY));
        }
        else
        {
            ServerConnection.getInstance(context).getImageLoader().get(ServerConnection.getInstance(context).getBaseURI() +  alunosList.get(position).getImg().substring(2), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    imageViewAluno.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    imageViewAluno.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_perm_identity_black_48dp));
                    imageViewAluno.setImageTintList(ColorStateList.valueOf(Color.LTGRAY));
                }
            }, 72, 72);
        }
        if (alunosList.get(position).getIdModalidade() == 0) //swimming
        {
            textViewDesc.setText("Natação");
        }
        else if (alunosList.get(position).getIdModalidade() == 1) //running
        {
            textViewDesc.setText("Corrida");
        }
        else if (alunosList.get(position).getIdModalidade() == 2) //bodyworkout
        {
            textViewDesc.setText("Musculação");
        }

        return v;
    }
}
