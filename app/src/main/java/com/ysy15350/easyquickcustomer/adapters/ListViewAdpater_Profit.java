package com.ysy15350.easyquickcustomer.adapters;

import android.content.Context;

import com.ysy15350.easyquickcustomer.R;

import java.util.List;

import api.model.ProfitInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.CommFunAndroid;

/**
 * 收益明细
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Profit extends CommonAdapter<ProfitInfo> {


    public ListViewAdpater_Profit(Context context, List<ProfitInfo> list) {
        super(context, list, R.layout.list_item_profit);


    }

    @Override
    public void convert(ViewHolder holder, ProfitInfo t) {
        if (t != null) {

            if (t != null) {
                int c_type = t.getC_type();//类型  1 收入  2 支出
                int type = t.getType();//流水类型 1  会员支付  2 充值  3  提现
                switch (c_type) {
                    case 1://收入
                        holder
                                .setText(R.id.tv_flag, "+")
                                .setImageResource(R.id.img_pay, R.mipmap.icon_recharge).setText(R.id.tv_type, "余额收益");
                        break;
                    case 2://支出
                        holder
                                .setText(R.id.tv_flag, "-")
                                .setImageResource(R.id.img_pay, R.mipmap.icon_withdraw).setText(R.id.tv_type, "消费支出");
                        break;

                }

//                switch (type) {
//                    case 1://会员支付
//                        holder.setText(R.id.tv_type, "会员支付");
//                        break;
//                    case 2://充值
//                        holder.setText(R.id.tv_type, "充值");
//                        break;
//                    case 3://提现
//                        holder.setText(R.id.tv_type, "提现");
//                        break;
//                }

                long createTime = t.getCreate_time();
                String str = CommFunAndroid.stampToDateStr(createTime + "", "yyyy.MM.dd HH:mm:ss");

                holder.setText(R.id.tv_total_price, t.getPrice() + "")
                        .setText(R.id.tv_createTime, str)
                ;

            }

        }

    }


}
