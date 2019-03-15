package com.example.regexquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuizActivity extends AppCompatActivity {
    List<RegexQuestion> regexQuestionList;
    int score = 0;
    int question_id = 0;
    RegexQuestion curQuestion;
    TextView tvQuestion, tvScore, tvAnswer, tvSolved;
    Button submitButton;
    private QuizDBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_quiz);

        QuizDBHelper db = new QuizDBHelper(this);
        dbh = db;
        regexQuestionList = db.getAllRegexQuestions();
        curQuestion = regexQuestionList.get(question_id);
        tvQuestion = findViewById(R.id.question);
        tvScore = findViewById(R.id.score);
        tvAnswer = findViewById(R.id.answer);
        tvSolved = findViewById(R.id.solved);

        // setup view:
        setView();

        // setup button:
        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(tvAnswer.getText().toString());
            }
        });

    }

    private void setView() {
        tvQuestion.setText(curQuestion.getREGEXTEXT());
        tvSolved.setText(curQuestion.isSolved() ? "Already solved" : "Not solved yet");
        tvAnswer.setText("");
        question_id++;
    }

    private void checkAnswer(String answer) {
        String regex_text = curQuestion.getREGEXTEXT();
        Pattern p = Pattern.compile(regex_text);
        Matcher m = p.matcher(answer);
        boolean ok = m.matches() || curQuestion.isSolved(); // don't check already solved questions

        if (ok) {
            score++;
            tvScore.setText("Score: " + score);
            dbh.setQuestionSolved(curQuestion.getID());
        }

        int max_score = regexQuestionList.size();
        if (! ok || question_id == max_score) {
            // show result:
            Intent intent = new Intent(this, QuizResult.class);
            Bundle b = new Bundle();
            b.putInt("score", score);
            b.putInt("max_score", max_score);
            String final_message = ok ? "Well done!" : "Failed!";
            b.putString("final_message", final_message);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        } else {
            curQuestion = regexQuestionList.get(question_id);
            setView();
        }
    }
}
