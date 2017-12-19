package com.ysy15350.easyquickcustomer.account;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.adapters.ListViewAdpater_BankCard;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.model.BankCardInfo;
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
public class BankCardListActivity extends MVPBaseListViewActivity<BankCardListViewInterface, BankCardListPresenter>
        implements BankCardListViewInterface {

    @Override
    protected BankCardListPresenter createPresenter() {

        return new BankCardListPresenter(BankCardListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("选择银行卡");
        setMenu(R.mipmap.icon_add_bank, "添加");
        xListView.setDividerHeight(CommFunAndroid.dip2px(1)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        String bank_cardlistJson = BaseData.getCache("bank_cardlist");
        if (!CommFunAndroid.isNullOrEmpty(bank_cardlistJson)) {
            List<BankCardInfo> list = JsonConvertor.fromJson(bank_cardlistJson, new TypeToken<List<BankCardInfo>>() {
            }.getType());
            if (list != null && !list.isEmpty())
                bindData(list);
        }

        page = 1;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {
        mPresenter.bank_cardlist();
    }

    ListViewAdpater_BankCard mAdapter;
    List<BankCardInfo> mList = new ArrayList<>();

    @Override
    public void bank_cardlistCallback(boolean isCache, Response response) {
        try {
            if (response != null) {

                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("bank_cardlist", response.getResultJson());
                    List<BankCardInfo> list = response.getData(new TypeToken<List<BankCardInfo>>() {
                    }.getType());
                    bindData(list);
                } else
                    showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void bindData(List<BankCardInfo> list) {

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
        mAdapter = new ListViewAdpater_BankCard(this, mList);

        mAdapter.setListener(new ListViewAdpater_BankCard.BankCardListener() {
            @Override
            public void delSuccess() {
                page = 1;
                initData(page, pageSize);
            }
        });

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        try {
            BankCardInfo bankCardInfo = (BankCardInfo) parent.getItemAtPosition(position);
            if (bankCardInfo != null) {
                Intent intent = getIntent();
                intent.putExtra("bankCardInfo", bankCardInfo);
                setResult(RESULT_OK, intent);
                this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


