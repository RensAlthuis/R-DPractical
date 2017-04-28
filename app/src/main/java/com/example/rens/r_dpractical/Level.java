package com.example.rens.r_dpractical;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import static com.example.rens.r_dpractical.Shortcuts.bh;
import static com.example.rens.r_dpractical.Shortcuts.bn;
import static com.example.rens.r_dpractical.Shortcuts.rd;
import static com.example.rens.r_dpractical.Shortcuts.rn;

// dit is een level. hiervan kunnen er meerdere van worden opgeslagen zonder problemen, en kunnen aangeroepen worden door 'board' wanneer je ze wilt spelen
public class Level {

    Tile[][] tiles;
    Pos size; // niet zozeer een ecte 'positie', maar dat werkt
    Pos start;
    Pos finish;

    Vector<Pos> path = new Vector<>(); // de vorige posities van de lijn (als cordinaten in tiles)
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

    public Level(String fileName, String name, Context context){
        try{
            // loadFile
            InputStream is = context.getAssets().open("levels.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String str;
            int x = 0;
            boolean nameFound = false;

            //search through lines
            while ((str = br.readLine()) != null) {

                String[] words = str.split(" ");
                //search for correct level
                if (!nameFound) {
                    if (words[0].equals("name:")) {
                        nameFound = words[1].equals(name);
                        Log.d("UDEBUG", words[1]);
                    }
                } else {

                    if (words[0].equals("start:")) {
                        start = new Pos(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                        current = start;

                    } else if (words[0].equals("end:")) {
                        finish = new Pos(Integer.parseInt(words[1]), Integer.parseInt(words[2]));

                    } else if (words[0].equals("size:")) {
                        size = new Pos(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                        tiles = new Tile[size.x][size.y];

                    } else if (words[0].equals("{")) {
                        for (int i = 0; i < words.length; i++) {
                            Log.d("UDEBUG", "x: " + x + " y: " + i + "= " + words[i]);
                            switch (words[i]) {
                                case "bn":
                                    tiles[x][i - 1] = bn();
                                    break;
                                case "bh":
                                    tiles[x][i - 1] = bh(Integer.parseInt(words[++i]));
                                    break;
                                case "rn":
                                    tiles[x][i - 1] = rn();
                                    break;
                                case "rd":
                                    tiles[x][i - 1] = rd();
                                    break;
                            }

                        }
                        x++;
                    }else if (str.equals("")){
                        return;
                    } else {
                        throw new RuntimeException("ERROR: file is incorrect");
                    }
                }
            }
        } catch (IOException e){
            Log.d("ERROR", e.toString());
        }
    }

    // bedoelt voor als je zelf een nieuw level maakt
    public Level(final Tile[][] board, final Pos start_, final Pos finish_) {
        size    = new Pos(board.length, board[0].length);
        start  = start_;
        current = start;
        finish = finish_;
        tiles   = board;
        if (!(start.fitsIn(size) && start.isCrossing() && finish.fitsIn(size) && finish.isCrossing() && wellTiled())) {
            throw new RuntimeException("ERROR: error creating board");
        }
    }

    // ga vanuit 'current' naar 'pos'
    public void moveTo(Pos pos){
        pos = pos.toBoardCoord();

        // de positie van het vorige kruispunt
        Log.d("UDEBUG", "moving to pos: " + pos);
        Log.d("UDEBUG", "path pos = " + getLastPos());

        Pos lastPos = null;
        try{ lastPos = path.get(path.size()-2); }
        catch(Exception e){} // zodat als de vector leeg is lastPos gewoon null is (wat het zou moeten zijn, ipv crashen!! >:( )

        // als je klikt op de kruising waar je vandaan komt
        if(pos.equals(lastPos)){
            Log.d("UDEBUG", "returning to last: " + pos);

            walkOff(current);
            walkOff(current.inBetween(pos));

            path.remove(path.size()-1);
            current = lastPos;
        }

        // als je klikt op een kruising die 1) dichtbij is, 2) waar je niet al eerber hebt gelopen
        else if(pos.isNeighbour(current) && !((Road)tiles[pos.x][pos.y]).onRoute /*!isOnRoute(pos)*/){
            Log.d("UDEBUG", "this is a neighbour");

            walkOn(pos.inBetween(current));
            walkOn(pos);

            path.add(pos);
            current = pos;

            if(current.equals(finish)){
                if(isCorrect()){
                    Log.d("UDEBUG", "YOU WIN");
                }else{
                    Log.d("UDEBUG", "YOU LOOOOOOOSE");
                }
            }
        }
    }

    public Pos getLastPos(){
        try { return path.lastElement().toDrawCoord();}
        catch (Exception e){return null; }
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