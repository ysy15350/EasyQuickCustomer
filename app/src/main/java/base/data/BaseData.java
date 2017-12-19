package base.data;

import android.content.Context;
import android.util.Log;

import java.io.ObjectStreamException;

import base.data.entity.BaseInfo;
import base.data.entity.UserInfo;
import common.cache.ACache;

public class BaseData {


    private static ACache aCache;


    /**
     * 是否登录
     */
    private boolean isLogin = false;

    String token;

    /**
     * 是否有网络
     */
    public static boolean isNetwork;

    private final static String TAG = "BaseData";

    private BaseData() {
    }

    public static BaseData getInstance() {
        return BaseDataHolder.sInstance;
    }

    public static BaseData getInstance(Context context) {
        init(context);
        return BaseDataHolder.sInstance;
    }

    private static class BaseDataHolder {
        private static final BaseData sInstance = new BaseData();
    }

    // 杜绝单例对象在反序列化时重新生成对象
    private Object readResolve() throws ObjectStreamException {
        return BaseDataHolder.sInstance;
    }

    private static void init(Context context) {
        if (aCache == null && context != null) {
            aCache = ACache.get(context);
        }
    }

    /**
     * 获取token
     *
     * @return
     */
    public String getToken() {

        BaseInfo baseInfo = BaseInfoHelper.getInstance().getBaseInfo();
        if (baseInfo != null) {
            String token = baseInfo.getToken();
            if (token == null) {
                token = "";
            }
            return token;
        }
        return "";

    }


    /**
     * 设置token
     *
     * @param token
     */
    public static void setToken(String token) {
        BaseInfoHelper.getInstance().setToken(token);
    }


    /**
     * 获取当前用户登录信息
     *
     * @return
     */
    public UserInfo getUserInfo() {

        try {
            int uid = getUid();
            UserInfo userInfo = UserHelper.getInstance().getUserInfoByUid(uid);
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 缓存已登录用户信息
     *
     * @param userInfo
     */
    public void setUserInfo(UserInfo userInfo) {

        try {
            //UserHelper.getInstance().delete();
            if (userInfo != null)
                userInfo.setUid(getUid());
            UserHelper.getInstance().saveOrUpdate(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置UID
     *
     * @param uid
     */
    public void setUid(int uid) {
        BaseInfoHelper.getInstance().setUid(uid);
    }

    /**
     * 获取Uid
     *
     * @return
     */
    public static int getUid() {
        BaseInfo baseInfo = BaseInfoHelper.getInstance().getBaseInfo();
        if (baseInfo != null) {
            int uid = baseInfo.getUid();

            Log.d(TAG, "getUid() called ,uid=" + uid);

            return uid;
        }
        return 0;
    }


    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public static void setCache(String key, String value) {
        int uid = getUid();
        if (aCache != null && value != null) {
            Log.d(TAG, "setCache() called with: key = [" + key + uid + "], value = [" + value + "]");
            aCache.put(key + uid, value);
        }
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public static void setCache(String key, String value, int time) {
        if (aCache != null && value != null) {
            int uid = getUid();
            Log.d(TAG, "setCache() called with: key = [" + key + uid + "], value = [" + value + "], time = [" + time + "]");
            aCache.put(key + uid, value, time);
        }
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public static String getCache(String key) {
        if (aCache != null) {
            int uid = getUid();
            String cacheStr = aCache.getAsString(key + uid);
            Log.d(TAG, "getCache() called with: key = [" + key + "],value=" + cacheStr);
            return cacheStr;
        }
        return "";
    }

}