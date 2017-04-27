/*
package com.example.rens.r_dpractical;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SelectFilterActivity extends Activity implements View.OnClickListener {

    private static final int SWIPE_MIN_DISTANCE = 0;
    private static final int SWIPE_MAX_OFF_PATH = 0;
    private static final int SWIPE_THRESHOLD_VELOCITY = 0;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

    }

    @Override
    public void onClick(View v) {

    }

    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Toast.makeText(SelectFilterActivity.this, "Ble", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}
*/