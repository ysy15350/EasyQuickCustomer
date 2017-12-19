package com.ysy15350.easyquickcustomer.adapters;

import android.content.Context;
import android.view.View;

import com.ysy15350.easyquickcustomer.R;

import java.util.List;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import api.model.BankCardInfo;
import base.adapter.CommonAdapter;
import base.adapter.ViewHolder;
import common.message.CommFunMessage;
import custom_view.dialog.ConfirmDialog;

/**
 * 余额明细
 *
 * @author yangshiyou
 */
public class ListViewAdpater_BankCard extends CommonAdapter<BankCardInfo> {


    public ListViewAdpater_BankCard(Context context, List<BankCardInfo> list) {
        super(context, list, R.layout.list_item_bank_card);


    }

    @Override
    public void convert(ViewHolder holder, final BankCardInfo t) {
        if (t != null) {
            holder.setText(R.id.tv_title, t.getTitle())
                    .setText(R.id.tv_number, t.getNumber())
            ;
            holder.getView(R.id.btn_del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ConfirmDialog dialog = new ConfirmDialog(mContext, "是否删除【" + t.getTitle() + "】银行卡？");
                    dialog.setDialogListener(new ConfirmDialog.DialogListener() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onOkClick() {
                            CommFunMessage.showWaitDialog(mContext, "服务器处理中...");
                            MemberApi memberApi = new MemberApiImpl();
                            memberApi.del_user_bank(t.getId(), new ApiCallBack() {
                                @Override
                                public void onSuccess(boolean isCache, Response response) {
                                    super.onSuccess(isCache, response);
                                    if (response != null) {
                                        CommFunMessage.hideWaitDialog();
                                        int code = response.getCode();
                                        String msg = response.getMessage();

                                        if (code == 200) {
                                            if (mListener != null) {
                                                mListener.delSuccess();
                                            }
                                        }

                                        CommFunMessage.showMsgBox(msg);
                                    }
                                }

                                @Override
                                public void onFailed(String msg) {
                                    super.onFailed(msg);
                                    CommFunMessage.hideWaitDialog();
                                    CommFunMessage.showMsgBox("服务器错误");
                                }
                            });
                        }
                    });
                    dialog.show();
                }
            });
        }

    }

    private BankCardListener mListener;

    public void setListener(BankCardListener listener) {
        mListener = listener;
    }

    public interface BankCardListener {
        public void delSuccess();
    }

}
