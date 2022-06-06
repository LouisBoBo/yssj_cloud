package com.yssj.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.xuexiang.xui.utils.StatusBarUtils;
import com.yssj.myapplication.base.BaseActivity;
import com.yssj.myapplication.bean.BaseBean;
import com.yssj.myapplication.databinding.ActivityHomeBinding;
import com.yssj.myapplication.http.BeanResponseListener;
import com.yssj.myapplication.http.HttpApi;
import com.yssj.myapplication.http.HttpRequest;
import com.yssj.myapplication.utils.TokenUtils;
import com.yssj.myapplication.utils.XToastUtils;
import com.kongzue.baseokhttp.util.JsonMap;
import com.kongzue.baseokhttp.util.Parameter;
import com.mob.MobSDK;
import com.xuexiang.xui.XUI;

import static com.yssj.myapplication.R.drawable.ic_launcher_background;

public class HomePageActivity extends BaseActivity {
    private Context mcontext;
    private TextView myorder_btn;
    private TextView mywork_btn;

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //设置沉浸式状态栏
        StatusBarUtils.translucent(this);

        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStatusBarColor(getActivity(),R.color.pink_color);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏


        mcontext = this;

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //初始化应用主题
        XUI.initTheme(this);

        //回传用户隐私授权结果
        MobSDK.submitPolicyGrantResult(true, null);

        binding.myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "近期即将上线，敬请期待",
                        Toast.LENGTH_SHORT).show();
            }
        });

        binding.mywork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TokenUtils.getToken() != null && TokenUtils.getToken().length()>0){
                    startActivity(MainActivity.class);
                    finish();
                }else {

                    Intent intent = new Intent(getActivity(), Loginactivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("isfirstlogin", "1");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);

                    finish();
                }

            }
        });
    }

}
