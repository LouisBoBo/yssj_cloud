package com.yssj.myapplication.ui.cangraborder.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kongzue.baseokhttp.util.JsonMap;
import com.kongzue.baseokhttp.util.Parameter;
import com.yssj.myapplication.R;
import com.yssj.myapplication.bean.AuthKeyTools;
import com.yssj.myapplication.bean.GrabOrderDetailBean;
import com.yssj.myapplication.http.BeanResponseListener;
import com.yssj.myapplication.http.HttpApi;
import com.yssj.myapplication.http.HttpRequest;
import com.yssj.myapplication.ui.cangraborder.Adapter.CangraSizeAdapter;
import com.yssj.myapplication.ui.cangraborder.Adapter.CangraborderAdapter;
import com.yssj.myapplication.utils.TokenUtils;
import com.yssj.myapplication.utils.XToastUtils;
import com.yssj.network.YConn;

public class CangraborderDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private int requestCode;
    private RecyclerView myrecyclerview;
    private TextView tip_title;
    private CangraSizeAdapter adapter;
    private GrabOrderDetailBean.GrabOrdersDTO grabOrdersDTO;
    private int grab_orders_id;

    public CangraborderDialog(Context context,GrabOrderDetailBean.GrabOrdersDTO bean,int id) {
        super(context, R.style.my_invate_dialog);
        this.context=context;
        this.grabOrdersDTO = bean;
        this.grab_orders_id = id;
        setContentView(R.layout.cangraborder_dialog);
        this.getWindow().setWindowAnimations(R.style.my_dialog_anim_style);

        findViewById(R.id.dialog_confirm).setOnClickListener(this::onClick);

        initView();
    }

    public void initView(){
        myrecyclerview = findViewById(R.id.myrecyclerview);
        tip_title = findViewById(R.id.tip_title);

        GridLayoutManager device_manager = new GridLayoutManager(getContext(),1);
        myrecyclerview.setLayoutManager(device_manager);
        adapter = new CangraSizeAdapter();
        myrecyclerview.setAdapter(adapter);
        adapter.setmDatas(this.grabOrdersDTO.getStock_types());
        adapter.setOnItemClick(new CangraSizeAdapter.OnItemClick() {
            @Override
            public void click(int index, int input_num) {
                grabOrdersDTO.getStock_types().get(index).setInput_num(input_num);
            }
        });
    }
    public void setRequestCode(int code){
        this.requestCode=code;
    }

    @Override
    public void onClick(View v) {
        boolean no_stock = false;
        for (GrabOrderDetailBean.GrabOrdersDTO.StockTypesDTO stock_type : grabOrdersDTO.getStock_types()) {
            if(stock_type.getInput_num() > stock_type.getNum()){
                tip_title.setVisibility(View.VISIBLE);
                no_stock = true;
                break;
            }
        }
        if(no_stock){
            tip_title.setVisibility(View.VISIBLE);
            return;
        }else {
            tip_title.setVisibility(View.GONE);
        }

        dismiss();
        getGrabOrder();

    }

    public void getGrabOrder(){
        com.alibaba.fastjson.JSONArray orders = new JSONArray();
        for (GrabOrderDetailBean.GrabOrdersDTO.StockTypesDTO stock_type : grabOrdersDTO.getStock_types()) {
            com.alibaba.fastjson.JSONObject obj = new JSONObject();
            if(stock_type.getInput_num()>0){
                obj.put("id",stock_type.getId());
                obj.put("num",stock_type.getInput_num());
                orders.add(obj);
            }
        }

        Parameter parameter = new Parameter();
        parameter.put("version", HttpApi.VERSION_CODE);
        parameter.put("grab_orders_id",this.grab_orders_id);

        for(int i=0;i<orders.size();i++){
            JSONObject object = orders.getJSONObject(i);
            String key_id = "stock_types" + "[" + i + "]" + ".id";
            String value_id = object.getString("id");

            String key_num = "stock_types" + "[" + i + "]" + ".num";
            String value_num = object.getString("num");

            parameter.put(key_id,value_id);
            parameter.put(key_num,value_num);
        }


        JsonMap map = new JsonMap();
        map.put("stock_types",orders);
        map.put("id",this.grab_orders_id);
        map.put("channel","18");
        map.put("app_id",HttpApi.APP_ID);
        map.put("appVersion",HttpApi.APP_VERSION);
        map.put("version",HttpApi.VERSION_CODE);
        map.put("version", HttpApi.VERSION_CODE);

        JSONObject body = new JSONObject();
        body.put("stock_types",orders);
        body.put("id",this.grab_orders_id);
        body.put("channel","18");
        body.put("app_id",HttpApi.APP_ID);
        body.put("appVersion",HttpApi.APP_VERSION);
        body.put("version",HttpApi.VERSION_CODE);
        body.put("token", TokenUtils.getToken());

        String ss = HttpApi.USERGRABORDERS_ADDGRABORDERS + "?" + parameter.toParameterString();
        String authKey = null;
        try {
            authKey = AuthKeyTools.builderURL(ss,"yunshangshiji");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String I10o = YConn.test(authKey);

        body.put("authKey",authKey);
        body.put("I10o",I10o);


        HttpRequest.POST(this.context, HttpApi.USERGRABORDERS_ADDGRABORDERS, parameter, new BeanResponseListener<GrabOrderDetailBean>() {

            @Override
            public void onResponse(GrabOrderDetailBean bean, Exception error) {

                if(error == null){
                    CangraborderSuccessDialog dialog = new CangraborderSuccessDialog(getContext());
                    dialog.show();
                }
            }
        });
    }
}
