package com.ysy15350.easyquickcustomer.author;

import api.base.model.Response;

public interface UpdatePwdViewInterface {

    /**
     * 注册回调
     *
     * @param response
     */
    public void save_passwordCallback(boolean isCache, Response response);

    public void getTelVerifyCallback(Response response);

}
