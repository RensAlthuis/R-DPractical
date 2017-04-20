package com.example.rens.r_dpractical;

import android.app.Activity;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Rens on 19-4-2017.
 */
public class Board {
    Corner[] corners;
    HashMap<String, Side> sides;
    Activity activity;

    public Board(Activity _activity){
        activity = _activity;

        //Corners
        corners = new Corner[25];
        for(int i = 0; i < 25; i++) {
            String str = "corner" + (i+1);
            int resId = activity.getResources().getIdentifier(str, "id", activity.getPackageName());
            Log.d("LOG", "asdfasdfasdf " + resId);
            corners[i] = new Corner(i+1, resId, activity);
        }

        //Boxes

        //Sides
        sides = new HashMap<String, Side>();
        for(int i = 0; i < 5; i++){
            //horizontals
            for(int i2 = 1; i2 < 5; i2++) {
                String str = "side" + (i * 5 + i2) + "_" + (i * 5 + i2 + 1);
                int resId = activity.getResources().getIdentifier(str, "id", activity.getPackageName());
                sides.put(str, new Side(resId, activity));
            }

            for(int i2 = 0; i2 < 4; i2++){
                String str = "side" + (i2 * 5 + i + 1) + "_" + (i2 * 5 + i + 6);
                int resId = activity.getResources().getIdentifier(str, "id", activity.getPackageName());
                sides.put(str, new Side(resId, activity));
            }
        }
    }
}
