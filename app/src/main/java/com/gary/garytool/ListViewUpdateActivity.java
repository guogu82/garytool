package com.gary.garytool;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gary.garytool.info.ApkEntity;
import com.gary.garytool.view.ReFlashListView;

public class ListViewUpdateActivity extends Activity implements ReFlashListView.IReflashListerner {

    ArrayList<ApkEntity> apk_list;
    Myadapter adapter;
    ReFlashListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_update);
        setData();
        showList(apk_list);
    }

    private void showList(ArrayList<ApkEntity> apk_list) {

        if(adapter==null)
        {
            listView= (ReFlashListView) findViewById(R.id.lv);
            listView.setInterface(this);
            adapter=new Myadapter(this,apk_list);
            listView.setAdapter(adapter);
        }else
        {
            adapter.onDateChange(apk_list);
        }
    }

    private void setData() {
        apk_list=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            ApkEntity entity=new ApkEntity();
            entity.setName("默认数据"+i);
            entity.setDes("这是一个神奇的应用");
            entity.setInfo("50w用户");
            apk_list.add(entity);
        }

    }


    @Override
    public void onReflash() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setReflashData();
                showList(apk_list);
                listView.reflashComplate();
            }
        },2000);

    }

    private void setReflashData() {
        for(int i=0;i<2;i++)
        {
            ApkEntity entity=new ApkEntity();
            entity.setName("刷新加入"+apk_list.size());
            entity.setDes("这个是刷新的神奇应用");
            entity.setInfo("80w用户");
            apk_list.add(0,entity);
        }
    }


    class Myadapter extends BaseAdapter {

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

