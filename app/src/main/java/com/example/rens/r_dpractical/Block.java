package com.example.rens.r_dpractical;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by rens on 4/20/17.
 */

public class Block {
    int id;
    Activity activity;
    ImageView view;

    public Block(int _id, int resId, Activity _activity){
        id = _id;
        activity = _activity;

        view = (ImageView) activity.findViewById(resId);
        if(view == null){
            Log.d("ERROR", "couldn't find view for block" + id);
        }

        DisplayMetrics d = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(d);
        int size = Math.min(d.heightPixels,d.widthPixels) / 5;
        view.setMinimumHeight(size);
        view.setMinimumWidth(size);
    }
}
