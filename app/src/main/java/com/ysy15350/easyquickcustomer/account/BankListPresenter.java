package com.ysy15350.easyquickcustomer.account;

import android.content.Context;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class BankListPresenter extends BasePresenter<BankListViewInterface> {

    public BankListPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();


    MemberApi memberApi = new MemberApiImpl();

    public void bank_cardlist(int page, int pageSize) {
        memberApi.bank_list(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.bank_cardlistCallback(isCache, response);
            }
        });
    }

}
