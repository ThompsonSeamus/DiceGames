package com.example.dicegames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private int sides, cheat;
    private EditText sidesEditText;
    private SeekBar cheatBar;
    //diceImage Things
    private ImageView diceImage;
    private Button d12, d10p, d20l, d8, d10, d20, d4, d6n, d6d, d8c;
    //cheatBar things
    private TextView ninetyNine, zero, cheatPercentText;


    //on Create||Main method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing data getters
        sidesEditText = findViewById(R.id.editTextNumber);
        cheatBar =  findViewById(R.id.cheat_bar);
        cheatBar.setVisibility(View.INVISIBLE);

        //initializing the hidden/viewed cheat bar items
        diceImage = findViewById(R.id.imageView);
        diceImage.setVisibility(View.VISIBLE);
        ninetyNine = findViewById(R.id.ninetynine_percent);
        ninetyNine.setVisibility(View.INVISIBLE);
        zero = findViewById(R.id.zero_percent);
        zero.setVisibility(View.INVISIBLE);
        cheatPercentText = findViewById(R.id.cheat_percent);
        cheatPercentText.setVisibility(View.INVISIBLE);

        //adding the creation dice buttons
        d12 = findViewById(R.id.d_12_die_button);
        d10p = findViewById(R.id.d_10_die_button_percentile);
        d20l = findViewById(R.id.d_20_die_button_long);
        d8 = findViewById(R.id.d_8_die_button);
        d10 = findViewById(R.id.d_10_die_button);
        d20 = findViewById(R.id.d_20_die_button);
        d4 = findViewById(R.id.d_4_die_button);
        d6n = findViewById(R.id.d_6_die_button_nums);
        d6d = findViewById(R.id.d_6_die_button_dots);
        d8c = findViewById(R.id.d_8_die_button_closer);

        //setting All dice buttons to VISIBLE
        d12.setVisibility(View.VISIBLE);
        d10p.setVisibility(View.VISIBLE);
        d20l.setVisibility(View.VISIBLE);
        d8.setVisibility(View.VISIBLE);
        d10.setVisibility(View.VISIBLE);
        d20.setVisibility(View.VISIBLE);
        d4.setVisibility(View.VISIBLE);
        d6n.setVisibility(View.VISIBLE);
        d6d.setVisibility(View.VISIBLE);
        d8c.setVisibility(View.VISIBLE);


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
        toRollingActivityButton.setOnClickListener(view -> switchActivity());

        //set pre-made dice buttons
            //d12
        d12.setOnClickListener(view -> {
            sides = 12;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 0;}
            switchActivity();
        });
            //d10p
        d10p.setOnClickListener(view -> {
            sides = 10;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 10;}
            switchActivity();
        });
            //d20l
        d20l.setOnClickListener(view -> {
            sides = 20;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 10;}
            switchActivity();
        });
            //d8
        d8.setOnClickListener(view -> {
            sides = 8;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 0;}
            switchActivity();
        });
            //d10
        d10.setOnClickListener(view -> {
            sides = 10;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 0;}
            switchActivity();
        });
            //d20
        d20.setOnClickListener(view -> {
            sides = 20;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 0;}
            switchActivity();
        });
            //d4
        d4.setOnClickListener(view -> {
            sides = 4;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 0;}
            switchActivity();
        });
            //d6n
        d6n.setOnClickListener(view -> {
            sides = 6;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 0;}
            switchActivity();
        });
            //d6d
        d6d.setOnClickListener(view -> {
            sides = 6;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 10;}
            switchActivity();
        });
            //d8c
        d8c.setOnClickListener(view -> {
            sides = 8;
            if(cheatBar.getProgress() == 10){cheat = 99;}
            else if (cheatBar.getProgress() > 0){cheat = cheatBar.getProgress() * 10;}
            else{cheat = 10;}
            switchActivity();
        });
    }

    //button to hide cheat bar
    public void hideOrViewCheatBar(View view){
        if(cheatBar.getVisibility() == View.INVISIBLE){
            //cheatBar things
            cheatBar.setVisibility(View.VISIBLE);
            cheatPercentText.setVisibility(View.VISIBLE);
            zero.setVisibility(View.VISIBLE);
            ninetyNine.setVisibility(View.VISIBLE);

            //diceImage things
            diceImage.setVisibility(View.INVISIBLE);
            d12.setVisibility(View.INVISIBLE);
            d10p.setVisibility(View.INVISIBLE);
            d20l.setVisibility(View.INVISIBLE);
            d8.setVisibility(View.INVISIBLE);
            d10.setVisibility(View.INVISIBLE);
            d20.setVisibility(View.INVISIBLE);
            d4.setVisibility(View.INVISIBLE);
            d6n.setVisibility(View.INVISIBLE);
            d6d.setVisibility(View.INVISIBLE);
            d8c.setVisibility(View.INVISIBLE);
        }
        else{
            //cheatBar things
            cheatBar.setVisibility(View.INVISIBLE);
            cheatPercentText.setVisibility(View.INVISIBLE);
            zero.setVisibility(View.INVISIBLE);
            ninetyNine.setVisibility(View.INVISIBLE);

            //diceImage things
            diceImage.setVisibility(View.VISIBLE);
            d12.setVisibility(View.VISIBLE);
            d10p.setVisibility(View.VISIBLE);
            d20l.setVisibility(View.VISIBLE);
            d8.setVisibility(View.VISIBLE);
            d10.setVisibility(View.VISIBLE);
            d20.setVisibility(View.VISIBLE);
            d4.setVisibility(View.VISIBLE);
            d6n.setVisibility(View.VISIBLE);
            d6d.setVisibility(View.VISIBLE);
            d8c.setVisibility(View.VISIBLE);
        }
    }

    //switching activities
    public void switchActivity(){
        if(sidesEditText.getText() != null){
            sides = Integer.parseInt(sidesEditText.getText().toString());
        }
        else if(sides == null){
            Toast.makeText(MainActivity.this, "NumberFormatException: Sides has been set to 6", Toast.LENGTH_SHORT).show();
            sides = 6;
        }
        Intent rollIntent = new Intent(this, RollingDice.class);
        rollIntent.putExtra("sides", sides);
        rollIntent.putExtra("cheat", cheat);
        startActivity(rollIntent);
    }



    //(DONE BUTTON WORKING) necessary override forDoneOnEditorActionListener private class
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