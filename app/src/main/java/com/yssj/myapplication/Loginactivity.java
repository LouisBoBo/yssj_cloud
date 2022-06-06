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
        //获取Code值
        mCode = wxEventBean.getCode();
        Log.e("CODE=",""+mCode);

        getAccessToken(mCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStatusBarColor(getActivity(),R.color.pink_color);

        setContentView(R.layout.activity_login);
        mcontext = this;

        login_btn = findViewById(R.id.login_wx_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApplication.getApi()!=null){
                    if (!MyApplication.getApi().isWXAppInstalled()) {
//                        ToastUtils.setToastTextview(WXLoginActivity.this, "您的设备未安装微信客户端");
//                        ToastUtils.show();
                    }else {
                        SendAuth.Req req = new SendAuth.Req();
                        req.scope="snsapi_userinfo";
                        req.state="wx_cc";
                        MyApplication.getApi().sendReq(req);
                    }
                }

//                getWeiXin();//微信授权登录
            }
        });

        right_btn = findViewById(R.id.headview_back_img);
        right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

//        regToWx();


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
                            XToastUtils.toast("请求成功");

                            //存入数据
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
     * 登录成功的处理
     */
    private void onLoginSuccess(String token) {
        if (TokenUtils.handleLoginSuccess(token)) {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            String isfirstlogin = b.getString("isfirstlogin");

            //判断是否是从homepage跳转过来
            if (isfirstlogin !=null && isfirstlogin.equals("1")) {
                startActivity(new Intent(Loginactivity.this, MainActivity.class));
                finish();
            }else {
                finish();
            }
        }
    }

    /*
     * 第三方微信登录
     * */
    public void getWeiXin(){

        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        plat.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false); //SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权
        plat.setPlatformActionListener(new PlatformActionListener() {//授权回调监听，监听oncomplete，onerror，oncancel三种状态
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if(i == Platform.ACTION_AUTHORIZING)//要功能不要数据
                {
                    //直接跳转到登陆成功的界面
                    Log.e("info","登陆成功");
                }
                else if(i == Platform.ACTION_USER_INFOR)//要数据不要功能
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
                Log.e("info","登陆失败");            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.e("info","登陆失败");            }
        });
        if (plat.isClientValid()) {
            //todo 判断是否存在授权凭条的客户端，true是有客户端，false是无
        }
        if (plat.isAuthValid()) {
            //todo 判断是否已经存在授权状态，可以根据自己的登录逻辑设置
            Toast.makeText(this, "已经授权过了", Toast.LENGTH_SHORT).show();
            return;
        }
        //plat.authorize();    //要功能，不要数据
        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面
    }

    /**
     * 获取access_token
     * @param code
     */
    private void getAccessToken(String code) {
//        createProgressDialog();
        //获取授权
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

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(loginUrl.toString())
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fan12", "onFailure: ");
//                mProgressDialog.dismiss();
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
                    if(access_token != null){
                        getWeiXin();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("access===============", access);
            }
        });

    }



//    /**
//     * 获取授权口令
//     */
//    private void getAccessToken(String code) {
//        Parameter parameter = new Parameter();
//        String url = "https://api.weixin.qq.com/sns/oauth3/access_token?" +
//                "appid=" + "wxec61c309c2e852c2" +
//                "&secret=" + "a75b13271fc4a3f60937ee805ad92a3e" +
//                "&code=" + code +
//                "&grant_type=authorization_code";
//
//
//        HttpRequest.POST(getActivity(), url, parameter, new BeanResponseListener<LoginInfoBean>() {
//
//                    @Override
//                    public void onResponse(LoginInfoBean bean, Exception error) {
//                        mMessageLoader.dismiss();
//                        if(error == null){
//                            XToastUtils.toast("请求成功");
//
//                        }
//                    }
//                });
//    }
}
