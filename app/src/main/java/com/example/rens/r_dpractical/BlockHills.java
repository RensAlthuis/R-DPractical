package com.example.rens.r_dpractical;

public class BlockHills extends Block {
    
    private final int neighbours;
    
    public BlockHills(int neighbours){
        this.neighbours = neighbours;
    }

    public int getNeighbours(){
        return neighbours;
    }
    
    @Override
    public boolean isCorrect(Tile[][] tiles, Pos size, int x, int y) {
        int current = 0;
        if(((Road) tiles[x + 1][y]).onRoute) current++;
        if(((Road)tiles[x-1][y]).onRoute) current++;
        if(((Road)tiles[x][y+1]).onRoute) current++;
        if(((Road)tiles[x][y-1]).onRoute) current++;
        return current == neighbours;
    }
}
