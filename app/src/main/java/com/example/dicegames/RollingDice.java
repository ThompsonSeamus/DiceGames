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

    MediaPlayer rockAndRoll,no,cheatedD20,cheatedD6,d100, d2, d10r1, d10r2, d10r3,d12r2,d20_roll2,
            d20_roll1,d20_roll3,d12r3,d4r2,d6r1,d6r2,d8r1,d8r2;

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
        rockAndRoll = MediaPlayer.create(this, R.raw.rock_and_roll);
        no = MediaPlayer.create(this, R.raw.nooooo);
        cheatedD20 = MediaPlayer.create(this, R.raw.cheatd20);
        cheatedD6 = MediaPlayer.create(this, R.raw.cheatd6);
        d100 = MediaPlayer.create(this, R.raw.d100);
        d2 = MediaPlayer.create(this, R.raw.d2);
        d10r1 = MediaPlayer.create(this, R.raw.d10r1);
        d10r2 = MediaPlayer.create(this, R.raw.d10r2);
        d10r3 = MediaPlayer.create(this, R.raw.d10r3);
        d12r2 = MediaPlayer.create(this, R.raw.d12r2);
        d12r3 = MediaPlayer.create(this, R.raw.d12r3);
        d20_roll1 = MediaPlayer.create(this, R.raw.d20_roll1);
        d20_roll2 = MediaPlayer.create(this, R.raw.d20_roll2);
        d20_roll3 = MediaPlayer.create(this, R.raw.d20_roll3);
        d4r2 = MediaPlayer.create(this, R.raw.d4r2);
        d6r1 = MediaPlayer.create(this, R.raw.d6r1);
        d6r2 = MediaPlayer.create(this, R.raw.d6r2);
        d8r1 = MediaPlayer.create(this, R.raw.d8r1);
        d8r2 = MediaPlayer.create(this, R.raw.d8r2);

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
            playRollingSound(rolledNum);
            rollButton.setText(rolledNumString);
            if(rolledNum == 0){no.start();}
        });
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));

            //releases
        rockAndRoll.release();
        no.release();
        cheatedD20.release();
        cheatedD6.release();
        d100.release();
        d2.release();
        d10r1.release();
        d10r2.release();
        d10r3.release();
        d12r2.release();
        d12r3.release();
        d20_roll1.release();
        d20_roll2.release();
        d20_roll3.release();
        d4r2.release();
        d6r1.release();
        d6r2.release();
        d8r1.release();
        d8r2.release();

            //nulls
        sensorManager = null;
        rockAndRoll = null;
        no = null;
        cheatedD20 = null;
        cheatedD6 =null;
        d100 = null;
        d2 = null;
        d10r1 = null;
        d10r2 = null;
        d10r3 = null;
        d12r2 = null;
        d12r3 = null;
        d20_roll1 = null;
        d20_roll2 = null;
        d20_roll3 = null;
        d4r2 = null;
        d6r1 = null;
        d6r2 = null;
        d8r1 = null;
        d8r2 = null;
        super.onPause();
    }

    @Override
    protected void onResume() {
        //shake sensor re-register
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        //media player remake
        rockAndRoll = MediaPlayer.create(this, R.raw.rock_and_roll);
        no = MediaPlayer.create(this, R.raw.nooooo);
        cheatedD20 = MediaPlayer.create(this, R.raw.cheatd20);
        cheatedD6 = MediaPlayer.create(this, R.raw.cheatd6);
        d100 = MediaPlayer.create(this, R.raw.d100);
        d2 = MediaPlayer.create(this, R.raw.d2);
        d10r1 = MediaPlayer.create(this, R.raw.d10r1);
        d10r2 = MediaPlayer.create(this, R.raw.d10r2);
        d10r3 = MediaPlayer.create(this, R.raw.d10r3);
        d12r2 = MediaPlayer.create(this, R.raw.d12r2);
        d12r3 = MediaPlayer.create(this, R.raw.d12r3);
        d20_roll1 = MediaPlayer.create(this, R.raw.d20_roll1);
        d20_roll2 = MediaPlayer.create(this, R.raw.d20_roll2);
        d20_roll3 = MediaPlayer.create(this, R.raw.d20_roll3);
        d4r2 = MediaPlayer.create(this, R.raw.d4r2);
        d6r1 = MediaPlayer.create(this, R.raw.d6r1);
        d6r2 = MediaPlayer.create(this, R.raw.d6r2);
        d8r1 = MediaPlayer.create(this, R.raw.d8r1);
        d8r2 = MediaPlayer.create(this, R.raw.d8r2);

        //needed super
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
                if (!shaking) {
                    int rolledNum = die.roll();
                    String rolledNumString = String.valueOf(rolledNum);
                    playRollingSound(rolledNum);
                    rollButton.setText(rolledNumString);
                    if(rolledNum == 0){no.start();}
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

    private void playRollingSound(int rolledNum) {
        if(sides == 6 && rolledNum == 6 && cheat > 0){
            cheatedD6.start();
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
            if((int) (Math.random() * 2) + 1 == 2){d6r1.start();}
            else{d6r2.start();}
        }
        else if(sides < 9){
            if((int) (Math.random() * 2) + 1 == 2){d8r1.start();}
            else{d8r2.start();}
        }
        else if(sides < 11){
            int miniRNG = (int) (Math.random() * 3);
            if(miniRNG == 2){d10r1.start();}
            else if(miniRNG == 1){d10r3.start();}
            else{d10r2.start();}
        }
        else if(sides < 19){
            if((int) (Math.random() * 2) + 1 == 2){d12r2.start();}
            else{d12r3.start();}
        }
        else if(sides < 21){
            int miniRNG = (int) (Math.random() * 4);
            if(miniRNG == 3){d20_roll1.start();}
            else if(miniRNG == 2){d20_roll2.start();}
            else if(miniRNG == 1){rockAndRoll.start();}
            else{d20_roll3.start();}
        }
        else{
            d100.start();
        }
    }
}
