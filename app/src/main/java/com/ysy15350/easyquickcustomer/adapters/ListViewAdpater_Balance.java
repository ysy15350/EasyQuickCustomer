package com.ysy15350.easyquickcustomer.adapters;

import android.content.Context;

import com.ysy15350.easyquickcustomer.R;

import java.util.List;

import api.model.BalanceInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

/**
 * 余额明细
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Balance extends CommonAdapter<BalanceInfo> {


    public ListViewAdpater_Balance(Context context, List<BalanceInfo> list) {
        super(context, list, R.layout.list_item_balance);


    }

    @Override
    public void convert(ViewHolder holder, BalanceInfo t) {
        if (t != null) {
            int c_type = t.getC_type();//类型  1 收入  2 支出
            int type = t.getType();//流水类型 1  会员支付  2 充值  3  提现
            switch (c_type) {
                case 1://收入
                    holder
                            .setText(R.id.tv_flag, "+")
                    ;
                    break;
                case 2://支出
                    holder
                            .setText(R.id.tv_flag, "-")
                    ;
                    break;

            }

            switch (type) {
                case 1://会员支付
                    holder.setText(R.id.tv_type, "会员支付").setImageResource(R.id.img_pay, R.mipmap.icon_yu_e);
                    break;
                case 2://充值
                    holder.setText(R.id.tv_type, "充值").setImageResource(R.id.img_pay, R.mipmap.icon_recharge);
                    break;
                case 3://提现
                    holder.setText(R.id.tv_type, "提现").setImageResource(R.id.img_pay, R.mipmap.icon_withdraw);
                    break;
                default:
                    holder.setImageResource(R.id.img_pay, R.mipmap.icon_yu_e);
            }

            holder.setText(R.id.tv_type, t.getType_title());

            long createTime = t.getCreate_time();
            String str = CommFunAndroid.stampToDateStr(createTime + "", "yyyy.MM.dd HH:mm:ss");


            holder.setText(R.id.tv_total_price, t.getTotal_price() + "")
                    .setText(R.id.tv_createTime, str)
            ;

        }

    }


}
