package com.example.dicegames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;

public class MainActivity extends AppCompatActivity {

    private MediaController mediaController;
    private MediaPlayer no, rockAndRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        no = no.create(this, R.raw.nooooo);
        rockAndRoll = rockAndRoll.create(this, R.raw.rock_and_roll);
    }
}