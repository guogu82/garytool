package com.gary.garytool;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gary.garytool.util.LogUtil;
import com.gary.garytool.volley.GsonRequest;
import com.gary.garytool.volley.VolleyManger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.LinkedList;


public class PullToRefreshBaseActivity extends Activity {

    private static final String TAG="PullToRefreshBaseActivity";

    private LinkedList<String> mListItems;
    private PullToRefreshListView mPullRefreshListView;
    private ArrayAdapter<String> mAdapter;

    private int mItemCount=9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_base);

        mPullRefreshListView= (PullToRefreshListView) findViewById(R.id.lv);
        initDatas();
        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mListItems);
        mPullRefreshListView.setAdapter(mAdapter);
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label= DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_DATE| DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                new GetDataTask().execute();
            }
        });
    }

    private void initDatas() {
        mListItems=new LinkedList<String>();
        for(int i=0;i<mItemCount;i++)
        {
            mListItems.add("gary"+i);
        }

    }

    private class GetDataTask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {
            try
            {
                Thread.sleep(2000);
                String httpUrl = "http://apis.baidu.com/txapi/world/world";
                String httpArg = "num=5&page="+mItemCount;
                String jsonResult = VolleyManger.request(httpUrl, httpArg);
              LogUtil.d(TAG,jsonResult);
            }
            catch (InterruptedException e) {
            }
            return ""+(mItemCount++);
        }

        @Override
        protected void onPostExecute(String result) {
            mListItems.add(result);
            mAdapter.notifyDataSetChanged();
            mPullRefreshListView.onRefreshComplete();
        }
    }

}
