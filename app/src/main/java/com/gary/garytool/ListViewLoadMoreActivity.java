package com.gary.garytool;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gary.garytool.info.ApkEntity;
import com.gary.garytool.view.LoadListView;

import java.util.ArrayList;



public class ListViewLoadMoreActivity extends ActionBarActivity implements LoadListView.ILoadListener {

    ArrayList<ApkEntity> m_ApkList=new ArrayList<>();

    Myadapter m_adapter;
    LoadListView m_LoadListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_load_more);
        getData();
        showListView(m_ApkList);
    }

    private void getData() {
        ApkEntity entity;
        for(int i=0;i<10;i++)
        {
             entity =new ApkEntity();
            entity.setName("程序名字"+i);
            entity.setInfo("50w用户");
            entity.setDes("这是一个神奇的应用！");
            m_ApkList.add(entity);
        }
    }

    private void showListView(ArrayList<ApkEntity> m_apkList) {
        if(m_adapter==null)
        {
            m_LoadListView= (LoadListView) findViewById(R.id.lv);
            m_LoadListView.setInterface(this);
            m_adapter=new Myadapter(this,m_apkList);
            m_LoadListView.setAdapter(m_adapter);
        }
        else
        {
            m_adapter.onDateChange(m_apkList);
        }
    }

    private void getLoadData()
    {
        ApkEntity entity;
        for (int i=0;i<2;i++)
        {
            entity =new ApkEntity();
            entity.setName("程序名字"+m_ApkList.size());
            entity.setInfo("50w用户");
            entity.setDes("这是一个神奇的应用！");
            m_ApkList.add(entity);
        }
    }
    @Override
    public void onLoad() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              getLoadData();
                showListView(m_ApkList);
                m_LoadListView.loadComplete();
            }
        },2000);
    }


    class Myadapter extends BaseAdapter{

        ArrayList<ApkEntity> m_ApkList;
        LayoutInflater m_inflater;

        public Myadapter(Context context,ArrayList<ApkEntity> apk_list)
        {
            m_ApkList=apk_list;
            m_inflater=LayoutInflater.from(context);
        }

        public void onDateChange(ArrayList<ApkEntity> apk_list)
        {
            m_ApkList=apk_list;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return m_ApkList.size();
        }

        @Override
        public Object getItem(int position) {
            return m_ApkList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ApkEntity entity=m_ApkList.get(position);
            ViewHolder holder;
            if(convertView==null)
            {
                holder=new ViewHolder();
                convertView=m_inflater.inflate(R.layout.listview_item_load_more,null);
                holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_des= (TextView) convertView.findViewById(R.id.tv_des);
                holder.tv_info= (TextView) convertView.findViewById(R.id.tv_info);
                convertView.setTag(holder);
            }
            else
            {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tv_name.setText(entity.getName());
            holder.tv_des.setText(entity.getDes());
            holder.tv_info.setText(entity.getInfo());
            return convertView;
        }

        class  ViewHolder
        {
            TextView tv_name;
            TextView tv_des;
            TextView tv_info;
        }
    }

}

