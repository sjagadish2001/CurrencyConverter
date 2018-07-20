package com.example.ujm4.currencyconverter;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final Map<String, String> currencyMap = createMap();
    private static Map<String, String> createMap()
    {
        Map<String,String> myMap = new HashMap<String,String>();
        myMap.put("Dollar", "USD");
        myMap.put("Rupees", "INR");
        myMap.put("Euro", "EUR");
        myMap.put("Pound", "GBP");
        myMap.put("Yen", "JPY");
        myMap.put("Yuan", "CNY");
        return myMap;
    }

    public void convert(View view){
        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        EditText input = findViewById(R.id.editText);


        Log.i("Values","_______"+spinner1.getSelectedItem().toString());
        Log.i("Values","_______"+spinner2.getSelectedItem().toString());
        Log.i("Values","_______"+input.getText().toString());
        String from = currencyMap.get(spinner1.getSelectedItem().toString());
        String to = currencyMap.get(spinner2.getSelectedItem().toString());
        double d = Double.parseDouble(input.getText().toString());

        String url = "https://forex.1forge.com/1.0.3/convert?from="+ from +"&to="+to+"&quantity="+d+"&api_key=dXiT08FKQs1lXerl8MVY5wgVlJtFKaYj";

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print in String
                System.out.println(response.toString());
                JSONObject jObject = new JSONObject(response.toString());

               Toast.makeText( this, "Hi there !" + jObject.getString("value"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
