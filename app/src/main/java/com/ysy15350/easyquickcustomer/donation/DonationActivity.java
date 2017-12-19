package com.ysy15350.easyquickcustomer.donation;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquickcustomer.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import api.base.model.Config;
import api.base.model.Response;
import api.model.Banner;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.mvp.MVPBaseActivity;
import common.CommFun;
import common.CommFunAndroid;
import common.string.JsonConvertor;
import custom_view.SlideShowViewAuto;
import custom_view.dialog.PayDialog;

/**
 * Created by yangshiyou on 2017/9/20.
 */

@ContentView(R.layout.activity_donation)
public class DonationActivity extends MVPBaseActivity<DonationViewInterface, DonationPresenter>
        implements DonationViewInterface {

    private static final String TAG = "DonationActivity";


//    @ViewInject(R.id.img_qr_code)
//    private CanvasRQ img_qr_code;

    @ViewInject(R.id.img_qr_code)
    private ImageView img_qr_code;


    @Override
    protected DonationPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new DonationPresenter(DonationActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("捐款");
    }

    @Override
    protected void onResume() {
        super.onResume();


        String personScollImgJson = BaseData.getCache("personScollImg");
        if (!CommFun.isNullOrEmpty(personScollImgJson)) {
            List<Banner> list = JsonConvertor.fromJson(personScollImgJson, new TypeToken<List<Banner>>() {
            }.getType());
            bindBanner(list);
        }


        String ewmimg = BaseData.getCache("ewmimg");//二维码

        if (!CommFun.isNullOrEmpty(ewmimg)) {
            mHolder.setImageURL(R.id.img_qr_code, ConfigHelper.getServerUrl(Config.isDebug, false) + ewmimg, 200, 200);
            //img_qr_code.setUrl(Config.getServer() + ewmimg);
        }

        //mPresenter.scollImg();//轮播图
        mPresenter.personScollImg();//捐款页面轮播和二维码
    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);

        float price = checkPrice();

        if (price <= 0)
            return;


        PayDialog dialog = new PayDialog(DonationActivity.this);
        dialog.setDialogListener(new PayDialog.DialogListener() {
            @Override
            public void onCancelClick() {

            }

            @Override
            public void onOkClick(String password) {

                float price = checkPrice();

                if (price <= 0)
                    return;

                mPresenter.donationPerson(price, password);

            }
        });
        dialog.show();


    }

    private float checkPrice() {
        String et_priceText = mHolder.getViewText(R.id.et_price);
        if (CommFunAndroid.isNullOrEmpty(et_priceText)) {
            showMsg("请输入金额");

            return 0;
        }

        if (!CommFunAndroid.isNumber(et_priceText)) {
            showMsg("金额输入有误");
            return 0;
        }

        float price = Float.parseFloat(et_priceText);

        if (price == 0) {
            showMsg("金额不能为零");
            return 0;
        }

        return price;
    }

//    @Override
//    public void scollImgCallback(boolean isCahce, Response response) {
//        if (response != null) {
//            int code = response.getCode();
//            String msg = response.getMessage();
//            if (code == 200) {
//                String result = response.getResultJson();
//                Log.i(TAG, "scollImgCallback: " + result);
//            } else {
//                showMsg(msg);
//            }
//        }
//    }

    @Override
    public void personScollImgCallback(boolean isCahce, Response response) {
        if (response != null) {
            int code = response.getCode();
            String msg = response.getMessage();
            if (code == 200) {
                String result = response.getResultJson();

                if (!CommFunAndroid.isNullOrEmpty(result)) {

                    BaseData.setCache("personScollImg", result);

                    List<Banner> list = response.getData(new TypeToken<List<Banner>>() {
                    }.getType());


                    bindBanner(list);

                }

                Log.i(TAG, "personScollImg: " + result);
                String ewmimg = response.getEwmimg();//二维码

                BaseData.setCache("ewmimg", ewmimg);

                Log.d(TAG, "personScollImgCallback: " + ConfigHelper.getServerUrl(Config.isDebug, false) + ewmimg);

                mHolder.setImageURL(R.id.img_qr_code, ConfigHelper.getServerUrl(Config.isDebug, false) + ewmimg, 200, 200);
                //img_qr_code.setUrl(ConfigHelper.getServerUrl(Config.isDebug, false) + ewmimg);

            } else {
                showMsg(msg);
            }
        }
    }

    @Override
    public void donationPersonCallback(boolean isCahce, Response response) {
        if (response != null) {
            int code = response.getCode();
            String msg = response.getMessage();
            if (code == 200) {
                finish();
            }
            showMsg(msg);

        }
    }

    // ----------------banner--------------------------------

    /**
     * banner
     */
    @ViewInject(R.id.slideshowView)
    private SlideShowViewAuto slideShowViewAuto;

    /**
     * banner 打开类型（url或指定界面和详情id）
     */
    HashMap<String, Banner> openInfo = new HashMap<String, Banner>();

//    /**
//     * 活动列表>1
//     */
//    List<Banner> mBanners = new ArrayList<Banner>();

    /**
     * banner image url集合>2
     */
    List<String> imageUrls = new ArrayList<String>();

    /**
     * banner 超链接<img_url,link_url>3
     */
    HashMap<String, String> imagesMap = new HashMap<String, String>();

    // --------------banner end----------------------------------


    /**
     * 绑定banner
     */
    private void bindBanner(List<Banner> banners) {

        if (banners != null && banners.size() > 0) {

            imageUrls = new ArrayList<String>();
            openInfo = new HashMap<String, Banner>();

            for (Banner banner : banners) {
                if (banner != null && !CommFunAndroid.isNullOrEmpty(banner.getUrl())) {
                    imageUrls.add(ConfigHelper.getServerUrl(Config.isDebug, false) + CommFunAndroid.toString(banner.getImages()));
                    openInfo.put(ConfigHelper.getServerUrl(Config.isDebug, false) + CommFunAndroid.toString(banner.getImages()), banner);
                }
            }
        }

        if (slideShowViewAuto != null && imageUrls != null && imageUrls.size() > 0) {

            // ScreenInfo screenInfo =
            // CommFunAndroid.GetInstance(getActivity()).getScreenInfo();
            //
            // if (screenInfo != null) {//
            // slideShowViewAuto.setVisibility(View.GONE);设置无效，所以默认高度为0，如果有值改变view高度让控件可见
            //
            // int width = screenInfo.getWidth();
            // int height = screenInfo.getHeight();
            //
            // LayoutParams slideshowViewParams =
            // slideShowViewAuto.getLayoutParams();
            //
            // slideshowViewParams.height = (int) (width * 0.28);
            // }

            slideShowViewAuto.setVisibility(View.VISIBLE);

            // slideshowView.setLoadingImageResId(userSexImgResId);
            // slideshowView.setLoadfailImageResid(userSexImgResId);


            slideShowViewAuto.setImageUrls(imageUrls);
            slideShowViewAuto.setImagesMap(imagesMap);
            slideShowViewAuto.setOpenInfos(openInfo);

        } else {
            slideShowViewAuto.setVisibility(View.GONE);
        }
    }

}
