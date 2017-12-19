package com.ysy15350.easyquickcustomer.account;

import android.content.Context;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class MainBusinessListPresenter extends BasePresenter<MainBusinessListViewInterface> {

    public MainBusinessListPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();



    MemberApi memberApi = new MemberApiImpl();

    public void bank_cardlist() {
        memberApi.user_info(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache,response);
                mView.bank_cardlistCallback(isCache,response);
            }
        });
    }

}
