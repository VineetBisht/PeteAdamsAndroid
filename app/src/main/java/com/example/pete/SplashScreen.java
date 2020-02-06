package com.example.pete;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    int SPLASH = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final ImageView pic = findViewById(R.id.SplashImage);
        TextView firm = findViewById(R.id.SplashName);
        TextView caption = findViewById(R.id.SplashName);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        pic.setAnimation(topAnim);
        firm.setAnimation(bottomAnim);
        caption.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                Pair[] pair=new Pair[1];
                pair[0] = new Pair<View,String>(pic,"logo_image");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pair);
                startActivity(intent,options.toBundle());
                finish();
            }
        }, SPLASH);
    }
}
