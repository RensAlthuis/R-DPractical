package com.example.rens.r_dpractical;

/**
 * oi
 * Created by Pieps on 4/28/2017.
 */

public class Line extends Pos {
    public int endx;
    public int endy;

    public Line(int x, int y, int ex, int ey)
    {
        super(x,y);
        this.endx = ex;
        this.endy = ey;
    }
}
