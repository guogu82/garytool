package com.gary.garytool;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ListViewItemUIActivity extends ActionBarActivity {
    private static final int LISTVIEW_ITEM_WECHAT = 0;
    private static final int LISTVIEW_ITEM_WIFI = 1;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_item_ui);

        mListView = (ListView) findViewById(R.id.lv);
        MyAdapter adapter = new MyAdapter(this);
        mListView.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("微信列表Item");
        data.add("Wifi");
        return data;
    }

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return getData().size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        //每个convert view都会调用此方法，获得当前所需要的view样式
        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return getData().size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(position==LISTVIEW_ITEM_WECHAT)
            {
                convertView = mInflater.inflate(R.layout.activity_list_view_item_webchat, null);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewItemUIActivity.this, BottomMenuWithActionbarLikeWeChat6Activity.class);
                        startActivity(intent);
                    }
                });
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(ListViewItemUIActivity.this, "ok long click", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            } else if (position == LISTVIEW_ITEM_WIFI) {
                convertView = mInflater.inflate(R.layout.activity_list_view_item_wifi, null);
                ImageView iv= (ImageView) convertView.findViewById(R.id.iv_selector);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewItemUIActivity.this, BottomMenuWithActionbarLikeWeChat6Activity.class);
                        startActivity(intent);
                    }
                });
                LinearLayout ll= (LinearLayout) convertView.findViewById(R.id.ll_content);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewItemUIActivity.this, ListViewItemUIActivity.class);
                        startActivity(intent);
                    }
                });
                ImageView iv_arrow= (ImageView) convertView.findViewById(R.id.iv_arrow);
                iv_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewItemUIActivity.this, ListViewCustomStateActivity.class);
                        startActivity(intent);
                    }
                });

            }
            return convertView;
        }

    }
}
