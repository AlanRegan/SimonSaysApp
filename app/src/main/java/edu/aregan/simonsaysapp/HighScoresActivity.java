package edu.aregan.simonsaysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    ListView listView;
    TextView tvUserPoints, tvUsersName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        listView = findViewById(R.id.lv);
        tvUserPoints = findViewById(R.id.tvUserPoints);
        tvUsersName = findViewById(R.id.tvUsersName);
        String usersname = getIntent().getStringExtra("uname");
        tvUsersName.setText(String.valueOf(usersname));
        int pointz = getIntent().getIntExtra("userpoints", -1);
        tvUserPoints.setText(String.valueOf(pointz));

        DatabaseHandler db = new DatabaseHandler(this);

        db.emptyHiScores();     // empty table if required

        // Inserting hi scores
        Log.i("Insert: ", "Inserting ..");
        db.addHiScore(new HiScore("20 OCT 2020", "Ian C", 40));
        db.addHiScore(new HiScore("28 OCT 2020", "Dobby", 4));
        db.addHiScore(new HiScore("20 NOV 2020", "DarthV", 4));
        db.addHiScore(new HiScore("20 NOV 2020", "Bob", 8));
        db.addHiScore(new HiScore("22 NOV 2020", "Gemma", 4));
        db.addHiScore(new HiScore("30 NOV 2020", "Joe", 12));
        db.addHiScore(new HiScore("01 DEC 2020", "DarthV", 4));
        db.addHiScore(new HiScore("02 DEC 2020", "Gandalf", 8));


        // Reading all scores
        Log.i("Reading: ", "Reading all scores..");
        List<HiScore> hiScores = db.getAllHiScores();


        for (HiScore hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");

        HiScore singleScore = db.getHiScore(5);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " +
                singleScore.getScore());

        Log.i("divider", "====================");

        // Calling SQL statement
        List<HiScore> top5HiScores = db.getTopFiveScores();

        for (HiScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }
        Log.i("divider", "====================");

        HiScore hiScore = top5HiScores.get(top5HiScores.size() - 1);
        // hiScore contains the 5th highest score
        Log.i("fifth Highest score: ", String.valueOf(hiScore.getScore()) );


        // if 5th highest score < users current score(pointz), then insert new score
        if (hiScore.getScore() < pointz) {
            db.addHiScore(new HiScore("08 DEC 2020", usersname, pointz));
        }

        Log.i("divider", "====================");

        // Calling SQL statement
        top5HiScores = db.getTopFiveScores();
        List<String> scoresStr;
        scoresStr = new ArrayList<>();

        int j = 1;
        for (HiScore hs : top5HiScores) {

            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // store score in string array
            scoresStr.add(j++ + " : "  +
                    hs.getPlayer_name() + "\t" +
                    hs.getScore());
            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");
        Log.i("divider", "Scores in list <>>");
        for (String ss : scoresStr) {
            Log.i("Score: ", ss);
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresStr);
        listView.setAdapter(itemsAdapter);

    }

    public void doPlayAgainHS(View view) {
        Intent MainAct = new Intent(HighScoresActivity.this, MainActivity.class);
        startActivity(MainAct);
    }
}