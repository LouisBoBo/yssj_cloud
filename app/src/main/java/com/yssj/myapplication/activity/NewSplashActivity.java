package com.yssj.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
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
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

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
