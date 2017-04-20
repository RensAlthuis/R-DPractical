package com.example.rens.r_dpractical;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Rens on 19-4-2017.
 */
public class Side {
    private boolean isOn;
    private ImageView view;
    public Activity activity;
    private String id;
    private boolean isVert;

    public Side(boolean _isVert, String str, int resId, Activity _activity){
        id = str;
        activity = _activity;
        isVert = _isVert;
        view = (ImageView) activity.findViewById(resId);
        if(view == null){
            Log.d("ERROR", "couldn't find view for side" + id);
        }

        DisplayMetrics d = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(d);

        int s1 = Math.min(d.heightPixels,d.widthPixels) / 5;
        int s2 = s1 / 5;
        view.setMinimumHeight((isVert) ? s1 : s2);
        view.setMinimumWidth((isVert) ? s2 : s1);
    }

    public void setOnOff(boolean opt){
        isOn = opt;
        int col = (opt)? 0xFFFFFFFF : 0xFF000000;
        Log.d("LOG", "newCol " + col + " " + opt);
        view.setBackgroundColor(col);
    }
}
