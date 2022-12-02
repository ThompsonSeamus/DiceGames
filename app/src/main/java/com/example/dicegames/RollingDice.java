package com.example.dicegames;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import kotlin.jvm.internal.Ref;

public class RollingDice extends Activity {
    private MediaController mediaController;
    private MediaPlayer no, rockAndRoll;

    private Button toRollingDataButton, rollButton;
    private int sides, cheat;
    private Die die;

    //shake sensors
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    //shake sensor variables
    //doubles
    private float prevXAcc;
    private float prevYAcc;
    private float prevZAcc;
    //booleans
    private boolean firstTime = true;
    private boolean shaking = false;
    private boolean atRest = true;

    private static final int TYPE_LINEAR_ACCELERATION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);

        //media player
        no = MediaPlayer.create(this, R.raw.nooooo);
        rockAndRoll = MediaPlayer.create(this, R.raw.rock_and_roll);

        //buttons
        toRollingDataButton = findViewById(R.id.to_rolling_data_button);
        rollButton = findViewById(R.id.roll_button);

        //intent from the roll data
        Intent fromRollDataIntent = getIntent();
        sides = fromRollDataIntent.getIntExtra("sides", sides);
        cheat = fromRollDataIntent.getIntExtra("cheat", cheat);

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
        toRollingDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sides = 0;
                cheat = 0;
                die = null;
                finish();
            }
        });

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rockAndRoll.start();
                String rolledNum = String.valueOf(die.roll());
                rollButton.setText(rolledNum);
            }
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
                if (!shaking) {
                    rockAndRoll.start();
                    String rolledNum = String.valueOf(die.roll());
                    rollButton.setText(rolledNum);
                    shaking = true;
                    atRest = false;
                }
            } else if (deltaXAcc == 0 && deltaYAcc == 0 && deltaZAcc == 0) {
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
