package api.impl;

import api.PublicApi;
import api.base.ApiCallBack;
import api.base.IServer;
import api.base.Request;
import api.base.model.Config;
import base.data.BaseData;
import common.CommFunAndroid;


public class PublicApiImpl implements PublicApi {

    private String moduleName = "Public/";

    // private String mUrl = Config.getServer_url() + moduleName;

    @Override
    public void getToken(String className, ApiCallBack callBack) {

        IServer server = new Request();

        server.setMethodName(moduleName + "send_token");

        server.setParam("account", Config.getTokenUsername());
        server.setParam("password", Config.getTokenPassword());
        server.setParam("className", className);

        server.setApiCallBack(callBack);

        //server.setUseCache(true, 5);

        server.request();

    }

    @Override
    public void register(String mobile, String password,
                         String verification_code, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "register");

        server.setParam("mobile", mobile);
        server.setParam("password", password);
        server.setParam("verification_code", verification_code);

        server.setApiCallBack(callBack);

        server.request();

    }

    @Override
    public void login(String account, String password, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "login");

        server.setParam("mobile", account);
        server.setParam("password", password);
        server.setParam("type", 2);//类型  1:店铺   2:会员
        server.setParam("identification", CommFunAndroid.getSharedPreferences("device_id"));//CommFunAndroid.setSharedPreferences("device_id", deviceId);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void logout_identification(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "logout_identification");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", 2);//类型  1:店铺   2:会员
        server.setParam("identification", CommFunAndroid.getSharedPreferences("device_id"));//CommFunAndroid.setSharedPreferences("device_id", deviceId);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void checkVersion(int version_code, ApiCallBack callBack) {


        IServer server = new Request();

        server.setMethodName(moduleName + "checkVersion");

        server.setParam("platform", 1);
        server.setParam("type", 2);//1：商家；2：用户
        server.setParam("vsersionCode", version_code);


        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void return_alipay(int resultStatus, String result, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "return_alipay");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("resultStatus", resultStatus);//店铺id
        server.setParam("result", result);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void adImgIndex(int type, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "adImgIndex");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", type);//type：1趣收款，2趣支付

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void scollImg(int type, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "scollImg");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", type);//type：1趣收款，2趣支付

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void personScollImg(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "personScollImg");
        server.setParam("uid", BaseData.getInstance().getUid());
        //server.setParam("type", type);//type：1趣收款，2趣支付

        server.setApiCallBack(callBack);

        server.request();
    }
}
