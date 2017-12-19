package com.ysy15350.easyquickcustomer.mine;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jph.takephoto.model.TImage;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.account.SetPayPwdActivity;
import com.ysy15350.easyquickcustomer.author.UpdatePwdActivity;
import com.ysy15350.easyquickcustomer.image_select.ImgSelectActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.base.model.Config;
import api.base.model.Response;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.data.entity.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import custom_view.popupwindow.PhotoSelectPopupWindow;

/**
 * Created by yangshiyou on 2017/9/20.
 */

/**
 * 个人资料
 */
@ContentView(R.layout.activity_user_info)
public class MyInfoActivity extends MVPBaseActivity<MyInfoViewInterface, MyInfoPresenter>
        implements MyInfoViewInterface, Validator.ValidationListener {


    @Override
    protected MyInfoPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new MyInfoPresenter(MyInfoActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("个人资料");

        setMenuText("保存");
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.user_info();
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
            if (BaseData.getInstance().getUid() != 0) {

                //            ((MainActivity) getActivity()).bindData();

                UserInfo userInfo = BaseData.getInstance().getUserInfo();
                if (userInfo != null) {
                    mHolder
                            .setImageURL(R.id.img_head, ConfigHelper.getServerUrl(Config.isDebug, false) + userInfo.getHeadimgurl(), true)
                            .setText(R.id.et_mobile, userInfo.getMobile())
                            .setText(R.id.et_nickName, userInfo.getNickname())
                            .setText(R.id.et_fullName, userInfo.getFullname())
                            .setText(R.id.et_cardNo, userInfo.getCards())
                            .setText(R.id.et_address, userInfo.getAddress())
                    ;

                    String pay_pwd = userInfo.getPay_password();
                    if (CommFunAndroid.isNullOrEmpty(pay_pwd)) {
                        mHolder.setText(R.id.tv_pay_pwd, "点击设置支付密码");
                    } else {
                        mHolder.setText(R.id.tv_pay_pwd, "点击修改支付密码");
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传头像
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
        PhotoSelectPopupWindow popupWindow = new PhotoSelectPopupWindow(this);
        popupWindow.showPopupWindow(mContentView);

        popupWindow.setPopListener(new PhotoSelectPopupWindow.PopListener() {
            @Override
            public void onTakePhotoClick() {
                getPhoto(2);
            }

            @Override
            public void onSelectPhotoClick() {
                getPhoto(1);
            }

            @Override
            public void onCancelClick() {

            }
        });
    }

    private void getPhoto(int type) {
        Intent intent = new Intent(this, ImgSelectActivity.class);
        CommFunAndroid.setSharedPreferences("type", type + "");
        intent.putExtra("width", 300);
        intent.putExtra("height", 300);
        startActivityForResult(intent, PHOTO_REQUEST);
    }

//    /**
//     * 选择主营范围
//     *
//     * @param view
//     */
//    @Event(value = R.id.ll_menu_9)
//    private void ll_menu_9Click(View view) {
//        Intent intent = new Intent(this, MainBusinessListActivity.class);
//
//        startActivity(intent);
//    }

    /**
     * 修改密码
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_11)
    private void ll_menu_11Click(View view) {
        Intent intent = new Intent(this, UpdatePwdActivity.class);

        startActivity(intent);

    }

    /**
     * 修改密码
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_12)
    private void ll_menu_12Click(View view) {
        Intent intent = new Intent(this, SetPayPwdActivity.class);

        startActivityForResult(intent, PAY_PWD_REQUEST);

    }

    @Event(value = R.id.ll_menu)
    private void ll_menuClick(View view) {

        String headimgurl = "";
        String nickname = mHolder.getViewText(R.id.et_nickName);
        String fullname = mHolder.getViewText(R.id.et_fullName);
        String cards = mHolder.getViewText(R.id.et_cardNo);
        String address = mHolder.getViewText(R.id.et_address);

        UserInfo userInfo = BaseData.getInstance().getUserInfo();
        if (userInfo != null) {
            userInfo.setHeadimgurl(headimgurl);
            userInfo.setNickname(nickname);
            userInfo.setFullname(fullname);
            userInfo.setCards(cards);
            userInfo.setAddress(address);

        }
        save_info(userInfo);
    }

    private void save_info(UserInfo userInfo) {

        try {
            if (userInfo != null) {
                String headimgurl = userInfo.getHeadimgurl();
                String nickname = userInfo.getNickname();
                String fullname = userInfo.getFullname();
                String cards = userInfo.getCards();
                String address = userInfo.getAddress();
                String pay_password = userInfo.getPay_password();
                mPresenter.save_info(headimgurl, nickname, fullname, cards, address, pay_password);
            } else
                showMsg("用户信息丢失，请重试");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save_infoCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    mPresenter.user_info();
                    //this.finish();
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private static final int PHOTO_REQUEST = 100;// 选择照片
    private static final int PAY_PWD_REQUEST = 101;// 选择照片

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PHOTO_REQUEST) {
                if (null != data) {//(resultCode == RESULT_OK) {
                    ArrayList<TImage> images = (ArrayList<TImage>) data.getSerializableExtra("images");
                    if (images != null && !images.isEmpty()) {
                        String path = images.get(0).getOriginalPath();
                        if (CommFunAndroid.isNullOrEmpty(path))
                            path = images.get(0).getCompressPath();

                        File file = new File(path);
                        if (file != null && file.exists()) {

                            ImageView img_head = mHolder.getView(R.id.img_head);

                            img_head.setImageURI(Uri.parse(path));
                        }
                        uploadImage(file);
                    }
                } else {
                    showMsg("图片获取失败");
                }
            } else if (requestCode == PAY_PWD_REQUEST) {
                String password = data.getStringExtra("password");
                UserInfo userInfo = BaseData.getInstance().getUserInfo();
                if (userInfo != null) {
                    userInfo.setPay_password(password);
                }
                save_info(userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage(File file) {
        if (file != null && file.exists()) {
            mPresenter.uppicture(file);
        } else {
            showMsg("文件不存在");
        }

    }

    @Override
    public void uppictureCallback(Response response) {
        try {
            if (response != null) {
                //String msg = response.getMessage();
                if (response.getCode() == 200) {
                    showMsg("上传成功");
                    //\/Uploads\/Picture\/2017-09-26\/59c9ca728b2f4.png
                    String imgUrl = ConfigHelper.getServerUrl(Config.isDebug, false) + response.getPic();


                    mHolder.setImageURL(R.id.img_head, imgUrl, true);

                    //mPresenter.save_info("icon", response.getId() + "");

                    UserInfo userInfo = BaseData.getInstance().getUserInfo();
                    if (userInfo != null) {
                        userInfo.setHeadimgurl(response.getId() + "");
                    }
                    save_info(userInfo);
                } else {
                    showMsg("上传失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
