package com.example.dicegames;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class RollingDice extends Activity {

    //needed for the die
    private Button rollButton;
    private int sides, cheat;
    private Die die;

    //shake sensors
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;

    //shake sensor variables
        //floats
    private float prevXAcc;
    private float prevYAcc;
    private float prevZAcc;
        //booleans
    private boolean firstTime = true;
    private boolean shaking = false;
    private boolean atRest = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);

        //buttons
        Button toRollingDataButton = findViewById(R.id.to_rolling_data_button);
        rollButton = findViewById(R.id.roll_button);

        //intent from the roll data
        Intent fromRollDataIntent = getIntent();
        sides = fromRollDataIntent.getIntExtra("sides", sides);
        cheat = fromRollDataIntent.getIntExtra("cheat", cheat);

        //Media Player Things
        MediaPlayer rockAndRoll = MediaPlayer.create(this, R.raw.rock_and_roll);
        MediaPlayer no = MediaPlayer.create(this, R.raw.nooooo);
        MediaPlayer cheatedD20 = MediaPlayer.create(this, R.raw.cheatd20);
        MediaPlayer cheatedD6 = MediaPlayer.create(this, R.raw.cheatd6);
        MediaPlayer d100 = MediaPlayer.create(this, R.raw.d100);
        MediaPlayer d2 = MediaPlayer.create(this, R.raw.d2);
        MediaPlayer d10r1 = MediaPlayer.create(this, R.raw.d10r1);
        MediaPlayer d10r2 = MediaPlayer.create(this, R.raw.d10r2);
        MediaPlayer d10r3 = MediaPlayer.create(this, R.raw.d10r3);
        MediaPlayer d12r2 = MediaPlayer.create(this, R.raw.d12r2);
        MediaPlayer d12r3 = MediaPlayer.create(this, R.raw.d12r3);
        MediaPlayer d20_roll1 = MediaPlayer.create(this, R.raw.d20_roll1);
        MediaPlayer d20_roll2 = MediaPlayer.create(this, R.raw.d20_roll2);
        MediaPlayer d20_roll3 = MediaPlayer.create(this, R.raw.d20_roll3);
        MediaPlayer d4r2 = MediaPlayer.create(this, R.raw.d4r2);
        MediaPlayer d6r1 = MediaPlayer.create(this, R.raw.d6r1);
        MediaPlayer d6r2 = MediaPlayer.create(this, R.raw.d6r2);
        MediaPlayer d8r1 = MediaPlayer.create(this, R.raw.d8r1);
        MediaPlayer d8r2 = MediaPlayer.create(this, R.raw.d8r2);

        //sensor things
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new ShakeListener();
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


        //making a die
        try {
            die = new Die(sides, cheat);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Die Couldn't Be Initialized With Given Parameters", Toast.LENGTH_SHORT).show();
            die = new Die();
        }

        //button listeners
        toRollingDataButton.setOnClickListener(view -> finish());

        rollButton.setOnClickListener(view -> {
            int rolledNum = die.roll();
            String rolledNumString = String.valueOf(rolledNum);

            if(sides == 6 && rolledNum == 6 && cheat > 0){
                cheatedD20.start();
            }
            else if(rolledNum == sides && cheat > 0){
                cheatedD20.start();
            }
            else if(sides < 3){
                d2.start();
            }
            else if(sides < 5){
                d4r2.start();
            }
            else if(sides < 7){
                if((int) (Math.random() * 2) == 2){d6r1.start();}
                else{d6r2.start();}
            }
            else if(sides < 9){
                if((int) (Math.random() * 2) == 2){d8r1.start();}
                else{d8r2.start();}
            }
            else if(sides < 11){
                int miniRNG = (int) (Math.random() * 3);
                if(miniRNG == 3){d10r1.start();}
                else if(miniRNG == 2){d10r3.start();}
                else{d10r2.start();}
            }
            else if(sides < 19){
                if((int) (Math.random() * 2) == 2){d12r2.start();}
                else{d12r3.start();}
            }
            else if(sides < 21){
                int miniRNG = (int) (Math.random() * 4);
                if(miniRNG == 4){d20_roll1.start();}
                else if(miniRNG == 3){d20_roll2.start();}
                else if(miniRNG == 2){rockAndRoll.start();}
                else{d20_roll3.start();}
            }
            else{
                d100.start();
            }
            rollButton.setText(rolledNumString);
        });
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    class ShakeListener implements SensorEventListener {
        private static final int SHAKE_THRESHOLD = 5;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            if (firstTime) {
                prevXAcc = sensorEvent.values[0];
                prevYAcc = sensorEvent.values[1];
                prevZAcc = sensorEvent.values[2];
                firstTime = false;
            }
            float xAcc = sensorEvent.values[0];
            float yAcc = sensorEvent.values[1];
            float zAcc = sensorEvent.values[2];

            float deltaXAcc = Math.abs(xAcc - prevXAcc);
            float deltaYAcc = Math.abs(yAcc - prevYAcc);
            float deltaZAcc = Math.abs(zAcc - prevZAcc);

            if (deltaXAcc > SHAKE_THRESHOLD || deltaYAcc > SHAKE_THRESHOLD || deltaZAcc > SHAKE_THRESHOLD) {
                if (!shaking) {int rolledNum = die.roll();
                    String rolledNumString = String.valueOf(rolledNum);

                    if(sides == 6 && rolledNum == 6 && cheat > 0){
                        cheatedD20.start();
                    }
                    else if(rolledNum == sides && cheat > 0){
                        cheatedD20.start();
                    }
                    else if(sides < 3){
                        d2.start();
                    }
                    else if(sides < 5){
                        d4r2.start();
                    }
                    else if(sides < 7){
                        if((int) (Math.random() * 2) == 2){d6r1.start();}
                        else{d6r2.start();}
                    }
                    else if(sides < 9){
                        if((int) (Math.random() * 2) == 2){d8r1.start();}
                        else{d8r2.start();}
                    }
                    else if(sides < 11){
                        int miniRNG = (int) (Math.random() * 3);
                        if(miniRNG == 3){d10r1.start();}
                        else if(miniRNG == 2){d10r3.start();}
                        else{d10r2.start();}
                    }
                    else if(sides < 19){
                        if((int) (Math.random() * 2) == 2){d12r2.start();}
                        else{d12r3.start();}
                    }
                    else if(sides < 21){
                        int miniRNG = (int) (Math.random() * 4);
                        if(miniRNG == 4){d20_roll1.start();}
                        else if(miniRNG == 3){d20_roll2.start();}
                        else if(miniRNG == 2){rockAndRoll.start();}
                        else{d20_roll3.start();}
                    }
                    else{
                        d100.start();
                    }
                    rollButton.setText(rolledNumString);
                    shaking = true;
                    atRest = false;
                }
            } else if (deltaXAcc <= .5 && deltaYAcc <= .5 && deltaZAcc <= .5) {
                if (atRest && shaking) {
                    shaking = false;
                }
                atRest = true;
            }
            prevXAcc = xAcc;
            prevYAcc = yAcc;
            prevZAcc = zAcc;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }
}
