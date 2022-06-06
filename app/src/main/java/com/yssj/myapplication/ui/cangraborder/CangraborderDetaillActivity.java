package com.yssj.myapplication.ui.cangraborder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.kongzue.baseokhttp.util.Parameter;
import com.yssj.myapplication.Loginactivity;
import com.yssj.myapplication.R;
import com.yssj.myapplication.base.BaseActivity;
import com.yssj.myapplication.bean.Constant;
import com.yssj.myapplication.bean.GrabOrderBean;
import com.yssj.myapplication.bean.GrabOrderDetailBean;
import com.yssj.myapplication.databinding.ActivityCangraborderDatailBinding;
import com.yssj.myapplication.http.BeanResponseListener;
import com.yssj.myapplication.http.HttpApi;
import com.yssj.myapplication.http.HttpRequest;
import com.yssj.myapplication.ui.cangraborder.Dialog.CangraborderDialog;
import com.yssj.myapplication.utils.XToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.yssj.myapplication.R.color.pink_color;

public class CangraborderDetaillActivity extends BaseActivity {
    private ActivityCangraborderDatailBinding binding;
    private GrabOrderDetailBean grabOrderDetailBean;
    private List<ImageView> imageViews = new ArrayList<>();
    private String id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCangraborderDatailBinding.inflate(getLayoutInflater());
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

    @SuppressLint("ResourceAsColor")
    public void initView(){

        binding.tvName.setText(grabOrderDetailBean.getGrabOrders().getName()+"-"+grabOrderDetailBean.getGrabOrders().getShop_code());
        binding.tvColor.setText(grabOrderDetailBean.getGrabOrders().getColor());
        binding.tvOrderNum.setText(grabOrderDetailBean.getGrabOrders().getShop_code());
        binding.tvReceiveAdress.setText(grabOrderDetailBean.getGrabOrders().getReceive_address().getAddress());
        binding.tvReciiveTime.setText(grabOrderDetailBean.getGrabOrders().getPush_date());
        binding.tvMachineTime.setText(String.valueOf("抢单后"+grabOrderDetailBean.getGrabOrders().getDelivery_time())+"小时内");
        binding.tvRestoreAdress.setText(grabOrderDetailBean.getGrabOrders().getRestore_address().getAddress());

        imageViews.add(binding.headImg1);
        imageViews.add(binding.headImg2);
        imageViews.add(binding.headImg3);
        imageViews.add(binding.headImg4);
        imageViews.add(binding.headImg5);
        imageViews.add(binding.headImg6);

        String[] sArr = grabOrderDetailBean.getPic_list().split(",");
        for (int i=0;i<imageViews.size();i++){
            ImageView imageView = imageViews.get(i);
            String imageurl = HttpApi.YSS_IMGURL + grabOrderDetailBean.getGrabOrders().getShop_code().substring(1,4) + "/" + grabOrderDetailBean.getGrabOrders().getShop_code() + "/" + sArr[i+1];
            Glide.with(getActivity()).load(imageurl).into(imageView);
        }

        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    CangraborderDialog dialog = new CangraborderDialog(getActivity(),grabOrderDetailBean.getGrabOrders(),Integer.parseInt(id));
                    dialog.show();
                }
            }
        });

    }

    public void initData(){
        Parameter parameter = new Parameter();
        parameter.put("version", HttpApi.VERSION_CODE);
        parameter.put("id",id);

        mMessageLoader.show();
        HttpRequest.POST(getActivity(), HttpApi.GRABORDERS_QUERYDETAIL, parameter, new BeanResponseListener<GrabOrderDetailBean>() {

            @Override
            public void onResponse(GrabOrderDetailBean bean, Exception error) {

                mMessageLoader.dismiss();
                if(error == null){
                    grabOrderDetailBean = bean;

                    initView();
                }
            }
        });
    }
}
