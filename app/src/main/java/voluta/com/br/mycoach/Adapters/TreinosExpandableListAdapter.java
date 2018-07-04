package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import voluta.com.br.mycoach.Model.Treino;
import voluta.com.br.mycoach.Model.TreinoSemanal;
import voluta.com.br.mycoach.R;

public class TreinosExpandableListAdapter extends BaseExpandableListAdapter {

    private TreinoSemanal _treinoSemanal;
    private LayoutInflater _layoutInflater;

    public TreinosExpandableListAdapter(Context context, TreinoSemanal treinoSemanal) {
        _layoutInflater = LayoutInflater.from(context);
        _treinoSemanal = treinoSemanal;
    }

    @Override
    public int getGroupCount() {
        return _treinoSemanal.gettreino().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1; //somente a desc do treino
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _treinoSemanal.gettreino().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _treinoSemanal.gettreino().get(groupPosition).getDescricao();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Treino treino = _treinoSemanal.gettreino().get(groupPosition);
        convertView = _layoutInflater.inflate(R.layout.expandable_list_item_collapsed, null);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textView);
        textViewTitle.setText(treino.getdia());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Treino treino = _treinoSemanal.gettreino().get(groupPosition);
        convertView = _layoutInflater.inflate(R.layout.expandable_list_item_expanded, null);
        TextView textViewDesc = (TextView) convertView.findViewById(R.id.textViewDesc);
        textViewDesc.setText(treino.getDescricao());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
