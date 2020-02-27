package com.example.mathquiztest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TextView txtViewQuestion,txtViewQuestionNo;
    TextView txtViewResult;
    TextView txtTime;
    TextView txtScore;
    ImageButton btnCorrect;
    ImageButton btnIncorrect;
    boolean isResultCorrect;
    int seconds = 59;
    private int score = 0;
    private boolean stopTimer = false;

    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        txtViewQuestion = findViewById(R.id.txtViewQuestion);
        txtViewQuestionNo = findViewById(R.id.txtViewQuestionNo);
        txtViewResult = findViewById(R.id.txtViewResult);
        txtTime = findViewById(R.id.txtTime);
        txtScore = findViewById(R.id.txtScore);
        btnCorrect = findViewById(R.id.btnCorrect);
        btnIncorrect = findViewById(R.id.btnIncorrect);
        timer();
        btnCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count<10){
                    count++;
                    txtViewQuestionNo.setText("Q."+count);
                    verifyAnswer(true);
                }else
                    Toast.makeText(GameActivity.this, "You have completed Your TEST...Please wait tile to complete time.", Toast.LENGTH_SHORT).show();
            }
        });
        btnIncorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count<10){
                    count++;
                    txtViewQuestionNo.setText("Q."+count);
                    verifyAnswer(false);
                }else
                    Toast.makeText(GameActivity.this, "You have completed Your TEST...Please wait tile to complete time.", Toast.LENGTH_SHORT).show();
            }
        });
        generateQuestion();
    }

    private void generateQuestion() {
        isResultCorrect = true;
        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);
        int result = a + b;
        float f = random.nextFloat();
        if (f > 0.5f) {
            result = random.nextInt(100);
            isResultCorrect = false;
        }
        txtViewQuestion.setText(a + "+" + b);
        txtViewResult.setText("=" + result);
    }

    public void verifyAnswer(boolean answer) {
        if (answer == isResultCorrect) {
            score += 1;
            txtScore.setText("SCORE: " + score);
        } else {
            Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
        generateQuestion();
    }

    private void timer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                txtTime.setText("Time :" + seconds+"sec.");
                seconds--;
                if (count == 10) {
                    Intent i = new Intent(GameActivity.this, ScoreActivity.class);
                    i.putExtra("score", score);
                    startActivity(i);
                    stopTimer = true;
                }
                if (stopTimer == false) {
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer = false;
        finish();
    }
}
