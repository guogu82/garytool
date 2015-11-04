package com.gary.garytool;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gary.garytool.info.ForeignNew;
import com.gary.garytool.volley.VolleyManger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class PullToRefreshGradViewActivity extends Activity {

    private LinkedList<String> mListItems;
    private PullToRefreshGridView mPullToRefreshListView;
    private ArrayAdapter<String> mAdapter;

    private int mItemCount=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_grad_view);

        mPullToRefreshListView= (PullToRefreshGridView) findViewById(R.id.pull_to_refresh_grid);

        initDatas();
        mAdapter=new ArrayAdapter<String>(this,R.layout.pull_to_refresh_grid_view_item,R.id.id_grid_item_text,mListItems);
        mPullToRefreshListView.setAdapter(mAdapter);

        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label= DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_DATE| DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                loadData();

            }
        });
    }

    private void initDatas() {
        mListItems=new LinkedList<String>();
        for(int i=0;i<mItemCount;i++)
        {
            mListItems.add(i+"");
        }

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


}
