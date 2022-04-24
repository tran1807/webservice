package com.example.webservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lstNhanVien;
    Button btnInsert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstNhanVien = (ListView) findViewById(R.id.listviewNhanvien);
        btnInsert = (Button) findViewById(R.id.buttonInsert);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InsertActivity.class);
                startActivity(intent);


            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://lenhutran1807-001-site1.htempurl.com/json.php");
            }
        });

    }
        @NonNull
        private String docNoiDung_Tu_URL (String theUrl){
            StringBuilder content = new StringBuilder();
            try {
                //create a url object
                URL url = new URL(theUrl);
                //create a urlconnection object
                URLConnection urlConnection = url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));

                String line;

                //read from urlconnection -> the bufferedReader
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line + "\n");

                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }
        class docJSON extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String... strings) {
                return docNoiDung_Tu_URL(strings[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                ArrayList<String> arrayNhanVien = new ArrayList<String>();
                try {
                    JSONArray mang = new JSONArray(s);
                    for (int i = 0; i < mang.length(); i++) {
                        JSONObject nhanvien = mang.getJSONObject(i);
                        arrayNhanVien.add(nhanvien.getString("Tennv"));
                    }
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,
                            android.R.layout.simple_list_item_1, arrayNhanVien);
                    lstNhanVien.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
}