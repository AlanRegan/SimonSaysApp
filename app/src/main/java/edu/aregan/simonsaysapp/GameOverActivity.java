package edu.aregan.simonsaysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView tvPoints;
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        tvPoints = findViewById(R.id.tvPoints);
        int points = getIntent().getIntExtra("userpoints", 0);
        tvPoints.setText(String.valueOf(points));

        etName = findViewById(R.id.etName);

    }

    public void doViewScores(View view) {
        Intent i= new Intent(GameOverActivity.this, HighScoresActivity.class);
        String name = etName.getText().toString();
        i.putExtra("uname", name);
        int points = getIntent().getIntExtra("userpoints", 0);
        i.putExtra("userpoints", points);
        startActivity(i);
    }

    public void doPlayAgain(View view) {
        Intent MainAct = new Intent(GameOverActivity.this, MainActivity.class);
        startActivity(MainAct);
    }
}