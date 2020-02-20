package com.example.khalesimageswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    static int callCount = 1;
    public void onSwitch(View view) {
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        int ret = callCount % 2;
        if (ret != 0) {
            iv.setImageResource(R.drawable.cat2);
        } else {
            iv.setImageResource(R.drawable.cat1);
        }
        callCount++;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
