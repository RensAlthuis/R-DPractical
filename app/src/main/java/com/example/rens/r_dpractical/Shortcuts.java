package com.example.rens.r_dpractical;

import android.util.Log;

class Shortcuts {
    static Block bn(){ return new Block(); }
    static BlockHills bh(int neighbours){ return new BlockHills(neighbours); }
    static Road rn(){ return new Road(); }
    static RoadDot rd(){ return new RoadDot(); }
}