package com.ysy15350.easyquickcustomer.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.adapters.ListViewAdpater_BusinessType;

import java.util.List;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.model.BusinessType;
import base.data.BaseData;
import common.CommFunAndroid;
import common.string.JsonConvertor;


public class BusinessTypeListViewPopupWindow extends PopupWindow {

    private String mTitle;

    private Context mContext;

    private View conentView;

    private TextView tv_title;

    private ListView listView;

    private View btn_cancel;

    public BusinessTypeListViewPopupWindow(final Activity context) {
        //mTitle = title;
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_list, null);

        initView();// 初始化按钮事件

        loadCache();

        initData();

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
        // ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        // this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.app_pop_2);
    }

    private void initView() {

        conentView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

//        tv_title = (TextView) conentView.findViewById(R.id.tv_title);
//        if (tv_title != null) {
//            tv_title.setText(mTitle);
//        }

        listView = (ListView) conentView.findViewById(R.id.lv_pop);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {

                    BusinessType ticketType = mList.get(position);

                    mListener.onItemClick(position, ticketType);

                    dismiss();
                }

            }
        });

//        btn_cancel = conentView.findViewById(R.id.btn_cancel);
//
//        btn_cancel.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                dismiss();
//            }
//        });

    }

    BaseAdapter mAdpater;

    List<BusinessType> mList;


    private void loadCache() {
//        BaseData.setCache("store_cate",response.getResultJson());
        String store_cateJson = BaseData.getCache("store_cate");
        if (!CommFunAndroid.isNullOrEmpty(store_cateJson)) {
            mList = JsonConvertor.fromJson(store_cateJson, new TypeToken<List<BusinessType>>() {
            }.getType());

            mAdpater = new ListViewAdpater_BusinessType(mContext, mList);
            listView.setAdapter(mAdpater);

        }
    }


    private void initData() {


        MemberApi api = new MemberApiImpl();

        api.store_cate(1, 100, new ApiCallBack() {

            @Override
            public void onSuccess(boolean isCache, Response response) {
                if (response != null) {
                    int code = response.getCode();
                    if (code == 200) {

                        BaseData.setCache("store_cate", response.getResultJson());

                        mList = response.getData(new TypeToken<List<BusinessType>>() {
                        }.getType());

                        mAdpater = new ListViewAdpater_BusinessType(mContext, mList);
                        listView.setAdapter(mAdpater);

                    }
                }
            }
        });

    }

    /**
     * 显示popupwindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }

    private BusinessTypePopListener mListener;

    public void setBusinessTypePopListener(BusinessTypePopListener listener) {
        this.mListener = listener;
    }

    public interface BusinessTypePopListener {
        public void onItemClick(int position, BusinessType ticketType);
    }

}
