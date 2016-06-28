package com.gary.garytool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.gary.garytool.business.datepicker.DatePickerDialogActivity;
import com.gary.garytool.function.carbrand.CarBrandIndexActivity;
import com.gary.garytool.view.listview.ListViewAdapterMActivity;
import com.gary.garytool.view.listview.ListViewCustomStateActivity;
import com.gary.garytool.view.listview.ListViewItemUIActivity;
import com.gary.garytool.view.listview.ListViewLoadMoreActivity;
import com.gary.garytool.view.listview.ListViewUpdateActivity;
import com.gary.garytool.view.viewpager.BottomMenuWithActionbarLikeWeChat6Activity;
import com.gary.garytool.view.viewpager.BottomMenuWithFragment;
import com.gary.garytool.view.viewpager.BottomMenuWithPopupWindowLikeQQ;
import com.gary.garytool.view.viewpager.BottomMenuWithViewpager;
import com.gary.garytool.view.PictureChoseActivity;
import com.gary.garytool.view.PopupWindowActivity;
import com.gary.garytool.view.pulltorefreshused.PullToRefreshActivity;
import com.gary.garytool.view.viewpager.ViewPagerGuideActivity;
import com.gary.garytool.business.volley.VolleyActivity;
import com.gary.garytool.business.volley.VolleyTableLayoutActivity;
import com.gary.garytool.business.xutil.XUtilsActivity;
import com.gary.garytool.view.viewpager.ViewPagerIndicatorActivity;

import java.util.ArrayList;
import java.util.List;


public class MainFunctionActivity extends Activity {
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
    private static final int PAGER_INDICATOR = 16;
    private static final int DATE_PICKER = 17;
    private static final int CAR_BRAND = 18;

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
        data.add("跟随型ViewPager指示器");
        data.add("日期控件");
        data.add("车辆品牌");
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
                    intent = new Intent(MainFunctionActivity.this, BottomMenuWithActionbarLikeWeChat6Activity.class);
                    startActivity(intent);
                    break;
                case BOTTOM_MENU_WITH_POPUPWINDOW:
                    intent = new Intent(MainFunctionActivity.this, BottomMenuWithPopupWindowLikeQQ.class);
                    startActivity(intent);
                    break;
                case BOTTOM_MENU_WITH_VIEWPAGER:
                    intent = new Intent(MainFunctionActivity.this, BottomMenuWithViewpager.class);
                    startActivity(intent);
                    break;
                case BOTTOM_MENU_WITH_FRAGMENT:
                    intent = new Intent(MainFunctionActivity.this, BottomMenuWithFragment.class);
                    startActivity(intent);
                    break;
                case LIST_VIEW_ITEM_UI:
                    intent = new Intent(MainFunctionActivity.this, ListViewItemUIActivity.class);
                    startActivity(intent);
                    break;
                case LIST_VIEW_MESSAGE_READED:
                    intent =new Intent(MainFunctionActivity.this, ListViewCustomStateActivity.class);
                    startActivity(intent);
                    break;
                case LIST_VIEW_UPDATE:
                    intent =new Intent(MainFunctionActivity.this, ListViewUpdateActivity.class);
                    startActivity(intent);
                    break;
                case LIST_VIEW_LOAD_MORE:
                    intent =new Intent(MainFunctionActivity.this, ListViewLoadMoreActivity.class);
                    startActivity(intent);
                    break;
                case LIST_VIEW_ADAPTER_BASE:
                    intent =new Intent(MainFunctionActivity.this, ListViewAdapterMActivity.class);
                    startActivity(intent);
                    break;
                case VIEW_PAGER_GUIDE:
                    intent =new Intent(MainFunctionActivity.this, ViewPagerGuideActivity.class);
                    startActivity(intent);
                    break;
                case VOLLEY:
                    intent =new Intent(MainFunctionActivity.this, VolleyActivity.class);
                    startActivity(intent);
                    break;
                case POPUP_WINDOW:
                    intent =new Intent(MainFunctionActivity.this, PopupWindowActivity.class);
                    startActivity(intent);
                    break;
                case VOLLEY_TABLE_LAYOUT:
                    intent =new Intent(MainFunctionActivity.this, VolleyTableLayoutActivity.class);
                    startActivity(intent);
                    break;
                case PULL_TO_REFRESH:
                    intent =new Intent(MainFunctionActivity.this, PullToRefreshActivity.class);
                    startActivity(intent);
                    break;
                case WECHAT_PICTURE_CHOSE:
                    intent =new Intent(MainFunctionActivity.this, PictureChoseActivity.class);
                    startActivity(intent);
                    break;
                case X_UTILS:
                    intent =new Intent(MainFunctionActivity.this, XUtilsActivity.class);
                    startActivity(intent);
                    break;
                case PAGER_INDICATOR:
                    intent =new Intent(MainFunctionActivity.this, ViewPagerIndicatorActivity.class);
                    startActivity(intent);
                    break;
                case DATE_PICKER:
                    intent =new Intent(MainFunctionActivity.this, DatePickerDialogActivity.class);
                    startActivity(intent);
                    break;
                case CAR_BRAND:
                    intent =new Intent(MainFunctionActivity.this, CarBrandIndexActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }

           /*-----done------*/
        //actionbar
        //fragment
        //activity
        //popupWindow 省学校二级联动

        //listview
        //adapter
        //viewpager   JazzyViewPager https://github.com/jfeinstein10/JazzyViewPager

