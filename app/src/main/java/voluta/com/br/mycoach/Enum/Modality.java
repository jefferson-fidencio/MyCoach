package voluta.com.br.mycoach.Enum;

import android.content.Context;

import voluta.com.br.mycoach.R;

/**
 * Created by jdfid on 06/11/2017.
 */

public enum Modality {
    swimming(R.string.title_swimming, 0),
    running(R.string.title_running,1),
    body_workout(R.string.title_body_workout,2);

    private int resource_title_modality;
    private int modalityPosition;
    Modality(int resource_title_modality, int position) {
        this.resource_title_modality = resource_title_modality;
        this.modalityPosition = position;
    }

    public String toString(Context context) {
        return context.getResources().getString(resource_title_modality);
    }
}
