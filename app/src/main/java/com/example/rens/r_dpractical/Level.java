package com.example.rens.r_dpractical;

import java.util.Vector;

// dit is een level. hiervan kunnen er meerdere van worden opgeslagen zonder problemen, en kunnen aangeroepen worden door 'board' wanneer je ze wilt spelen
public class Level {

    final Tile[][] tiles;
    final Pos size; // niet zozeer een ecte 'positie', maar dat werkt
    private final Pos start;
    private final Pos finish;

    Vector<Pos> last = new Vector<>(); // de vorige posities van de lijn (als cordinaten in tiles)
    Pos current;  // de huidige positie van de lijn (als cordinaten in tiles)

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
        start   = level.start;
        finish  = level.finish;
        tiles   = level.tiles;
        reset(); // rare bug met draaien zorgt dat dit er (tijdelijk) inmoet. reset wel nogsteeds het hele level dus i'm not amused >:(
    }

    // bedoelt voor als je zelf een nieuw level maakt
    public Level(final Tile[][] board, final Pos start, final Pos finish) {
        size    = new Pos(board.length, board[0].length);
        this.start  = start;
        this.finish = finish;
        tiles   = board;
        if (!(start.fitsIn(size) && start.isCrossing() && finish.fitsIn(size) && finish.isCrossing() && wellTiled())) {
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

     // ga vanuit 'current' naar 'pos'
     void moveTo(Pos pos){
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
        // als je klikt op een kruising die 1) dichtbij is, 2) waar je niet al eerder hebt gelopen
        else if(pos.isNeighbour(current) && !((Road)tiles[pos.x][pos.y]).onRoute){
            walkOn(current.inBetween(pos));
            walkOn(pos);

            last.add(current);
            current = pos;

            if(current.equals(finish)){
                // iets hier?
            }
        }
    }

    // zet een wegstuk 'aan'
    protected void walkOn(Pos pos){
        ((Road)tiles[pos.x][pos.y]).onRoute = true;
    }

    // zet een wegstuk 'uit'
    protected void walkOff(Pos pos){
        ((Road)tiles[pos.x][pos.y]).onRoute = false;
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