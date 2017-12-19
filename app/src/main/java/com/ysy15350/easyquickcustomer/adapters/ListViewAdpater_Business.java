package com.ysy15350.easyquickcustomer.adapters;

import android.content.Context;

import com.ysy15350.easyquickcustomer.R;

import java.util.List;

import api.base.model.Config;
import api.model.Business;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import base.data.ConfigHelper;
import common.CommFunAndroid;

/**
 * 商家列表
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Business extends CommonAdapter<Business> {


    public ListViewAdpater_Business(Context context, List<Business> list) {
        super(context, list, R.layout.list_item_business);


    }

    @Override
    public void convert(ViewHolder holder, Business t) {
        if (t != null) {
            if (CommFunAndroid.isNullOrEmpty(t.getIcon())) {
                holder
                        .setImageResource(R.id.img_business, R.mipmap.icon_business);
            } else {
                holder
                        .setImageURL(R.id.img_business, ConfigHelper.getServerUrl(Config.isDebug, false) + t.getIcon(), true);
            }


            holder
                    .setText(R.id.tv_title, t.getTitle())
                    .setText(R.id.tv_descrtext, t.getDescrtext())
                    .setText(R.id.tv_discount, t.getDiscount())//折扣
                    .setText(R.id.tv_distance, t.getDistance() + " m");
            ;

        }

    }


}
