package com.ysy15350.easyquickcustomer.account;

/**
 * Created by yangshiyou on 2017/9/20.
 */


import android.content.Intent;

import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.broadcast.MyReceiver;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import base.BaseActivity;
import common.CommFunAndroid;
import custom_view.qrcode.CanvasRQ;

/**
 * 付款，收款二维码
 */
@ContentView(R.layout.activity_qr_code_pay)
public class QRCodePayActivity extends BaseActivity {

    @ViewInject(R.id.img_qr_code)
    private CanvasRQ img_qr_code;


    @Override
    public void initView() {


        MyReceiver.addReceiverListener(new MyReceiver.ReceiverListener() {
            @Override
            public void test(int type, String message) {
                showMsg(message);
                finish();
            }
        });

        super.initView();
        setFormHead("付款");

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);//0:余额支付；1：支付宝；2：微信
        float price = intent.getFloatExtra("price", 0);
        String url = intent.getStringExtra("url");
        String remark = intent.getStringExtra("remark");

        if (CommFunAndroid.isNullOrEmpty(url) || price == 0) {
            showMsg("金额有误");
            this.finish();
        }

        String scanTypeStr = "";
        switch (type) {
            case 0:
                scanTypeStr = "(使用趣支付客户端扫描)";
                break;
            case 1:
                scanTypeStr = "(使用支付宝客户端扫描)";
                break;
            case 2:
                scanTypeStr = "(使用微信客户端扫描)";
                break;
        }

        scanTypeStr = "";

        mHolder.setText(R.id.tv_price, String.format("%.2f 元%s", price, scanTypeStr))
        ;

        if (!CommFunAndroid.isNullOrEmpty(remark)) {
            mHolder
                    .setText(R.id.tv_remark, remark)
            ;

        }

        img_qr_code.setUrl(url);
    }
}
