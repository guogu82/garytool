package com.gary.garytool;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class ListViewActivity extends ActionBarActivity {
    private ListView mListView;

    private static final int BOTTOM_MENU_WITH_ACTIONBAR_LIKE_WECHAT6=0;
    private static final int BOTTOM_MENU_WITH_POPUPWINDOW=1;
    private static final int BOTTOM_MENU_WITH_VIEWPAGER = 2;
    private static final int BOTTOM_MENU_WITH_FRAGMENT = 3;
    private static final int LIST_VIEW_ITEM_UI = 4;
    private static final int LIST_VIEW_MESSAGE_READED = 5;
    private static final int LIST_VIEW_UPDATE = 6;
    private static final int LIST_VIEW_LOAD_MORE = 7;
    private static final int LIST_VIEW_ADAPTER_BASE = 8;
    private static final int VIEW_PAGER_GUIDE = 9;
    private static final int VOLLEY = 10;
    private static final int POPUP_WINDOW = 11;
    private static final int VOLLEY_TABLE_LAYOUT = 12;
    private static final int PULL_TO_REFRESH = 13;
    private static final int WECHAT_PICTURE_CHOSE = 14;
    private static final int X_UTILS = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mListView = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this);
        mListView.setAdapter(adapter);
    }

    /**
     * 功能列表展示
     * @return
     */
    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("微信6.0主界面");
        data.add("底部菜单仿QQ空间");
        data.add("底部菜单WithViewpager");
        data.add("底部菜单WithFragment");
        data.add("ListViewItem界面展示");
        data.add("ListView邮件已读");
        data.add("ListView下拉刷新");
        data.add("ListView上拉加载更多");
        data.add("万能的adapter适配器");
        data.add("ViewPager引导页");/*http://www.cnblogs.com/yc-755909659/p/4283294.html*/
        data.add("volley");
        data.add("地区学校二级联动-PopupWindow");//http://www.cnblogs.com/tonycheng93/p/4823860.html
        data.add("股票显示-TableLayout");
        data.add("PullToRefresh");
        data.add("微信图片选择器");
        data.add("XUtils");

        /*-----done------*/
        //actionbar
        //fragment
        //activity
        //popupWindow 省学校二级联动

        //listview
        //adapter
        //viewpager

        //textview
        //edittext
        //imageview
        //button


        //dialog
        //notify
        //toast
        //json操作
        //网络访问
        //OOM
        //xUtils(Afinal) VS GreenDAO https://github.com/wyouflf/xUtils
        //Gson
        //PullToRefresh
        //volley VS universal-image-loader VS android-async-http  VS DiskLruCache
        //Volley是将AsyncHttpClient和Universal-Image-Loader的优点集成于一身的一个框架


        /*----will do---*/
        //数据库操作
        //webView
        //Loader
        //login 第三方登录
        //socket tcp通信 --文件上传下载--http://blog.csdn.net/lmj623565791/article/details/23781773
        //               --文件上传下载--http://blog.csdn.net/lmj623565791/article/details/26994463
        //地图应用
        //DiskLruCache
        //生成验证码--自定义view --http://blog.csdn.net/lmj623565791/article/details/24252901
        //RecycleView + CardView 控件
        //ActionBar to ToolBar
        /*----框架---*/
        //OkHttp 可以与volley配合，做volley的传输层
        //EventBus
        //ButterKnife
        //PhotoView  https://github.com/chrisbanes/PhotoView/tree/master/library

        return data;
    }

    public final class ViewHolder {
        public Button item;
    }

    private class MyAdapter extends BaseAdapter {

        private void onItemClick(int position) {
            Intent intent;
            switch (position) {
                case BOTTOM_MENU_WITH_ACTIONBAR_LIKE_WECHAT6:
                    intent = new Intent(ListViewActivity.this, BottomMenuWithActionbarLikeWeChat6Activity.class);
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
                case LIST_VIEW_ITEM_UI:
                    intent = new Intent(ListViewActivity.this, ListViewItemUIActivity.class);
                    startActivity(intent);
                    break;
                case LIST_VIEW_MESSAGE_READED:
                    intent =new Intent(ListViewActivity.this, ListViewCustomStateActivity.class);
                    startActivity(intent);
                    break;
                case LIST_VIEW_UPDATE:
                    intent =new Intent(ListViewActivity.this, ListViewUpdateActivity.class);
                    startActivity(intent);
                    break;
                case LIST_VIEW_LOAD_MORE:
                    intent =new Intent(ListViewActivity.this, ListViewLoadMoreActivity.class);
                    startActivity(intent);
                    break;
                case LIST_VIEW_ADAPTER_BASE:
                    intent =new Intent(ListViewActivity.this, ListViewAdapterMActivity.class);
                    startActivity(intent);
                    break;
                case VIEW_PAGER_GUIDE:
                    intent =new Intent(ListViewActivity.this, ViewPagerGuideActivity.class);
                    startActivity(intent);
                    break;
                case VOLLEY:
                    intent =new Intent(ListViewActivity.this, VolleyActivity.class);
                    startActivity(intent);
                    break;
                case POPUP_WINDOW:
                    intent =new Intent(ListViewActivity.this, PopupWindowActivity.class);
                    startActivity(intent);
                    break;
                case VOLLEY_TABLE_LAYOUT:
                    intent =new Intent(ListViewActivity.this, VolleyTableLayoutActivity.class);
                    startActivity(intent);
                    break;
                case PULL_TO_REFRESH:
                    intent =new Intent(ListViewActivity.this, PullToRefreshActivity.class);
                    startActivity(intent);
                    break;
                case WECHAT_PICTURE_CHOSE:
                    intent =new Intent(ListViewActivity.this, PictureChoseActivity.class);
                    startActivity(intent);
                    break;
                case X_UTILS:
                    intent =new Intent(ListViewActivity.this, XUtilsActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }

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

                holder.item.setText(getData().get(position));
                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(position);
                    }
                });
            return convertView;
        }



    }


}
