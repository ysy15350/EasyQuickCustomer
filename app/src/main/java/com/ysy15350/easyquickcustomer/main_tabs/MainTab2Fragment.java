package com.ysy15350.easyquickcustomer.main_tabs;


import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.adapters.ListViewAdpater_Business;
import com.ysy15350.easyquickcustomer.pop.BusinessTypeListViewPopupWindow;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.Business;
import api.model.BusinessType;
import base.data.BaseData;
import base.mvp.MVPBaseListViewFragment;
import common.CommFunAndroid;
import common.string.JsonConvertor;


/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_main_tab2)
public class MainTab2Fragment extends MVPBaseListViewFragment<MainTab2ViewInterface, MainTab2Presenter>
        implements MainTab2ViewInterface {

    public MainTab2Fragment() {
    }

    @Override
    public MainTab2Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab2Presenter(getActivity());
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        xListView.setDividerHeight(CommFunAndroid.dip2px(1));
        setTitle("附近优惠");
        //setFormHead("附近优惠");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

//        BaseData.setCache("businessList", response.getResultJson());

        String businessJson = BaseData.getCache("businessList");
        if (!CommFunAndroid.isNullOrEmpty(businessJson)) {
            List<Business> list = JsonConvertor.fromJson(businessJson, new TypeToken<List<Business>>() {
            }.getType());
            bindData(list);
        }


        page = 1;
        initData(page, pageSize);
    }


    //29.527259,106.563512
    double latitude = 29.527259;
    double longitude = 106.563512;
    int business = -1;

    @Override
    public void initData(int page, int pageSize) {
        showWaitDialog("数据加载中...");
        business = -1;

//        BaseData.setCache("latitude", aMapLocation.getLatitude() + "");
//        BaseData.setCache("longitude", aMapLocation.getLongitude() + "");
        if (!CommFunAndroid.isNullOrEmpty(BaseData.getCache("latitude")))
            latitude = Double.parseDouble(BaseData.getCache("latitude"));
        if (!CommFunAndroid.isNullOrEmpty(BaseData.getCache("longitude")))
            longitude = Double.parseDouble(BaseData.getCache("longitude"));

        mPresenter.store_list(latitude, longitude, business, page, pageSize);
    }

    ListViewAdpater_Business mAdapter;
    List<Business> mList = new ArrayList<>();

    @Override
    public void store_listCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("businessList", response.getResultJson());
                    List<Business> list = response.getData(new TypeToken<List<Business>>() {
                    }.getType());
                    bindData(list);
                } else
                    showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void bindData(List<Business> list) {

        if (page == 1) {
            mList.clear();
        } else {
            if (list == null || list.isEmpty()) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            }
        }

        if (list != null)
            mList.addAll(list);
        mAdapter = new ListViewAdpater_Business(mContext, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }


    @Event(value = R.id.ll_tab3)
    private void ll_tab3Click(View view) {
        BusinessTypeListViewPopupWindow popupWindow = new BusinessTypeListViewPopupWindow(getActivity());
        popupWindow.showPopupWindow(rl_head);
        popupWindow.setBusinessTypePopListener(new BusinessTypeListViewPopupWindow.BusinessTypePopListener() {
            @Override
            public void onItemClick(int position, BusinessType ticketType) {
                if (ticketType != null) {
                    // mTicketType = ticketType.getId();
                    business = ticketType.getId();
                    mPresenter.store_list(latitude, longitude, business, page, pageSize);
                    showMsg(ticketType.getTitle());
                }
            }
        });
    }
}
