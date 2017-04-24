package com.example.rens.r_dpractical;

public class Pos {
    public int x;
    public int y;
    
    public Pos(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public boolean isBlock(){
        return (x%2==1 && y%2==1);
    }
    
    public boolean isCrossing(){
        return (x%2==0 && y%2==0);
    }

    public boolean smallerThan(Pos pos){
        return x < pos.x && y < pos.y;
    }
    public boolean biggerThan(Pos pos){
        return pos.smallerThan(this);
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof Pos)) return false;
        return x == ((Pos)object).x && y == ((Pos)object).y;
    }
    
    public boolean fitsIn(Pos size){
        return smallerThan(size) && x>=0 && y>=0;
    }

    @Override
    public String toString(){
        return "_" + x + "_" + y;
    }

    public Pos inBetween(Pos otherPos){
        return new Pos((otherPos.x+x)/2,(otherPos.y+y)/2);
    }

    public boolean isNeighbour(Pos pos){
        return (pos.x==x && (y-2==pos.y || pos.y==y+2)) || (pos.y==y && (x-2==pos.x || pos.x==x+2));
    }

    public Pos clone(){
        return new Pos(this.x,this.y);
    }
}
