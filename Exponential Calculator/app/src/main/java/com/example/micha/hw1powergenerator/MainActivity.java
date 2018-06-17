package com.example.micha.hw1powergenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.math.RoundingMode;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button2, button3, button4, button5;
    private TextView result2, result3, result4, result5;
    private EditText edit2, edit3, edit4, edit5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        result2 = findViewById(R.id.result2);
        result3 = findViewById(R.id.result3);
        result4 = findViewById(R.id.result4);
        result5 = findViewById(R.id.result5);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        edit4 = findViewById(R.id.edit4);
        edit5 = findViewById(R.id.edit5);

        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    public void onClick(View view) {
        String num2 = edit2.getText().toString();
        String num3 = edit3.getText().toString();
        String num4 = edit4.getText().toString();
        String num5 = edit5.getText().toString();
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.DOWN); // prevents rounding
        switch(view.getId()){
            case R.id.button2:
                double power2 = Math.pow(2, Double.parseDouble(num2));
                String rp2 = df.format(power2);
                result2.setText(rp2);
                break;
            case R.id.button3:
                double power3 = Math.pow(3, Double.parseDouble(num3));
                String rp3 = df.format(power3);
                result3.setText(rp3);
                break;
            case R.id.button4:
                double power4 = Math.pow(4, Double.parseDouble(num4));
                String rp4 = df.format(power4);
                result4.setText(rp4);
                break;
            case R.id.button5:
                double power5 = Math.pow(5, Double.parseDouble(num5));
                String rp5 = df.format(power5);
                result5.setText(rp5);
                break;
        }
    }
}