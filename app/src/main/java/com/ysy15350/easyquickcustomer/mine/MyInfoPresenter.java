package com.ysy15350.easyquickcustomer.mine;

import android.content.Context;

import java.io.File;

import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;

public class MyInfoPresenter extends BasePresenter<MyInfoViewInterface> {

    public MyInfoPresenter(Context context) {
        super(context);

    }

    PublicApi publicApi = new PublicApiImpl();
    MemberApi memberApi = new MemberApiImpl();


    /**
     * 用户信息
     */
    public void user_info() {
        memberApi.user_info(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                mView.user_infoCallback(isCache, response);
            }
        });
    }

    /**
     * 修改个人资料
     *
     * @param headimgurl
     * @param nickname
     * @param cards
     * @param address
     */
    public void save_info(String headimgurl, String nickname,String fullname, String cards, String address, String pay_password) {
        memberApi.save_info(headimgurl, nickname,fullname, cards, address, pay_password, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.save_infoCallback(isCache, response);
            }
        });
    }

    /**
     * 上传头像
     *
     * @param file
     */
    public void uppicture(File file) {
        memberApi.uppicture(file, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                //{"code":200,"pic":"\/Uploads\/Picture\/2017-08-31\/59a7a5689e7f9.jpg","id":2}
                if (response != null)
                    mView.uppictureCallback(response);
            }


        });
    }

}
