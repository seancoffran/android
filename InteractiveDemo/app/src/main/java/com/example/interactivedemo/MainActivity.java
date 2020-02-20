package com.example.interactivedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void onCliceAction(View view){
        EditText nameEditText = (EditText)findViewById(R.id.nameEditText);

        Log.i("info", "button clicked");
        Log.i("values", nameEditText.getText().toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
