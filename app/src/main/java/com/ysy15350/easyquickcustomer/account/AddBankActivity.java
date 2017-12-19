package com.ysy15350.easyquickcustomer.account;

/**
 * Created by yangshiyou on 2017/9/20.
 */


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.ysy15350.easyquickcustomer.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import api.MemberApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.MemberApiImpl;
import base.BaseActivity;
import common.message.CommFunMessage;

/**
 * 添加银行
 */
@ContentView(R.layout.activity_add_bank)
public class AddBankActivity extends BaseActivity implements Validator.ValidationListener {

    private static final String TAG = "AddBankActivity";

    @Override
    public void initView() {

        super.initView();
        setFormHead("添加银行卡");

        validator = new Validator(this);

        validator.setValidationListener(this);
    }

    /**
     * 持卡人
     */
    @ViewInject(R.id.et_userName)
    @NotEmpty(messageResId = R.string.add_bank_card_mustName)
    @Order(1)
    private EditText et_userName;

    /**
     * 手机号
     */
    @ViewInject(R.id.et_mobile)
    @Pattern(regex = "^0?(1[0-9][0-9])[0-9]{8}$", messageResId = R.string.register_phone_error)
    @Order(2)
    private EditText et_mobile;

    /**
     * 持卡人
     */
    @ViewInject(R.id.et_bank_card)
    @NotEmpty(messageResId = R.string.add_bank_card_mustCard)
    @Order(3)
    private EditText et_bank_card;


    private final int REQUEST_CODE_BANK = 1;

    /**
     * 选择银行
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {
        Intent intent = new Intent(this, BankListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_BANK);
        //startActivity(new Intent(this, BankListActivity.class));
    }

    /**
     * 表单验证器
     */
    Validator validator;

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);

        validator.validate();

        if (validationSucceeded) {

            String et_userName = mHolder.getViewText(R.id.et_userName);
            String mobile = mHolder.getViewText(R.id.et_mobile);
            String et_bank_card = mHolder.getViewText(R.id.et_bank_card);
            String et_open_bank = mHolder.getViewText(R.id.et_open_bank);

            if (mBankId == -1) {
                showMsg("请选择银行");
                return;
            }

            add_user_bank(2, et_userName, mobile, et_bank_card, mBankId, et_open_bank);
        }
    }

    /**
     * 添加银行卡
     *
     * @param type
     * @param username
     * @param mobile
     * @param bank_id
     * @param open_bank
     */
    public void add_user_bank(int type, String username, String mobile, String number, int bank_id, String open_bank) {
        CommFunMessage.showWaitDialog(this, "服务器处理中...");
        MemberApi memberApi = new MemberApiImpl();
        memberApi.add_user_bank(2, username, mobile, number, bank_id, open_bank, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);

                CommFunMessage.hideWaitDialog();

                try {
                    if (response != null) {
                        int code = response.getCode();
                        String msg = response.getMessage();
                        if (code == 200) {
                            AddBankActivity.this.finish();
                        }
                        showMsg(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    boolean validationSucceeded = false;

    @Override
    public void onValidationSucceeded() {
        validationSucceeded = true;
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        if (errors != null && !errors.isEmpty()) {
            validationSucceeded = false;
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(this);
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                }
            }
        }
    }

    int mBankId = -1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        switch (requestCode) {
            case REQUEST_CODE_BANK:
                if (resultCode == RESULT_OK) {
                    int bank_id = data.getIntExtra("bank_id", 0);
                    String bank_name = data.getStringExtra("bank_name");
                    mBankId = bank_id;
                    mHolder.setText(R.id.tv_bank, bank_name);
//                    showMsg(bank_id + "接收银行id" + bank_name);
                }
                break;
        }
    }
}
