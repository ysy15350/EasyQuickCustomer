package api;


import api.base.ApiCallBack;

public interface PublicApi {

    /**
     * 获取token
     */
    public void getToken(String className, ApiCallBack callBack);

    /**
     * 注册
     *
     * @param mobile
     * @param password
     * @param verification_code
     * @param callBack
     */
    public void register(String mobile, String password,
                         String verification_code, ApiCallBack callBack);

    /**
     * 会员登录
     *
     * @param account
     * @param password
     * @param callBack
     */
    public void login(String account, String password, ApiCallBack callBack);

    /**
     * 注销
     * @param callBack
     */
    public void logout_identification(ApiCallBack callBack);



    /**
     * 检查版本
     *
     * @param version_code 版本号
     * @param callBack
     */
    public void checkVersion(int version_code, ApiCallBack callBack);

    public void return_alipay(int resultStatus, String result, ApiCallBack callBack);

    /**
     * 启动图
     *
     * @param type
     * @param callBack
     */
    public void adImgIndex(int type, ApiCallBack callBack);

    /**
     * 轮播图
     *
     * @param type
     * @param callBack
     */
    public void scollImg(int type, ApiCallBack callBack);

    /**
     * 捐款页面轮播和二维码
     *
     * @param callBack
     */
    public void personScollImg(ApiCallBack callBack);


}
