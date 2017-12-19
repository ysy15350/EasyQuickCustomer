package base.log;


import java.util.List;

import base.data.entity.RequestLog;

public interface LogListViewInterface {
    /**
     * 绑定我的收藏
     *
     * @param response
     */
    public void bindData(List<RequestLog> list);
}
