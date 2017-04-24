package com.example.rens.r_dpractical;

abstract class Tile {

    /**
     * Gaat uit van:
     * VOOR EEN KRUISING:
     *  _   _ _   _
     * | 1   2   3 |
     * | 4   5   6 |
     * |_7  _8_  9_|
     *
     * en,
     * VOOR EEN WEG:
     *
     * van links naar rechts = -1
     * van boven naar onder = -2
     *
     * met 0 als het een blok is.
     *
     * hiermee kun je gemakkelijk verschillende eigenschappen van een weg weten.
     * ( bijv: type>0 => het is een kruising, type>0 && type%3==0 => het zit aan de linkerzijde)
     */
    public int type(Pos size, Pos pos) { return type(size,pos.x,pos.y); }
    public int type(Pos size, int x, int y){
        int type = 0;
        if(x%2==0 && y%2==0){
            if(x==size.x) type += 2;
            else if(x!=0) type += 1;
            if(y==size.y) type += 6;
            else if(y!=0) type += 3;
            type++;
        }
        else if(x%2==0 || y%2==0){
            if(x%2==1)    type -= 1;
            else          type -= 2;
        }
        return type;
    }

    public abstract boolean isCorrect(Tile[][] tiles, Pos size, int x, int y);
}