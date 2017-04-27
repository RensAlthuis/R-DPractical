package com.example.rens.r_dpractical;

import android.app.Activity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

// dit is het active spelbord. hier wordt dus een level geladen en die kan alleen hier ook bespeeld worden.
public class Board extends Level{
    final Board board = this;

    private final Activity activity; // de huidige activiteit
    private final ImageView[][] tilesView; // om alles een plaatje te geven
    private final GridLayout boardView;

    public Board(final Activity current_activity, Level level){
        super(level);
        activity  = current_activity;
        tilesView = tilesView();
        boardView = ((GridLayout)activity.findViewById(activity.getResources().getIdentifier("board","id", activity.getPackageName())));

        // voor de eerste stap op het bord
        walkOn(current);
    }

    // zoekt voor alle tiles de bijbehorende ImageView, en past die aan aan de hand van de speciale tiles (tussen de '*')
    private ImageView[][] tilesView(){
        final ImageView[][] views = new ImageView[size.x][size.y];
        for (int i=0; i<size.x ; i++)
            for (int j=0; j<size.y ; j++){
                final Pos pos = new Pos(i,j);

                // hiermee wordt automatisch de juiste ID gemaakt, waar straks de bijbebehorende ImageView bij wordt gezocht
                String str;
                if(pos.isCrossing())   str = "corner" + pos;
                else if(pos.isBlock()) str = "block"  + pos;
                else                   str = "side"   + pos;

                // hier worden de verschillende ImageViews gezocht en daarna verzameld binnen een matrix
                views[i][j] = ((ImageView)activity.findViewById(activity.getResources().getIdentifier(str, "id", activity.getPackageName())));

                /********************************************/
                // AANPASSINGEN:

                // voorbeeld 1: alle kruispunten worden klikbaar
                if(pos.isCrossing()) {
                    views[i][j].setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return dragTo(pos,event);
                        }
                    });
                }

                // voorbeeld 2: overal waar een 'roaddot' zou staan krijgt het bord een mooi plaatje
                if(tiles[i][j] instanceof RoadDot){
                    views[i][j].setImageResource(R.drawable.dot);
                }
                /********************************************/

            }
        return views;
    }

    private boolean dragTo(Pos pos, MotionEvent event){
        board.moveTo(pos); // stuurt de positie van die kruising door de functie moveTo, in de hoop dat de lijn naar pos beweegt
        if(event.)
    }

    // ga vanuit 'current' naar 'pos'
    private void moveTo(Pos pos){
        // de positie van het vorige kruispunt
        Pos lastPos = null;
        try{ lastPos = last.lastElement(); }
        catch(Exception e){} // zodat als de vector leeg is lastPos gewoon null is (wat het zou moeten zijn, ipv crashen!! >:( )

        // als je klikt op de kruising waar je vandaan komt
        if(pos.equals(lastPos)){
            walkOff(current);
            walkOff(current.inBetween(lastPos));

            last.remove(last.size()-1);
            current = lastPos;
        }
        // als je klikt op een kruising die 1) dichtbij is, 2) waar je niet al eerber hebt gelopen
        else if(pos.isNeighbour(current) && !((Road)tiles[pos.x][pos.y]).onRoute){
            walkOn(current.inBetween(pos));
            walkOn(pos);

            last.add(current);
            current = pos;

            if(current.equals(finish)){
                // iets hier?
            }
        }
        else if(pos.sharesAxis(current) && !((Road)tiles[pos.x][pos.y]).onRoute){

        }
    }

    // zet een wegstuk 'aan'
    private void walkOn(Pos pos){
        ((Road)tiles[pos.x][pos.y]).onRoute = true;
        tilesView[pos.x][pos.y].setBackgroundColor(0xFFFFFFFF);
    }

    // zet een wegstuk 'uit'
    private void walkOff(Pos pos){
        ((Road)tiles[pos.x][pos.y]).onRoute = false;
        tilesView[pos.x][pos.y].setBackgroundColor(0xFF000000);
    }
}