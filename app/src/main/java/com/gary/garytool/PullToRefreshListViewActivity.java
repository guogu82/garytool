package com.gary.garytool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gary.garytool.view.TextViewM;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class PullToRefreshListViewActivity extends Activity {
    ListView mListView;
    ArrayAdapter mAdapter;
    List<String> mDatas;
    LayoutInflater mInfrater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_list_view);
        //http://blog.csdn.net/lmj623565791/article/details/38238749 --鸿洋PullToRefresh demo

        mDatas=new ArrayList<>();
        mDatas.add("下拉加载");
        mDatas.add("上拉下拉都加载");
        mListView= (ListView) findViewById(R.id.lv);
        mInfrater=LayoutInflater.from(this);
        mAdapter=new ArrayAdapter(this,-1,mDatas){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View item=mInfrater.inflate(R.layout.listview_item,null);
                Button text= (Button) item.findViewById(R.id.list_item);
                text.setText(mDatas.get(position).toString());
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(position);
                    }
                });
                return item;
            }


        };
        mListView.setAdapter(mAdapter);
    }
    //仅仅实现了下拉刷新数据
    private static final int BASE=0;
    //实现了上拉和下拉刷新数据
    private static final int BOTH=1;
    private void onItemClick(int position) {
        Intent intent;
        switch (position)
        {
            case BASE:
                intent = new Intent(PullToRefreshListViewActivity.this, PullToRefreshBaseActivity.class);
                startActivity(intent);
                break;
            case BOTH:
                intent =new Intent(PullToRefreshListViewActivity.this,PullToRefreshBothActivity.class);
                startActivity(intent);
                break;
        }
    }


}
