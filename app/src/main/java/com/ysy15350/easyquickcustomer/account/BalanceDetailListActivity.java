package com.ysy15350.easyquickcustomer.account;


import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.adapters.ListViewAdpater_Balance;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.BalanceInfo;
import base.data.BaseData;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/9/21.
 */

/**
 * 余额明细
 */
@ContentView(R.layout.activity_balance_detail_list)
public class BalanceDetailListActivity extends MVPBaseListViewActivity<BalanceDetailListViewInterface, BalanceDetailListPresenter>
        implements BalanceDetailListViewInterface {

    @Override
    protected BalanceDetailListPresenter createPresenter() {

        return new BalanceDetailListPresenter(BalanceDetailListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("明细");
        xListView.setDividerHeight(CommFunAndroid.dip2px(1)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        page = 1;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {

        mPresenter.balance_detail_list(page, pageSize);
    }

    ListViewAdpater_Balance mAdapter;
    List<BalanceInfo> mList = new ArrayList<>();

    @Override
    public void balance_detail_listCallback(boolean isCache, Response response) {

        try {
            if (response != null) {

                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("balanceInfolist", response.getResultJson());
                    List<BalanceInfo> list = response.getData(new TypeToken<List<BalanceInfo>>() {
                    }.getType());
                    bindData(list);
                } else
                    showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindData(List<BalanceInfo> list) {


        if (page == 1) {
            mList.clear();
        } else {
            if (list == null) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            } else if (list.isEmpty()) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            }
        }

        if (list != null)
            mList.addAll(list);
        mAdapter = new ListViewAdpater_Balance(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null && !list.isEmpty()) {
            page++;
        }
    }
}


