package com.example.dicegames;

import android.content.Context;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

import kotlin.jvm.internal.Ref;

public class RollingDice extends Activity {
    private MediaController mediaController;
    private MediaPlayer no, rockAndRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);

        no = MediaPlayer.create(this, R.raw.nooooo);
        rockAndRoll = MediaPlayer.create(this, R.raw.rock_and_roll);

        Button toRollingDataButton = findViewById(R.id.to_rolling_data_button);
        toRollingDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
