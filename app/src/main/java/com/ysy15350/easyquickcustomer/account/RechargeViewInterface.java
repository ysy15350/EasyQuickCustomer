package com.ysy15350.easyquickcustomer.account;

import api.base.model.Response;

public interface RechargeViewInterface {

    public void order_changeCallback(boolean isCache, Response response);

    public void return_alipayCallback(boolean isCache, Response response);

    public void showAliPayResult(String msg);

}
