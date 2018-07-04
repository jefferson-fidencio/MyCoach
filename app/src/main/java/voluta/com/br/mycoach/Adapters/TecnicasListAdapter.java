package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import voluta.com.br.mycoach.Model.Tecnica;
import voluta.com.br.mycoach.R;

public class TecnicasListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Tecnica> tecnicas;

    public TecnicasListAdapter(Context context, List<Tecnica> tecnicas) {
        layoutInflater = LayoutInflater.from(context);
        this.tecnicas = tecnicas == null ? new ArrayList<Tecnica>() : tecnicas;
    }

    @Override
    public int getCount() {
        return tecnicas.size();
    }

    @Override
    public Object getItem(int position) {
        return tecnicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tecnicas.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_item_double_line_icon, null);
        TextView textView = (TextView) convertView.findViewById(R.id.textViewTitle);
        textView.setText(tecnicas.get(position).getNome());
        TextView textView2 = (TextView) convertView.findViewById(R.id.textViewDesc);
        textView2.setText("Conceito: " + tecnicas.get(position).getConceito());
        return convertView;
    }

}
