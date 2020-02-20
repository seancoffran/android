package com.example.eggtimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button stopButton;
    SeekBar timeBar;
    TextView textView;
    boolean running = false;
    CountDownTimer timer;
    int timeInMs = 60000;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    public void onStartClick(View view){
        if(!running) {
            running = true;
        startButton.setEnabled(false);
        startButton.setVisibility(View.INVISIBLE);
        timeBar.setEnabled(false);
        stopButton.setEnabled(true);
        stopButton.setVisibility(View.VISIBLE);

           timer = new CountDownTimer(timeInMs, 1000) {
                public void onTick(long millisecoundsTillDone) {
                    setTimerText(millisecoundsTillDone);
                }

                public void onFinish() {
                    mediaPlayer.start();
                }
            }.start();
        }
    }

    private void setTimerText(long millisecoundsTillDone) {
        long second = (millisecoundsTillDone / 1000) % 60;
        long minute = (millisecoundsTillDone / (1000 * 60)) % 60;

        String time = String.format("%02d:%02d", minute, second);
        textView.setText(time);
    }

    public void onStopClick(View view){
        if(running) {
            running = false;
            stopButton.setEnabled(false);
            stopButton.setVisibility(View.INVISIBLE);

            startButton.setEnabled(true);
            startButton.setVisibility(View.VISIBLE);
            mediaPlayer.stop();
            timeBar.setEnabled(true);
            timer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.goButton);
        stopButton = (Button)findViewById(R.id.stopButton);
        textView = (TextView)findViewById(R.id.textView);
        mediaPlayer = MediaPlayer.create(this, R.raw.rooster);
        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        timeBar = (SeekBar)findViewById(R.id.timeBar);
        timeBar.setMax(15);

        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeInMs = progress * 60000;
                setTimerText(timeInMs);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
