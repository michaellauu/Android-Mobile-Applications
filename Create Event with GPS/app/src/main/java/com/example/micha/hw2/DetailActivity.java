package com.example.micha.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.Object;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DetailActivity extends AppCompatActivity implements OnClickListener {
    public JSONObject jo = null;
    public JSONArray ja = null;
    public JSONArray test = null;
    Button deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        String title = i.getStringExtra("Title");
        String description = i.getStringExtra("Description");
        String time = i.getStringExtra("Time");
        String date = i.getStringExtra("Date");
        String gps = i.getStringExtra("GPS");

        TextView titleView = (TextView) findViewById(R.id.titleView);
        TextView descriptView = (TextView) findViewById(R.id.descriptView);
        TextView timeView = (TextView) findViewById(R.id.timeView);
        TextView dateView = (TextView) findViewById(R.id.dateView);
        TextView gpsView = (TextView) findViewById(R.id.gpsView);

        titleView.setText(title);
        descriptView.setText(description);
        timeView.setText(time);
        dateView.setText(date);
        gpsView.setText(gps);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);
    }
    public void onClick(View v) {
        Bundle bundle = getIntent().getExtras();
        int position= bundle.getInt("stuff");
        try {
            File f = new File(getFilesDir(), "file.ser");
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(fi);
            String j = null;
            try {
                j = (String) o.readObject();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
            try {
                jo = new JSONObject(j);
                ja = jo.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            // Here, initialize a new JSONObject
            jo = new JSONObject();
            ja = new JSONArray();
            try {
                jo.put("data", ja);
            } catch (JSONException j) {
                j.printStackTrace();
            }
        }
            if (ja != null) {
            ja.remove(position);
        }
        try {
            File f = new File(getFilesDir(), "file.ser");
            FileOutputStream fo = new FileOutputStream(f);
            ObjectOutputStream o = new ObjectOutputStream(fo);
            String j = jo.toString();
            o.writeObject(j);
            o.close();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(DetailActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
