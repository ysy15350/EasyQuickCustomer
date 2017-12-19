package com.ysy15350.easyquickcustomer.account;

import android.view.View;

import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.adapters.ListViewAdpater_Sale;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.BalanceInfo;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/9/21.
 */

/**
 * 主营范围
 */
@ContentView(R.layout.activity_list)
public class MainBusinessListActivity extends MVPBaseListViewActivity<MainBusinessListViewInterface, MainBusinessListPresenter>
        implements MainBusinessListViewInterface {

    @Override
    protected MainBusinessListPresenter createPresenter() {

        return new MainBusinessListPresenter(MainBusinessListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("选择主营范围");
//        setMenu(R.mipmap.icon_add_bank, "添加");
        setMenuText("确定");
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
        mPresenter.bank_cardlist();
    }

    ListViewAdpater_Sale mAdapter;
    List<BalanceInfo> mList = new ArrayList<>();

    @Override
    public void bank_cardlistCallback(boolean isCache, Response response) {

        List<BalanceInfo> list = new ArrayList<>();

        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());
        list.add(new BalanceInfo());


        bindData(list);
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
        mAdapter = new ListViewAdpater_Sale(this, mList);

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

        this.finish();
    }
}


