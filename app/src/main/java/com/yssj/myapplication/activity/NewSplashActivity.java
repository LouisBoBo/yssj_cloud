package com.yssj.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.yssj.myapplication.HomePageActivity;
import com.yssj.myapplication.R;
import com.yssj.myapplication.base.BaseActivity;
import com.yssj.myapplication.utils.SSLAgent;

import java.util.Timer;
import java.util.TimerTask;

public class NewSplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStatusBarColor(getActivity(),R.color.pink_color);

        setContentView(R.layout.activity_splash);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SSLAgent.getInstance().trustAllHttpsCertificates();
                loginOrGoMainPage();
            }
        }, 2000);
    }

    private void loginOrGoMainPage() {
        startActivity(new Intent(NewSplashActivity.this, HomePageActivity.class));
        finish();
    }
}
