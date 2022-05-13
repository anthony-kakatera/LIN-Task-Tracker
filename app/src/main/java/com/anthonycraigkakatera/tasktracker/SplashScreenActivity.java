package com.anthonycraigkakatera.tasktracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SplashScreenActivity extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    //persions to verify
    private String[] permissionArrays = new String[]{Manifest.permission.INTERNET};
    private int REQUEST_CODE = 101;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen_layout);
        //dissapearing title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //used to launch the entire app
        launchApp();
    }

    private void launchApp(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //first verify permissions
                verifyPermissions();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void verifyPermissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissionArrays, REQUEST_CODE);
        }else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean openActivity= false;

        if(requestCode == REQUEST_CODE){
            for(int i = 0; i < grantResults.length; i++){
                String permission = permissions[i];
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    //if the permission hasn't been accepted redisplay the request
                    ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_CODE);
                }else {openActivity = true;}
            }
            if(openActivity){
                //launching the login activity here
                Intent login = new Intent(SplashScreenActivity.this,LoginActivity.class);
                SplashScreenActivity.this.startActivity(login);
                SplashScreenActivity.this.finish();
            }
        }
    }

}
