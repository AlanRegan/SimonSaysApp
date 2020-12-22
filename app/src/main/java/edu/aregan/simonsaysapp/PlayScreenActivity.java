package edu.aregan.simonsaysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;

public class PlayScreenActivity extends AppCompatActivity implements SensorEventListener {

    // experimental values for hi and lo magnitude limits
    private final double NORTH_MOVE_FORWARD = 9.0;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 6.0;      // lower mag limit
    private final double ylimit = -9.4;     // upper mag limit
    private final double xlimit = -2.2;
    boolean highLimit = false;      // detect high limit
    boolean highLimit2 = false;      // detect high limit
    int counter = 0;                // step counter
    int counter2 = 0;
    int counter3 = 0;
    int counter4 = 0;
   // int points = 1;
    int pointsPS = 4;
    int roundsPS = 1;
    ArrayList<Integer> inputSequence = new ArrayList<Integer>();
    ArrayList<Integer> seqarray = new ArrayList<>();


    TextView tvx, tvy, tvz, tvSteps, tvSteps2, tvSteps3, sequences,
            tvInputSequence, tvWinOrLose, tvUserRound, tvPointsOnPS;
    Button bRed, bBlue, bYellow, bGreen;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);
        // Textviews
        tvx = findViewById(R.id.tvX);
        tvy = findViewById(R.id.tvY);
        tvz = findViewById(R.id.tvZ);
        tvSteps = findViewById(R.id.tvSteps);
        tvSteps2 = findViewById(R.id.tvSteps2);
        tvSteps3 = findViewById(R.id.tvSteps3);
        tvInputSequence = findViewById(R.id.tvInputSequence);
        tvWinOrLose = findViewById(R.id.tvWinOrLose);

        // Get round from main activity + display
        tvUserRound = findViewById(R.id.tvRound23);
        roundsPS = getIntent().getIntExtra("round", 1);
        pointsPS = getIntent().getIntExtra("points", 1);
        tvPointsOnPS = findViewById(R.id.tvPointsOnPS);
      //  tvUserRound.setText(round);

        // Buttons
        bRed = findViewById(R.id.btnRed);
        bBlue = findViewById(R.id.btnBlue);
        bYellow = findViewById(R.id.btnYellow);
        bGreen = findViewById(R.id.btnGreen);

//      Passing + displaying sequence
        sequences = findViewById(R.id.tvSequence);
        int[] array = getIntent().getIntArrayExtra("key");
        for (int i : array){
            seqarray.add(Integer.valueOf(i));
        }

//        seqarray.add(4);
//        seqarray.add(1);

        sequences.setText(seqarray.toString());


        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }


    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        tvx.setText(String.valueOf(x));
        tvy.setText(String.valueOf(y));
        tvz.setText(String.valueOf(z));

        // North
        if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) {
            highLimit = true;
        }
        if ((x < NORTH_MOVE_BACKWARD) && (z > NORTH_MOVE_BACKWARD) && (highLimit == true)) {
            // we have a tilt to the north
            counter++;
            tvSteps.setText(String.valueOf(counter));
            highLimit = false;
            bGreen.setPressed(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bGreen.setPressed(false);
                }
            }, 600);
            inputSequence.add(4);
        }

        // South
        if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) {
            highLimit = true;
        }
        if ((x < NORTH_MOVE_BACKWARD) && (z < -1.0) && (highLimit == true)) {
            // we have a tilt to the north
            counter2++;
            tvSteps2.setText(String.valueOf(counter2));
            highLimit = false;
            bBlue.setPressed(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bBlue.setPressed(false);
                }
            }, 600);
            inputSequence.add(1);
        }

        // West
        //screen rotated to the left
        if ((y > -0.1 && z > 0.2) && (highLimit2 == false)) {
            highLimit2 = true;
        }
        if ((z < 0.156 && y < -0.34) && (highLimit2 == true)) {
            counter3++;
            tvSteps3.setText(String.valueOf(counter3));
            highLimit2 = false;
            bYellow.setPressed(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bYellow.setPressed(false);
                }
            }, 600);
            inputSequence.add(3);
        }

        // East
        if ((y > -0.1 && z > 0.2) && (highLimit2 == false)) {
            highLimit2 = true;
        }
        if ((z < 0.156 && y > 0.33) && (highLimit2 == true)) {
            counter4++;
        //    tvSteps3.setText(String.valueOf(counter3));
            highLimit2 = false;
            bRed.setPressed(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bRed.setPressed(false);
                }
            }, 600);


            inputSequence.add(2);
        }

        for (int i=0; i < 4; i++){
            tvInputSequence.setText(inputSequence.toString());
        }

     //   tvUserRound.setText(String.valueOf(roundsPS));
        tvPointsOnPS.setText(String.valueOf(pointsPS));




        // East
//        if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) {
//            highLimit = true;
//        }
//        if ((y < -9.4) && (z < -4.6) && (highLimit == true)) {
//            // we have a tilt to the north
//            counter3++;
//            tvSteps3.setText(String.valueOf(counter3));
//            highLimit = false;
//        }

        }    // we have a tilt to the north




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void doCheck(View view) {
        if (seqarray.toString().equals(inputSequence.toString())){
          //  Toast.makeText(this, "You won", Toast.LENGTH_SHORT).show();
            tvWinOrLose.setText("Correct!");
            tvWinOrLose.setTextColor(Color.GREEN);
            pointsPS = pointsPS + 4;
            roundsPS = roundsPS + 1;
            Intent MainActivity = new Intent(PlayScreenActivity.this, MainActivity.class);
            MainActivity.putExtra("userpoints", pointsPS);
            MainActivity.putExtra("userrounds", roundsPS);
            startActivity(MainActivity);
        }
        else if (!seqarray.toString().equals(inputSequence.toString())) {
            tvWinOrLose.setText("Incorrect!");
            tvWinOrLose.setTextColor(Color.RED);
        Intent i= new Intent(PlayScreenActivity.this, GameOverActivity.class);
        i.putExtra("userpoints", pointsPS);
        startActivity(i);
        }



    }
}
