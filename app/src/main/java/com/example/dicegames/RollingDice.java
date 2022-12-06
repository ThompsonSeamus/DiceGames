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
    private MediaPlayer rockAndRoll;

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

        //media player
        MediaPlayer.create(this, R.raw.nooooo);
        rockAndRoll = MediaPlayer.create(this, R.raw.rock_and_roll);

        //buttons
        Button toRollingDataButton = findViewById(R.id.to_rolling_data_button);
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
        toRollingDataButton.setOnClickListener(view -> {
            finish();
        });

        rollButton.setOnClickListener(view -> {
            rockAndRoll.start();
            String rolledNum = String.valueOf(die.roll());
            rollButton.setText(rolledNum);
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
