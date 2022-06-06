package com.yssj.myapplication.ui.robbedorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.kongzue.baseokhttp.util.Parameter;
import com.yssj.myapplication.Loginactivity;
import com.yssj.myapplication.R;
import com.yssj.myapplication.base.BaseActivity;
import com.yssj.myapplication.bean.Constant;
import com.yssj.myapplication.bean.GrabOrderDetailBean;
import com.yssj.myapplication.bean.UserGrabOrderDetailBean;
import com.yssj.myapplication.databinding.ActivityCangraborderDatailBinding;
import com.yssj.myapplication.databinding.ActivityGraborderDetailBinding;
import com.yssj.myapplication.http.BeanResponseListener;
import com.yssj.myapplication.http.HttpApi;
import com.yssj.myapplication.http.HttpRequest;
import com.yssj.myapplication.ui.cangraborder.Dialog.CangraborderDialog;
import com.yssj.myapplication.utils.XToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GrabOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityGraborderDetailBinding binding;
    private UserGrabOrderDetailBean grabOrderDetailBean;
    private List<ImageView> imageViews = new ArrayList<>();
    private String id;
    private CountDownTimer timer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGraborderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        binding.headviewBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initData();
    }

    public void initView(){
        binding.tvName.setText(grabOrderDetailBean.getUserGrabOrders().getName());
        binding.tvColor.setText(grabOrderDetailBean.getUserGrabOrders().getColor());
        binding.tvOrderNum.setText(grabOrderDetailBean.getUserGrabOrders().getCode());
        binding.tvArticleNumber.setText(grabOrderDetailBean.getUserGrabOrders().getBatch_num());
        binding.tvReceiveAdress.setText(grabOrderDetailBean.getUserGrabOrders().getReceive_address().getAddress());
        binding.tvReceiveTime.setText(grabOrderDetailBean.getUserGrabOrders().getReceive_date());

        binding.tvRestoreAddress.setText(grabOrderDetailBean.getUserGrabOrders().getRestore_address().getAddress());
        binding.tvHandoverMember.setText(grabOrderDetailBean.getUserGrabOrders().getRestore_address().getConsignee()+" "+grabOrderDetailBean.getUserGrabOrders().getRestore_address().getPhone());
        binding.tvQc.setText(getqc(grabOrderDetailBean.getUserGrabOrders().getQc_status()));
        binding.tvProcessCost.setText(String.valueOf(grabOrderDetailBean.getUserGrabOrders().getMoney())+(+ grabOrderDetailBean.getUserGrabOrders().getQc_status()==4?"已到账":""));

        String str_time = getTime(grabOrderDetailBean.getUserGrabOrders().getCreate_date());
        getCountDownTime(getStrTime(str_time));

        imageViews.add(binding.headImg1);
        imageViews.add(binding.headImg2);
        imageViews.add(binding.headImg3);
        imageViews.add(binding.headImg4);
        imageViews.add(binding.headImg5);
        imageViews.add(binding.headImg6);

        String[] sArr = grabOrderDetailBean.getPic_list().split(",");
        for (int i=0;i<imageViews.size();i++){
            ImageView imageView = imageViews.get(i);
            String imageurl = HttpApi.YSS_IMGURL + grabOrderDetailBean.getUserGrabOrders().getShop_code().substring(1,4) + "/" + grabOrderDetailBean.getUserGrabOrders().getShop_code() + "/" + sArr[i+1];
            Glide.with(getActivity()).load(imageurl).into(imageView);
        }

        if(grabOrderDetailBean.getUserGrabOrders().getQc_status()==0 || grabOrderDetailBean.getUserGrabOrders().getQc_status()==1){
            binding.confirm.setText("交回裁片");
            binding.confirm.setBackgroundResource(R.color.pink_color);
            binding.confirm.setOnClickListener(this::onClick);
        }else{
            binding.confirm.setText("裁片已交回");
            binding.tvOverTime.setText("已交货");
            binding.confirm.setBackgroundResource(R.color.gray_color);
        }
    }

    //获取详情信息
    public void initData(){
        Parameter parameter = new Parameter();
        parameter.put("version", HttpApi.VERSION_CODE);
        parameter.put("id",id);

        mMessageLoader.show();
        HttpRequest.POST(getActivity(), HttpApi.USERGRABORDERS_QUERYDETAIL, parameter, new BeanResponseListener<UserGrabOrderDetailBean>() {

            @Override
            public void onResponse(UserGrabOrderDetailBean bean, Exception error) {

                mMessageLoader.dismiss();
                if(error == null){
                    grabOrderDetailBean = bean;

                    initView();
                }
            }
        });
    }

    //交回裁片
    public void restoreHttp(){

        Parameter parameter = new Parameter();
        parameter.put("version", HttpApi.VERSION_CODE);
        parameter.put("id",id);
        parameter.put("qc_status",2);

        mMessageLoader.show();
        HttpRequest.POST(getActivity(), HttpApi.USERGRABORDERS_UPDATEBYSELECTIVE, parameter, new BeanResponseListener<UserGrabOrderDetailBean>() {

            @Override
            public void onResponse(UserGrabOrderDetailBean bean, Exception error) {

                mMessageLoader.dismiss();
                if(error == null){
                    binding.confirm.setText("裁片已交回");
                    binding.confirm.setBackgroundResource(R.color.gray_color);
                    XToastUtils.toast("操作成功");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == binding.confirm){
            SharedPreferences sp = getActivity().getSharedPreferences("logindata", MODE_PRIVATE);
            String token= sp.getString(Constant.USER_TOKEN,"");

            //没有登录去登录
            if(token.length() == 0){
                Intent intent = new Intent(getActivity(), Loginactivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("isfirstlogin", "0");
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            }else {

                restoreHttp();
            }
        }
    }

    // 将字符串转为时间戳
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        }catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return re_time;
    }

    // 将时间戳转为剩余毫秒数
    public static long getStrTime(String cc_time) {
        //当前时间毫秒数
        long currentTime = System.currentTimeMillis();

        //剩余毫秒数
        long lcc_time = (Long.valueOf(cc_time)+48*60*60)*1000 - currentTime;

        return lcc_time;
    }

    // 获取订单状态
    public static String getqc(int qc_status){
        String qc_str = "";

        switch (qc_status){
            case 0:
                qc_str="订单创建完成";
                break;
            case 1:
                qc_str="已领裁片";
                break;
            case 2:
                qc_str="交回裁片";
                break;
            case 3:
                qc_str="缝合完成";
                break;
            case 4:
                qc_str="订单完结";
                break;

        }

        return qc_str;
    }

    //倒计时
    private void getCountDownTime(long timeStemp) {
        if(timeStemp <=0){
            binding.tvOverTime.setText("剩余时间：" + 0 + "小时" + 0 + "分钟" + 0 + "秒");
            return;
        }

        timer = new CountDownTimer(timeStemp, 1000) {
            @Override
            public void onTick(long l) {

                long day = l / (1000 * 24 * 60 * 60); //单位天
                long hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
                long minute = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
                long second = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒

                long H = day*24+hour;
                binding.tvOverTime.setText("剩余时间：" + H + "小时" + minute + "分钟" + second + "秒");
            }

            @Override
            public void onFinish() {
                //倒计时为0时执行此方法
                binding.tvOverTime.setText("剩余时间：" + 0 + "小时" + 0 + "分钟" + 0 + "秒");
                timer.cancel();
            }
        };

        timer.start();
    }
}
