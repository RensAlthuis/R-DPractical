package com.example.rens.r_dpractical;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Hills_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hills_menu);

        final Activity activity = this;
        for(int i = 1; i < 8; i++){
            final String str = "Hill" + i;
            final int resID = getResources().getIdentifier(str,"id",getPackageName());
            Button b = (Button)findViewById(resID);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(activity, GameActivity.class);
                    intent.putExtra("name", str);
                    startActivity(intent);
                }
            });
        }

        ((ImageButton)findViewById(R.id.BackButtonHills)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
