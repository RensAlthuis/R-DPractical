package com.example.rens.r_dpractical;

public class Road extends Tile {
    public boolean onRoute;

    @Override
    public boolean isCorrect(Tile[][] tiles, Pos size, int x, int y) {
        return true;
    }
}