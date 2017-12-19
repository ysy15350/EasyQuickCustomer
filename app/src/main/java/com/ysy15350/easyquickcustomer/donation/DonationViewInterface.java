package com.ysy15350.easyquickcustomer.donation;

import api.base.model.Response;

public interface DonationViewInterface {

    /**
     * 轮播图
     *
     * @param isCahce
     * @param response
     */
    //public void scollImgCallback(boolean isCahce, Response response);

    /**
     * 捐款页面轮播和二维码
     * @param isCahce
     * @param response
     */
    public void personScollImgCallback(boolean isCahce, Response response);//

    public void donationPersonCallback(boolean isCahce, Response response);

}
