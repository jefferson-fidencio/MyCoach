package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.zip.Inflater;

import voluta.com.br.mycoach.Model.SubTecnica;
import voluta.com.br.mycoach.Model.Tecnica;
import voluta.com.br.mycoach.R;

/**
 * Created by jdfid on 06/12/2017.
 */

public class SubTecnicaListAdapter extends BaseAdapter implements View.OnClickListener {

    private Tecnica tecnica;
    private Context context;

    public SubTecnicaListAdapter(Context context, Tecnica tecnica) {
        this.context = context;
        this.tecnica = tecnica;
    }

    @Override
    public int getCount() {
        return tecnica.getSubTecnicas() != null ? tecnica.getSubTecnicas().size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return tecnica.getSubTecnicas() != null ? tecnica.getSubTecnicas().get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return tecnica.getSubTecnicas() != null ? tecnica.getSubTecnicas().get(position).getId() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (tecnica.getSubTecnicas()!= null){
            SubTecnica subTecnica = tecnica.getSubTecnicas().get(position);
            View subTecnicaView = LayoutInflater.from(context).inflate(R.layout.list_item_sub_tecnica_removable, null);
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
            ImageButton imageButton = (ImageButton) subTecnicaView.findViewById(R.id.imageButton);
            imageButton.setTag(position); //stores the position in the adapter of the item
            imageButton.setOnClickListener(this);
            return subTecnicaView;
        }
        else
            return null;
    }

    @Override
    public void onClick(View v) {
        // removes item
        int position = (int) v.getTag();
        tecnica.getSubTecnicas().remove(position);
        this.notifyDataSetChanged();
    }
}
