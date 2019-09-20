package com.t3h.scmovie.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;

public class SplashActivity  extends BaseActivity {
    private ProgressBar prg;

    @Override
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
