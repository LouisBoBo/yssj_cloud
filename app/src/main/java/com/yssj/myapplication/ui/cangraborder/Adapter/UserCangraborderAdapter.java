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
import com.yssj.myapplication.bean.UserGrabOrderBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserCangraborderAdapter extends RecyclerView.Adapter<UserCangraborderAdapter.VH> {
    private List<UserGrabOrderBean.GrabOrderListDTO> areaModelList;


    public UserCangraborderAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(UserCangraborderAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public void setmDatas(List<UserGrabOrderBean.GrabOrderListDTO> modelList) {
        areaModelList = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserCangraborderAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cangrab_order, parent, false);
        return new UserCangraborderAdapter.VH(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull UserCangraborderAdapter.VH holder, int position) {

        UserGrabOrderBean.GrabOrderListDTO grabOrderListDTO = areaModelList.get(position);
        holder.tv_name.setText(grabOrderListDTO.getName());
        holder.tv_quality.setText(grabOrderListDTO.getQuality_requirement()==1?"品牌":"品牌");
//        holder.tv_delivery.setText(String.valueOf(grabOrderListDTO.getDelivery_time())+"小时");
        String str_time = getTime(grabOrderListDTO.getCreate_date());
        String date_teme = getStrTime(str_time);
        holder.tv_delivery.setText(date_teme);
        holder.tv_process.setText(String.valueOf(grabOrderListDTO.getMoney())+"/件");
        holder.tv_color.setText(grabOrderListDTO.getColor());

        holder.tv_count.setText(getCount(grabOrderListDTO));
        holder.tv_count_title.setText("加工件数：");

        holder.tv_issue.setVisibility(View.GONE);
        holder.tv_issue_title.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //item 点击事件
            public void onClick(View v) {
                mOnItemClick.click(position);
            }
        });
    }

    public String getCount(UserGrabOrderBean.GrabOrderListDTO grabOrderListDTO){

        String count_str = "";
        for(int i=0; i < grabOrderListDTO.getStock_types().size();i++)
        {
            UserGrabOrderBean.GrabOrderListDTO.StockTypesDTO stock_type = grabOrderListDTO.getStock_types().get(i);
            if(i==grabOrderListDTO.getStock_types().size()-1)
            {
                count_str = stock_type.getName() + " " + stock_type.getNum() + "件";
            }else {
                count_str = stock_type.getName() + " " + stock_type.getNum() + "件" + "/";
            }
        }
        return count_str;
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

    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long lcc_time = Long.valueOf(cc_time)+48*60*60;
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
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
        public TextView tv_count_title;
        public TextView tv_issue;
        public TextView tv_issue_title;

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
            tv_issue_title = v.findViewById(R.id.tv_issue_title);
            tv_count_title = v.findViewById(R.id.tv_count_title);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }

}