package com.vyw.differences;

import static com.vyw.differences.R.layout.activity_splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vyw.differences.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_splash_screen);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler(); //A class in java that enables us to create thread
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,Register.class));
                finish();
            }
        },2000);
    }
}