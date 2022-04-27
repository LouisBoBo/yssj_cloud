package com.yssj.myapplication.ui.cangraborder.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yssj.myapplication.R;
import com.yssj.myapplication.bean.GrabOrderDetailBean;
import com.yssj.myapplication.ui.cangraborder.Adapter.CangraSizeAdapter;
import com.yssj.myapplication.ui.cangraborder.Adapter.CangraborderAdapter;

public class CangraborderDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private int requestCode;
    private RecyclerView myrecyclerview;
    private TextView tip_title;
    private CangraSizeAdapter adapter;
    private GrabOrderDetailBean.GrabOrdersDTO grabOrdersDTO;

    public CangraborderDialog(Context context,GrabOrderDetailBean.GrabOrdersDTO bean) {
        super(context, R.style.my_invate_dialog);
        this.context=context;
        this.grabOrdersDTO = bean;
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
        CangraborderSuccessDialog dialog = new CangraborderSuccessDialog(getContext());
        dialog.show();
    }
}
