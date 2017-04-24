package com.example.rens.r_dpractical;

import java.util.Vector;

// dit is een level. hiervan kunnen er meerdere van worden opgeslagen zonder problemen, en kunnen aangeroepen worden als 'board' wanneer je ze wilt spelen
public class Level {

    final Tile[][] tiles;
    final Pos size; // niet zozeer een ecte 'positie', maar dat werkt
    final Pos finish;
    Vector<Pos> last = new Vector<>(); // de vorige posities van de lijn
    Pos current;  // de huidige positie van de lijn

    public boolean isCorrect(){
        if(!current.equals(finish)) return false;
        for(int i=0; i<size.x ; i++)
            for(int j=0; j<size.y ; j++)
                if(!tiles[i][j].isCorrect(tiles,size,i,j))
                    return false;
        return true;
    }

    Level(Level level){
        size    = level.size;
        current = level.current;
        finish  = level.finish;
        tiles   = level.tiles;
    }

    public Level(final Tile[][] board, final Pos start, final Pos end) {
        size    = new Pos(board.length, board[0].length);
        current = start;
        finish  = end;
        tiles   = board;
        if (!(start.fitsIn(size) && start.isCrossing() && end.fitsIn(size) && end.isCrossing() && wellTiled())) {
            throw new RuntimeException("ERROR: error creating board");
        }
    }

    private boolean wellTiled(){
        for(int i=0; i<size.x ; i++)
            for(int j=0; j<size.y ; j++){
                if(i%2==1 && j%2==1){
                    if(!(tiles[i][j] instanceof Block)) return false;
                }
                else
                    if(!(tiles[i][j] instanceof Road)) return false;
            }
        return true;
    }
}
