package voluta.com.br.mycoach.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import voluta.com.br.mycoach.Model.Treino;
import voluta.com.br.mycoach.Model.TreinoSemanal;
import voluta.com.br.mycoach.R;

public class TreinosListAdapter extends BaseAdapter {

    private TreinoSemanal _treinoSemanal;
    private LayoutInflater _layoutInflater;

    @Override
    public int getCount() {
        return _treinoSemanal.gettreino().size();
    }

    @Override
    public Object getItem(int position) {
        return _treinoSemanal.gettreino().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Treino treino = _treinoSemanal.gettreino().get(position);
        convertView = _layoutInflater.inflate(R.layout.list_item_title_desc, null);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
        TextView textViewDesc = (TextView) convertView.findViewById(R.id.textViewDesc);
        textViewTitle.setText(treino.getdia());
        textViewDesc.setText(treino.getDescricao());
        return convertView;
    }
}
