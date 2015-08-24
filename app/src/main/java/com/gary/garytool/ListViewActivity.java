package com.gary.garytool;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ListViewActivity extends ActionBarActivity {
    private ListView listView;

    private static final int BOTTOM_MENU_WITH_ACTIONBAR_LIKE_WECHAT6=0;
    private static final int BOTTOM_MENU_WITH_POPUPWINDOW=2;
    private static final int BOTTOM_MENU_WITH_VIEWPAGER = 3;
    private static final int BOTTOM_MENU_WITH_FRAGMENT = 4;
    private static final int WECHT = 5;
    private static final int WIFI = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("微信6.0主界面");
        data.add("背景渐变");
        data.add("底部菜单仿QQ空间");
        data.add("BottomMenuWithViewpager");
        data.add("底部菜单WithFragment");
        data.add("wechat");
        data.add("wifi");
        return data;
    }

    public final class ViewHolder {
        public Button item;
    }

    public class MyAdapter extends BaseAdapter {

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
            if(position==WIFI)
            {
                return 2;
            }
            else if (position == WECHT )
                return 1;
            else {
                return 0;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(position==WIFI)
            {
                convertView = mInflater.inflate(R.layout.activity_list_view_item_wifi, null);
                ImageView iv= (ImageView) convertView.findViewById(R.id.iv_selector);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewActivity.this, BottomMenuWithActionbarLikeWeChat6Activity.class);
                        startActivity(intent);
                    }
                });
                LinearLayout ll= (LinearLayout) convertView.findViewById(R.id.ll_content);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewActivity.this, ListViewDemo2Activity.class);
                        startActivity(intent);
                    }
                });
                ImageView iv_arrow= (ImageView) convertView.findViewById(R.id.iv_arrow);
                iv_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewActivity.this, ListViewDemo3Activity.class);
                        startActivity(intent);
                    }
                });
            } else  if (position == WECHT) {
                convertView = mInflater.inflate(R.layout.activity_list_view_item_webchat, null);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewActivity.this, BottomMenuWithActionbarLikeWeChat6Activity.class);
                        startActivity(intent);
                    }
                });
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(ListViewActivity.this, "ok long click", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            } else {
                ViewHolder holder = null;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.listview_item, null);
                    holder.item = (Button) convertView.findViewById(R.id.list_item);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.item.setText(getData().get(position));
                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        switch (position) {
                            case BOTTOM_MENU_WITH_ACTIONBAR_LIKE_WECHAT6:
                                intent = new Intent(ListViewActivity.this, BottomMenuWithActionbarLikeWeChat6Activity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(ListViewActivity.this, ListViewDemo3Activity.class);
                                startActivity(intent);
                                break;
                            case BOTTOM_MENU_WITH_POPUPWINDOW:
                                intent = new Intent(ListViewActivity.this, BottomMenuWithPopupWindowLikeQQ.class);
                                startActivity(intent);
                                break;
                            case BOTTOM_MENU_WITH_VIEWPAGER:
                                intent = new Intent(ListViewActivity.this, BottomMenuWithViewpager.class);
                                startActivity(intent);
                                break;
                            case BOTTOM_MENU_WITH_FRAGMENT:
                                intent = new Intent(ListViewActivity.this, BottomMenuWithFragment.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }


                    }
                });
            }
            return convertView;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
