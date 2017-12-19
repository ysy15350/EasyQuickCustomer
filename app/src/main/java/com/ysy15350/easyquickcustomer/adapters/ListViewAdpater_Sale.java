package com.ysy15350.easyquickcustomer.adapters;

import android.content.Context;

import com.ysy15350.easyquickcustomer.R;

import java.util.List;

import api.model.BalanceInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;

/**
 * 选择主营范围
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Sale extends CommonAdapter<BalanceInfo> {


    public ListViewAdpater_Sale(Context context, List<BalanceInfo> list) {
        super(context, list, R.layout.list_item_sale);


    }

    @Override
    public void convert(ViewHolder holder, BalanceInfo t) {
        if (t != null) {


        }

    }


}
