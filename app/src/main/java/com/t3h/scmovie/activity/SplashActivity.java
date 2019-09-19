package com.t3h.scmovie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.transition.ArcMotion;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;

public class SplashActivity  extends Activity {
    private ProgressBar prg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initAct();
    }

    protected void initAct() {
        prg = findViewById(R.id.prg_bar);
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.translate);
        ImageView img = findViewById(R.id.img_logo);
        animation.reset();
        img.clearAnimation();
        img.startAnimation(animation);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();

            }
        }, 3000);
    }

}
