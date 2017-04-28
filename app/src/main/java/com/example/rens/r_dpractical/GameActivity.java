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
    final GameActivity gameActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String str = intent.getStringExtra("name");
        final Level level1 = new Level("assets/levels.txt", str,this);
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

                finish();

            }
        });


    }

    public void win(){
        Intent intent = new Intent(this, victory_screen.class);
        startActivity(intent);
        finish();
    }
}