package com.ysy15350.easyquickcustomer.account;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.ysy15350.easyquickcustomer.R;

import org.xutils.view.annotation.ContentView;

import alipay.model.AliPayResult;
import api.base.model.Response;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/20.
 */

/**
 * 充值
 */
@ContentView(R.layout.activity_recharge)
public class RechargeActivity extends MVPBaseActivity<RechargeViewInterface, RechargePresenter> implements RechargeViewInterface {


    @Override
    protected RechargePresenter createPresenter() {
        // TODO Auto-generated method stub
        return new RechargePresenter(RechargeActivity.this);
    }

    private static final String TAG = "RechargeActivity";

    @Override
    public void initView() {

        super.initView();
        setFormHead("充值");


    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);
        String et_priceText = mHolder.getViewText(R.id.et_price);
        if (CommFunAndroid.isNullOrEmpty(et_priceText)) {
            showMsg("请输入充值金额");

            return;
        }

        if (!CommFunAndroid.isNumber(et_priceText)) {
            showMsg("充值金额输入有误");
            return;
        }

        int price = CommFunAndroid.toInt32(et_priceText, 0);//充值金额

        showWaitDialog("服务器处理中，请稍后....");
        mPresenter.order_change(price);

    }

    @Override
    public void order_changeCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    //{"code":200,
                    // "message":"\u8ba2\u5355\u751f\u6210\u6210\u529f",
                    // "order_number":"20170929728994",
                    // "title":"\u5145\u503c\u8ba2\u5355",
                    // "description":"\u60a8\u6b63\u5728\u5145\u503c\u4f59\u989d"}
                    //rechargeSuccess();

                    String order_numer = response.getOrder_number();
                    String title = response.getTitle();
                    String description = response.getDescription();
                    String sign_list = response.getSign_list();
                    mPresenter.alipay(sign_list, this);
                    Log.d(TAG, "order_changeCallback: sign_list=" + sign_list);

                }
                showMsg(msg);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showAliPayResult(String msg) {

//        "{\"alipay_trade_app_pay_response\":{\"code\":\"40002\",\"msg\":\"Invalid Arguments\",\"sub_code\":\"isv.invalid-signature\",\"sub_msg\":\"验签出错，建议检查签名字符串或签名私钥与应用公钥是否匹配，网关生成的验签字符串为：app_id\u003d2017061407489558\u0026biz_content\u003d{\\\"subject\\\":\\\"\\\\u5145\\\\u503c\\\\u8ba2\\\\u5355\\\",\\\"out_trade_no\\\":\\\"20170929379052\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"product_code\\\":\\\"qkb\\\"}\u0026body\u003d充值金额0.01\u0026charset\u003dutf-8\u0026method\u003dalipay.trade.app.pay\u0026notify_url\u003dhttp://www.mg0607.cn/App/public/notify_alipay\u0026sign_type\u003dRSA2\u0026timestamp\u003d2017-09-29 11:35:35\u0026version\u003d1.0\"}}",
//                "resultStatus": "4000"
//    }

//        {
//            "memo": "操作已经取消。",
//                "result": "",
//                "resultStatus": "6001"
//        }

//        {
//            "memo": "",
//                "result": "{\"alipay_trade_app_pay_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2017061407489558\",\"auth_app_id\":\"2017061407489558\",\"charset\":\"utf-8\",\"timestamp\":\"2017-09-29 10:01:29\",\"total_amount\":\"0.01\",\"trade_no\":\"2017092921001004830273542225\",\"seller_id\":\"2088421590687653\",\"out_trade_no\":\"0929100109-1901\"},\"sign\":\"f1LaeF/gLUE6O7JWtnF+DDNDoBqt5KNBCvBY9LFCChXxcwVhOT2pYD923gu1+7NV85e3NlsPbo1DG1ybI2CspHbnl9G0IAhC3ri9eqknBC6Mw/cDNgJwe+RfA2KrD2nXCXkfNxKXk4uoKrUjOID3qZIXFunkR3gvdlj/Yz0qYJdLXaijOOQV3cMZEtoqVS80ywfCQwYUOPlLFu7ms+67jkOd00VlvlPPHoUCicn1qyRwcoV8rj3dIeiYpP8w7QCwv8J1uVn9UBp70b6SAoBiVe0XZ4jfTYv0st0QxXj4TQ7DdTsCCmAzDXXnjN3jeQIMXLkJ9SsWlJWXGYo3shVn6w\u003d\u003d\",\"sign_type\":\"RSA2\"}",
//                "resultStatus": "9000"
//        }


        try {
            if (!CommFunAndroid.isNullOrEmpty(msg)) {

                Log.d(TAG, "showAliPayResult() called with: msg = [" + msg + "]");

                AliPayResult payResult = JsonConvertor.fromJson(msg, AliPayResult.class);

                if (payResult != null) {
                    int resultStatus = payResult.getResultStatus();
                    String resultJson = payResult.getResultJson();
                    mPresenter.return_alipay(resultStatus, resultJson);
                }
            } else {
                showMsg("未知支付错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void return_alipayCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    rechargeSuccess();
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 充值成功
     */
    private void rechargeSuccess() {

        Intent intent = new Intent(this, AccountResultActivity.class);
        intent.putExtra("title", "充值成功");
        intent.putExtra("content", "");
        startActivity(intent);
        this.finish();
    }
}
