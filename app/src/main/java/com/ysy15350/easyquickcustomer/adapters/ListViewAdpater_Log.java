package com.ysy15350.easyquickcustomer.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ysy15350.easyquickcustomer.BR;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.databinding.ListItemLogBinding;

import java.util.List;

import base.data.entity.RequestLog;

/**
 * 日志列表
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Log extends BaseAdapter {

    ListItemLogBinding binding;

    private Context mContext;

    private LayoutInflater mInflater;

    List<RequestLog> mList;

    public ListViewAdpater_Log(Context context, List<RequestLog> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //binding = DataBindingUtil.inflate(mInflater, R.layout.list_item_log, viewGroup, false);

        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.list_item_log, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(BR.log, mList.get(position));
        return binding.getRoot();
    }
}
