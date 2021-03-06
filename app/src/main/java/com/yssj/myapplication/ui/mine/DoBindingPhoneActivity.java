package com.yssj.myapplication.ui.mine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.kongzue.baseokhttp.util.Parameter;
import com.yssj.myapplication.R;
import com.yssj.myapplication.base.BaseActivity;
import com.yssj.myapplication.bean.AuthKeyTools;
import com.yssj.myapplication.bean.BaseBean;
import com.yssj.myapplication.bean.Constant;
import com.yssj.myapplication.bean.MD5;
import com.yssj.myapplication.databinding.ActivityBindingPhoneBinding;
import com.yssj.myapplication.databinding.ActivityDoBindingPhoneBinding;
import com.yssj.myapplication.http.BeanResponseListener;
import com.yssj.myapplication.http.HttpApi;
import com.yssj.myapplication.http.HttpRequest;
import com.yssj.myapplication.utils.CodeUtils;
import com.yssj.myapplication.utils.PeterTimeCountRefresh;
import com.yssj.myapplication.utils.Util;
import com.yssj.myapplication.utils.XToastUtils;
import com.yssj.network.YConn;

import java.net.URL;

public class DoBindingPhoneActivity extends BaseActivity implements View.OnClickListener {
    ActivityDoBindingPhoneBinding binding;

    private PeterTimeCountRefresh timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoBindingPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    public void initView(){

        //手机号输入
        binding.etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==11){
                    getVodeHttp();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //输入图片验证码
        binding.etAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("sfjsajfj","1");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("sfjsajfj","2");
                binding.btnNextStepNext.setBackgroundResource(charSequence.length()>=4? R.drawable.btn_pink:R.drawable.btn_back);
//                binding.llGetCode.setVisibility(charSequence.length()>=4?View.VISIBLE:View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("sfjsajfj","3");
            }
        });

        //验证码输入
        binding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.btnNextStepNext.setText(charSequence.length()>=4?"绑定":"下一步");
                binding.btnNextStepNext.setBackgroundResource(charSequence.length()>=4? R.drawable.btn_pink:R.drawable.btn_back);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.headviewBackImg.setOnClickListener(this::onClick);
        binding.ivGif.setOnClickListener(this::onClick);
//        binding.ivGif.setImageBitmap(CodeUtils.getInstance().createBitmap(this));
        binding.btnNextStepNext.setOnClickListener(this::onClick);
        binding.ivCode.setOnClickListener(this::onClick);
    }

    //获取图片验证码
    public void getVodeHttp(){
        Parameter parameter = new Parameter();

        SharedPreferences sp = getActivity().getSharedPreferences("logindata", MODE_PRIVATE);
        parameter.put("phone",binding.etPhoneNum.getText());

        String ss = HttpApi.VCODE_GETVCODE + "?" + parameter.toParameterString();
        String authKey = null;
        try {
            authKey = AuthKeyTools.builderURL(ss,"yunshangshiji");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String I10o = YConn.test(authKey);

        String murl = ss + "&"
                + "authKey=" + authKey + "&"
                + "I10o=" + I10o + "&"
                + "channel=" + "18" + "&"
                + "imei=" + "" + "&"
                + "appVersion=" + HttpApi.APP_VERSION + "&"
                + "version=" + HttpApi.VERSION_CODE;

        Util.getPicture(getActivity(),binding.ivGif,murl);
    }

    //获取短信验证码
    public void getPhoneCodeHttp(){
        Parameter parameter = new Parameter();

        parameter.put("phone",binding.etPhoneNum.getText().toString());
        parameter.put("codetype",7);
        parameter.put("merge",1);
        parameter.put("vcode",binding.etAuto.getText().toString());


        HttpRequest.POST(getActivity(), HttpApi.USER_GET_PHONE_CODE, parameter, new BeanResponseListener<BaseBean>() {

            @Override
            public void onResponse(BaseBean lampDeviceListBean, Exception error) {
                mMessageLoader.dismiss();
                if(error == null){
                    XToastUtils.toast("操作成功");

                    binding.relPhoneNum.setVisibility(View.GONE);
                    binding.llGetPiccode.setVisibility(View.GONE);

                    binding.llGetCode.setVisibility(View.VISIBLE);
                    binding.etPwdRl.setVisibility(View.VISIBLE);

                    binding.btnNextStepNext.setText("绑定");
                    binding.btnNextStepNext.setBackgroundResource(R.drawable.btn_back);

                    timer = new PeterTimeCountRefresh(120000, 1000, binding.ivCode);
                    timer.start();
                }
            }

        });
    }

    //验证手机
    public void checkPhoneHttp(){
        Parameter parameter = new Parameter();
        parameter.put("phone",binding.etPhoneNum.getText().toString());

        HttpRequest.POST(getActivity(), HttpApi.USER_CHECKPHONE, parameter, new BeanResponseListener<BaseBean>() {

            @Override
            public void onResponse(BaseBean lampDeviceListBean, Exception error) {
                mMessageLoader.dismiss();
                if(error == null){
                    checkPhoneCodeHttp();
                }
            }

        });
    }

    //验证code绑定手机
    public void checkPhoneCodeHttp(){
        Parameter parameter = new Parameter();
        parameter.put("code",binding.etCode.getText().toString());
        parameter.put("pwd",MD5.getMessageDigest(binding.etPwd.toString().getBytes()));

        HttpRequest.POST(getActivity(), HttpApi.CHECKCODE, parameter, new BeanResponseListener<BaseBean>() {

            @Override
            public void onResponse(BaseBean lampDeviceListBean, Exception error) {
                mMessageLoader.dismiss();
                if(error == null){
                    XToastUtils.toast("操作成功");
                    onBackPressed();
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        if(view == binding.headviewBackImg){
            onBackPressed();
        }else if(view == binding.ivGif){
//            binding.ivGif.setImageBitmap(CodeUtils.getInstance().createBitmap(this));
            getVodeHttp();
        }else if(view == binding.btnNextStepNext){
            if(binding.btnNextStepNext.getText().equals("下一步")){
                if(binding.etPhoneNum.getText().length() ==0)
                {
                    XToastUtils.toast("请输入正确的手机号");
                    return;
                }
                if(binding.etAuto.getText().length() !=4){
                    XToastUtils.toast("请输入正确的图片验证码");
                    return;
                }

                getPhoneCodeHttp();

//                SharedPreferences sp = getActivity().getSharedPreferences("piccode", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                String pic_code = sp.getString(Constant.PIC_CODE,"");
//                editor.commit();

//                String input_code = binding.etAuto.getText().toString();
//                if(input_code.equals(pic_code))
//                {
//                    getPhoneCodeHttp();
//                }else {
//                    XToastUtils.toast("图片验证码有误");
//                    return;
//                }
            }else if(binding.btnNextStepNext.getText().equals("绑定")){
                if(binding.etCode.getText().length() !=4){
                    XToastUtils.toast("请输入正确的手机验证码");
                    return;
                }
                if(binding.etPwd.getText().length()<6 || binding.etPwd.getText().length() >12)
                {
                    XToastUtils.toast("密码输入有误");
                    return;
                }
                checkPhoneHttp();//先验证手机再验证短信
            }
        }else if(view == binding.ivCode && binding.ivCode.getText().equals("重新获取")){

            getPhoneCodeHttp();

//            timer = new PeterTimeCountRefresh(120000, 1000, binding.ivCode);
//            timer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
