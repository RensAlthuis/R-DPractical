package com.example.rens.r_dpractical;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import static com.example.rens.r_dpractical.Shortcuts.*;

public final class GameActivity extends Activity {
    final Activity gameActivity = this;

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

        final Level level1 = new Level(level1Tiles, new Pos(0,0), new Pos(8,8));
        final Board board = new Board(this, level1);
        final drawPuzzle canvas = new drawPuzzle(this, board);

        setContentView(R.layout.activity_game);
        FrameLayout game_frame = (FrameLayout) findViewById(R.id.game_frame);

        canvas.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        game_frame.addView(canvas);

        ImageButton backButton = (ImageButton)findViewById(R.id.back_button);
        backButton.bringToFront();

        // voor wat er gebeurt als je op de terugknop drukt:
        ((ImageButton)findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(gameActivity, MainActivity.class);
                startActivity(intent);

            }
        });


    }





}