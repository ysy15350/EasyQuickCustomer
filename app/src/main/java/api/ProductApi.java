package api;

import api.base.ApiCallBack;

/**
 * Created by yangshiyou on 2017/11/20.
 */

public interface ProductApi {

    /**
     * 获取token
     */
    public void micro_pay(String qrcode, ApiCallBack callBack);
}
