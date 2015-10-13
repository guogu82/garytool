package com.gary.garytool;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gary.garytool.view.TextViewM;

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
        mDatas=new ArrayList<>();
        mDatas.add("最轻量入门");
        mListView= (ListView) findViewById(R.id.lv);
        mInfrater=LayoutInflater.from(this);
        mAdapter=new ArrayAdapter(this,-1,mDatas){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View item=mInfrater.inflate(R.layout.listview_item,null);
                TextView text= (TextView) item.findViewById(R.id.list_item);
                text.setText(mDatas.get(position).toString());

                return item;
            }
        };
        mListView.setAdapter(mAdapter);
    }


}
