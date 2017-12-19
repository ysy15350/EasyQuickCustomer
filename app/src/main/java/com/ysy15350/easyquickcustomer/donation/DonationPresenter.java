package com.ysy15350.easyquickcustomer.donation;

import android.content.Context;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class DonationPresenter extends BasePresenter<DonationViewInterface> {

    public DonationPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();
    MemberApi memberApi = new MemberApiImpl();


//    public void scollImg() {
//        publicApi.scollImg(1, new ApiCallBack() {
//            @Override
//            public void onSuccess(boolean isCache, Response response) {
//                super.onSuccess(isCache, response);
//                mView.scollImgCallback(isCache, response);
//            }
//        });
//    }

    public void personScollImg() {
        publicApi.personScollImg(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.personScollImgCallback(isCache, response);
            }
        });
    }

    public void donationPerson(float price, String pay_password) {
        memberApi.donationPerson(price, pay_password, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.donationPersonCallback(isCache, response);
            }
        });
    }

}
