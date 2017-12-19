package com.ysy15350.easyquickcustomer.mine;

import api.base.model.Response;

public interface MyInfoViewInterface {


    public void user_infoCallback(boolean isCache, Response response);

    public void save_infoCallback(boolean isCache, Response response);

    /**
     * 上传头像回调
     *
     * @param response
     */
    public void uppictureCallback(Response response);

}
