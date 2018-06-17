package com.example.micha.hw2;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.io.*;
import java.text.DecimalFormat;
import android.view.View;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;
import android.support.annotation.NonNull;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

import android.content.Intent;
import android.content.Context;
import android.support.v4.app.ActivityCompat;


public class AddEvent extends AppCompatActivity implements OnRequestPermissionsResultCallback {
    private final String TAG = "TESTGPS";
    public JSONObject jo = null;
    public JSONArray ja = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Start up the Location Service
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
        final EditText inputTitle = findViewById(R.id.inputTitle);
        final EditText inputDescription = findViewById(R.id.inPutDescription);
        Button b = findViewById(R.id.button);
        // Read the file
        try {
            File f = new File(getFilesDir(), "file.ser");
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(fi);
            // Notice here that we are de-serializing a String object (instead of
            // a JSONObject object) and passing the String to the JSONObject’s
            // constructor. That’s because String is serializable and
            // JSONObject is not. To convert a JSONObject back to a String, simply
            // call the JSONObject’s toString method.
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

        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String time = new SimpleDateFormat("HH:mm:ss a").format(new Date());
                String date = new SimpleDateFormat("MM-d-yyyy").format(new Date());
                String title = inputTitle.getText().toString();
                String description = inputDescription.getText().toString();
                String gps = gpsLongLat();
                JSONObject temp = new JSONObject();
                try {
                    temp.put("Title", title);
                    temp.put("Description", description);
                    temp.put("Time", time);
                    temp.put("Date", date);
                    temp.put("GPS", gps);
                } catch (JSONException j) {
                    j.printStackTrace();
                }

                ja.put(temp);
                // write the file
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
                //pop the activity off the stack
                Intent i = new Intent(AddEvent.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    public String gpsLongLat() {
        // A reference to the location manager. The LocationManager has already
        // been set up in MyService, we're just getting a reference here.
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location l;
        // Go through the location providers starting with GPS, stop as soon
        // as we find one.
        Double a, b;
        DecimalFormat df = new DecimalFormat("0.000000");
        String combined = "";
        for (int i=providers.size()-1; i>=0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            a = l.getLongitude();
            b = l.getLatitude();
            combined = (df.format(a)+", "+df.format(b));
            if(l != null) break;
        }
        return combined;
    }

    // This class implements OnRequestPermissionsResultCallback, so when the
// user is prompted for location permission, the below method is called
// as soon as the user chooses an option.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "callback");
        switch (requestCode) {
            case 99:
                // If the permissions aren't set, then return. Otherwise, proceed.
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                                , 10);
                    }
                    Log.d(TAG, "returning program");
                    return;
                } else {
                    // Create Intent to reference MyService, start the Service.
                    Log.d(TAG, "starting service");
                    Intent i = new Intent(this, MyService.class);
                    if (i == null)
                        Log.d(TAG, "intent null");
                    else {
                        startService(i);
                    }

                }
                break;
            default:
                break;
        }
    }
}