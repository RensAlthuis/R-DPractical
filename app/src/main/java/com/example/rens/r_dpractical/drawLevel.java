package com.example.rens.r_dpractical;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * 100% not too shabby
 * Created by Pieps on 4/27/2017.
 */

public class drawLevel extends View {
    // TEKEN VARRIABELEN:

    private boolean mayDraw;

    // de margins voor de zijkanten van het bord
    private final static int X_MARGIN = 40;
    private final static int Y_MARGIN = 100;

    // de grootte van de bollen
    private final static int SIZE_ORB = 40;

    // de grootte van de hitboxen voor iedere corner (nu 140x140 groot)
    private final static int HITBOX_LEFT  = 10;
    private final static int HITBOX_RIGHT = 70;
    private final static int HITBOX_UP    = 70;
    private final static int HITBOX_DOWN  = 130;

    // VERF
    private final static Paint circlePaint    = new Paint();
    private final static Paint selectedCircle = new Paint();
    private final static Paint linePaint      = new Paint();
    private final static Paint activeLine     = new Paint();
    private final static Paint roadDotPaint   = new Paint();

    private static void setPaint(){
        //-------------
        circlePaint.setStyle(Paint.Style.FILL);
        //-------------
        selectedCircle.setStyle(Paint.Style.FILL);
        selectedCircle.setColor(Color.WHITE);
        //-------------
        linePaint.setStyle(Paint.Style.STROKE);
        activeLine.setColor(Color.BLACK);
        linePaint.setStrokeWidth(45);
        //-------------
        activeLine.setStyle(Paint.Style.STROKE);
        activeLine.setColor(Color.WHITE);
        activeLine.setStrokeWidth(37);
        //-------------
        roadDotPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        roadDotPaint.setColor(Color.RED);
        //-------------
    }

    // CANVAS
    private void setCanvas(Canvas canvas){
        canvas.drawColor(Color.WHITE); //achtergrondkleur
        for (int a=0; a < size.x; a++)
            for (int b=0; b < size.y; b++) {
                canvas.drawCircle(X_MARGIN + lineDistance.x * a, Y_MARGIN + lineDistance.y * b, SIZE_ORB, circlePaint);
                if (a >= 1)
                    canvas.drawLine(toScreenX(a - 1), toScreenY(b), toScreenX(a), toScreenY(b), linePaint);
                if (b >= 1)
                    canvas.drawLine(toScreenX(a), toScreenY(b - 1), toScreenX(a), toScreenY(b), linePaint);
            }
    }

    private void setSpecial(Canvas canvas){
        for (int a = 0; a < lvl.size.x; a++)
            for (int b = 0; b < lvl.size.y; b++) {

                if(lvl.tiles[a][b] instanceof RoadDot) { // ROADDOT
                    float px  = toScreenX(a/2);
                    float py  = toScreenY(b/2);

                    if (a%2==1 || b%2==1) {
                        if (a % 2 == 0) {
                            py /= 2;
                            py += toScreenY(b/2+1)/2;
                        } else {
                            px /= 2;
                            px += toScreenX(a/2+1)/2;
                        }
                    }
                    canvas.drawCircle(px,py, 20, roadDotPaint);
                }

                else if(lvl.tiles[a][b] instanceof BlockHills){ // BLOCKHILL
                    float px = (toScreenX(a/2) + toScreenX(a/2+1)) / 2.0f - hill1.getWidth()/2;
                    float py = (toScreenY(b/2) + toScreenY(b/2+1)) / 2.0f - hill1.getHeight()/2;

                    switch(((BlockHills)lvl.tiles[a][b]).getNeighbours()){
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
                }

                // andere speciale tiles?
            }
    }

    /*********************************************************************************************/
    /*********************************************************************************************/
    // GAME VARRIABLENEN:

    Level lvl;

    private final Pos size;
    private final Pos lineDistance = new Pos();

    private static Pos mouse = new Pos();
    private static Pos lineEnd = new Pos();

    private final Bitmap hill1 = BitmapFactory.decodeResource(getResources(), R.drawable.single_hill);
    private final Bitmap hill2 = BitmapFactory.decodeResource(getResources(), R.drawable.double_hill);
    private final Bitmap hill3 = BitmapFactory.decodeResource(getResources(), R.drawable.triple_hill);

    /*********************************************************************************************/
    /*********************************************************************************************/

    public drawLevel(Context context, Level level) {
        super(context, null);

        lvl = level;
        lvl.path.add(lvl.start);
        lvl.walkOn(lvl.start);

        size = new Pos((lvl.size.x+1)/2,(lvl.size.y+1)/2);

        setPaint();
    }

    /*********************************************************************************************/

    @Override
    protected void onDraw(Canvas canvas) {
        lineDistance.x = getWidth()/(size.x-1) - X_MARGIN/2;
        lineDistance.y = getHeight()/(size.y-1) - Y_MARGIN/2;

        setCanvas(canvas);

        Pos lastLine = lvl.getLastPos();
        mouseToStraightLine(lastLine);

        // redrawing corners
        for (int a = 0; a < size.x; a++)
            for (int b = 0; b < size.y; b++)
                if (((Road)lvl.tiles[2*a][2*b]).onRoute)
                    canvas.drawCircle(toScreenX(a), toScreenY(b), SIZE_ORB-6, selectedCircle); //This circle has been selected!

        // the current line
        if(mayDraw) canvas.drawLine(toScreenX(lastLine.x), toScreenY(lastLine.y), lineEnd.x, lineEnd.y, activeLine);

        // the old lines
        for(int i=0; i<(lvl.path.size()-1) ; i++)
            canvas.drawLine( toScreenX(lvl.path.get(i).x/2), toScreenY(lvl.path.get(i).y/2), toScreenX(lvl.path.get(i+1).x/2), toScreenY(lvl.path.get(i+1).y/2), activeLine);

        setSpecial(canvas);

        invalidate();
    }

    // maakt de lijn die getrokken wordt recht t.o.v. vanuit de laatste lijn
    private void mouseToStraightLine(Pos lastLine){
        if(Math.abs( toScreenY(lastLine.y) - mouse.y) > Math.abs(toScreenX(lastLine.x) - mouse.x)){
            lineEnd.x = toScreenX(lastLine.x);
            lineEnd.y = mouse.y;
        } else {
            lineEnd.x = mouse.x;
            lineEnd.y = toScreenY(lastLine.y);
        }
    }

    public int toScreenX(int x){
        return X_MARGIN + lineDistance.x * x;
    }

    public int toScreenY(int y){
        return Y_MARGIN + lineDistance.y * y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mayDraw = true; //We are currently drawing the line!

            mouse.x = (int)event.getX();
            mouse.y = (int)event.getY();

            for (int a = 0; a < size.x; a++)
                for (int b = 0; b < size.y; b++)
                    if(inHitbox(a,b))
                        lvl.moveTo(new Pos(a,b));

            invalidate();
        }

        if (event.getAction() == MotionEvent.ACTION_UP)
            mayDraw = false;

        return true;
    }

    private boolean inHitbox(int a, int b){
        return lineEnd.x>(HITBOX_LEFT + lineDistance.x * a) && lineEnd.x<(HITBOX_RIGHT + lineDistance.x * a) && lineEnd.y>(HITBOX_UP + lineDistance.y * b) && lineEnd.y<(HITBOX_DOWN + lineDistance.y *b);
    }
}