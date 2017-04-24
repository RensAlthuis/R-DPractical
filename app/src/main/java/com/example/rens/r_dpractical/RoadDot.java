package com.example.rens.r_dpractical;

public class RoadDot extends Road {

    @Override
    public boolean isCorrect(Tile[][] tiles, Pos size, int x, int y) {
        return onRoute;
    }
}