        //textview
        //edittext
        //imageview
        //button


        //Dialog 主要保护后面四个子类 AlterDialog, ProgressDialog, DatePickerDialog, TimePikerDialog  VS DialogFragment
        //PreferenceActivity VS PreferenceFragment
        //notify
        //toast
        //json操作
        //网络访问
        //OOM
        //xUtils(Afinal) VS GreenDAO https://github.com/wyouflf/xUtils
        //Gson
        //PullToRefresh http://blog.csdn.net/lmj623565791/article/details/38238749
        //volley VS universal-image-loader VS android-async-http  VS DiskLruCache
        //Volley是将AsyncHttpClient和Universal-Image-Loader的优点集成于一身的一个框架
        //底部导航栏 http://blog.csdn.net/lmj623565791/article/details/24740977

        /*----will do---*/
        //android-percent-support-lib-sample http://blog.csdn.net/lmj623565791/article/details/46695347
        //RecycleView + CardView 控件
        //RecyclerView，CardView，Palette http://www.open-open.com/lib/view/open1434014850864.html
        //ActionBar to ToolBar
        //RemoteViews
        //Drawable Bitmap

        //自定义view 内存缓存 硬盘缓存 图像处理
        //数据库操作
        //webView
        //progressbar ProgressDialog
        //Loader
        //login 第三方登录
        //socket tcp通信 --文件上传下载--http://blog.csdn.net/lmj623565791/article/details/23781773
        //               --文件上传下载--http://blog.csdn.net/lmj623565791/article/details/26994463
        //地图应用
        //DiskLruCache
        //生成验证码--自定义view --http://blog.csdn.net/lmj623565791/article/details/24252901
        //CrashHandler and multidex
        //AsyncTask
        //IntentService http://blog.csdn.net/lmj623565791/article/details/47143563 后台服务
        //HandlerThread http://blog.csdn.net/lmj623565791/article/details/47079737 子线程执行looper
        //ThreadPoolExecutor
        //DownLoadManager http://www.cnblogs.com/819158327fan/p/4906436.html
        //CountDownTimer http://www.cnblogs.com/shiwei-bai/p/4949997.html
        //ViewDragHelper并不是第一个用于分析手势处理的类，gesturedetector也是，但是在和拖动相关的手势分析方面gesturedetector只能说是勉为其难。

        /*----框架---*/
        //OkHttp 可以与volley配合，做volley的传输层 http://blog.csdn.net/lmj623565791/article/details/47911083
        //EventBus
        //ButterKnife

        //项目里差不多都用自己写的框架，除了一些UI会找lib，能自己写的基本自己动手，毕竟架构再完善也很难去满足一个特定的需求
        //网络层： Retrofit或者Volley＋OkHttp，async-http-lib尽量就别用了，比较老。另外这些都需要再进一步扩展的，可以自己搜下，有用的就集成进去。
        //数据库： Ormlite或者Realm，要加密的话用SqlCipher
        //图片缓存： Fresco， Picasso，如果集成的效果不理想，多看看配置参数是否正确
        //工具： 查内存泄漏（leakcanary）异步通知（RxJava谨慎使用）数学计算表达式（expression4j）日期处理（joda time）
        //sourceinsight java源码查看工具

        //Fresco https://github.com/facebook/fresco Fresco是Facebook最新推出的一款用于Android应用中展示图片的强大图片库，可以从网络、本地存储和本地资源中加载图片。
        //Picasso http://square.github.io/picasso/  Picasso是Square公司开源的一个Android图形缓存库.

        // --UI liberty
        //JazzyViewPager https://github.com/jfeinstein10/JazzyViewPager
        //ViewPageIndicator https://github.com/JakeWharton/Android-ViewPagerIndicator http://blog.csdn.net/xiaanming/article/details/10766053/
        //CircleProgress  https://github.com/lzyzsd/CircleProgress  进度条UI
        //SlidingMenu 侧滑菜单 http://blog.csdn.net/lmj623565791/article/details/36677279
        //PhotoView  https://github.com/chrisbanes/PhotoView/tree/master/library 一款扩展自Android ImageView ，支持通过单点/多点触摸来进行图片缩放的智能控件
        //BadgeView https://github.com/stefanjauker/BadgeView 数字提醒
        //NineOldAndroids https://github.com/JakeWharton/NineOldAndroids http://blog.csdn.net/singwhatiwanna/article/details/17639987 动画库
        //NiftyNotification动画效果生动有趣的通知（Android Toast替代品）https://github.com/sd6352051/NiftyNotification 本身又依赖于另外一个github上的第三方开源项目NineOldAndroids
        //NiftyDialogEffects 对话框UI库 https://github.com/sd6352051/NiftyDialogEffects
        //CustomShapeImageView https://github.com/MostafaGazar/CustomShapeImageView  如果仅仅是需要获取圆形、心形、花瓣形头像图片（具体看Demo），那么经过Android CustomShapeImageView简单XML设置就可以实现。
        //SweetAlertDialog  制作精美、动画效果的对话、消息提示框插件 https://github.com/pedant/sweet-alert-dialog  注意，其自身依赖materialish-progress（https://github.com/pnikosis/materialish-progress ）。
        //LabelView：https://github.com/linger1216/labelView 电商、商城类APP常用标签"hot"
        //MPAndroidChart 统计图表 https://github.com/PhilJay/MPAndroidChart

        //android 5.0 Material Design  new UI
        //SnackBar TextInputLayout TabLayout


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
