package com.yssj.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.myapplication.base.BaseActivity;
import com.yssj.myapplication.bean.Constant;
import com.yssj.myapplication.bean.LoginInfoBean;
import com.yssj.myapplication.bean.MD5Tools;
import com.yssj.myapplication.bean.WXEventBean;
import com.yssj.myapplication.http.BeanResponseListener;
import com.yssj.myapplication.http.HttpApi;
import com.yssj.myapplication.http.HttpRequest;
import com.yssj.myapplication.http.SSLSocketManager;
import com.yssj.myapplication.utils.TokenUtils;
import com.yssj.myapplication.utils.XToastUtils;
import com.kongzue.baseokhttp.util.Parameter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import baseokhttp3.Call;
import baseokhttp3.Callback;
import baseokhttp3.OkHttpClient;
import baseokhttp3.Request;
import baseokhttp3.Response;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class Loginactivity extends BaseActivity {
    private Context mcontext;
    private TextView login_btn;
    private ImageView right_btn;

    private String mCode;
    private String access_token;

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(WXEventBean wxEventBean){
        //??????Code???
        mCode = wxEventBean.getCode();
        Log.e("CODE=",""+mCode);

        getAccessToken(mCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        //???????????????
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStatusBarColor(getActivity(),R.color.pink_color);

        setContentView(R.layout.activity_login);
        mcontext = this;

        login_btn = findViewById(R.id.login_wx_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //???????????????????????? ????????????????????????
                if (MyApplication.getApi()!=null){
                    if (!MyApplication.getApi().isWXAppInstalled()) {
//                        ToastUtils.setToastTextview(WXLoginActivity.this, "????????????????????????????????????");
//                        ToastUtils.show();
                    }else {
                        SendAuth.Req req = new SendAuth.Req();
                        req.scope="snsapi_userinfo";
                        req.state="wx_cc";
                        MyApplication.getApi().sendReq(req);
                    }
                }
            }
        });

        right_btn = findViewById(R.id.headview_back_img);
        right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }


    public void loginHttp(String unionid,String openid,String nickname,String headimgurl,String gender){
//                        mMessageLoader.show();
                Parameter parameter = new Parameter();

                parameter.put("version",HttpApi.VERSION_CODE);
                parameter.put("device",1);
                parameter.put("deviceToken","");
                parameter.put("imei","");
                parameter.put("mac","");

                parameter.put("unionid",unionid);
                parameter.put("openid",openid);
                parameter.put("nickname",nickname);
                parameter.put("pic",headimgurl);
                parameter.put("gender",gender);
                parameter.put("usertype",5);
                parameter.put("token",access_token);



                HttpRequest.POST(getActivity(), HttpApi.ACCOUNT_LOGIN, parameter, new BeanResponseListener<LoginInfoBean>() {

                    @Override
                    public void onResponse(LoginInfoBean bean, Exception error) {
                        mMessageLoader.dismiss();
                        if(error == null){
                            XToastUtils.toast("????????????");

                            //????????????
                            SharedPreferences sp = mcontext.getSharedPreferences("logindata", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Constant.USER_TOKEN, bean.getToken());
                            editor.putInt(Constant.USER_ID, bean.getUserinfo().getUser_id());
                            editor.putString(Constant.NICK_NAME, bean.getUserinfo().getNickname());
                            editor.putString(Constant.USET_PIC,bean.getUserinfo().getPic());
                            editor.commit();

                            onLoginSuccess(bean.getToken());
                        }
                    }
                });
    }
    /**
     * ?????????????????????
     */
    private void onLoginSuccess(String token) {
        if (TokenUtils.handleLoginSuccess(token)) {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            String isfirstlogin = b.getString("isfirstlogin");

            //??????????????????homepage????????????
            if (isfirstlogin !=null && isfirstlogin.equals("1")) {
                onBackPressed();
                startActivity(new Intent(getActivity(), MainActivity.class));
                Loginactivity.this.finish();
            }else {
                onBackPressed();
                Loginactivity.this.finish();
            }
        }
    }

    /*
     * ?????????????????????
     * */
    public void getWeiXin(){

        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        plat.removeAccount(true); //???????????????????????????????????????????????????????????????
        plat.SSOSetting(false); //SSO????????????false????????????????????????????????????????????????????????????????????????????????????web??????
        plat.setPlatformActionListener(new PlatformActionListener() {//???????????????????????????oncomplete???onerror???oncancel????????????
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if(i == Platform.ACTION_AUTHORIZING)//?????????????????????
                {
                    //????????????????????????????????????
                    Log.e("info","????????????");
                }
                else if(i == Platform.ACTION_USER_INFOR)//?????????????????????
                {
                    String unionid = "";
                    String openid = "";
                    String nickname = "";
                    String headimgurl = "";
                    String sex = "";
                    for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                        Log.e("info","key:"+entry.getKey()+"\tvalue:"+entry.getValue());

                        if(entry.getKey().toString().equals("unionid")){
                            unionid = entry.getValue().toString();
                        }
                        if(entry.getKey().toString().equals("openid")){
                            openid = entry.getValue().toString();
                        }
                        if(entry.getKey().toString().equals("nickname")){
                            nickname = entry.getValue().toString();
                        }
                        if(entry.getKey().toString().equals("headimgurl")){
                            headimgurl = entry.getValue().toString();
                        }
                        if(entry.getKey().toString().equals("sex")){
                            sex = entry.getValue().toString();
                        }
                        if(unionid.length()>0 && openid.length()>0 && nickname.length()>0&& headimgurl.length()>0 && sex.length()>0){
                            loginHttp(unionid,openid,nickname,headimgurl,sex);
                            break;
                        }
                    }


                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e("info","????????????");            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.e("info","????????????");            }
        });
        if (plat.isClientValid()) {
            //todo ?????????????????????????????????????????????true??????????????????false??????
        }
        if (plat.isAuthValid()) {
            //todo ??????????????????????????????????????????????????????????????????????????????
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        //plat.authorize();    //????????????????????????
        plat.showUser(null);    //?????????????????????????????????????????????????????????????????????
    }

    /**
     * ??????access_token
     * @param code
     */
    private void getAccessToken(String code) {
        //????????????
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append("wxec61c309c2e852c2")
                .append("&secret=")
                .append("a75b13271fc4a3f60937ee805ad92a3e")
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        Log.d("urlurl", loginUrl.toString());

        //??????OkHttpClient??????
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketManager.getSSLSocketFactory())//??????
                .hostnameVerifier(SSLSocketManager.getHostnameVerifier())//??????
                .build();

        final Request request = new Request.Builder()
                .url(loginUrl.toString())
                .get()//????????????GET?????????????????????
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fan12", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo= response.body().string();
                Log.d("fan12", "onResponse: " +responseInfo);
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");

                    access_token = access;
                    if(access != null && openId != null){
                        getUserInfo(access,openId);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * ??????????????????
     */
    public void getUserInfo(String access,String openId){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" +
                access + "&openid=" + openId;

        //??????OkHttpClient??????
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketManager.getSSLSocketFactory())//??????
                .hostnameVerifier(SSLSocketManager.getHostnameVerifier())//??????
                .build();

        final Request request = new Request.Builder()
                .url(url.toString())
                .get()//????????????GET?????????????????????
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fan12", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo= response.body().string();
                Log.d("fan12", "onResponse: " +responseInfo);
                String unionid = "";
                String nickname = "";
                String headimgurl = "";
                String sex = "";
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);

                    unionid = jsonObject.getString("unionid");
                    nickname = jsonObject.getString("nickname");
                    sex = jsonObject.getString("sex");
                    headimgurl = jsonObject.getString("headimgurl");

                    if(unionid.length()>0 && openId.length()>0 && nickname.length()>0&& headimgurl.length()>0 && sex.length()>0){
                        loginHttp(unionid,openId,nickname,headimgurl,sex);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
