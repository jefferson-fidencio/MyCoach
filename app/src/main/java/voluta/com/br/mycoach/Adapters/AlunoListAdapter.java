package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.Model.Coach;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;

/**
 * Created by jdfid on 25/05/2017.
 */
public class AlunoListAdapter extends BaseAdapter{

    private Context _context;
    private String[] _opcoes;
    private String[] _opcoes_coach;
    private String[] _opcoes_desc;
    private String[] _opcoes_desc_coach;
    private int[] _opcoes_resources;

    public AlunoListAdapter(Context context){
        _context = context;
        _opcoes = context.getResources().getStringArray(R.array.opcoes_user);
        _opcoes_coach = context.getResources().getStringArray(R.array.opcoes_coach);
        _opcoes_desc = context.getResources().getStringArray(R.array.opcoes_user_desc);
        _opcoes_desc_coach = context.getResources().getStringArray(R.array.opcoes_coach_desc);

        if (MyCoachApp.modality == Modality.swimming)
            _opcoes_resources = new int[]{
                R.mipmap.swim_card_xxxhdpi_new,
                R.mipmap.train_card_xxxhdpi_new,
                R.mipmap.metrics_card_xxxhdpi_new,
                R.mipmap.tec_card_xxxhdpi_new,
                R.mipmap.vid_card_xxxhdpi_new
             };
        else if (MyCoachApp.modality == Modality.running)
            _opcoes_resources = new int[]{
                R.mipmap.running_card_xxxhdpi_new,
                R.mipmap.train_card_running_xxxhdpi_new,
                R.mipmap.metrics_card_running_xxxhdpi_new,
                R.mipmap.tec_card_running_xxxhdpi_new,
                R.mipmap.vid_card_running_xxxhdpi_new
            };
        else if (MyCoachApp.modality == Modality.body_workout)
            _opcoes_resources = new int[]{
                    R.mipmap.bodyworkout_card_xxxhdpi_new,
                    R.mipmap.train_card_bodyworkout_xxxhdpi_new,
                    R.mipmap.metrics_card_bodyworkout_xxxhdpi_new,
                    R.mipmap.tec_card_bodyworkout_xxxhdpi_new,
                    R.mipmap.vid_card_bodyworkout_xxxhdpi_new
            };

    }

    @Override
    public int getCount() {
        return _opcoes.length;
    }

    @Override
    public Object getItem(int position) {
        return _opcoes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(_context);
        convertView = layoutInflater.inflate(R.layout.list_item_cardview_full_image, null);
        TextView textView = (TextView) convertView.findViewById(R.id.cv_title);
        TextView textView2 = (TextView) convertView.findViewById(R.id.cv_desc);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.cv_image);
        if (MyCoachApp.userLogado.getClass().equals(Coach.class))
        {
            textView.setText(_opcoes_coach[position]);
            textView2.setText(_opcoes_desc_coach[position]);
        }
        else
        {
            textView.setText(_opcoes[position]);
            textView2.setText(_opcoes_desc[position]);
        }
        imageView.setImageDrawable(_context.getResources().getDrawable(_opcoes_resources[position]));

        return convertView;
    }
}
