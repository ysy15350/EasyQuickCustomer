package com.ysy15350.easyquickcustomer.account;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.adapters.ListViewAdpater_Bank;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.BankInfo;
import base.data.BaseData;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/21.
 */

/**
 * 选择银行卡
 */
@ContentView(R.layout.activity_list)
public class BankListActivity extends MVPBaseListViewActivity<BankListViewInterface, BankListPresenter>
        implements BankListViewInterface {

    @Override
    protected BankListPresenter createPresenter() {

        return new BankListPresenter(BankListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("选择银行");
        //setMenu(R.mipmap.icon_add_bank, "添加");
        xListView.setDividerHeight(CommFunAndroid.dip2px(1)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        String bankListJson = BaseData.getCache("banklist");
        if (!CommFunAndroid.isNullOrEmpty(bankListJson)) {
            List<BankInfo> bankInfoList = JsonConvertor.fromJson(bankListJson, new TypeToken<List<BankInfo>>() {
            }.getType());

            bindData(bankInfoList);
        }

        page = 1;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {
        mPresenter.bank_cardlist(page, pageSize);
    }

    ListViewAdpater_Bank mAdapter;
    List<BankInfo> mList = new ArrayList<>();

    @Override
    public void bank_cardlistCallback(boolean isCache, Response response) {

        List<BankInfo> list = new ArrayList<>();

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("banklist", response.getResultJson());
                    list = response.getData(new TypeToken<List<BankInfo>>() {
                    }.getType());
                } else
                    showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        bindData(list);
    }


    public void bindData(List<BankInfo> list) {


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
        mAdapter = new ListViewAdpater_Bank(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null && !list.isEmpty()) {
            page++;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        BankInfo bankInfo = (BankInfo) parent.getItemAtPosition(position);
        if (bankInfo != null) {
            showMsg(bankInfo.getTitle());
            Intent intent = getIntent();
            intent.putExtra("bank_id", bankInfo.getId());
            intent.putExtra("bank_name", bankInfo.getTitle());
            setResult(RESULT_OK, intent);
            BankListActivity.this.finish();
        }
    }

    //    /**
//     * 标题栏菜单点击事件，添加银行卡
//     *
//     * @param view
//     */
//    @Override
//    protected void ll_menuOnClick(View view) {
//        super.ll_menuOnClick(view);
//
//        Intent intent = new Intent(this, AddBankActivity.class);
//
//        startActivity(intent);
//
//    }
}


