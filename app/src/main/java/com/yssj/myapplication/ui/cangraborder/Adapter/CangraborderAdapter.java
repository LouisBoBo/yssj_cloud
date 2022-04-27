package com.yssj.myapplication.ui.cangraborder.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yssj.myapplication.R;
import com.yssj.myapplication.bean.GrabOrderBean;

import java.util.List;

public class CangraborderAdapter extends RecyclerView.Adapter<CangraborderAdapter.VH> {
    private List<GrabOrderBean.GrabOrderListDTO> areaModelList;


    public CangraborderAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(CangraborderAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public void setmDatas(List<GrabOrderBean.GrabOrderListDTO> modelList) {
        areaModelList = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CangraborderAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cangrab_order, parent, false);
        return new CangraborderAdapter.VH(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CangraborderAdapter.VH holder, int position) {

        GrabOrderBean.GrabOrderListDTO grabOrderListDTO = areaModelList.get(position);
        holder.tv_name.setText(grabOrderListDTO.getName());
        holder.tv_quality.setText(grabOrderListDTO.getQuality_requirement()==1?"品牌":"品牌");
        holder.tv_delivery.setText(String.valueOf(grabOrderListDTO.getDelivery_time())+"小时");
        holder.tv_process.setText(String.valueOf(grabOrderListDTO.getMoney())+"/件");
        holder.tv_color.setText(grabOrderListDTO.getColor());
        holder.tv_count.setText(getCount(grabOrderListDTO));
        holder.tv_issue.setText(grabOrderListDTO.getPush_date());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //item 点击事件
            public void onClick(View v) {
                mOnItemClick.click(position);
            }
        });
    }

    public String getCount(GrabOrderBean.GrabOrderListDTO grabOrderListDTO){

        String count_str = "";
        for(int i=0; i < grabOrderListDTO.getStock_types().size();i++)
        {
            GrabOrderBean.GrabOrderListDTO.StockTypesDTO stock_type = grabOrderListDTO.getStock_types().get(i);
            if(i==grabOrderListDTO.getStock_types().size()-1)
            {
                count_str = stock_type.getName() + " " + stock_type.getNum() + "件";
            }else {
                count_str = stock_type.getName() + " " + stock_type.getNum() + "件" + "/";
            }
        }
        return count_str;
    }

    @Override
    public int getItemCount() {
        if(areaModelList == null || areaModelList.size() ==0){
            return 0;
        }else
            return areaModelList.size();
    }

    //② 创建ViewHolder 绑定item元素
    public static class VH extends RecyclerView.ViewHolder {
        public View baseview;
        public ImageView img_order;
        public TextView tv_name;
        public TextView tv_quality;
        public TextView tv_delivery;
        public TextView tv_process;
        public TextView tv_color;
        public TextView tv_count;
        public TextView tv_issue;

        public VH(View v) {
            super(v);
            img_order = v.findViewById(R.id.img_order);
            tv_name = v.findViewById(R.id.tv_name);
            tv_quality = v.findViewById(R.id.tv_quality);
            tv_delivery = v.findViewById(R.id.tv_deliver);
            tv_process = v.findViewById(R.id.tv_process);
            tv_color = v.findViewById(R.id.tv_color);
            tv_count = v.findViewById(R.id.tv_count);
            tv_issue = v.findViewById(R.id.tv_issue);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }

}
