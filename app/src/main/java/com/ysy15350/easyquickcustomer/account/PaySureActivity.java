package com.ysy15350.easyquickcustomer.account;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;

import org.xutils.view.annotation.ContentView;

import java.util.HashMap;
import java.util.Map;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import base.BaseActivity;
import base.data.BaseData;
import common.CommFunAndroid;
import common.string.JsonConvertor;
import custom_view.dialog.PayDialog;

/**
 * Created by yangshiyou on 2017/9/25.
 */

@ContentView(R.layout.activity_pay_sure)
public class PaySureActivity extends BaseActivity {

    String mContent = "";


    @Override
    public void initView() {

        super.initView();
        setFormHead("确认支付");

        Intent intent = getIntent();
        mContent = intent.getStringExtra("content");

        if (!CommFunAndroid.isNullOrEmpty(mContent)) {
            Map<String, String> map = JsonConvertor.fromJson(mContent, new TypeToken<HashMap<String, String>>() {
            }.getType());

            if (map != null) {

                String uid = map.get("uid");
                String price = map.get("price");
                String name = map.get("name");
                String remark = map.get("remark");
                if (!CommFunAndroid.isNullOrEmpty(name)) {
                    String title = String.format("正在向\" %s \"付款 %s 元", name, price);
                    mHolder.setText(R.id.tv_title, title).setText(R.id.tv_business, name);
                }
                if (!CommFunAndroid.isNullOrEmpty(remark)) {

                    mHolder.setText(R.id.tv_remark, "备注：" + remark);
                }
                mHolder.setText(R.id.tv_price, "￥" + price);
            }

        }

    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);
        paySure(mContent);
    }

    //    https://github.com/yuzhiqiang1993/zxing

    /**
     * 确认支付
     *
     * @param content
     */
    private void paySure(String content) {

        try {
            if (!CommFunAndroid.isNullOrEmpty(content)) {

                final Map<String, String> map = JsonConvertor.fromJson(content, new TypeToken<HashMap<String, String>>() {
                }.getType());

                if (map != null) {

                    PayDialog dialog = new PayDialog(PaySureActivity.this);
                    dialog.setDialogListener(new PayDialog.DialogListener() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onOkClick(String password) {

                            personPayPassword(password, map);

                        }
                    });
                    dialog.show();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    MemberApi memberApi = new MemberApiImpl();

    private void personPayPassword(String password, final Map<String, String> map) {

        memberApi.personPayPassword(password, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                if (response != null) {
                    int code = response.getCode();
                    String msg = response.getMessage();
                    if (code == 200) {

                        //                        map.put("uid", BaseData.getInstance().getUid() + "");
                        //                        map.put("price", price + "");
                        //                        uid：会员ID;sid店铺id


                        String uid = map.get("uid");
                        String price = map.get("price");
                        String remark = map.get("remark");


                        showWaitDialog("服务器处理中...");

                        int payType = 1;//1：个人付款给商家，2商家付款给商家
                        String uid_a = BaseData.getInstance().getUid() + "";//付款方id
                        String uid_b = uid;//收款方id

                        memberApi.user_payment(payType, uid_a, uid_b, price, remark, new ApiCallBack() {
                            @Override
                            public void onSuccess(boolean isCache, Response response) {
                                super.onSuccess(isCache, response);
                                if (response != null) {
                                    int code = response.getCode();
                                    String msg = response.getMessage();
                                    if (code == 200) {
                                        Intent intent = new Intent(PaySureActivity.this, AccountResultActivity.class);
                                        intent.putExtra("title", "付款成功");
                                        intent.putExtra("content", "");
                                        startActivity(intent);
                                        finish();
                                    }
                                    showMsg(msg);
                                }
                            }
                        });


                    }
                    showMsg(msg);

                }
            }
        });
    }

}
