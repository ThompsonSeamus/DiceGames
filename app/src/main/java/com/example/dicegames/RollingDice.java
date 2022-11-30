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

public class RollingDice extends Activity implements SensorEventListener {
    private MediaController mediaController;
    private MediaPlayer no, rockAndRoll;
    private Button toRollingDataButton, rollButton;
    private int sides, cheat, rolledNum;
    private Die die;
    private Sensor shakeSensor;
    private SensorManager sensorManager;
    private static final int TYPE_LINEAR_ACCELERATION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);

        //media player
        no = MediaPlayer.create(this, R.raw.nooooo);
        rockAndRoll = MediaPlayer.create(this, R.raw.rock_and_roll);

        //Shake Sensor
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        shakeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //buttons
        toRollingDataButton = findViewById(R.id.to_rolling_data_button);
        rollButton = findViewById(R.id.roll_button);

        //intent from the roll data
        Intent fromRollDataIntent = getIntent();
        sides = fromRollDataIntent.getIntExtra("sides", sides);
        cheat = fromRollDataIntent.getIntExtra("cheat", cheat);

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
    public void onSensorChanged(SensorEvent sensorEvent) {
        rockAndRoll.start();
        String rolledNum = String.valueOf(die.roll());
        rollButton.setText(rolledNum);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
