package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

public class Logo extends AppCompatActivity {
    RelativeLayout logoLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        logoLayout=findViewById(R.id.logoLayout);
        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(2000);
        animation1.setFillAfter(true);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // pass it visible before starting the animation
                logoLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {    }
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent=new Intent();
                intent.setClass(Logo.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
        logoLayout.setAnimation(animation1);
    }
}
