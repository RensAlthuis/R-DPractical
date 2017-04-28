package com.example.rens.r_dpractical;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Vector;

/**
 * 100% not too shabby
 * Created by Pieps on 4/27/2017.
 */

public class drawPuzzle extends View {

    private final Level lvl;

    final Vector<Line> stored =  new Vector<>();
    private final Pos size;

    private final Pos mouse;
    private final Pos lineEnd;
    private final Pos lineDistance;

    /********************************************/
    // TEKEN VARRIABELEN:

    private boolean mayDraw;

    private final static Paint circlePaint =    new Paint();
    private final static Paint selectedCircle = new Paint();
    private final static Paint linePaint =      new Paint();
    private final static Paint activeLine =     new Paint();

    // de margins voor de zijkanten van het bord
    private final static int X_MARGIN = 40;
    private final static int Y_MARGIN = 100;

    // de grootte van de bollen
    private final static int SIZE_ORB = 40;

    // de grootte van de hitboxen voor iedere corner
    private final static int HITBOX_LEFT  = 10;
    private final static int HITBOX_RIGHT = 70;
    private final static int HITBOX_UP    = 70;
    private final static int HITBOX_DOWN  = 130;

    /********************************************/
    // TEKEN FUNCTIES

    // de kleuren verf
    private void setPaint(){
        selectedCircle.setStyle(Paint.Style.FILL);
        selectedCircle.setColor(Color.WHITE);

        circlePaint.setStyle(Paint.Style.FILL);

        activeLine.setStyle(Paint.Style.STROKE);
        activeLine.setColor(Color.WHITE);
        activeLine.setStrokeWidth(37);

        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(45);
    }

    // de canvas
    private void setCanvas(Canvas canvas){
        canvas.drawColor(Color.BLUE);
        for (int a = 0; a < size.x; a++)
            for (int b = 0; b < size.y; b++) {
                canvas.drawCircle(X_MARGIN + lineDistance.x * a, Y_MARGIN + lineDistance.y * b, SIZE_ORB, circlePaint);
                if (a >= 1)
                    canvas.drawLine(X_MARGIN + lineDistance.x * (a - 1), Y_MARGIN + lineDistance.y * b, X_MARGIN + lineDistance.x * a, Y_MARGIN + lineDistance.y * b, linePaint);
                if (b >= 1)
                    canvas.drawLine(X_MARGIN + lineDistance.x * a, Y_MARGIN + lineDistance.y * (b - 1), X_MARGIN + lineDistance.x * (a), Y_MARGIN + lineDistance.y * b, linePaint);
            }
    }

    /********************************************/


    public drawPuzzle(Context context, Level level) {
        super(context, null);
        this.lvl = level;

        size     = new Pos(lvl.size.x/2+1,lvl.size.y/2+1);
        lineDistance = new Pos();
        mouse    = new Pos();
        lineEnd      = new Pos();

        level.last.add(new Pos(X_MARGIN,Y_MARGIN)); //First position to start
        ((Road)lvl.tiles[2*lvl.current.x][2*lvl.current.y]).onRoute = true;

        setPaint();
    }

    private boolean hasNeighbor(int a, int b) {
        if(a>0)
            if (((Road)lvl.tiles[2*(a-1)][2*b]).onRoute)
                return true;

        if(b>0)
            if (((Road)lvl.tiles[2*a][2*(b-1)]).onRoute)
                return true;

        if(a+1<size.x)
            if (((Road)lvl.tiles[2*(a+1)][b]).onRoute)
                return true;

        if(b+1<size.y)
            if (((Road)lvl.tiles[a][2*(b+1)]).onRoute)
                return true;

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setCanvas(canvas);
        Pos drawline = lvl.last.lastElement();
        lineDistance.x = getWidth()/(size.x-1) - X_MARGIN/2;
        lineDistance.y = getHeight()/(size.y-1) - Y_MARGIN/2;

        if(Math.abs(drawline.y - mouse.y) > Math.abs(drawline.x - mouse.x)){
            lineEnd.x = drawline.x;
            lineEnd.y = mouse.y;
        } else {
            lineEnd.x = mouse.x;
            lineEnd.y = drawline.y;
        }

        //For these I want to be on top, I draw them again(incredibly inefficient IK )
        for (int a = 0; a < size.x; a++)
            for (int b = 0; b < size.y; b++) {
                if (((Road)lvl.tiles[2*a][2*b]).onRoute)
                    canvas.drawCircle(X_MARGIN + lineDistance.x * a, Y_MARGIN + lineDistance.y * b, SIZE_ORB-6, selectedCircle); //This circle has been selected!
            }

        if(mayDraw) canvas.drawLine(drawline.x, drawline.y, lineEnd.x, lineEnd.y, activeLine);

        invalidate(); // optioneel!!! met dit aan herverst hij de lijn iedere tijdseenheid ipv alleen als ingedrukt
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mayDraw = true; //We are currently drawing the line!
            mouse.x = (int)event.getX();
            mouse.y = (int)event.getY();

            for (int a = 0; a < size.x ; a++)
                for (int b = 0; b < size.y ; b++)
                    if(inHitbox(lineEnd,a,b) && hasNeighbor(a,b) && !((Road)lvl.tiles[2*a][2*b]).onRoute) {
                            ((Road)lvl.tiles[2*a][2*b]).onRoute = true;

                            stored.add(new Line(lvl.last.lastElement().x, lvl.last.lastElement().y, X_MARGIN+lineDistance.x * a, Y_MARGIN+lineDistance.y * b));
                            lvl.last.add(new Pos(X_MARGIN + lineDistance.x * a,  Y_MARGIN + lineDistance.y * b)); //We want to draw from here now

                            //if(skippedSecond)
                        }

            invalidate(); // tekent de nieuwe lijn
        }

        if (event.getAction() == MotionEvent.ACTION_UP){
            lineEnd.x = lvl.last.lastElement().x;
            lineEnd.y = lvl.last.lastElement().y;
            mayDraw = false;
        }

        return true;
    }

    private boolean inHitbox(Pos lineEnd, int a, int b){
        return lineEnd.x>(HITBOX_LEFT + lineDistance.x * a) && lineEnd.x<(HITBOX_RIGHT + lineDistance.x * a) && lineEnd.y>(HITBOX_UP + lineDistance.y * b) && lineEnd.y<(HITBOX_DOWN + lineDistance.y *b);
    }
}