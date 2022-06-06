package com.yssj.myapplication;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.myapplication.utils.TokenUtils;
import com.mob.MobSDK;

public class MyApplication extends Application {
    public static final String APP_ID = "wxec61c309c2e852c2";
    private static IWXAPI mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        TokenUtils.init(this);

        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        mApi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        //注册到微信
        mApi.registerApp(APP_ID);
    }

    public static IWXAPI getApi(){
        return mApi;
    }
}
