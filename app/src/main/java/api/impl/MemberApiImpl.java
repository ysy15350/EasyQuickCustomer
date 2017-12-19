package api.impl;

import java.io.File;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.IServer;
import api.base.Request;
import base.data.BaseData;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/8/31.
 */

public class MemberApiImpl implements MemberApi {

    private String moduleName = "Member/";

    @Override
    public void getTelVerify(int type, String mobile, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "getTelVerify");

        server.setParam("mobile", mobile);//filename   upload.png
        server.setParam("type", type);//type：1商家，2会员

        server.setApiCallBack(callBack);

        server.request();
    }


    @Override
    public void personRegister(String mobile, String password, String verify, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "personRegister");

        server.setParam("mobile", mobile);
        server.setParam("password", password);
        server.setParam("verify", verify);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void uppicture(File file, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "uppicture");

        server.setParam("filename", file);//filename   upload.png

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void user_info(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "user_info");

        server.setParam("uid", BaseData.getInstance().getUid());

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void save_info(String headimgurl, String nickname, String fullname, String cards, String address, String pay_password, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "save_info");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("headimgurl", headimgurl);
        server.setParam("nickname", nickname);
        server.setParam("fullname", fullname);
        server.setParam("cards", cards);
        server.setParam("address", address);
        server.setParam("pay_password", pay_password);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void save_mobile(String password, String mobile, String mobile_code, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "save_mobile");

        server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("mobile", mobile);
        server.setParam("password", password);
        server.setParam("mobile_code", mobile_code);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void save_password(String password, String mobile, String mobile_code, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "save_password");

        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", 2);//类型  1:店铺   2:会员
        server.setParam("mobile", mobile);
        server.setParam("password", password);
        server.setParam("mobile_code", mobile_code);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void adlist(int type, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "adlist");

        //server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("type", type);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void store_cate(int page, int pageSize, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "store_cate");

        //server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", pageSize);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void store_list(double lat, double log, int business, int page, int pageSize, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "store_list");

        //server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("lat", lat);
        server.setParam("log", log);
        if (business != -1)
            server.setParam("business", business);
        server.setParam("page", page);
        server.setParam("limit", pageSize);
        server.setParam("type", 2);//类型  1:店铺   2:会员

        server.setApiCallBack(callBack);

        server.request();


    }

    @Override
    public void bank_list(int page, int pageSize, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "bank_list");

        //server.setParam("sid", BaseData.getInstance().getUid());
        server.setParam("page", page);
        server.setParam("limit", pageSize);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void user_bank(int type, int page, int pageSize, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "user_bank");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", type);
        server.setParam("page", page);
        server.setParam("limit", pageSize);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void add_user_bank(int type, String username, String mobile, String number, int bank_id, String open_bank, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "add_user_bank");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", type);
        server.setParam("username", username);
        server.setParam("mobile", mobile);
        server.setParam("number", number);
        server.setParam("bank_id", bank_id);
        server.setParam("open_bank", open_bank);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void order_list(String start_time, String end_time, int page, int pageSize, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "order_list");

        server.setParam("uid", BaseData.getInstance().getUid());
        if (!CommFunAndroid.isNullOrEmpty(start_time))
            server.setParam("start_time", start_time);
        if (!CommFunAndroid.isNullOrEmpty(end_time))
            server.setParam("end_time", end_time);
        server.setParam("page", page);
        server.setParam("limit", pageSize);
        server.setParam("type", 2);//类型  1:店铺   2:会员

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void order_change(int type, int price, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "order_change");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", type);
        server.setParam("price", price);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void withdrawals(float txprice, int bank_id, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "withdrawals");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", 2);//类型  1:店铺   2:会员
        server.setParam("txprice", txprice);
        server.setParam("bank_id", bank_id);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void personPayPassword(String password, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "personPayPassword");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", 2);//类型  1:店铺   2:会员
        server.setParam("password", password);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void tx_rate(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "tx_rate");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", 1);//类型  1:店铺   2:会员

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void user_payment(String sid, String price, String remark, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "user_payment");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("sid", sid);//店铺id
        server.setParam("price", price);
        server.setParam("remark", remark);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void user_payment(int type, String uid_a, String uid_b, String price, String remark, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "user_payment");
        server.setParam("type", type);
        server.setParam("uid_a", uid_a);
        server.setParam("uid_b", uid_b);
        server.setParam("price", price);
        server.setParam("remark", remark);

        server.setApiCallBack(callBack);

        server.request();
    }


    @Override
    public void balance_list(int page, int pageSize, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "balance_list");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", 2);//类型  1:店铺   2:会员
        server.setParam("page", page);
        server.setParam("limit", pageSize);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void profit_list(int page, int pageSize, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "profit_list");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", 2);//类型  1:店铺   2:会员
        server.setParam("page", page);
        server.setParam("limit", pageSize);

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void donationPerson(float price, String pay_password, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "donationPerson");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("price", price);
        server.setParam("pay_password", pay_password);

        server.setApiCallBack(callBack);

        server.request();
    }

    /**
     * 检查用户信息是否完善接口（type:类型  1:店铺   2:会员， uid：用户id）
     *
     * @param callBack
     */
    @Override
    public void checkUserinfo(ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "checkUserinfo");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("type", 2);//type: 1:店铺   2:会员

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void del_user_bank(int bank_id, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "del_user_bank");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("bank_id", bank_id);
        server.setParam("type", 2);//type: 1:店铺   2:会员

        server.setApiCallBack(callBack);

        server.request();
    }

    @Override
    public void balance_pay(String user_type, ApiCallBack callBack) {
        IServer server = new Request();

        server.setMethodName(moduleName + "balance_pay");
        server.setParam("uid", BaseData.getInstance().getUid());
        server.setParam("user_type", user_type);
        server.setParam("type", 2);//type: 1:店铺   2:会员

        server.setApiCallBack(callBack);

        server.request();
    }
}
