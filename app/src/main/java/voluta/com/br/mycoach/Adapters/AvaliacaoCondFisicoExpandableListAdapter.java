package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import voluta.com.br.mycoach.Model.AvaliacaoCondFisico;
import voluta.com.br.mycoach.R;
import voluta.com.br.mycoach.Services.ServerConnection;

public class AvaliacaoCondFisicoExpandableListAdapter extends BaseExpandableListAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<AvaliacaoCondFisico> avaliacaoCondFisicos;

    public AvaliacaoCondFisicoExpandableListAdapter(Context context, List<AvaliacaoCondFisico> avaliacaoCondFisicos) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.avaliacaoCondFisicos = avaliacaoCondFisicos;
    }

    @Override
    public int getGroupCount() {
        return avaliacaoCondFisicos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return avaliacaoCondFisicos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
                return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return avaliacaoCondFisicos.get(groupPosition).getId();
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
        convertView = layoutInflater.inflate(R.layout.list_item_double_line_icon, null);
        TextView textView = (TextView) convertView.findViewById(R.id.textViewTitle);
        textView.setText(avaliacaoCondFisicos.get(groupPosition).getNome());
        TextView textView2 = (TextView) convertView.findViewById(R.id.textViewDesc);
        textView2.setText("Criado por Coach Bruno Bertelli");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        AvaliacaoCondFisico avaliacaoCondFisico = avaliacaoCondFisicos.get(groupPosition);
        convertView = layoutInflater.inflate(R.layout.expandable_list_item_expanded_avaliacao_cond_fisico, null);
        TextView textViewNome = (TextView) convertView.findViewById(R.id.textViewNome);
        textViewNome.setText(avaliacaoCondFisico.getNome());
        NetworkImageView networkImageView = (NetworkImageView) convertView.findViewById(R.id.imageView);
        networkImageView.setImageUrl(ServerConnection.getInstance(context).getBaseURI() + avaliacaoCondFisico.getcondicionamentoFisicoImg().substring(2),ServerConnection.getInstance(context).getImageLoader());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
