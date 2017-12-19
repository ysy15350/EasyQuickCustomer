package com.ysy15350.easyquickcustomer.account;

/**
 * Created by yangshiyou on 2017/9/20.
 */

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.ysy15350.easyquickcustomer.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import api.base.model.Response;
import api.model.BankCardInfo;
import base.data.BaseData;
import base.data.entity.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * 提现
 */
@ContentView(R.layout.activity_withdraw)
public class WithdrawActivity extends MVPBaseActivity<WithdrawViewInterface, WithdrawPresenter> implements WithdrawViewInterface {


    @Override
    protected WithdrawPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new WithdrawPresenter(WithdrawActivity.this);
    }

    @ViewInject(R.id.et_price)
    private EditText et_price;

    @Override
    public void initView() {

        super.initView();
        setFormHead("提现");

        et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateRatePrice();

            }
        });
    }

    private void calculateRatePrice() {
        String textPrice = mHolder.getViewText(R.id.et_price);

        if (!CommFunAndroid.isNullOrEmpty(textPrice)) {
            int price = CommFunAndroid.toInt32(textPrice, 0);
            if (price != 0) {
                if (price > balance) {
                    mHolder.setVisibility_VISIBLE(R.id.tv_txRatePrcie).setText(R.id.tv_txRatePrcie, "余额不足");
                } else {
                    if (txprice != 0) {
//                    if (price > txprice) {
//                        float ratePrice = price * user_rate / 100;
//                        mHolder.setText(R.id.tv_txRatePrcie, String.format("提现手续费%.1f", ratePrice));
//                    } else
                        if (txrate != 0) {
                            float ratePrice = price * txrate / 100;
                            mHolder.setText(R.id.tv_txRatePrcie, String.format("提现手续费%.1f", ratePrice));
                        }
                    } else if (txrate != 0) {
                        float ratePrice = price * txrate / 100;
                        mHolder.setText(R.id.tv_txRatePrcie, String.format("提现手续费%.1f", ratePrice));
                    }
                }

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        String txpriceCache = BaseData.getCache("txprice");
        String txrateCache = BaseData.getCache("txrate");
        String user_rateCache = BaseData.getCache("user_rate");

        if (!CommFunAndroid.isNullOrEmpty(txpriceCache)) {
            txprice = Float.parseFloat(txpriceCache);
        }
        if (!CommFunAndroid.isNullOrEmpty(txrateCache)) {
            txrate = Float.parseFloat(txrateCache);
        }
        if (!CommFunAndroid.isNullOrEmpty(user_rateCache)) {
            user_rate = Float.parseFloat(user_rateCache);
        }


        mPresenter.tx_rate();

        String bankCardInfoSelectJson = BaseData.getCache("bankCardInfoSelect");
        if (!CommFunAndroid.isNullOrEmpty(bankCardInfoSelectJson)) {
            BankCardInfo bankCardInfo = JsonConvertor.fromJson(bankCardInfoSelectJson, BankCardInfo.class);
            bindBankCardInfo(bankCardInfo);
        }

    }


    float balance = 0;
    float txprice = 0;
    float txrate = 0;
    float user_rate = 0;


    @Override
    public void tx_rateCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    txprice = response.getTxprice();
                    txrate = response.getTxrate();
                    user_rate = response.getUser_rate();

                    BaseData.setCache("txprice", txprice + "");
                    BaseData.setCache("txrate", txrate + "");
                    BaseData.setCache("user_rate", user_rate + "");

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    int mBankId = 0;

    private void bindBankCardInfo(BankCardInfo bankCardInfo) {

        try {
            if (bankCardInfo != null) {
                mBankId = bankCardInfo.getId();
                mHolder.setText(R.id.tv_bank, bankCardInfo.getTitle())
                        .setText(R.id.tv_bank_card, bankCardInfo.getNumber())
                ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void user_infoCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    UserInfo userInfo = response.getData(UserInfo.class);
                    userInfo.setUid(BaseData.getInstance().getUid());
                    BaseData.getInstance().setUserInfo(userInfo);

                    bindData();
                } else {
                    showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindData() {
        super.bindData();


        try {
            // if (BaseData.getInstance().getUid() != 0) {

            UserInfo userInfo = BaseData.getInstance().getUserInfo();
            bindUserInfo(userInfo);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定用户信息
     *
     * @param userInfo
     */
    private void bindUserInfo(UserInfo userInfo) {

        try {
            if (userInfo != null) {
                //
                balance = userInfo.getBalance();
                mHolder
                        .setText(R.id.tv_balance, String.format("可用余额%.2f元", userInfo.getBalance()))
                ;
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择银行卡
     *
     * @param view
     */
    @Event(value = R.id.ll_select_bank)
    private void ll_select_bankClick(View view) {
        Intent intent = new Intent(this, BankCardListActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void btn_okOnClick(View view) {
        String et_price = mHolder.getViewText(R.id.et_price);

        try {
            if (CommFunAndroid.isNullOrEmpty(et_price)) {
                showMsg("请输入提现金额");
                return;
            }

            float price = Float.parseFloat(et_price);
            if (mBankId == 0) {
                showMsg("请选择银行卡");
                return;
            }
            if (price == 0) {
                showMsg("请输入提现金额");
                return;
            }

            UserInfo userInfo = BaseData.getInstance().getUserInfo();
            if (userInfo == null) {
                showMsg("用户数据获取失败");
                return;
            }
            float balance = userInfo.getBalance();
            if (price > balance) {
                showMsg("提现金额不能大于余额");
                return;
            }

            showWaitDialog("服务器处理中，请稍后...");
            mPresenter.withdraw(price, mBankId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdrawCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    withdrawSuccess();
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提现成功
     */
    private void withdrawSuccess() {
        Intent intent = new Intent(this, AccountResultActivity.class);
        intent.putExtra("title", "提现成功");
        intent.putExtra("content", "你的提现将会在次日24点前到账");
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                BankCardInfo bankCardInfo = (BankCardInfo) data.getSerializableExtra("bankCardInfo");
                BaseData.setCache("bankCardInfoSelect", JsonConvertor.toJson(bankCardInfo));
                bindBankCardInfo(bankCardInfo);
            }
        }
    }


}
