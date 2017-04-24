package com.example.rens.r_dpractical;

public class Shortcuts {
    public static Block bn(){
        return new Block();
    }
    public static BlockHills bh(int neighbours){
        return new BlockHills(neighbours);
    }
    public static Road rn(){
        return new Road();
    }
    public static RoadDot rd(){
        return new RoadDot();
    }
}
