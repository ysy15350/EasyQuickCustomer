package base.log;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import base.data.RequestLogHelper;
import base.data.entity.RequestLog;
import base.mvp.BasePresenter;

public class LogListPresenter extends BasePresenter<LogListViewInterface> {

    public LogListPresenter(Context context) {
        super(context);

    }

    RequestLogHelper requestLogHelper = RequestLogHelper.getInstance();


    public void getMyCollection() {

        //noinspection Since15
        try {
            List<RequestLog> list = requestLogHelper.getList();

            List<RequestLog> list_1 = new ArrayList<RequestLog>();
            for (int i = list.size(); i > 0; i--) {
                list_1.add(list.get(i - 1));
            }
            mView.bindData(list_1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
