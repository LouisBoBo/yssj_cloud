package com.yssj.myapplication.wxapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yssj.myapplication.MyApplication;
import com.yssj.myapplication.bean.WXEventBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import baseokhttp3.Call;
import baseokhttp3.Callback;
import baseokhttp3.OkHttpClient;
import baseokhttp3.Request;
import baseokhttp3.Response;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //调用app类中的getApi方法，获取mApi，切记这句必写
        MyApplication.getApi().handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode){
            //登陆成功
            case BaseResp.ErrCode.ERR_OK:
                //登陆成功，获取code值
                String code = ((SendAuth.Resp) baseResp).code;
                Log.e("Code++", code);
                //通过EventBus将获取到的code值，传递到登录页面
                WXEventBean wxEventBean = new WXEventBean();
                wxEventBean.setCode(code);
                EventBus.getDefault().postSticky(wxEventBean);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this, "授权出错", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this, "取消登录", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                finish();
                break;
        }
    }
}