package com.example.rens.r_dpractical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;

import static com.example.rens.r_dpractical.Shortcuts.*;

public final class MainActivity extends AppCompatActivity {
    final Activity mainActivity = this;

    // x en y zijn hier gedraait: x gaat van boven naar beneden \/, y gaat van links naar rechts -> (normaal andersom)
    private static final Tile[][] level1Tiles =
            {{rn() , rn() , rn() , rn() , rn() , rd() , rn() , rn() , rn() },
            { rn() , bn() , rn() , bn() , rn() , bn() , rd() , bn() , rn() },
            { rd() , rn() , rd() , rn() , rn() , rn() , rd() , rn() , rn() },
            { rn() , bn() , rn() , bn() , rn() , bn() , rn() , bn() , rn() },
            { rd() , rn() , rd() , rn() , rn() , rn() , rd() , rn() , rn() },
            { rn() , bn() , rd() , bn() , rn() , bn() , rd() , bn() , rn() },
            { rd() , rn() , rd() , rn() , rn() , rn() , rd() , rn() , rn() },
            { rn() , bn() , rn() , bn() , rn() , bn() , rn() , bn() , rn() },
            { rn() , rd() , rn() , rn() , rn() , rd() , rn() , rn() , rn() }};
    private static final Level level1 = new Level(level1Tiles, new Pos(0,0), new Pos(8,8));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ((Button)findViewById(R.id.StartButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_game);
                Board board = new Board(mainActivity,level1);
            }
        });
    }
}