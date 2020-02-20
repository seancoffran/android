package com.example.toastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void onClick(View view){

        EditText nameText = (EditText)findViewById(R.id.nameEditText);
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append(nameText.getText().toString());
        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
