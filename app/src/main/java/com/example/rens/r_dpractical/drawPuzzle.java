package com.example.rens.r_dpractical;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private Paint roadDotPaint = new Paint();

    private Bitmap hill1;
    private Bitmap hill2;
    private Bitmap hill3;


    Board board;
    //Size of field, not necessary for this demonstration but perhaps later on ehh
    private int sizex = 5;
    private int sizey = 5;

    public drawPuzzle(Context context, Board mBoard)
    {
        this(context, null, mBoard);


    }

    public drawPuzzle(Context context, AttributeSet attrs, Board mBoard) {
        super(context, attrs);
        selectedCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        selectedCircle.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        activeLine.setStyle(Paint.Style.STROKE);
        roadDotPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        roadDotPaint.setColor(Color.RED);
        activeLine.setColor(Color.WHITE);
        activeLine.setStrokeWidth(37);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(45);

        board = mBoard;
        hill1 = BitmapFactory.decodeResource(getResources(),R.drawable.single_hill);
        hill2 = BitmapFactory.decodeResource(getResources(),R.drawable.double_hill);
        hill3 = BitmapFactory.decodeResource(getResources(),R.drawable.triple_hill);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        distancex = getWidth() / 4 - 20;
        distancey = getHeight() / 4 - 50;
        Pos drawline = board.getLastPos();
        int startX = toScreenX(drawline.x);
        int startY = toScreenY(drawline.y);

        if(Math.abs( startY - mouseY) > Math.abs(startX - mouseX)){
            endX = startX;
            endY = mouseY;
            invalidate();
        } else {
            endX = mouseX;
            endY = startY;
            invalidate();
        }

        //clearing out sides
        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {

                if (a >= 1)
                    canvas.drawLine(toScreenX(a - 1), toScreenY(b), toScreenX(a), toScreenY(b), linePaint);
                if (b >= 1)
                    canvas.drawLine(toScreenX(a), toScreenY(b - 1), toScreenX(a), toScreenY(b), linePaint);
            }
        }

        //redrawing corners.
        //For these I want to be on top, I draw them again(incredibly inefficient IK )
        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {
                canvas.drawCircle(toScreenX(a), toScreenY(b), 30, circlePaint);
                if (board.isOnRoute(new Pos(a, b).toBoardCoord())) {
                    canvas.drawCircle(toScreenX(a), toScreenY(b), 25, selectedCircle); //This circle has been selected!
                }
            }
        }

        // the current line
        if(mayDraw)
            canvas.drawLine(startX, startY, endX, endY, activeLine);

        // the old lines
        int prevX = 0;
        int prevY = 0;
        for(Pos temp: board.last) {
            temp = temp.toDrawCoord();
            canvas.drawLine( toScreenX(prevX), toScreenY(prevY), toScreenX(temp.x), toScreenY(temp.y), activeLine);
            prevX = temp.x;
            prevY = temp.y;
        }

        for (int a = 0; a < board.size.x; a++) {
            for (int b = 0; b < board.size.y; b++) {
                //teken alle road dots
                if(board.tiles[a][b] instanceof RoadDot) {

                    Pos pos = new Pos(a, b);

                    if (pos.isCrossing()) {
                        pos = pos.toDrawCoord();
                        canvas.drawRect((float) toScreenX(pos.x) - 10, (float) toScreenY(pos.y) - 10, (float) toScreenX(pos.x) + 10, (float) toScreenY(pos.y) + 10, roadDotPaint);
                    }else{

                        float px = 0;
                        float py = 0;

                        //horizontaal vs verticaal check
                        if(pos.x % 2 == 0){
                            pos = pos.toDrawCoord();
                            px = toScreenX(pos.x);
                            py = (toScreenY(pos.y) + toScreenY(pos.y+1)) / 2.0f;
                        }else{
                            pos = pos.toDrawCoord();
                            px = (toScreenX(pos.x) + toScreenX(pos.x+1)) / 2.0f;
                            py = toScreenY(pos.y);
                        }
                        canvas.drawRect(px - 10, py - 10, px + 10, py + 10, roadDotPaint);
                    }
                }
                else if(board.tiles[a][b] instanceof BlockHills){

                    Pos p = new Pos(a,b).toDrawCoord();

                    float px = (toScreenX(p.x) + toScreenX(p.x+1)) / 2.0f;
                    float py = (toScreenY(p.y) + toScreenY(p.y+1)) / 2.0f;
                    px -= hill1.getWidth()/2;
                    py -= hill1.getHeight()/2;
                    BlockHills block = (BlockHills) board.tiles[a][b];
                    Log.d("UDEBUG", "SDKLJFLSDJF");
                    switch(block.getNeighbours()){
                        case 1:

                            canvas.drawBitmap(hill1,px,py,null);
                            break;
                        case 2:
                            canvas.drawBitmap(hill2,px,py,null);
                            break;
                        case 3:
                            canvas.drawBitmap(hill3,px,py,null);
                            break;
                    }
                }else{
                }
            }
        }


    }

    public int toScreenX(int x){
       return 40 + distancex * x;
    }

    public int toScreenY(int y){
        return 100 + distancey * y;
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

    public void setBoard(Board mBoard)
    {
        board = mBoard;
    }
}