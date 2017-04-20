package com.example.rens.r_dpractical;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Rens on 19-4-2017.
 */
public class Side {
    private boolean isOn;
    private ImageView view;
    public Activity activity;

    public Side(int resId, Activity _activity){
        activity = _activity;
        view = (ImageView) activity.findViewById(resId);
    }

    public void setOnOff(boolean opt){
        isOn = opt;
        int col = (opt)? 0xFFFFFFFF : 0xFF000000;
        Log.d("LOG", "newCol " + col + " " + opt);
        view.setBackgroundColor(col);
    }
}
