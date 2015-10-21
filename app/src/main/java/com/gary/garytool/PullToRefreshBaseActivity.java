package com.gary.garytool;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.gary.garytool.info.ForeignNew;
import com.gary.garytool.util.LogUtil;
import com.gary.garytool.volley.GsonRequest;
import com.gary.garytool.volley.VolleyManger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.internal.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


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
                //http://apistore.baidu.com/apiworks/servicedetail/823.html  API集市 > 服务类API > 国际新闻数据
                String url = "http://apis.baidu.com/txapi/world/world?num=1&page="+mItemCount;
                //response datas
//                {
//                    "0": {
//                    "time": "2015-10-16 13:34",
//                            "title": "叙利亚政府军攻打西部要塞 击毙反政府武装头目",
//                            "description": "叙利亚政府军攻打西部要塞 击毙反政府武装头目...",
//                            "picUrl": "http://photocdn.sohu.com/20151016/Img423415868_ss.jpeg",
//                            "url": "http://news.sohu.com/20151016/n423416238.shtml"
//                },
//                    "1": {
//                    "time": "2015-10-16 13:30",
//                            "title": "驻日使馆：赴日中国公民谨防某地歌舞伎町敲诈",
//                            "description": "驻日使馆：赴日中国公民谨防某地歌舞伎町敲诈...",
//                            "picUrl": "http://photocdn.sohu.com/20151016/Img423415047_ss.jpg",
//                            "url": "http://news.sohu.com/20151016/n423415867.shtml"
//                },
//               "code": 200,
//               "msg": "ok"
//                }
                JsonObjectRequest request = new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        try {
                            JSONObject first = result.getJSONObject("0");
                            ForeignNew foreignNew=new ForeignNew();
                            foreignNew.setDescription(first.getString("description"));

                            LogUtil.d(TAG, foreignNew.getDescription());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtil.d(TAG, volleyError.toString());
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
