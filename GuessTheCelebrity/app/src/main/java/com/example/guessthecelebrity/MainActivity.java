package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    String correctName = "";
    Map<String, String> data = new HashMap<>();
    List<Button> buttons = new ArrayList<>();
    List<String> allNames = new ArrayList<>();
    List<String> seen = new ArrayList<>();
    public void clickGuess(View view){
        Button selected = (Button)view;
        String answer = selected.getText().toString();
        if(answer.equals(correctName)){
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            play(buttons, allNames);
        } else{
            Toast.makeText(getApplicationContext(), "Incorrect! try again", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        buttons.add(b4);
        Collections.shuffle(buttons);
        LoadData loader = new LoadData();

        try {
            data = loader.execute("http://www.posh24.se/kandisar").get();
            allNames.addAll(data.keySet());
            play(buttons, allNames);
        } catch (Exception e ){
            e.getLocalizedMessage();
        }
    }

    private void play(List<Button> buttons, List<String> allNames) {
        for(String name : data.keySet()){
            if(seen.contains(name)){
                continue;
            }
            allNames.remove(name);
            String imageUrl = data.get(name);
            ImageReader reader = new ImageReader();
            Bitmap image = null;
            try {
                image = reader.execute(imageUrl).get();
                if (image == null) {
                    reader.cancel(true);
                    continue;
                }
            }catch(Exception e){
                continue;
            }
            imageView.setImageBitmap(image);

            Collections.shuffle(buttons);
            buttons.get(0).setText(name);
            Collections.shuffle(allNames);

            buttons.get(1).setText(allNames.get(0));
            buttons.get(2).setText(allNames.get(1));
            buttons.get(3).setText(allNames.get(2));
            allNames.add(name);
            correctName = name;
            seen.add(name);
            break;
        }
    }

    public class ImageReader extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);

            }catch(Exception e){
                return null;
            }
        }
    }

    public class LoadData extends AsyncTask<String, Void, Map<String, String>>{

        @Override
        protected Map<String, String> doInBackground(String... urls) {

            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int index = reader.read();
                String input = "";
                while(index != -1){
                    input += (char)index;
                    index = reader.read();
                }
                return parseDataFromInput(input);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),Toast.LENGTH_LONG);
            }
            return null;
        }

        private Map<String, String> parseDataFromInput(final String input){
            Map<String, String> data = new HashMap<>();

            Pattern p1 = Pattern.compile("img src=\"(.*?)/>");
            Matcher m = p1.matcher(input);
            try {
                while (m.find()) {
                    String line = m.group(1);
                    Pattern p2 = Pattern.compile("alt=\"(.*?)\"");
                    Matcher m2 = p2.matcher(line);
                    String[] split = line.split(" ");
                    String imageUrl = split[0];
                    int index = imageUrl.lastIndexOf("\"");
                    imageUrl = imageUrl.substring(0,index);
                    while(m2.find()){
                        String name = m2.group(1);
                        data.put(name, imageUrl);
                    }
                }
            }catch(Exception e){
                 e.getLocalizedMessage();
            }
            return data;
        }

    }
}
