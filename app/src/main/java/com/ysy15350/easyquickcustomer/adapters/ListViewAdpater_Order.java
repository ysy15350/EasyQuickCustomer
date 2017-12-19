package com.ysy15350.easyquickcustomer.adapters;

import android.content.Context;

import com.ysy15350.easyquickcustomer.R;

import java.util.List;

import api.model.OrderInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

/**
 * 车辆品牌gridview
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Order extends CommonAdapter<OrderInfo> {


    public ListViewAdpater_Order(Context context, List<OrderInfo> list) {
        super(context, list, R.layout.list_item_order);


    }

    @Override
    public void convert(ViewHolder holder, OrderInfo t) {
        if (t != null) {
            int type = t.getType();
            switch (type) {
                case 1://支付宝支付
                    holder
                            //.setText(R.id.tv_type, "支付宝支付")
                            .setImageResource(R.id.img_pay, R.mipmap.icon_zhifubao);
                    break;
                case 2://微信支付
                    holder
                            //.setText(R.id.tv_type, "微信支付")
                            .setImageResource(R.id.img_pay, R.mipmap.icon_weixin);
                    break;
                case 3://余额
                    holder
                            //.setText(R.id.tv_type, "余额转入")
                            .setImageResource(R.id.img_pay, R.mipmap.icon_yu_e);
                    break;
            }

            long create_time = t.getCreate_time();
            String time_str = CommFunAndroid.stampToDateStr(create_time + "", "yyyy.MM.dd HH:mm:ss");
            holder
                    .setText(R.id.tv_type,  t.getType_title())
                    .setText(R.id.tv_pre_price, t.getPre_price() + "")
                    .setText(R.id.tv_total_price, t.getTotal_price() + "")
                    .setText(R.id.tv_createTime, time_str);


        }

    }


}
