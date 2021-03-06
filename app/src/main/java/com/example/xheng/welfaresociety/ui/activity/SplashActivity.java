package com.example.xheng.welfaresociety.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.model.dao.DBUser;
import com.example.xheng.welfaresociety.model.utils.SharedPreferencesUtils;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

public class SplashActivity extends AppCompatActivity {
    TextView tvSecond;
    MyCountDownTimer myCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        mySplash();
    }
    private void mySplash() {
        myCountDownTimer = new MyCountDownTimer(5000, 1000);
        myCountDownTimer.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = FuLiApplication.getUser();
                String username = SharedPreferencesUtils.getmUtils().getUserName();
                if (user == null && username != null) {
                    user = DBUser.getInstance(SplashActivity.this).getUserInfo(username);
                    Log.d("user", "SplashActivity,user:" + user.toString());
                    FuLiApplication.setUser(user);
                }
                MFGT.gotoMain(SplashActivity.this);
                MFGT.finish(SplashActivity.this);
            }
        }, 5000);
    }

    private void initView() {
        tvSecond = (TextView) findViewById(R.id.tv_second);
    }
    class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvSecond.setTextSize(15);
            tvSecond.setText("倒计时(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            tvSecond.setText("正在跳转");
        }
    }
}
