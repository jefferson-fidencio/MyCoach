package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import voluta.com.br.mycoach.Model.AvaliacaoTecnica;
import voluta.com.br.mycoach.R;

public class AvaliacaoTecnicaListViewAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    List<AvaliacaoTecnica> avaliacaoTecnicaList;

    public AvaliacaoTecnicaListViewAdapter(Context context, List<AvaliacaoTecnica> avaliacaoTecnicas ) {
        layoutInflater = LayoutInflater.from(context);
        avaliacaoTecnicaList = avaliacaoTecnicas;
    }

    @Override
    public int getCount() {
        return avaliacaoTecnicaList.size();
    }

    @Override
    public Object getItem(int position) {
        return avaliacaoTecnicaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_item_double_line_icon, null);
        TextView textView = (TextView) convertView.findViewById(R.id.textViewTitle);
        textView.setText(avaliacaoTecnicaList.get(position).getNome());
        TextView textView2 = (TextView) convertView.findViewById(R.id.textViewDesc);
        textView2.setText("Criado por Coach Bruno Bertelli");
        return convertView;
    }
}
