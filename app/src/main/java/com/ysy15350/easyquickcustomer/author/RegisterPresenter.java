package com.ysy15350.easyquickcustomer.author;

import android.content.Context;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class RegisterPresenter extends BasePresenter<RegisterViewInterface> {

    public RegisterPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();
    MemberApi memberApi = new MemberApiImpl();


    /**
     * 获取验证码
     *
     * @param mobile
     */
    public void getTelVerify(String mobile) {
        memberApi.getTelVerify(2, mobile, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.getTelVerifyCallback(response);
            }
        });
    }

    public void register(String mobile, String password,
                         String verification_code) {

//        publicApi.register( mobile,  password,   verification_code, new ApiCallBack() {
//
//            @Override
//            public void onSuccess(boolean isCache, Response response) {
//                mView.registerCallback(response);
//            }
//        });

        memberApi.personRegister(mobile, password, verification_code, new ApiCallBack() {

            @Override
            public void onSuccess(boolean isCache, Response response) {
                mView.registerCallback(response);
            }
        });

    }


    /**
     * 用户登录
     *
     * @param account  用户名
     * @param password 密码
     */
    public void login(String account, String password) {
        publicApi.login(account, password, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                mView.userLoginCallBack(response);
            }
        });
    }

}
