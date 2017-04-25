package com.example.rens.r_dpractical;

import java.util.Vector;

// dit is een level. hiervan kunnen er meerdere van worden opgeslagen zonder problemen, en kunnen aangeroepen worden door 'board' wanneer je ze wilt spelen
public class Level {

    final Tile[][] tiles;
    final Pos size; // niet zozeer een ecte 'positie', maar dat werkt
    final Pos finish;
    Vector<Pos> last = new Vector<>(); // de vorige posities van de lijn
    Pos current;  // de huidige positie van de lijn

    public boolean Sovable(){
        throw new RuntimeException("Solvable() moeten nog worden gemaakt :P");
    }

    // is de lijn zo getrokken zodat alle tiles kloppen?
    public boolean isCorrect(){
        if(!current.equals(finish)) return false;
        for(int i=0; i<size.x ; i++)
            for(int j=0; j<size.y ; j++)
                if(!tiles[i][j].isCorrect(tiles,size,i,j))
                    return false;
        return true;
    }

    // deze wordt alleen geimplimenteerd door 'Board', en past hopelijk het level zelf niet aan
    Level(final Level level){
        size    = level.size;
        current = level.current;
        finish  = level.finish;
        tiles   = level.tiles;
        reset(); // rare bug met draaien zorgt dat dit er (tijdelijk) inmoet. reset wel nogsteeds het hele level dus i'm not amused >:(
    }

    // bedoelt voor als je zelf een nieuw level maakt
    public Level(final Tile[][] board, final Pos start, final Pos end) {
        size    = new Pos(board.length, board[0].length);
        current = start;
        finish  = end;
        tiles   = board;
        if (!(start.fitsIn(size) && start.isCrossing() && end.fitsIn(size) && end.isCrossing() && wellTiled())) {
            throw new RuntimeException("ERROR: error creating board");
        }
    }

    // haalt alle (onzichtbare) lijnstukken weg. Om een of andere reden is dit nodig voor wanneer je het scherm draait
    private void reset(){
        for(int i=0; i<size.x ; i++)
            for(int j=0; j<size.y ; j++)
                if(i%2==0 || j%2==0)
                    ((Road)tiles[i][j]).onRoute = false;
    }

    /**
     * Dit garandeerd dat het level er als volgt uitziet:
     * -  -  -  -  -  ..
     * - [ ] - [ ] -  ..
     * -  -  -  -  -  ..
     * - [ ] - [ ] -  ..
     * -  -  -  -  -  ..
     * .. .. .. .. .. ..
     *
     * (met ' - ' een weg, '[ ]' een blok en '..' als een soort van 'etc')
     * zo kunnen een stuk meer dingen worden aangenomen, en hoeft maar enkel de positie gecheckt te worden om te weten over wat voor soort tile dit gaat.
     **/
    private boolean wellTiled(){
        for(int i=0; i<size.x ; i++)
            for(int j=0; j<size.y ; j++){
                if(i%2==0 || j%2==0){
                    if(!(tiles[i][j] instanceof Road)) return false;
                }
                else
                    if(!(tiles[i][j] instanceof Block)) return false;
            }
        return true;
    }
}