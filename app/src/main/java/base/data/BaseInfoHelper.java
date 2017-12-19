package base.data;

import java.util.Collection;
import java.util.List;

import base.data.entity.BaseInfo;
import common.CommFunAndroid;
import common.database.DbUtilsXutils3;

/**
 * Created by yangshiyou on 2017/9/12.
 */

public class BaseInfoHelper extends DbUtilsXutils3<BaseInfo> {

    private static BaseInfoHelper baseInfoHelper;

    private BaseInfoHelper() {

    }

    public static BaseInfoHelper getInstance() {
        if (baseInfoHelper == null) {
            baseInfoHelper = new BaseInfoHelper();
            init();
            //baseInfoDao.checkVersion();
        }
        return baseInfoHelper;
    }


    @Override
    public int saveOrUpdate(BaseInfo baseInfo) throws Exception {
        db.saveOrUpdate(baseInfo);
        return 1;
    }

    @Override
    public int insert(BaseInfo baseInfo) throws Exception {
        db.saveOrUpdate(baseInfo);
        return 1;
    }

    @Override
    public int update(BaseInfo baseInfo) throws Exception {
        db.saveOrUpdate(baseInfo);
        return 1;
    }

    @Override
    public int delete() throws Exception {
        db.delete(BaseInfo.class);
        return 1;
    }

    @Override
    public int delete(int id) throws Exception {
        return 0;
    }

    @Override
    public int delete(Collection<BaseInfo> list) throws Exception {

        return 0;
    }

    @Override
    public BaseInfo getEntity(int id) throws Exception {
        return null;
    }

    @Override
    public List<BaseInfo> getList() throws Exception {
        return db.findAll(BaseInfo.class);
    }

    public void setToken(String token) {
        try {
            if (!CommFunAndroid.isNullOrEmpty(token)) {

                BaseInfo baseInfo = null;

                List<BaseInfo> list = getList();
                if (list != null) {
                    baseInfo = list.get(list.size() - 1);
                    //delete();
                }

                if (baseInfo == null)
                    baseInfo = new BaseInfo();
                baseInfo.setToken(token);
                baseInfo.setTokenCreateTime(System.currentTimeMillis());
                insert(baseInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUid(int uid) {
        try {
            BaseInfo baseInfo = null;

            List<BaseInfo> list = getList();
            if (list != null) {
                baseInfo = list.get(list.size() - 1);
                //delete();
            }

            if (baseInfo == null)
                baseInfo = new BaseInfo();
            baseInfo.setUid(uid);
            insert(baseInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BaseInfo getBaseInfo() {
        try {
            BaseInfo baseInfo = null;

            List<BaseInfo> list = getList();
            if (list != null) {
                baseInfo = list.get(list.size() - 1);
            }
            return baseInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
