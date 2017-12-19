package base.log;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.ysy15350.easyquickcustomer.R;
import com.ysy15350.easyquickcustomer.adapters.ListViewAdpater_Log;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import base.data.entity.RequestLog;
import base.mvp.MVPBaseListViewActivity;

/**
 * Created by yangshiyou on 2017/8/31.
 */

@ContentView(R.layout.activity_list)
public class LogListActivity extends MVPBaseListViewActivity<LogListViewInterface, LogListPresenter>
        implements LogListViewInterface {

    @Override
    protected LogListPresenter createPresenter() {

        return new LogListPresenter(LogListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("接口访问日志");
        //xListView.setDividerHeight(CommFunAndroid.dip2px(this, 0)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)
        xListView.setPullLoadEnable(false);

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {

        mPresenter.getMyCollection();
    }

    ListViewAdpater_Log mAdapter;
    List<RequestLog> mList = new ArrayList<RequestLog>();

    @Override
    public void bindData(List<RequestLog> list) {

        //showMsg("bindData" + list.size());

        if (page == 1) {
            mList = new ArrayList<RequestLog>();
        } else {
            if (list == null || list.isEmpty())
                showMsg("没有更多了");
        }

        mList.addAll(list);
        mAdapter = new ListViewAdpater_Log(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        xListView.notify();

        if (list != null || !list.isEmpty()) {
            setFormHead("接口访问日志" + list.size());
            page++;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        //showMsg(position + "," + id);
        RequestLog requestLog = mList.get(position - 1);

        Intent intent = new Intent(this, LogDetailActivity.class);
        intent.putExtra("log", requestLog);
        startActivity(intent);
    }
}
