package com.example.covid19app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    ImageView image;
    TextView text;
    Animation tob_animation,bottom_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        },2000);
        image= findViewById(R.id.image);
        text = findViewById(R.id.text);
        tob_animation= AnimationUtils.loadAnimation(this,R.anim.tob_animation);
        bottom_animation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        image.setAnimation(tob_animation);
        text.setAnimation(bottom_animation);

    }
}