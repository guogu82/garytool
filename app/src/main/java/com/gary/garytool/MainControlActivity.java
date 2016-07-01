package com.gary.garytool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.gary.garytool.function.shape.StyleActivity;
import com.gary.garytool.function.spinner.SpinnerActivity;
import com.gary.garytool.function.toast.ToastActivity;
import com.gary.garytool.util.Util;
import java.util.ArrayList;
import java.util.List;


/**
 *  Created by gary on 2016/06/29.
 * @author gary guo
 * 本类用于记录安卓系统控件的基础用法
 */
public class MainControlActivity extends Activity {
    private ListView lvList;
    private  List<String> mData=new ArrayList<String>(){
        {
            add("Style");//shape selector anim theme
            add("TextView");
            add("EditText");
            add("Button");
            add("ImageView");
            add("Spinner");
            add("进度条");
            add("对话框");
            add("Toast");
            add("Notification");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lvList = (ListView) findViewById(R.id.lv_list);
        MyAdapter adapter = new MyAdapter(this);
        lvList.setAdapter(adapter);
    }



    private class MyAdapter extends BaseAdapter {

        private void onItemClick(int position) {
            String value= (String) getItem(position);
            if(value.equals("Spinner"))
                Util.startActivity(MainControlActivity.this, SpinnerActivity.class);
            else if(value.equals("Toast"))
                Util.startActivity(MainControlActivity.this, ToastActivity.class);
            else if(value.equals("Style"))
                Util.startActivity(MainControlActivity.this, StyleActivity.class);
        }

        private LayoutInflater mInflater;
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

                ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.listview_item, null);
                    holder.item = (Button) convertView.findViewById(R.id.list_item);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.item.setText(mData.get(position));
                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(position);
                    }
                });
            return convertView;
        }

        @Override
        public int getCount() {
            return mData.size();
        }
        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }



        public final class ViewHolder {
            public Button item;
        }


    }


}
