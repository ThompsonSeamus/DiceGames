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
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private int sides, cheat;
    private EditText sidesEditText;
    private SeekBar cheatBar;
    private ImageView diceImage;
    private TextView ninetyNine, zero, cheatPercentText;

    //on Create||Main method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing data getters
        sidesEditText = (EditText) findViewById(R.id.editTextNumber);
        cheatBar = (SeekBar) findViewById(R.id.cheat_bar);
        cheatBar.setVisibility(View.VISIBLE);

        //initializing the hidden/viewed items
        diceImage = (ImageView) findViewById(R.id.imageView);
        diceImage.setVisibility(View.INVISIBLE);
        ninetyNine = (TextView) findViewById(R.id.ninetynine_percent);
        ninetyNine.setVisibility(View.VISIBLE);
        zero = (TextView) findViewById(R.id.zero_percent);
        zero.setVisibility(View.VISIBLE);
        cheatPercentText = (TextView) findViewById(R.id.cheat_percent);
        cheatPercentText.setVisibility(View.VISIBLE);

        //on click listeners
        sidesEditText.setOnEditorActionListener(new DoneOnEditorActionListener());//private class for soft Keyboard
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

        //switch activities button
        Button toRollingActivityButton = findViewById(R.id.to_rolling_button);
        toRollingActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity();
            }
        });
    }

    //button to hide cheat bar
    public void hideOrViewCheatBar(View view){
        if(cheatBar.getVisibility() == View.INVISIBLE){
            cheatBar.setVisibility(View.VISIBLE);
            cheatPercentText.setVisibility(View.VISIBLE);
            zero.setVisibility(View.VISIBLE);
            ninetyNine.setVisibility(View.VISIBLE);
            diceImage.setVisibility(View.INVISIBLE);
        }
        else{
            cheatBar.setVisibility(View.INVISIBLE);
            cheatPercentText.setVisibility(View.INVISIBLE);
            zero.setVisibility(View.INVISIBLE);
            ninetyNine.setVisibility(View.INVISIBLE);
            diceImage.setVisibility(View.VISIBLE);
        }

    }

    //switching activities
    public void switchActivity(){
        try {
            sides = Integer.parseInt(sidesEditText.getText().toString());
        }
        catch(NumberFormatException e){
            Toast.makeText(MainActivity.this, "NumberFormatException: Sides has been set to 6", Toast.LENGTH_SHORT).show();
            sides = 6;
        }
        Intent rollIntent = new Intent(this, RollingDice.class);
        rollIntent.putExtra("sides", sides);
        rollIntent.putExtra("cheat", cheat);
        startActivity(rollIntent);
    }

    //necessary override forDoneOnEditorActionListener private class
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
                try {
                    sides = Integer.parseInt(sidesEditText.getText().toString());
                }
                catch(NumberFormatException e){
                    Toast.makeText(MainActivity.this, "NumberFormatException: Sides has been set to 6", Toast.LENGTH_SHORT).show();
                    sides = 6;
                }
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }
}