package com.ysy15350.easyquickcustomer.account;


import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.adapters.ListViewAdpater_Profit;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.ProfitInfo;
import base.data.BaseData;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/9/21.
 */

/**
 * 收益明细
 */
@ContentView(R.layout.activity_profit_detail_list)
public class ProfitDetailListActivity extends MVPBaseListViewActivity<ProfitDetailListViewInterface, ProfitDetailListPresenter>
        implements ProfitDetailListViewInterface {

    @Override
    protected ProfitDetailListPresenter createPresenter() {

        return new ProfitDetailListPresenter(ProfitDetailListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("收益明细");
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
        mPresenter.profit_detail_list(page, pageSize);
    }

    ListViewAdpater_Profit mAdapter;
    List<ProfitInfo> mList = new ArrayList<>();

    @Override
    public void profit_detail_listCallback(boolean isCache, Response response) {

        try {
            if (response != null) {

                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("profitInfoInfolist", response.getResultJson());
                    float total_price = response.getTotal_price();
                    mHolder.setText(R.id.tv_total_price, total_price + "");
                    List<ProfitInfo> list = response.getData(new TypeToken<List<ProfitInfo>>() {
                    }.getType());
                    bindData(list);
                } else
                    showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindData(List<ProfitInfo> list) {


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
        mAdapter = new ListViewAdpater_Profit(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null && !list.isEmpty()) {
            page++;
        }
    }
}


