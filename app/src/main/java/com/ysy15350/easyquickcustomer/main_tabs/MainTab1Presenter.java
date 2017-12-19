package com.ysy15350.easyquickcustomer.main_tabs;


import android.content.Context;

import api.ProductApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.ProductApiImpl;
import base.mvp.BasePresenter;

public class MainTab1Presenter extends BasePresenter<MainTab1ViewInterface> {

    public MainTab1Presenter() {
    }

    public MainTab1Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    ProductApi productApi = new ProductApiImpl();

    public void micro_pay(String qrcode) {
        productApi.micro_pay(qrcode, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
            }
        });
    }



}
