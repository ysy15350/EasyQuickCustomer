package api.impl;

import api.ProductApi;
import api.base.ApiCallBack;
import api.base.IServer;
import api.base.Request;
import base.data.BaseData;

/**
 * Created by yangshiyou on 2017/11/20.
 */

public class ProductApiImpl implements ProductApi {

    private String moduleName = "Product/";

    @Override
    public void micro_pay(String qrcode, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "micro_pay");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("qrcode", qrcode);
        server.setParam("type", 2);//type: 1:店铺   2:会员

        server.setApiCallBack(callBack);

        server.request();
    }
}
