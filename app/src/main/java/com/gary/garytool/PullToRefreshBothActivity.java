package com.gary.garytool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gary.garytool.info.ForeignNew;
import com.gary.garytool.util.LogUtil;
import com.gary.garytool.volley.VolleyManger;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;


public class PullToRefreshBothActivity extends Activity {

    private LinkedList<String> mListItems;
    private PullToRefreshListView mPullToRefreshListView;
    private ArrayAdapter<String> mAdapter;

    private int mItemCount=9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_both);

        mPullToRefreshListView= (PullToRefreshListView) findViewById(R.id.lv);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        initDatas();

        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mListItems);
        mPullToRefreshListView.setAdapter(mAdapter);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                ILoadingLayout label=mPullToRefreshListView.getLoadingLayoutProxy();
                label.setPullLabel("刚下拉时显示的文字");
                label.setRefreshingLabel("正在刷新时显示的文字");
                label.setReleaseLabel("拉动一定距离时候显示的文字");
                label.setLastUpdatedLabel("上次刷新时间显示位置");
                //initIndicator();上拉下拉单独改变
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }
        });
    }

    //mPullRefreshListView.getLoadingLayoutProxy(true, false);接收两个参数，为true,false返回设置下拉的ILoadingLayout；为false,true返回设置上拉的。
    private void initIndicator()
    {
        ILoadingLayout startLabels = mPullToRefreshListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("你可劲拉，拉...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("好嘞，正在刷新...");// 刷新时
        startLabels.setReleaseLabel("你敢放，我就敢刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("你可劲拉，拉2...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("好嘞，正在刷新2...");// 刷新时
        endLabels.setReleaseLabel("你敢放，我就敢刷新2...");// 下来达到一定距离时，显示的提示
    }

    private void loadData() {
        String url = "http://apis.baidu.com/txapi/world/world?num=2&page="+mItemCount++;

        JsonObjectRequest request = new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject httpResult) {
                try {
                    JSONObject first = httpResult.getJSONObject("0");
                    ForeignNew foreignNew=new ForeignNew();
                    foreignNew.setDescription(first.getString("description"));
                    foreignNew.setTitle(first.getString("title"));
                    updateUi(foreignNew);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("apikey", "3fa06fc0f9c91551e068779fb3872e10");
                return header;
            }
        };

        VolleyManger.getVolleyRequestQueue().add(request);
    }
    private void updateUi(ForeignNew foreignNew) {
        mListItems.add(foreignNew.getTitle());
        mAdapter.notifyDataSetChanged();
        mPullToRefreshListView.onRefreshComplete();
    }

    private void initDatas() {
        mListItems=new LinkedList<>();
        for(int i=0;i<mItemCount;i++)
        {
            mListItems.add(""+i);
        }


    }


}
