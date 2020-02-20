package com.example.timestablesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String> itemsList = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsList);

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(100);
        ListView listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(arrayAdapter);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            List<String> temp;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temp = new ArrayList<String>();
                for(int i =0; i< progress; i++){
                    temp.add(String.valueOf(i));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                arrayAdapter.clear();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                arrayAdapter.addAll(temp);

            }
        });

    }
}
