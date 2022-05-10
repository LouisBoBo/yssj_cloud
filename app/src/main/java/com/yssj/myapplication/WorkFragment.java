package com.yssj.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kongzue.baseokhttp.util.Parameter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.progress.loading.IMessageLoader;
import com.yssj.myapplication.bean.GrabOrderBean;
import com.yssj.myapplication.bean.UserGrabOrderBean;
import com.yssj.myapplication.http.BeanResponseListener;
import com.yssj.myapplication.http.HttpApi;
import com.yssj.myapplication.http.HttpRequest;
import com.yssj.myapplication.ui.cangraborder.Adapter.CangraborderAdapter;
import com.yssj.myapplication.ui.cangraborder.Adapter.UserCangraborderAdapter;
import com.yssj.myapplication.ui.cangraborder.CangraborderDetaillActivity;
import com.yssj.myapplication.ui.robbedorder.GrabOrderDetailActivity;
import com.yssj.myapplication.utils.FTPClientFunctions;
import com.yssj.myapplication.utils.XToastUtils;

import java.util.ArrayList;
import java.util.List;

public class WorkFragment extends Fragment implements View.OnClickListener {
    private ViewPager viewpager;
    private View slider;
    private TextView title_one;
    private TextView title_two;
    private TextView title_three;
    private TextView tv_update;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离

    private int curPage = 1;
    private int pageSize = 30;

    private FTPClientFunctions ftpClient;

    private RecyclerView myrecyclerview;
    private SmartRefreshLayout refreshLayout;
    private UserCangraborderAdapter adapter;
    private String content;
    private List<UserGrabOrderBean.GrabOrderListDTO> grabOrderList = new ArrayList<>();

    public IMessageLoader mMessageLoader;//消息加载框

    public WorkFragment(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content,container,false);
        myrecyclerview = view.findViewById(R.id.myrecyclerview);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        initView();
        initData();
        return view;
    }

    public void initView(){
        getMessageLoader();

        GridLayoutManager device_manager = new GridLayoutManager(getContext(),1);
        myrecyclerview.setLayoutManager(device_manager);
        adapter = new UserCangraborderAdapter();
        myrecyclerview.setAdapter(adapter);

        adapter.setOnItemClick(new UserCangraborderAdapter.OnItemClick() {
            @Override
            public void click(int index) {
                UserGrabOrderBean.GrabOrderListDTO grabOrderListDTO = grabOrderList.get(index);

                Intent intent = new Intent(getActivity(), GrabOrderDetailActivity.class);
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
        HttpRequest.POST(getActivity(), HttpApi.USERGRABORDERS_GRABORDERSLIST, parameter, new BeanResponseListener<UserGrabOrderBean>() {

            @Override
            public void onResponse(UserGrabOrderBean bean, Exception error) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                mMessageLoader.dismiss();
                if(error == null){
                    XToastUtils.toast("请求成功");
                    if(curPage == 1){
                        grabOrderList.clear();
                    }
                    grabOrderList.addAll(bean.getGrabOrderList());
                    adapter.setmDatas(grabOrderList);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public IMessageLoader getMessageLoader() {
        if (mMessageLoader == null) {
            mMessageLoader = WidgetUtils.getMiniLoadingDialog(getActivity());
            mMessageLoader.setCancelable(true);

        }
        return mMessageLoader;
    }
}
