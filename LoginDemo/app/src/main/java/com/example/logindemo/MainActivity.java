package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void onClickAction(View view){
        EditText name = (EditText)findViewById(R.id.nameText);
        EditText pw = (EditText)findViewById(R.id.pwText);
        Log.i("Name: ", name.getText().toString());
        Log.i("Password: ",pw.getText().toString());
        Toast.makeText(this, "Hello there!", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
