package com.ysy15350.easyquickcustomer.account;

import android.content.Context;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class ProfitDetailListPresenter extends BasePresenter<ProfitDetailListViewInterface> {

    public ProfitDetailListPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();


    MemberApi memberApi = new MemberApiImpl();

    public void profit_detail_list(int page, int pageSize) {
        memberApi.profit_list(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.profit_detail_listCallback(isCache, response);
            }
        });
    }

}
