package com.example.rens.r_dpractical;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

// dit is het active spelbord. hier wordt dus een level geladen en die wordt dan hier bespeeld.
public class Board extends Level{

    private final Activity activity; // de huidige activiteit
    private final ImageView[][] tilesView; // om alles een plaatje te geven

    public Board(final Activity current_activity, Level level){
        super(level);
        activity  = current_activity;
        tilesView = tilesView();

        walkOn(current);
    }

    private ImageView[][] tilesView(){
        ImageView[][] views = new ImageView[size.x][size.y];
        for (int i=0; i<size.x ; i++)
            for (int j=0; j<size.y ; j++){
                final Pos pos = new Pos(i,j);

                String str;
                if(pos.isCrossing())   str = "corner" + pos;
                else if(pos.isBlock()) str = "block"  + pos;
                else                   str = "side"   + pos;

                views[i][j] = ((ImageView)activity.findViewById(activity.getResources().getIdentifier(str, "id", activity.getPackageName())));

                if(pos.isCrossing()){
                    final Board board = this;
                    views[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            board.moveTo(pos);
                        }
                    });
                }

                if(tiles[i][j] instanceof RoadDot){
                    views[i][j].setImageResource(R.drawable.dot);
                }
            }
        return views;
    }

    private void moveTo(Pos pos){
        Pos lastPos = null;
        try{ lastPos = last.lastElement(); }
        catch(Exception e){} // zodat als de vector leeg is lastPos gewoon null is (wat het zou moeten zijn, ipv crashen!!)

        if(pos.equals(lastPos)){
            walkOff(current);
            walkOff(current.inBetween(lastPos));

            last.remove(last.size()-1);
            current = lastPos;
        }
        else if(pos.isNeighbour(current) && !((Road)tiles[pos.x][pos.y]).onRoute){
            walkOn(current.inBetween(pos));
            walkOn(pos);

            last.add(current);
            current = pos;
        }
    }

    private void walkOn(Pos pos){
        ((Road)tiles[pos.x][pos.y]).onRoute = true;
        tilesView[pos.x][pos.y].setBackgroundColor(0xFFFFFFFF);
    }

    private void walkOff(Pos pos){
        ((Road)tiles[pos.x][pos.y]).onRoute = false;
        tilesView[pos.x][pos.y].setBackgroundColor(0xFF000000);
    }
}
