package edu.aregan.simonsaysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView tvPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        tvPoints = findViewById(R.id.tvPoints);
        int points = getIntent().getIntExtra("userpoints", -1);
        tvPoints.setText(String.valueOf(points));
    }

    public void doViewScores(View view) {
        Intent i= new Intent(GameOverActivity.this, HighScoresActivity.class);
     //   i.putExtra("userpoints", points);
        startActivity(i);
    }
}