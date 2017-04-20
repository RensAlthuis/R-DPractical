package com.example.rens.r_dpractical;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Rens on 19-4-2017.
 */
public class Corner {
    private boolean isOn;
    private ImageView view;
    public Activity activity;
    int id;

    public Corner(int _id, final int resId, Activity _activity) {
        activity = _activity;
        view = ((ImageView) activity.findViewById(resId));
        if (view == null) {
            Log.d("ERROR", "Couldn't find corner: " + resId);
        }

        id = _id;

        Log.d("LOG", "initialize");
        setOnOff(false);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnOff(!isOn);
            }
        });
    }

    public void setOnOff(boolean opt){
        isOn = opt;
        int col = (opt)? 0xFFFFFFFF : 0xFF000000;
        Log.d("LOG", "newCol " + col + " " + opt);
        view.setBackgroundColor(col);
    }

    public boolean isNeighbour(int i){
        if(i != id || i < 1 || i > 25){
            return false;
        }
        if((id - 1) == i){

        }
        if((id + 1) == i){

        }
        if((id - 5) == i){

        }
        if((id + 5) == i){
            return true;
        }

        return false;
    }

}
