package com.ysy15350.easyquickcustomer.account;

import api.base.model.Response;

public interface WithdrawViewInterface {

    public void user_infoCallback(boolean isCache, Response response);

    public void tx_rateCallback(boolean isCache, Response response);

    public void withdrawCallback(boolean isCache, Response response);

}
