package com.example.rens.r_dpractical;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import static com.example.rens.r_dpractical.Shortcuts.*;

public class MainActivity extends Activity {
    final Activity mainActivity = this;

    /******************************************************************************************/
    // PRIMITIEVE LEVEL CREATIE:
    // hier kun je je eigen level maken, net zoals ik een voorbeeld level 1 heb gemaakt (op een dan wel wat primitieve manier)
    // het is verplicht dat je level een vast patroon van 'weg'-stukken en 'blok'-stukken heeft (anders crashed hij door een programma die precies dat checkt)
    // wel mag dit level afwijken van de 5x5 grote! maar dan moet er wel een andere kloppende layout worden geladen

    // (x en y zijn hier gedraait: x gaat van boven naar beneden, y gaat van links naar rechts (overal anders precies andersom))

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

    /******************************************************************************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // hoofdmenu

        // voor wat er gebeurt als je op de startknop drukt:
        ((Button)findViewById(R.id.StartButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                RelativeLayout game = new RelativeLayout(mainActivity);
                game.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                game.addView(new drawLevel(mainActivity,level1));
                */

                setContentView(new drawLevel(mainActivity,level1)); // laad de 5x5 grid
            }
        });

    }
}