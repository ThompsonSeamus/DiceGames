package com.example.dicegames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;

public class MainActivity extends AppCompatActivity {

    private int sides, cheat;
    private EditText sidesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sidesEditText = (EditText) findViewById(R.id.editTextNumber);
        sidesEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sides = Integer.parseInt(sidesEditText.getText().toString());
            }
        });

        Button toRollingActivityButton = findViewById(R.id.to_rolling_button);
        toRollingActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity();
            }
        });
    }

    //passing a JSON to get an object
    public void switchActivity(){
        Intent rollIntent = new Intent(this, RollingDice.class);
        rollIntent.putExtra("sides", sides);
        rollIntent.putExtra("cheat", cheat);
        startActivity(rollIntent);
    }



}