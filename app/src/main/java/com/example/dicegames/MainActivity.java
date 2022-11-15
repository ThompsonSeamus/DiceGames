package com.example.dicegames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private int sides, cheat;
    private EditText sidesEditText;
    private SeekBar cheatBar;

    //on Create/Main method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing data getters
        sidesEditText = (EditText) findViewById(R.id.editTextNumber);
        cheatBar = (SeekBar) findViewById(R.id.cheat_bar);

        //on click listeners
        sidesEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sides = Integer.parseInt(sidesEditText.getText().toString());
                }
                catch(NumberFormatException e){
                    Toast.makeText(MainActivity.this, "NumberFormatException: Sides has been set to 6", Toast.LENGTH_SHORT).show();
                    sides = 6;
                }
            }
        });
        sidesEditText.setOnEditorActionListener(new DoneOnEditorActionListener());

        cheatBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(cheatBar.getProgress() == 10){cheat = 99;}
                else{cheat = cheatBar.getProgress() * 10;}
                Toast.makeText(MainActivity.this, "Cheat% set to: " + cheat + "%", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        /**
         * This code is designed to close the soft keyboard when you press the done button
         * (if the done button isn't pressed by the user then it doesn't take the int into the intent)
         * */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }
}