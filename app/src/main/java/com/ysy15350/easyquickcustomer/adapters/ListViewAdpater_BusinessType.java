package com.ysy15350.easyquickcustomer.adapters;

import android.content.Context;

import com.ysy15350.easyquickcustomer.R;

import java.util.List;

import api.model.BusinessType;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * Created by yangshiyou on 2017/9/28.
 */

/**
 * 店铺分类
 */
public class ListViewAdpater_BusinessType extends CommonAdapter<BusinessType> {


    public ListViewAdpater_BusinessType(Context context, List<BusinessType> list) {
        super(context, list, R.layout.list_item_business_type);

    }

    @Override
    public void convert(ViewHolder holder, BusinessType t) {
        if (t != null) {
            holder.setText(R.id.tv_title, t.getTitle());

        }

    }


}
