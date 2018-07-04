package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import voluta.com.br.mycoach.Model.FrequenciaMetragem;
import voluta.com.br.mycoach.R;

public class FrequenciasMetragensListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<FrequenciaMetragem> frequenciasMetragens;
    private Context context;

    public FrequenciasMetragensListAdapter(Context context, List<FrequenciaMetragem> _frequenciasMetragens) {
        layoutInflater = LayoutInflater.from(context);
        this.frequenciasMetragens = _frequenciasMetragens;
        this.context = context;
    }

    /*@Override
    public int getGroupCount() {
        return frequenciasMetragens.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return frequenciasMetragens.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        switch (childPosition){
            case 0:
                return frequenciasMetragens.get(groupPosition).getfrequenciaImg();
            case 1:
                return frequenciasMetragens.get(groupPosition).getmetragemImg();
            default:
                return "";
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return frequenciasMetragens.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }
*/
    @Override
    public int getCount() {
        return frequenciasMetragens.size();
    }

    @Override
    public Object getItem(int position) {
        return frequenciasMetragens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return frequenciasMetragens.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_item_double_line_icon, null);
        TextView textView = (TextView) convertView.findViewById(R.id.textViewTitle);
        textView.setText(frequenciasMetragens.get(position).getNome());
        TextView textView2 = (TextView) convertView.findViewById(R.id.textViewDesc);
        textView2.setText("Criado por Coach Bruno Bertelli");
        return convertView;
    }

    /*@Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.expandable_list_item_collapsed, null);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(frequenciasMetragens.get(groupPosition).getNome());
        return convertView;
    }*/
/*
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.expandable_list_item_expanded_frequencias, null);
        final NetworkImageView imageViewFrequencia = (NetworkImageView) convertView.findViewById(R.id.imageViewFrequencia);
        imageViewFrequencia.setImageUrl(ServerConnection.getInstance(context).getBaseURI() + frequenciasMetragens.get(groupPosition).getfrequenciaImg().substring(2), ServerConnection.getInstance(context).getImageLoader());
        final NetworkImageView imageViewMetragem = (NetworkImageView) convertView.findViewById(R.id.imageViewMetragem);
        imageViewMetragem.setImageUrl(ServerConnection.getInstance(context).getBaseURI() + frequenciasMetragens.get(groupPosition).getmetragemImg().substring(2), ServerConnection.getInstance(context).getImageLoader());
        return convertView;
    }*/

}
