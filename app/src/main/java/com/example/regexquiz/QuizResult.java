package com.example.regexquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuizResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        TextView textScore = findViewById(R.id.final_score);
        TextView finalMessage = findViewById(R.id.final_message);

        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        int max_score = b.getInt("max_score");
        String final_message = b.getString("final_message");

        textScore.setText("Final score: " + score + "/" + max_score);
        finalMessage.setText(final_message);
    }

    public void restart(View v) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void reset(View v) {
        QuizDBHelper db = new QuizDBHelper(this);
        db.resetDB();
        restart(v);
    }
}