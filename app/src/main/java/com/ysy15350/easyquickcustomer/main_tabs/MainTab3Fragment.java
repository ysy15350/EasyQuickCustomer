package com.ysy15350.easyquickcustomer.main_tabs;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.account.BalanceActivity;
import com.ysy15350.easyquickcustomer.account.OrderListActivity;
import com.ysy15350.easyquickcustomer.account.PaySureActivity;
import com.ysy15350.easyquickcustomer.account.RechargeActivity;
import com.ysy15350.easyquickcustomer.account.SetPayPriceActivity;
import com.ysy15350.easyquickcustomer.donation.DonationActivity;
import com.ysy15350.easyquickcustomer.login.LoginActivity;
import com.ysy15350.easyquickcustomer.mine.MyInfoActivity;
import com.ysy15350.easyquickcustomer.others.SettingActivity;
import com.yzq.zxinglibrary.Consants;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.Map;

import api.base.model.Config;
import api.base.model.Response;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.data.entity.UserInfo;
import base.mvp.MVPBaseFragment;
import cn.jpush.android.api.JPushInterface;
import common.CommFunAndroid;
import common.string.JsonConvertor;


/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_main_tab3)
public class MainTab3Fragment extends MVPBaseFragment<MainTab3ViewInterface, MainTab3Presenter>
        implements MainTab3ViewInterface {

    private static final String TAG = "MainTab3Fragment";

    public MainTab3Fragment() {
    }

    @Override
    public MainTab3Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab3Presenter(getActivity());
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.user_info();
        mPresenter.adlist();//广告图
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
    public void adlistCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("adlist", response.getResultJson());
                    Map<String, String> map = response.getData(new TypeToken<Map<String, String>>() {
                    }.getType());

                    bindAdList(map);//绑定广告图
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


            //        BaseData.setCache("adlist", response.getResultJson());
            String adlistJson = BaseData.getCache("adlist");

            if (!CommFunAndroid.isNullOrEmpty(adlistJson)) {
                Map<String, String> map = JsonConvertor.fromJson(adlistJson, new TypeToken<Map<String, String>>() {
                }.getType());

                bindAdList(map);//绑定广告图
            }

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

                //设置别名，SettingActivity注销功能删除别名
                JPushInterface.setAlias(getActivity().getApplicationContext(), userInfo.getUid(), CommFunAndroid.getDeviceId(getActivity().getApplicationContext()));

                //
                mHolder
                        .setImageURL(R.id.img_head, ConfigHelper.getServerUrl(Config.isDebug, false) + userInfo.getHeadimgurl(), true)
                        .setText(R.id.tv_nickName, userInfo.getFullname())
                        .setText(R.id.tv_mobile, userInfo.getMobile())
                        .setText(R.id.tv_total_price, String.format("%.2f", userInfo.getTotal_price()))
                        .setText(R.id.tv_profit, String.format("%.2f", userInfo.getProfit()))
                        .setText(R.id.tv_balance, String.format("%.2f", userInfo.getBalance() + userInfo.getProfit()))
                        .setText(R.id.tv_rate, String.format("%.2f", userInfo.getRate()))
                ;
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定广告图
     *
     * @param map
     */
    private void bindAdList(Map<String, String> map) {

        try {
            if (null != map) {
                String icon = map.get("icon");
                if (!CommFunAndroid.isNullOrEmpty(icon)) {

                    String url = ConfigHelper.getServerUrl(Config.isDebug, false) + icon;

                    Log.d(TAG, "bindAdList: url=" + url);
                    mHolder.setImageURL(R.id.img_ad, url, 480, 150);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置
     *
     * @param view
     */
    @Event(value = R.id.btn_setting)
    private void btn_settingClick(View view) {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    /**
     * 个人资料
     *
     * @param view
     */
    @Event(value = R.id.ll_info)
    private void ll_infoClick(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, MyInfoActivity.class));
    }

    public static final int REQUEST_CODE_SCAN = 1;

    public static int type = 1;

    /**
     * 扫一扫
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
//        https://github.com/yuzhiqiang1993/zxing
        type = 1;
        mPresenter.checkUserinfo();
    }


    /**
     * 付钱
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {
        type = 2;
        mPresenter.checkUserinfo();
    }

    /**
     * 余额
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, BalanceActivity.class));
    }


    /**
     * 充值
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, RechargeActivity.class));
        //startActivity(new Intent(mContext, PayDemoActivity.class));
    }

    /**
     * 提现
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_5)
    private void ll_menu_5Click(View view) {
//        if (isLogin())
//            startActivity(new Intent(mContext, WithdrawActivity.class));
//        //startActivity(new Intent(mContext, PayDemoActivity.class));

        if (isLogin())
            startActivity(new Intent(mContext, DonationActivity.class));//捐款

    }


    /**
     * 订单
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_6)
    private void ll_menu_6Click(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, OrderListActivity.class));
    }

    /**
     * 生活缴费
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_7)
    private void ll_menu_7Click(View view) {
        if (isLogin())
            showMsg("暂未开通，敬请期待，感谢您的谅解！");
    }

    /**
     * 城市服务
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_8)
    private void ll_menu_8Click(View view) {
        if (isLogin())
            showMsg("暂未开通，敬请期待，感谢您的谅解！");
    }

    /**
     * 共享单车
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_9)
    private void ll_menu_9Click(View view) {
        if (isLogin())
            showMsg("暂未开通，敬请期待，感谢您的谅解！");
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

            //showMsg(content);
            Log.d(TAG, "scanResult() called with: content = [" + content + "]");

            if (CommFunAndroid.isNullOrEmpty(content)) {
                showMsg("扫描结果为空");
                return;
            } else if (content.contains("payment_type")) {
                String str = content.substring(content.lastIndexOf("/") + 1, content.indexOf(".html"));
                mPresenter.balance_pay(str);
                return;
            } else if (content.contains("alipay") || content.contains("ALIPAY")) {
                showMsg("请使用支付宝客户端扫描");
                return;
            } else if (content.contains("weixin") || content.contains("wx")) {
                showMsg("请使用微信客户端扫描");
                return;
            } else if (CommFunAndroid.isNum(content)) {
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

    @Override
    public void checkUserinfoCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    if (type == 1) {
                        scanQrcode();
                    } else if (type == 2) {
                        if (isLogin()) {
                            startActivity(new Intent(mContext, SetPayPriceActivity.class));
                        }
                    }
                } else if (code == 502) {
                    showMsg(msg);

                    getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
//                MessageBox.show("跳转");

                        }
                    }, 1 * 1000);//3秒后执行


                } else showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void balance_payCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    int cid = response.getCid();
                    String name = response.getFullname();
                    Intent intent = new Intent(getActivity(), SetPayPriceActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("cid", cid);
                    intent.putExtra("name", name);
                    startActivity(intent);
                } else showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
