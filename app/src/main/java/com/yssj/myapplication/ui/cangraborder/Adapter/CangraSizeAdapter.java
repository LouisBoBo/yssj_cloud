package com.yssj.myapplication.ui.cangraborder.Adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yssj.myapplication.R;
import com.yssj.myapplication.bean.GrabOrderBean;
import com.yssj.myapplication.bean.GrabOrderDetailBean;

import java.util.List;

public class CangraSizeAdapter extends RecyclerView.Adapter<CangraSizeAdapter.VH> {
    private List<GrabOrderDetailBean.GrabOrdersDTO.StockTypesDTO> areaModelList;


    public CangraSizeAdapter.OnItemClick mOnItemClick;

    public interface OnItemClick {
        void click(int index,int input_num);
    }

    public void setOnItemClick(CangraSizeAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public void setmDatas(List<GrabOrderDetailBean.GrabOrdersDTO.StockTypesDTO> modelList) {
        areaModelList = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CangraSizeAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cangrab_size, parent, false);
        return new CangraSizeAdapter.VH(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CangraSizeAdapter.VH holder, int position) {
        GrabOrderDetailBean.GrabOrdersDTO.StockTypesDTO stockTypesDTO = this.areaModelList.get(position);
        holder.tv_title_size.setText(stockTypesDTO.getName()+"码：");

        holder.ed_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.ed_input.getText().length()>0){
                    mOnItemClick.click(position ,Integer.parseInt(holder.ed_input.getText().toString()));
                }else {
                    mOnItemClick.click(position,0);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (areaModelList == null || areaModelList.size() == 0) {
            return 0;
        } else
            return areaModelList.size();
    }

    //② 创建ViewHolder 绑定item元素
    public static class VH extends RecyclerView.ViewHolder {
        public View baseview;
        public TextView tv_title_size;
        public EditText ed_input;

        public VH(View v) {
            super(v);
            tv_title_size = v.findViewById(R.id.tv_title_size);
            ed_input = v.findViewById(R.id.ed_input);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }
}
