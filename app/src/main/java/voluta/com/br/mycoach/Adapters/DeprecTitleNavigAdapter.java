package voluta.com.br.mycoach.Adapters;

import java.util.ArrayList;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.MyCoachApp;
import voluta.com.br.mycoach.R;

public class DeprecTitleNavigAdapter extends BaseAdapter {

    private TextView txtTitle;
    private ArrayList spinnerNavItem;
    private Context context;

    public DeprecTitleNavigAdapter(Context context,
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
            convertView = mInflater.inflate(R.layout.deprec_spinner_layout, null);
            if (MyCoachApp.modality == Modality.swimming)
            {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.colorAppPrimary));
            }
            else if (MyCoachApp.modality == Modality.running)
            {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.colorAppRunningPrimary));
            }
        }
        txtTitle = (TextView) convertView.findViewById(R.id.textView);
        txtTitle.setText(((Modality)spinnerNavItem.get(position)).toString(context));
        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.deprec_spinner_layout, null);
            }
            if (MyCoachApp.modality == Modality.swimming) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.colorAppPrimary));
            } else if (MyCoachApp.modality == Modality.running) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.colorAppRunningPrimary));
            }

        txtTitle = (TextView) convertView.findViewById(R.id.textView);
        txtTitle.setText(((Modality)spinnerNavItem.get(position)).toString(context));
        return convertView;
    }

}