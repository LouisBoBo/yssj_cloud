package com.yssj.myapplication.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kongzue.baseokhttp.util.Parameter;
import com.yssj.myapplication.R;
import com.yssj.myapplication.base.BaseActivity;
import com.yssj.myapplication.bean.GrabOrderDetailBean;
import com.yssj.myapplication.bean.MywalletBean;
import com.yssj.myapplication.http.BeanResponseListener;
import com.yssj.myapplication.http.HttpApi;
import com.yssj.myapplication.http.HttpRequest;

public class AccountBalanceActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_balance;
    private TextView tv_balance_month;
    private TextView tv_balance_total;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);

        initView();
        initData();
    }

    public void initView() {
        tv_balance = findViewById(R.id.tv_balance);
        tv_balance_month = findViewById(R.id.tv_balance_month);
        tv_balance_total = findViewById(R.id.tv_balance_total);

        ImageView backimg = findViewById(R.id.headview_back_img);
        backimg.setOnClickListener(this::onClick);

        TextView mywork = findViewById(R.id.mywork);
        mywork.setOnClickListener(this::onClick);
    }

    public void initData(){
        Parameter parameter = new Parameter();
        parameter.put("version", HttpApi.VERSION_CODE);

        mMessageLoader.show();
        HttpRequest.POST(getActivity(), HttpApi.WALLET_MYWALLET, parameter, new BeanResponseListener<MywalletBean>() {

            @Override
            public void onResponse(MywalletBean bean, Exception error) {

                mMessageLoader.dismiss();
                if(error == null){
                    tv_balance.setText(String.valueOf(bean.getBalance()));
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.headview_back_img) {
            onBackPressed();
        } else if (view.getId() == R.id.mywork) {
            startActivity(AccountDetailsActivity.class);
        }
    }
}
