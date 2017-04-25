package com.example.rens.r_dpractical;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Vector;

/**
 * 100% not too shabby
 * Created by Pieps on 4/27/2017.
 */

public class drawPuzzle extends View {
    private int numColumns, numRows;

    private static int distancex;
    private static int distancey;
    private static int mouseX;
    private static int mouseY;
    private static int endX;
    private static int endY;
    private static boolean mayDraw; //May we draw the line yet????
    private int cellWidth, cellHeight;
    private Paint circlePaint = new Paint();
    private Paint selectedCircle = new Paint();
    private Paint linePaint = new Paint();
    private Paint activeLine = new Paint();


    Board board;
    //Size of field, not necessary for this demonstration but perhaps later on ehh
    private int sizex = 5;
    private int sizey = 5;

    public drawPuzzle(Context context, Board board)
    {
        this(context, null, board);


    }

    public drawPuzzle(Context context, AttributeSet attrs, Board _board) {
        super(context, attrs);
        selectedCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        selectedCircle.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        activeLine.setStyle(Paint.Style.STROKE);
        activeLine.setColor(Color.WHITE);
        activeLine.setStrokeWidth(37);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(45);
        board = _board;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        distancex = getWidth() / 4 - 20;
        distancey = getHeight() / 4 - 50;
        Pos drawline = board.getLastPos();
        int startX =  40 + distancex * drawline.x;
        int startY =  100 + distancey * drawline.y;

        if(Math.abs( startY - mouseY) > Math.abs(startX - mouseX)){
            endX = startX;
            endY = mouseY;
            invalidate();
        } else {
            endX = mouseX;
            endY = startY;
            invalidate();
        }


        for (int a = 0; a < 5; a++)
            for (int b = 0; b < 5; b++) {

                if (a >= 1)
                    canvas.drawLine(40 + distancex * (a - 1), 100 + distancey * b, 40 + distancex * a, 100 + distancey * b, linePaint);
                if (b >= 1)
                    canvas.drawLine(40 + distancex * a, 100 + distancey * (b - 1), 40 + distancex * (a), 100 + distancey * b, linePaint);
            }

        //For these I want to be on top, I draw them again(incredibly inefficient IK )
        for (int a = 0; a < 5; a++)
            for (int b = 0; b < 5; b++) {
                canvas.drawCircle(40 + distancex * a, 100 + distancey * b, 30, circlePaint);
                if (board.isOnRoute(new Pos(a, b).toBoardCoord())){
                    canvas.drawCircle(40 + distancex * a, 100 + distancey * b, 25, selectedCircle); //This circle has been selected!
                }
            }

        if(mayDraw)
            canvas.drawLine(startX, startY, endX, endY, activeLine);

        int prevX = 0;
        int prevY = 0;
        for(Pos temp: board.last) {
            temp = temp.toDrawCoord();
            canvas.drawLine( (40 + distancex * prevX), (100 + distancey * prevY), (40 + distancex * temp.x), (100 + distancey *  temp.y), activeLine);
            prevX = temp.x;
            prevY = temp.y;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mayDraw = true; //We are currently drawing the line!
            mouseX = (int)event.getX();
            mouseY = (int)event.getY();
            for (int a = 0; a < 5; a++)
                for (int b = 0; b < 5; b++) {
                    if(endX>(10 +distancex * a) && endX<(70 +distancex * a) && endY>(70+distancey * b) && endY<(130+distancey *b)) {
                        //hit a corner, now ask board if it's supposed to be turned on or off
                        board.moveTo(new Pos(a,b));

                    }
                    //cellChecked[column][row] = !cellChecked[column][row];
                    invalidate();
                }

        }
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            mayDraw = false;
        }

        return true;
    }
}