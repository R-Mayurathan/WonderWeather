package com.example.wonderweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=4500;
    //Variables
    Animation topAnim, bottomAnim;
    TextView fname,sname,Des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        fname=findViewById(R.id.textView3);
        sname=findViewById(R.id.textView6);
        Des=findViewById(R.id.textView5);

        fname.setAnimation(topAnim);
        sname.setAnimation(topAnim);
        Des.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_SCREEN);
    }
}