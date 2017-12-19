package com.ysy15350.easyquickcustomer.author;

import android.content.Context;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import base.mvp.BasePresenter;

public class UpdatePwdPresenter extends BasePresenter<UpdatePwdViewInterface> {

    public UpdatePwdPresenter(Context context) {
        super(context);

    }

    MemberApi memberApi = new MemberApiImpl();

    public void save_password(String password, String mobile, String mobile_code) {

        memberApi.save_password(password, mobile, mobile_code, new ApiCallBack() {

            @Override
            public void onSuccess(boolean isCache, Response response) {
                mView.save_passwordCallback(isCache, response);
            }
        });

    }

    /**
     * 获取验证码
     *
     * @param mobile
     */
    public void getTelVerify(String mobile) {
        memberApi.getTelVerify(4, mobile, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.getTelVerifyCallback(response);
            }
        });
    }


}
