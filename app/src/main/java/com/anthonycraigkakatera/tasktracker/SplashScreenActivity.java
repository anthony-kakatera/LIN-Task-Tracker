package com.anthonycraigkakatera.tasktracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen_layout);
        //dissapearing title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}
