package com.example.rens.r_dpractical;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

// dit is het active spelbord. hier wordt dus een level geladen en die kan alleen hier ook bespeeld worden.
public class Board extends Level{

    private final GameActivity activity; // de huidige activiteit
//    private final ImageView[][] tilesView; // om alles een plaatje te geven

    public Board(final GameActivity current_activity, Level level){
        super(level);
        activity  = current_activity;

    }

    // ga vanuit 'current' naar 'pos'
    public void moveTo(Pos pos){
        pos = pos.toBoardCoord();
        // de positie van het vorige kruispunt
        Pos lastPos = null;
        try{ lastPos = last.get(last.size()-2); }
        catch(Exception e){} // zodat als de vector leeg is lastPos gewoon null is (wat het zou moeten zijn, ipv crashen!! >:( )

        // als je klikt op de kruising waar je vandaan komt
        if(pos.equals(lastPos)){
            if(last.size() > 1) {
                Log.d("UDEBUG", "returning to last: " + pos);
                ((Road)tiles[pos.x][pos.y]).onRoute = false;
                last.remove(last.size() - 1);
                current = lastPos;
            }
        }
        // als je klikt op een kruising die 1) dichtbij is, 2) waar je niet al eerber hebt gelopen
        else if(pos.isNeighbour(current) && !isOnRoute(pos)){

            Log.d("UDEBUG", "moving to pos: " + pos);
            if(!pos.equals(new Pos(0,0))) {
                ((Road)tiles[pos.x][pos.y]).onRoute = true;
                Pos road = current.inBetween(pos);
                ((Road)tiles[road.x][road.y]).onRoute = true;
                last.add(pos);
            }
            current = pos;

            if(current.equals(finish)){
                boolean win = true;
                for(int a = 0; a < size.x; a++){
                    for(int b = 0; b < size.y; b++) {
                        if(!tiles[a][b].isCorrect(tiles,size,a,b))
                            win = false;
                    }
                }
                if(win){
                    Log.d("UDEBUG", "YOU WIN");
                    activity.win();
                }else{
                    Log.d("UDEBUG", "YOU LOOOOOOOSE");
                }
            }
        }
    }

    public Pos getLastPos(){
        return last.lastElement().toDrawCoord();
    }

    public boolean isOnRoute(Pos pos){
        for(Pos p: last){
            if(p.equals(pos)){
                return true;
            }
        }
        return false;
    }
}