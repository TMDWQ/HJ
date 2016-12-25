package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.micro.android316.housekeeping.R;

/**
 * Created by Administrator on 2016/12/13.
 */
public class GuideActivity extends Activity{
    ImageView startImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);
        startImageView = (ImageView) findViewById(R.id.start_imageview);
        startAlpnaAnimation();
    }
    public void startAlpnaAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f,0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setRepeatCount(0);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(GuideActivity.this,Login.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startImageView.startAnimation(alphaAnimation);
    }
}
