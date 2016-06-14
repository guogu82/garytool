package com.gary.garytool.view.pulltorefreshused;

import java.util.ArrayList;
import java.util.List;

import com.gary.garytool.R;
import com.gary.garytool.view.pulltorefresh.PullToRefreshBase;
import com.gary.garytool.view.pulltorefresh.PullToRefreshBase.Mode;
import com.gary.garytool.view.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.gary.garytool.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.gary.garytool.view.pulltorefresh.PullToRefreshListView;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;

public class PullToRefreshActivity extends ActionBarActivity {

    PullToRefreshListView mPullToRefreshListView;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);

        mPullToRefreshListView= (PullToRefreshListView) findViewById(R.id.lv_pull_to_refresh);
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                //最后一次刷新的时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新时间 " + label);
                //设置刷新图标 下拉的时候显示的内容
                refreshView.getLoadingLayoutProxy().setLoadingDrawable(getResources().getDrawable(R.drawable.pull_to_refresh_arrow));
                //下拉完成后，还没有刷新时 显示的内容
                refreshView.getLoadingLayoutProxy().setReleaseLabel("下拉到位，放手刷新");
                //松开手，正在刷新时，显示的内容
                refreshView.getLoadingLayoutProxy().setRefreshingLabel("已经松开手，刷新中");

                Toast.makeText(PullToRefreshActivity.this, "刷新了", Toast.LENGTH_SHORT).show();

                new GetDataTask().execute();
            }
        });

        //listview 滑到 最后一项
        mPullToRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Toast.makeText(PullToRefreshActivity.this, "listview到底了", Toast.LENGTH_SHORT).show();
            }
        });

        mPullToRefreshListView.setMode(Mode.PULL_FROM_START);

        /*
        //向下拉
        pullToRefreshListView.setMode( Mode.PULL_FROM_START );
        //向上拉
        pullToRefreshListView.setMode( Mode.PULL_FROM_END );
        //同时使用 下拉  和 上拉
        pullToRefreshListView.setMode( Mode.BOTH );
        //不启用刷新功能
        pullToRefreshListView.setMode( Mode.DISABLED );
        */

        mListView=mPullToRefreshListView.getRefreshableView();
        mListView.setAdapter(new ArrayAdapter<String>(PullToRefreshActivity.this,android.R.layout.simple_list_item_1,getData()));

        /**
         * 程序进来就执行刷新数据，自动执行刷新
         */
        mPullToRefreshListView.setRefreshing();
    }

    /**
     * pullToRefreshListView.onRefreshComplete() 这句仿真异步里面写合适
     */
    private class GetDataTask extends  AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mPullToRefreshListView.onRefreshComplete();
        }
    }

    List<String> getData()
    {
        List<String> list=new ArrayList<>();
        for(int i=0;i<100;i++)
        {
            list.add("item="+i);
        }
        return list;
    }
}
