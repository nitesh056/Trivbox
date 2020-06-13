package com.example.trivbox.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.trivbox.R;
import com.example.trivbox.models.Leaderboard;
import com.example.trivbox.models.Score;
import com.example.trivbox.utils.NameDialog;
import com.example.trivbox.utils.ScoresDbHelper;
import com.example.trivbox.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ScoreActivity extends AppCompatActivity implements NameDialog.SendNameInterface{
    private TextView scoreId;
    private Score scoreObj;
    private ScoresDbHelper dbObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent i = getIntent();
        int points = (int) i.getIntExtra("score", 0);
        scoreObj = (Score) i.getExtras().getSerializable("scoreObj");

        scoreId = findViewById(R.id.score_id);
        scoreId.setText("" + points);
        scoreObj.setPoint(points);

        if (points > 0){
            dbObject = new ScoresDbHelper(this);


            if (dbObject.isHighscore(points)){
                NameDialog nameDialogObj = new NameDialog(this);
                nameDialogObj.showDialog();
            }
            dbObject.insertScore(scoreObj);
        }
    }

    public void try_again(View view) {
        startActivity(new Intent(ScoreActivity.this, CategorySelectionActivity.class));
    }

    public void exit(View view) {
        startActivity(new Intent(ScoreActivity.this, MainActivity.class));
    }

    @Override
    public void sendName(String name){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Leaderboard");
        String id = dbRef.push().getKey();
        dbRef.child(id).setValue(new Leaderboard(id, scoreObj, name));
        Utils.showToast(this, "Name Saved", false);
    }
}
