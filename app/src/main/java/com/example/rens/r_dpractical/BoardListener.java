package com.example.rens.r_dpractical;

import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class BoardListener implements View.OnTouchListener {

    private final GridLayout gridLayout;
    private final ImageView[][] tilesView;

    public BoardListener(GridLayout gridLayout, ImageView[][] tilesView){
        this.gridLayout = gridLayout;
        this.tilesView = tilesView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }
}