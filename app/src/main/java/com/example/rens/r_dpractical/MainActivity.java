package com.example.rens.r_dpractical;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.FrameLayout;

import static com.example.rens.r_dpractical.Shortcuts.*;

public final class MainActivity extends AppCompatActivity {
    final Activity mainActivity = this;

    /******************************************************************************************/
    // PRIMITIEVE LEVEL CREATIE:
    // hier kun je je eigen level maken, net zoals ik een voorbeeld level 1 heb gemaakt (op een dan wel wat primitieve manier)
    // het is verplicht dat je level een vast patroon van 'weg'-stukken en 'blok'-stukken heeft (anders crashed hij door een programma die precies dat checkt)
    // wel mag dit level afwijken van de 5x5 grote! maar dan moet er wel een andere kloppende layout worden geladen

    // (x en y zijn hier gedraait: x gaat van boven naar beneden, y gaat van links naar rechts (overal anders precies andersom))


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // hoofdmenu

        // voor wat er gebeurt als je op de startknop drukt:
        ((Button)findViewById(R.id.HillsButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mainActivity, Hills_menu.class);
                startActivity(intent);

            }
        });

        ((Button)findViewById(R.id.DotsButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mainActivity, Dots_menu.class);
                startActivity(intent);

            }
        });

    }

}

