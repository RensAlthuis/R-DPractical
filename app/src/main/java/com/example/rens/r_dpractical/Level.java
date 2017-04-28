package com.example.rens.r_dpractical;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import static com.example.rens.r_dpractical.Shortcuts.*;

// dit is een level. hiervan kunnen er meerdere van worden opgeslagen zonder problemen, en kunnen aangeroepen worden door 'board' wanneer je ze wilt spelen
public class Level {

    Tile[][] tiles;
    Pos size; // niet zozeer een ecte 'positie', maar dat werkt
    Pos finish;
    public Vector<Pos> last = new Vector<>(); // de vorige posities van de lijn
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
        last.add(current);
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
                        current = new Pos(Integer.parseInt(words[1]), Integer.parseInt(words[2]));

                    } else if (words[0].equals("end:")) {
                        finish = new Pos(Integer.parseInt(words[1]), Integer.parseInt(words[2]));

                    } else if (words[0].equals("size:")) {
                        size = new Pos(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                        tiles = new Tile[size.x][size.y];

                    } else if (words[0].equals("{")) {
                        for (int i = 0; i < words.length - 1; i++) {
                            Log.d("UDEBUG", "x: " + x + " y: " + i + "= " + words[i]);
                            if (words[i].equals("bn")) {
                                tiles[x][i-1] = bn();
                            } else if (words[i].equals("bh")) {
                                tiles[x][i-1] = bh(Integer.parseInt(words[++i]));

                            } else if (words[i].equals("rn")) {
                                tiles[x][i-1] = rn();
                            } else if (words[i].equals("rd")) {
                                tiles[x][i-1] = rd();
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