package com.example.rens.r_dpractical;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Rens on 19-4-2017.
 */
public class Corner{
    private boolean isOn;
    private ImageView view;
    public Activity activity;
    int id;

    public Corner(final Board board, int _id, final int resId, Activity _activity) {
        activity = _activity;
        view = ((ImageView) activity.findViewById(resId));
        if (view == null) {
            Log.d("ERROR", "Couldn't find corner: " + resId);
        }

        id = _id;
        setOnOff(false);

        DisplayMetrics d = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(d);
        int size = Math.min(d.heightPixels,d.widthPixels) / 25;
        view.setMinimumHeight(size);
        view.setMinimumWidth(size);
        //Log.d("LOG", )
        //view.setMinimumHeight(view.getMinimumHeight() * size);
        //view.setMinimumWidth(view.getMinimumWidth() * size);
        final Corner c = this;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.update(c);
            }
        });
    }

    public void setOnOff(boolean opt){
        isOn = opt;
        int col = (opt)? 0xFFFFFFFF : 0xFF000000;
        view.setBackgroundColor(col);
    }

    public void flip(){
        setOnOff(!isOn);
    }
    public boolean getOn(){
        return isOn;
    }

    public int getId(){
        return id;
    }

    public boolean isNeighbour(Corner c){
        int other = c.getId();

        Log.d("LOG", "id = " + id + " other = " + other);
        if(other == id || other < 1 || other > 25){
            return false;
        }

        // turn ID to x,y coordinates
        int x = ((id-1) % 5);
        int y = ((id-1) / 5);

        int x2 = ((other-1) % 5);
        int y2 = ((other-1) / 5);
        Log.d("LOG", "x = " + x + " x2 = " + x2);
        Log.d("LOG", "y = " + y + " y2 = " + y2);

        if(x-1 == x2 && x-1 >= 0 && y == y2)
           return true;
        else if(x+1 == x2 && x+1 < 5 && y == y2)
            return true;
        else if(y-1 == y2 && y-1 >= 0 && x == x2)
            return true;
        else if(y+1 == y2 && y-2 < 5 && x == x2){
            return true;
        }
        return false;
    }

}
