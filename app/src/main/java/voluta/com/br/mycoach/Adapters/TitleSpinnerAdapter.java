package voluta.com.br.mycoach.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import voluta.com.br.mycoach.Enum.Conceito;
import voluta.com.br.mycoach.Enum.Execucao;
import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;

public class TitleSpinnerAdapter extends BaseAdapter {

    private TextView txtTitle;
    private ArrayList spinnerNavItem;
    private Context context;

    public TitleSpinnerAdapter(Context context,
                               ArrayList spinnerNavItem) {
        this.spinnerNavItem = spinnerNavItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return spinnerNavItem.size();
    }

    @Override
    public Object getItem(int index) {
        return spinnerNavItem.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_item_layout, null);
        }

        txtTitle = (TextView) convertView.findViewById(R.id.textView);
        if (spinnerNavItem.get(position).getClass().equals(Modality.class))
            txtTitle.setText(((Modality)spinnerNavItem.get(position)).toString(context));
        else if (spinnerNavItem.get(position).getClass().equals(Conceito.class))
            txtTitle.setText(((Conceito)spinnerNavItem.get(position)).toString());
        else
            txtTitle.setText(((Execucao)spinnerNavItem.get(position)).toString());

        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.spinner_item_layout, null);
            }

        txtTitle = (TextView) convertView.findViewById(R.id.textView);
        if (spinnerNavItem.get(position).getClass().equals(Modality.class))
            txtTitle.setText(((Modality)spinnerNavItem.get(position)).toString(context));
        else if (spinnerNavItem.get(position).getClass().equals(Conceito.class))
            txtTitle.setText(((Conceito)spinnerNavItem.get(position)).toString());
        else
            txtTitle.setText(((Execucao)spinnerNavItem.get(position)).toString());
        return convertView;
    }

}