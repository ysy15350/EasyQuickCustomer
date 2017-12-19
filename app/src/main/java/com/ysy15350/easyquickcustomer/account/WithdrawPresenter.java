package com.ysy15350.easyquickcustomer.account;

import android.content.Context;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class WithdrawPresenter extends BasePresenter<WithdrawViewInterface> {

    public WithdrawPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();


    /**
     * 个人中心
     */
    public void user_info() {

        memberApi.user_info(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.user_infoCallback(isCache, response);
            }
        });

    }

    /**
     * 提现汇率
     */
    public void tx_rate() {
        memberApi.tx_rate(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.tx_rateCallback(isCache, response);
            }
        });
    }

    /**
     * 提现
     *
     * @param price
     * @param bankId
     */
    public void withdraw(float price, int bankId) {
        memberApi.withdrawals(price, bankId, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.withdrawCallback(isCache, response);

            }
        });
    }

}
