package com.example.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// way 1
        new CountDownTimer( 10000, 10000){

            public void onTick(long millisecondsUntilDone){
                Log.i("Seconds left: ", String.valueOf(millisecondsUntilDone));
            }

            public void onFinish(){
                Log.i("We're Done", "");
            }

        }.start();


// way 2
        final Handler handler = new Handler();
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                Log.i("Hey ", "one second has past");
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runner);
    }


}
