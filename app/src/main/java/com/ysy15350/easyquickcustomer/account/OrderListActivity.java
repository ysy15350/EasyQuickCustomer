package com.ysy15350.easyquickcustomer.account;

import android.content.Intent;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.adapters.ListViewAdpater_Order;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.OrderInfo;
import base.data.BaseData;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/21.
 */

/**
 * 订单列表
 */
@ContentView(R.layout.activity_order_list)
public class OrderListActivity extends MVPBaseListViewActivity<OrderListViewInterface, OrderListPresenter>
        implements OrderListViewInterface {

    @Override
    protected OrderListPresenter createPresenter() {

        return new OrderListPresenter(OrderListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("订单");
        //setMenu(R.mipmap.icon_add_bank, "添加");
        xListView.setDividerHeight(CommFunAndroid.dip2px(1)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        String bankListJson = BaseData.getCache("orderlist");
        if (!CommFunAndroid.isNullOrEmpty(bankListJson)) {
            List<OrderInfo> bankInfoList = JsonConvertor.fromJson(bankListJson, new TypeToken<List<OrderInfo>>() {
            }.getType());

            bindData(bankInfoList);
        }

        page = 1;
        initData(page, pageSize);
    }

    String start_time;
    String end_time;

    @Override
    public void initData(int page, int pageSize) {
        mPresenter.order_list(start_time, end_time, page, pageSize);
    }

    ListViewAdpater_Order mAdapter;
    List<OrderInfo> mList = new ArrayList<>();


    @Override
    public void order_listCallback(boolean isCache, Response response) {

        List<OrderInfo> list = new ArrayList<>();

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {

                    mHolder.setText(R.id.tv_total_price, String.format("%.2f", response.getTotal_price()));

                    BaseData.setCache("orderlist", response.getResultJson());
                    list = response.getData(new TypeToken<List<OrderInfo>>() {
                    }.getType());
                } else
                    showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        bindData(list);
    }


    public void bindData(List<OrderInfo> list) {


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
        mAdapter = new ListViewAdpater_Order(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null && !list.isEmpty()) {
            page++;
        }
    }

    /**
     * 标题栏菜单点击事件，添加银行卡
     *
     * @param view
     */
    @Override
    protected void ll_menuOnClick(View view) {
        super.ll_menuOnClick(view);

        Intent intent = new Intent(this, AddBankActivity.class);

        startActivity(intent);

    }
}


