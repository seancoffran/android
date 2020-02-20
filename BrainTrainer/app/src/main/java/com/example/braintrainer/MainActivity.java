package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    TextView title;
    TextView timerText;
    TextView problemText;
    TextView scoreText;

    TextView answer1;
    TextView answer2;
    TextView answer3;
    TextView answer4;
    List<TextView> answerViews;
    CountDownTimer timer;
    Integer correctAnswer;
    Integer correct = 0;
    Integer wrong = 0;
    boolean gameActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button)findViewById(R.id.goButton);
        title = (TextView)findViewById(R.id.titeText);
        timerText = (TextView)findViewById(R.id.timerText);
        problemText = (TextView)findViewById(R.id.problemText);
        scoreText = (TextView)findViewById(R.id.scoreText);

        answer1 = (TextView)findViewById(R.id.answerBox1);
        answer2 = (TextView)findViewById(R.id.answerBox2);
        answer3 = (TextView)findViewById(R.id.answerBox3);
        answer4 = (TextView)findViewById(R.id.answerBox4);
        answerViews = Arrays.asList(answer1,answer2,answer3,answer4);

        timer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisecoundsTillDone) {
                setTimerText(millisecoundsTillDone);
            }

            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Final score: " + scoreText.getText(), Toast.LENGTH_LONG).show();
                goButton.setVisibility(View.VISIBLE);
                goButton.setEnabled(true);
            }
        };
    }

    public void checkAnswer(View view){
        TextView selected = (TextView)view;
        int answer = Integer.parseInt(selected.getText().toString());
        if(answer == correctAnswer){
            correct++ ;
        } else {
            wrong++;
        }
        scoreText.setText(String.valueOf(wrong) + "/" + String.valueOf(correct));
        newProblem();
        setAnswers();
    }



    private void newProblem(){
        Random r = new Random();
        int fn = r.nextInt(10);
        int ln = r.nextInt(10);
        correctAnswer = fn + ln;
        setProblemText(fn, ln);
    }

    public void onClickGo(View view){
        if(!gameActive) {
            timerText.setVisibility(View.VISIBLE);
            problemText.setVisibility(View.VISIBLE);
            scoreText.setVisibility(View.VISIBLE);
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.VISIBLE);
        }
        hideStartObjects();
        newProblem();
        setAnswers();
        scoreText.setText("0/0");
        correct = 0;
        wrong = 0;
        timer.start();

    }
    private void setAnswers(){
        Random r = new Random();
        Collections.shuffle(answerViews);
        for(TextView view : answerViews){
            view.setText(String.valueOf(r.nextInt(100)));
        }
        answerViews.get(0).setText(String.valueOf(correctAnswer));
    }

    private void setProblemText(int fn, int ln){
        problemText.setText(String.valueOf(fn) + " + " + String.valueOf(ln) );
    }

    private void setTimerText(long millisecoundsTillDone){
        long second = (millisecoundsTillDone / 1000) % 60;

        String time = String.format("%02ds", second);
        timerText.setText(time);
    }

    private void hideStartObjects() {
        goButton.setEnabled(false);
        goButton.setVisibility(View.INVISIBLE);
        gameActive = true;
    }
}
