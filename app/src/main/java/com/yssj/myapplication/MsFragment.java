package com.yssj.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kongzue.baseokhttp.util.Parameter;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.progress.loading.IMessageLoader;
import com.yssj.myapplication.bean.Constant;
import com.yssj.myapplication.bean.GrabOrderBean;
import com.yssj.myapplication.bean.LoginInfoBean;
import com.yssj.myapplication.bean.MD5Tools;
import com.yssj.myapplication.http.BeanResponseListener;
import com.yssj.myapplication.http.HttpApi;
import com.yssj.myapplication.http.HttpRequest;
import com.yssj.myapplication.ui.cangraborder.Adapter.CangraborderAdapter;
import com.yssj.myapplication.ui.cangraborder.CangraborderDetaillActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yssj.myapplication.utils.XToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MsFragment extends Fragment {
    private RecyclerView myrecyclerview;
    private CangraborderAdapter adapter;
    private String content;
    private View pubview;
    private int curPage = 1;
    private int pageSize = 30;
    private SmartRefreshLayout refreshLayout;

    public IMessageLoader mMessageLoader;//消息加载框

    private List<GrabOrderBean.GrabOrderListDTO> grabOrderList = new ArrayList<>();


    public MsFragment(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content,container,false);
        myrecyclerview = view.findViewById(R.id.myrecyclerview);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        initView();
        initData();
        pubview = view;
        return view;
    }

    public void initView(){
        getMessageLoader();

        GridLayoutManager device_manager = new GridLayoutManager(getContext(),1);
        myrecyclerview.setLayoutManager(device_manager);
        adapter = new CangraborderAdapter();
        myrecyclerview.setAdapter(adapter);

        adapter.setOnItemClick(new CangraborderAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                GrabOrderBean.GrabOrderListDTO grabOrderListDTO = grabOrderList.get(index);

                Intent intent = new Intent(getActivity(), CangraborderDetaillActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(grabOrderListDTO.getId()));
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }
        });


        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            curPage = 1;
            initData();
        });


        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {

            curPage ++;
            initData();

        });

    }

    public void initData(){
        Parameter parameter = new Parameter();

        parameter.put("version", HttpApi.VERSION_CODE);
        parameter.put("page",curPage);
        parameter.put("rows",pageSize);

        mMessageLoader.show();
        HttpRequest.POST(getActivity(), HttpApi.GRABORDERS_GRABORDERSLIST, parameter, new BeanResponseListener<GrabOrderBean>() {

            @Override
            public void onResponse(GrabOrderBean bean, Exception error) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                mMessageLoader.dismiss();
                if(error == null){
//                    XToastUtils.toast("请求成功");
                    if(curPage == 1){
                        grabOrderList.clear();
                    }
                    grabOrderList.addAll(bean.getGrabOrderList());
                    adapter.setmDatas(grabOrderList);
                }
            }
        });
    }

    public IMessageLoader getMessageLoader() {
        if (mMessageLoader == null) {
            mMessageLoader = WidgetUtils.getMiniLoadingDialog(getActivity());
            mMessageLoader.setCancelable(true);

        }
        return mMessageLoader;
    }
}
