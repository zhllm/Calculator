package com.beyourself.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

public class MainActivity_TestMeter extends AppCompatActivity {
    MyChronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__test_meter);
        chronometer = findViewById(R.id.meter);
        getLifecycle().addObserver(chronometer);
    }

}