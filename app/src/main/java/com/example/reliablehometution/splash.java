package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;
    Animation topAnim;
    Animation bottomAnim;

    private ImageView image;
    private TextView logo;
    private TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

            image = (ImageView) findViewById(R.id.imageView);
            logo = findViewById(R.id.textView);
            //slogan = findViewById(R.id.textView2);

            topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
            bottomAnim = AnimationUtils.loadAnimation(this, R.anim.botttom_animation);

            image.setAnimation(topAnim);
            logo.setAnimation(bottomAnim);
            //slogan.setAnimation(bottomAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(splash.this , MainActivity.class );
                    startActivity(intent);
                    finish();
                }
            },SPLASH_SCREEN);
    }
}