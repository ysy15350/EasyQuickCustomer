package com.ysy15350.easyquickcustomer.account;

import android.content.Context;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class OrderListPresenter extends BasePresenter<OrderListViewInterface> {

    public OrderListPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();


    MemberApi memberApi = new MemberApiImpl();

    public void order_list(String start_time, String end_time, int page, int pageSize) {
        memberApi.order_list(start_time, end_time, page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.order_listCallback(isCache, response);
            }
        });
    }

}
