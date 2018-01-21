package com.example.lenovo.renterskey.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.renterskey.R;


public class SplashActivity extends  FragmentActivity{

    private Animation animation;
    private ImageView logo;
    private TextView appTitle;
    private TextView appSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo=findViewById(R.id.logo_img);
        appTitle=findViewById(R.id.app_name);
        appSlogan=findViewById(R.id.app_slogan);

        if(savedInstanceState==null){
            flyIn();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                endSplash();
            }
        },3000);
    }

    private void flyIn(){
        animation= AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logo.startAnimation(animation);

        animation=AnimationUtils.loadAnimation(this, R.anim.app_name_animation);
        appTitle.startAnimation(animation);

        animation=AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        appSlogan.startAnimation(animation);
    }

    private void endSplash(){
        animation=AnimationUtils.loadAnimation(this, R.anim.logo_animation_back);
        logo.startAnimation(animation);

        animation=AnimationUtils.loadAnimation(this, R.anim.app_name_animation_back);
        appTitle.startAnimation(animation);

        animation=AnimationUtils.loadAnimation(this, R.anim.pro_animation_back);
        appSlogan.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent=new Intent(getApplicationContext(),StartingActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
