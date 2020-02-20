package com.example.showandhideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;

    public void onShow(View view){
        if(text.getVisibility() == View.INVISIBLE){
            text.setVisibility(View.VISIBLE);
        }
    }

    public void onHide(View view){
        if(text.getVisibility() == View.VISIBLE){
            text.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textView);
    }
}



