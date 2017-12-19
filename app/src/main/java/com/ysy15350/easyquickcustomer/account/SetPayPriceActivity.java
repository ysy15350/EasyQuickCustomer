package com.ysy15350.easyquickcustomer.account;

import android.content.Intent;
import android.util.ArrayMap;
import android.view.View;

import com.ysy15350.easyquickcustomer.R;

import org.xutils.view.annotation.ContentView;

import java.util.HashMap;
import java.util.Map;

import base.BaseActivity;
import base.data.BaseData;
import base.data.entity.UserInfo;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/20.
 */

/**
 * 设置金额
 */
@ContentView(R.layout.activity_set_price)
public class SetPayPriceActivity extends BaseActivity {


    int mType = 0;
    int mCid = 0;
    String mName = "";

    @Override
    public void initView() {

        super.initView();
        setFormHead("设置金额");
        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getIntExtra("type", 0);
            mCid = intent.getIntExtra("cid", 0);
            mName = intent.getStringExtra("name");
        }
    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);
        float price = checkPrice();

        if (price <= 0)
            return;

        if (mType == 3) {
            Intent intent = new Intent(SetPayPriceActivity.this, PaySureActivity.class);
            ArrayMap<String, String> map = new ArrayMap<>();
            map.put("type", mType + "");
            map.put("uid", mCid + "");
            map.put("name", mName);
            map.put("price", price + "");
            String content = JsonConvertor.toJson(map);
            intent.putExtra("content", content);
            startActivity(intent);
            finish();
        } else {
            produceQrcode(price);
        }

    }

    private void produceQrcode(float price) {
        UserInfo userInfo = BaseData.getInstance().getUserInfo();

        Map<String, String> map = new HashMap<>();
        map.put("type", "0");
        map.put("user_type", "2");//type:1：商家；2：用户
        map.put("uid", BaseData.getInstance().getUid() + "");
        map.put("price", price + "");

        String remark = mHolder.getViewText(R.id.et_remark);

        map.put("remark", remark);

        if (userInfo != null) {
            String name = userInfo.getNickname();
            map.put("name", name);
        }


        Intent intent = new Intent(SetPayPriceActivity.this, QRCodePayActivity.class);
        intent.putExtra("type", 0);
        intent.putExtra("price", price);
        intent.putExtra("url", JsonConvertor.toJson(map));
        intent.putExtra("remark", remark);
        startActivity(intent);
        finish();
    }

    private float checkPrice() {
        String et_priceText = mHolder.getViewText(R.id.et_price);
        if (CommFunAndroid.isNullOrEmpty(et_priceText)) {
            showMsg("请输入金额");

            return 0;
        }

        if (!CommFunAndroid.isNumber(et_priceText)) {
            showMsg("金额输入有误");
            return 0;
        }

        float price = Float.parseFloat(et_priceText);

        if (price == 0) {
            showMsg("金额不能为零");
            return 0;
        }

        return price;
    }


}
