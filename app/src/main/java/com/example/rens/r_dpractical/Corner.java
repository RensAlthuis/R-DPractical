package com.example.rens.r_dpractical;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.TouchDelegate;
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

    /**
     * Constructor
     * @param board containing board
     * @param _id is number of corner on the board, for easy reference
     * @param resId resource id, to get the corresponding ImageView
     * @param _activity Containing activity
     */
    public Corner(final Board board, int _id, final int resId, Activity _activity) {
        activity = _activity;
        view = ((ImageView) activity.findViewById(resId));
        if (view == null) {
            Log.d("ERROR", "Couldn't find corner: " + resId);
        }

        id = _id;

        //initialize to offState
        setOnOff(false);

        //Scaling logic
        DisplayMetrics d = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(d);
        // size is the screenWidth / some int
        //TODO get rid of magic number and replace with some better logic allowing for different dimesions of boards
        int size = Math.min(d.heightPixels,d.widthPixels) / 25;
        view.setMinimumHeight(size);
        view.setMinimumWidth(size);


        //TODO this doesn't seem to work, but was intended to increase the hitbox of the corner
        final View parent = (View) view.getParent();
        parent.post(new Runnable() {
            @Override
            public void run() {
                Rect delegateArea = new Rect();
                view.getHitRect(delegateArea);
                delegateArea.top -= 100;
                delegateArea.bottom += 100;
                delegateArea.left -= 100;
                delegateArea.right += 100;
                parent.setTouchDelegate(new TouchDelegate(delegateArea, view));

            }
        });

        //onclick logic
        final Corner c = this;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.update(c);
            }
        });

    }

    /**
     * Set the Corner to its on/off state
     * @param opt true = on, false = off
     */
    public void setOnOff(boolean opt){
        isOn = opt;
        int col = (opt)? 0xFFFFFFFF : 0xFF000000;
        view.setBackgroundColor(col);
    }

    /**
     * Switch the corner's on/off state
     */
    public void flip(){
        setOnOff(!isOn);
    }

    /**
     * @return the corner's on/off state
     */
    public boolean getOn(){
        return isOn;
    }

    /**
     * @return the corner's id
     */
    public int getId(){
        return id;
    }

    /**
     * check if the corner is neighbouring another corner
     * @param c the other corner to check against
     * @return true if neighbour, false if not neighbour
     */
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
