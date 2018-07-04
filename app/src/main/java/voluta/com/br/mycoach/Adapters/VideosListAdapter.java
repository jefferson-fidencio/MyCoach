package voluta.com.br.mycoach.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.List;
import voluta.com.br.mycoach.Model.Video;
import voluta.com.br.mycoach.R;

public class VideosListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Video> videos;

    public VideosListAdapter(Context context, List<Video> videos) {
        this.layoutInflater = LayoutInflater.from(context);
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return videos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_item_video_youtube, null);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(videos.get(position).getNome());
        FrameLayout frameLayout = (FrameLayout) convertView.findViewById(R.id.youtube_fragment);
        //HACK adiciono um identificador unico para cada item da listview, para depois adicionar um fragment exclusivo para cada uma
        frameLayout.setId(position+100);
        return convertView;
    }
}

