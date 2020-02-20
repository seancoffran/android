package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String APIKEY = "appid=256abfc6e550d29bcfd4fcb783f17ecc";
    String urlMain = "http://api.openweathermap.org/data/2.5/weather?q=";
    EditText input;
    TextView output;
    Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.editText);
        output = findViewById(R.id.textView5);
        enter = findViewById(R.id.button2);

    }

    public void getWeather(View view){
        String city = input.getText().toString();
        if(city == null || city.isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter a city name", Toast.LENGTH_SHORT).show();
        }
        WeatherDataLoader loader = new WeatherDataLoader();

        try{
            String cityName = input.getText().toString();
            String request = urlMain + cityName + ",us&" + APIKEY;
            loader.execute(request).get();
        }catch(Exception e){
            output.setText(e.getLocalizedMessage());
        }
    }

    public class WeatherDataLoader extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls){
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int index = reader.read();
                String result = "";
                while(index != -1){
                    result += (char)index;
                    index = reader.read();
                }
                return result;
            }catch(Exception e){
                return e.getLocalizedMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray weatherArray = jsonObject.getJSONArray("weather");

                StringBuilder sb = new StringBuilder("Description: ");
                sb.append(getValue(weatherArray, "description"));

                output.setText(sb.toString());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        private String getValue(JSONArray data, String target){
            for(int i =0; i < data.length(); i++){
                try {
                    JSONObject s = (JSONObject)data.get(i);
                    return s.getString(target);

                }catch (Exception e){
                    return "Error retrieving weather";
                }
            }
            return "";
        }
    }
}
