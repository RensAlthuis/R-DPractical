package com.example.rens.r_dpractical;

import android.app.Activity;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Rens on 19-4-2017.
 */
public class Board {

    private Corner[] corners;
    private Corner current;
    private HashMap<String, Side> sides;
    private Activity activity;
    private Block[] boxes;

    /**
     * Constructor
     * @param _activity Parent activity, (this)
     */
    public Board(Activity _activity){
        activity = _activity;

        //Corners, creates the corners with id's 1 through 25
        corners = new Corner[25];
        for(int i = 0; i < 25; i++) {
            String str = "corner" + (i+1);
            int resId = activity.getResources().getIdentifier(str, "id", activity.getPackageName());
            corners[i] = new Corner(this, i+1, resId, activity);
        }
        current = corners[24];
        current.flip();

        //Boxes creates the blocks with id's 1 through 16
        boxes = new Block[16];
        for(int i = 0; i<16; i++){
            String str = "block" + (i+1);
            int resId = activity.getResources().getIdentifier(str, "id", activity.getPackageName());
            boxes[i] = new Block(i+1, resId, activity);
        }

        //Sides, creates the blocks with id's as string in formate sideX_Y where X and Y are the connecting corners
        //its stored in a hashmap so if you know the corners it's easy to find the corresponding side
        sides = new HashMap<String, Side>();
        for(int i = 0; i < 5; i++){
            //horizontals
            for(int i2 = 1; i2 < 5; i2++) {
                String str = "side" + (i * 5 + i2) + "_" + (i * 5 + i2 + 1);
                int resId = activity.getResources().getIdentifier(str, "id", activity.getPackageName());
                sides.put(str, new Side(false, str, resId, activity));
            }

            //verticals
            for(int i2 = 0; i2 < 4; i2++){
                String str = "side" + (i2 * 5 + i + 1) + "_" + (i2 * 5 + i + 6);
                int resId = activity.getResources().getIdentifier(str, "id", activity.getPackageName());
                sides.put(str, new Side(true, str, resId, activity));
            }
        }
    }


    /**
     * Board update function, this is a sort off callback function called by Corner.OnClick()
     * @param clicked, the Corner object that was clicked
     */
    public void update(Corner clicked){

        Log.d("LOG", "Click!!");
        //make sure user clicked a nonactive neighbour.
        //TODO maybe change this to a List of sorts. Stack maybe?
        if (clicked.isNeighbour(current) && clicked.getOn() != true){

            //turn corner on
            clicked.flip();

            //turn connecting side on
            String str = "side" + Math.min(clicked.getId(), current.getId()) + "_" + Math.max(clicked.getId(), current.getId());
            sides.get(str).setOnOff(true);

            //update the last clicked
            current = clicked;
        }
    }
}
