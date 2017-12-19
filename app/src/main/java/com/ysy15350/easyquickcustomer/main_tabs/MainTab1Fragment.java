package com.ysy15350.easyquickcustomer.main_tabs;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.account.PaySureActivity;
import com.ysy15350.easyquickcustomer.account.ProfitDetailListActivity;
import com.ysy15350.easyquickcustomer.account.SetPayPriceActivity;
import com.yzq.zxinglibrary.Consants;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import api.base.model.Config;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.data.entity.UserInfo;
import base.mvp.MVPBaseFragment;
import common.CommFun;
import common.CommFunAndroid;
import custom_view.progress_bar.CircleBar;


/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_main_tab1)
public class MainTab1Fragment extends MVPBaseFragment<MainTab1ViewInterface, MainTab1Presenter>
        implements MainTab1ViewInterface {


    private static int count = 0;

    public MainTab1Fragment() {
    }


    @Override
    public MainTab1Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab1Presenter(getActivity());
    }


    @ViewInject(R.id.myProgress)
    private CircleBar myProgress;

    @Override
    public void onResume() {
        super.onResume();


        UserInfo userInfo = BaseData.getInstance().getUserInfo();
        bindUserInfo(userInfo);

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
                mHolder
                        .setImageURL(R.id.img_head, ConfigHelper.getServerUrl(Config.isDebug, false) + userInfo.getHeadimgurl(), true)
                        .setText(R.id.tv_nickName, userInfo.getNickname())
                        .setText(R.id.tv_mobile, userInfo.getMobile())
                        .setText(R.id.tv_total_price, String.format("%.2f", userInfo.getTotal_price()))
                        .setText(R.id.tv_day_total_price, String.format("%.2f", userInfo.getDay_total_price()))
                        .setText(R.id.tv_balance, String.format("%.2f", userInfo.getBalance() + userInfo.getProfit()))//余额+收益
                        .setText(R.id.tv_rate, String.format("%.2f", userInfo.getRate()))
                ;
                int rate = CommFunAndroid.toInt32(userInfo.getRate() * 100, 0);

                float sweepAngle = CommFunAndroid.toInt32(300 * rate / 1000, 0);

                myProgress.setText(rate);
                myProgress.setSweepAngle(sweepAngle);//总共300°
                myProgress.startCustomAnimation();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 收益明细
     *
     * @param view
     */
    @Event(value = R.id.btn_detail)
    private void btn_detailClick(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, ProfitDetailListActivity.class));
    }


    /**
     * 扫描二维码
     *
     * @param view
     */
    @Event(value = R.id.btn_recharge)
    private void btn_rechargeClick(View view) {
        if (callCameraPermission(getActivity()))
            scanQrcode();
    }

    public static final int REQUEST_CODE_SCAN = 1;


    /**
     * 付款二维码
     *
     * @param view
     */
    @Event(value = R.id.btn_withdraw)
    private void btn_withdrawClick(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, SetPayPriceActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Consants.CODED_CONTENT);
                //resultTv.setText("扫描结果为：" + content);

                scanResult(content);
            }
        }
    }

    private void scanResult(String content) {


        try {
            if (CommFunAndroid.isNullOrEmpty(content)) {
                showMsg("扫描结果为空");
                return;
            }
            else if (!CommFun.isNullOrEmpty(content) && CommFun.isNum(content)) {

                //showMsg("微信二维码：" + content);
                mPresenter.micro_pay(content);

                return;

            }
            else if (content.contains("alipay")|| content.contains("ALIPAY")) {
                showMsg("请使用支付宝客户端扫描");
                return;
            } else if (content.contains("weixin")|| content.contains("wx")) {
                showMsg("请使用微信客户端扫描");
                return;
            }
            else if (CommFunAndroid.isNum(content)) {
                showMsg("无法识别二维码");
                return;
            }

            Intent intent = new Intent(getActivity(), PaySureActivity.class);
            intent.putExtra("content", content);
            startActivity(intent);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
