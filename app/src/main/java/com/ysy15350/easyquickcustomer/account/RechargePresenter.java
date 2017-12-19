package com.ysy15350.easyquickcustomer.account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import alipay.AuthResult;
import alipay.PayResult;
import api.MemberApi;
import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.impl.PublicApiImpl;
import base.mvp.BasePresenter;
import common.string.JsonConvertor;

public class RechargePresenter extends BasePresenter<RechargeViewInterface> {

    public RechargePresenter(Context context) {
        super(context);

    }

    private static final String TAG = "RechargePresenter";

    MemberApi memberApi = new MemberApiImpl();

    PublicApi publicApi = new PublicApiImpl();

    /**
     * 充值预下单
     *
     * @param price
     */
    public void order_change(int price) {
        memberApi.order_change(2, price, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.order_changeCallback(isCache, response);
            }
        });
    }

    public void return_alipay(int resultStatus, String result) {
        publicApi.return_alipay(resultStatus, result, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.return_alipayCallback(isCache, response);
            }
        });
    }

//    public static final String RSA2_PRIVATE = "MIIEowIBAAKCAQEA0ONVgl0GOK7vzkUXoTfu3o6l/G2lYn1lMl9IsBf0cO0qkyZtWuckbtJ2lSStbBx/96NjZkmIJTrRY8yeGoBu6oSyuNJpAWp4S7y/yTx4EWh2MiH2IaUQmR89tMdOvs55IlG93mlJFvaerFDlF5IoxyiHEyd1yZzVU4zABPtLicRiQzgvX3Cki1RvVvmYfasoOjghjwOE4zadTw+LmZfK2J1g4qHv5HNYu8OBWzFnLNXVM5+EmS11Swsxm9WbOBVBg7Lr99kjTMM5LVIm2Ak2ZBh2X/IRkjZMuupGJC0TVUjcoaC1bowsUtc9gdBuzHK2vaPpB/jYtKSEEXToTwsYhwIDAQABAoIBAQDEG1lQ7TKFDr82IKmBgrhwGu38IZgt8dmKw+fC4oprZk0dHut1dyE+JbqMZIWnxM3i2HLeOdRmV2TzU8tux/rZk0ESwP8yBBnxyL8pFusGNNMaV8Xyajw99RWCXccw3TjeHm2pm6CxvVDUJ5IcYeyEpSjCs74iTGn+aVpwktek+paqVRYwdZjx1Wg3ohdYVM2nTgrh1MayHgI7piWGPIKSgliOLvYjpSNhejzxpCOFPAO42e34ZuodyOZuIy2xCtLcm+zr9ac6BBlgEqcRaMyT0+yZHQnhWISTxuE4ZIwPt9hKo5lNjimi4M4P4m9Vra8Rt5rWaKDEOlvnko4Ja/9pAoGBAOkdCzemiRuODfpvjhw9xzsKuWRU5b049oI+E1qayAUJenDLqJXBt8YnvTJ7S+AimA9jZRAbQaMiJxOpK9qnzo0MIknffb18oKBVTb1+6uzCcNOGh2gI+gHsseG3N6HBOlRk6XAKMq0FhDeZmlK/I717uiLvC6XownYuZRIf3gjDAoGBAOVla6Olu4Mu641E8cl0hPT2KS4tClnhGEXunIN+XTtEiYiyShlSHUz9qPSIPb9rzhoS5Mszd//b2I/GKTwiLhCziPzVdBqGckuJMPTIo96OM3x/z8xZAZ4GI6JafGNgY37C1bQFroSLYWP2co1f12+GnPN8uZdzSHAZIiVMH1TtAoGAXpczcwx3fB/jWiikEJ+c1T9fxO+SHXscw/KuZciCu1CCR3zMVqA5kwWTBwXy3usKqsvZOXTl6G02UAsISXxoGuG+lB1E53vHreePWQJED3hC0gI3taZW1CsYbw+qWk+O/KnJ83Wf5IdlhC4m6ca3mO64s+4t2s+dANmIl5cowIMCgYA3ebx7OfRL+EfcxSSX7IbO2s0ArsHHFKmTF+5f2mpfj+aW2U8UKoiNevamFGCib0bLhXAxOAQ4KwmHU4vbUATF4zUot9I5K/RuEgJ14CPJCain3hz5tTiv55oshCd6vO+y0xltTMZ0pWRrEUmIqPnnra09MReM6cNF4L4iOPOGzQKBgFRhDAhN0rIcPzfK3Yzd0nCwsBTp8kRbNFdgftupj4fdIXXO5MJtraiLDubG0ENJcvHyktQtt2ZVgSDwUA1HEw7v4bcMxKTwxhxc2gSI62aufDFVBwqgRv79jpopu2cwmfIHMtHbpGGEOVWCZaG5jXTcrdOrDV3oCnOclX5klwE+";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    public void alipay(final String orderInfo, final Activity context) {

        Log.d(TAG, "alipay() called with: authInfo = [" + orderInfo + "], context = [" + context + "]");

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    Log.d(TAG, "支付结果: " + JsonConvertor.toJson(payResult));

                    mView.showAliPayResult(JsonConvertor.toJson(payResult));

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(PayDemoActivity.this,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
                    } else {
                        // 其他状态值则为授权失败
//                        Toast.makeText(PayDemoActivity.this,
//                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


}
