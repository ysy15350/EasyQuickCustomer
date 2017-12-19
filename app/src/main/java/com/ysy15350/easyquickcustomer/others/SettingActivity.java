package com.ysy15350.easyquickcustomer.others;


import android.content.Intent;
import android.view.View;

import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.dialog.DownloadDialog;
import com.ysy15350.easyquickcustomer.login.LoginActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;

import api.PublicApi;
import api.base.ApiCallBack;
import api.base.model.Response;
import api.impl.PublicApiImpl;
import api.model.Version;
import base.BaseActivity;
import base.data.BaseData;
import base.data.entity.UserInfo;
import cn.jpush.android.api.JPushInterface;
import common.CommFunAndroid;
import common.message.CommFunMessage;


/**
 * 设置
 *
 * @author yangshiyou
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {


    @Override
    public void initView() {

        super.initView();
        setFormHead("设置");
    }

    @Override
    protected void onResume() {
        super.onResume();

        String vesionName = CommFunAndroid.getAppVersionName(getApplicationContext());
        mHolder.setText(R.id.tv_version, "当前版本号：" + vesionName);
    }

    /**
     * 清除缓存
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {

    }

    private void clearErrorLog(File file) {
        try {

            if (!file.exists())
                return;
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                clearErrorLog(files[i]);
            }
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 检测更新
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {
        showWaitDialog("版本检测中...");
        checkVersion();
    }

    /**
     * 注销
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {

        UserInfo userInfo = BaseData.getInstance().getUserInfo();

        if (userInfo != null) {

            JPushInterface.deleteAlias(getApplicationContext(), userInfo.getUid());//删除别名，LoginActivity登录成功添加的别名

            PublicApi publicApi=new PublicApiImpl();
            publicApi.logout_identification(new ApiCallBack() {
                @Override
                public void onSuccess(boolean isCache, Response response) {
                    super.onSuccess(isCache, response);
                }
            });

        }

        BaseData.getInstance().setUid(0);
        Intent intent = getIntent();
        setResult(1, intent);//让主页关闭
        this.finish();
        startActivity(new Intent(this, LoginActivity.class));
        //new MessageToast(this, R.mipmap.icon_success, "操作成功").show();
    }


    private void checkVersion() {

        try {
            int version_code = CommFunAndroid.getAppVersionCode(getApplicationContext());
            PublicApi publicApi = new PublicApiImpl();
            publicApi.checkVersion(version_code, new ApiCallBack() {
                @Override
                public void onSuccess(boolean isCache, Response response) {
                    super.onSuccess(isCache, response);

                    CommFunMessage.hideWaitDialog();

                    if (response != null) {
                        int code = response.getCode();
                        String msg = response.getMessage();
                        if (code == 200) {
                            checkUpdate(response);
                        } else {
                            showMsg(msg);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void checkUpdate(Response response) {
        try {
            if (response != null) {
                if (response.getCode() == 200) {
                    Version version = response.getData(Version.class);
                    if (version != null) {
                        String versionName = version.getVersionName();

//                        DownloadDialog dialog = new DownloadDialog(this, "版本更新(" + version.getVersionName() + ")",
//                                version.getVersionName(), version.getDescription(), version.getFileSize(),
//                                version.getFile_url());
                        // ClearCacheDialog dialog = new ClearCacheDialog(this);

                        DownloadDialog dialog = new DownloadDialog(this, "版本更新(" + version.getVersionName() + ")",
                                version.getVersionName(), version.getDescription(), version.getFileSize(),
                                version.getFile_url());
                        dialog.show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
