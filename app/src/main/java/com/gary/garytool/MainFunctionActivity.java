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

import com.gary.garytool.business.datepicker.DatePickerDialogActivity;
import com.gary.garytool.function.BaasActivity;
import com.gary.garytool.function.databinding.DataBindingActivity;
import com.gary.garytool.function.rxjava.RxJavaActivity;
import com.gary.garytool.function.carbrand.CarBrandIndexActivity;
import com.gary.garytool.util.Util;
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

/**
 *  Created by gary on 2016/06/29.
 * @author gary guo
 * 本类用于记录复杂控件，框架使用，类库使用的用法
 */
public class MainFunctionActivity extends Activity {
    private ListView mListView;


    private  List<String> mData=new ArrayList<String>(){
        {
            add("微信6.0主界面");
            add("dataBinding");
            add("RxJava");
            add("BaaS服务");
            add("底部菜单仿QQ空间");
            add("底部菜单WithViewpager");
            add("底部菜单WithFragment");
            add("ListViewItem界面展示");
            add("ListView邮件已读");
            add("ListView下拉刷新");
            add("ListView上拉加载更多");
            add("万能的adapter适配器");
            add("ViewPager引导页");/*http://www.cnblogs.com/yc-755909659/p/4283294.html*/
            add("volley");
            add("地区学校二级联动-PopupWindow");//http://www.cnblogs.com/tonycheng93/p/4823860.html
            add("股票显示-TableLayout");
            add("PullToRefresh");
            add("微信图片选择器");
            add("XUtils");
            add("跟随型ViewPager指示器");
            add("日期控件");
            add("车辆品牌");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mListView = (ListView) findViewById(R.id.lv_list);
        MyAdapter adapter = new MyAdapter(this);
        mListView.setAdapter(adapter);
    }


    public final class ViewHolder {
        public Button item;
    }

    private class MyAdapter extends BaseAdapter {

        private void onItemClick(int position) {

            String value= (String) getItem(position);
            if(value.equals("微信6.0主界面"))
                Util.startActivity(MainFunctionActivity.this, BottomMenuWithActionbarLikeWeChat6Activity.class);
            else if(value.equals("dataBinding"))
                Util.startActivity(MainFunctionActivity.this, DataBindingActivity.class);
            else if(value.equals("RxJava"))
                Util.startActivity(MainFunctionActivity.this, RxJavaActivity.class);
            else if(value.equals("BaaS服务"))
                Util.startActivity(MainFunctionActivity.this, BaasActivity.class);
            else if(value.equals("底部菜单仿QQ空间"))
                Util.startActivity(MainFunctionActivity.this, BottomMenuWithPopupWindowLikeQQ.class);
            else if(value.equals("底部菜单WithViewpager"))
                Util.startActivity(MainFunctionActivity.this, BottomMenuWithViewpager.class);
            else if(value.equals("底部菜单WithFragment"))
                Util.startActivity(MainFunctionActivity.this, BottomMenuWithFragment.class);
            else if(value.equals("ListViewItem界面展示"))
                Util.startActivity(MainFunctionActivity.this, ListViewItemUIActivity.class);
            else if(value.equals("ListView邮件已读"))
                Util.startActivity(MainFunctionActivity.this, ListViewCustomStateActivity.class);
            else if(value.equals("ListView下拉刷新"))
                Util.startActivity(MainFunctionActivity.this, ListViewUpdateActivity.class);
            else if(value.equals("ListView上拉加载更多"))
                Util.startActivity(MainFunctionActivity.this, ListViewLoadMoreActivity.class);
            else if(value.equals("万能的adapter适配器"))
                Util.startActivity(MainFunctionActivity.this, ListViewAdapterMActivity.class);
            else if(value.equals("ViewPager引导页"))
                Util.startActivity(MainFunctionActivity.this, ViewPagerGuideActivity.class);
            else if(value.equals("volley"))
                Util.startActivity(MainFunctionActivity.this, VolleyActivity.class);
            else if(value.equals("地区学校二级联动-PopupWindow"))
                Util.startActivity(MainFunctionActivity.this, PopupWindowActivity.class);
            else if(value.equals("股票显示-TableLayout"))
                Util.startActivity(MainFunctionActivity.this, VolleyTableLayoutActivity.class);
            else if(value.equals("PullToRefresh"))
                Util.startActivity(MainFunctionActivity.this, PullToRefreshActivity.class);
            else if(value.equals("微信图片选择器"))
                Util.startActivity(MainFunctionActivity.this, PictureChoseActivity.class);
            else if(value.equals("XUtils"))
                Util.startActivity(MainFunctionActivity.this, XUtilsActivity.class);
            else if(value.equals("跟随型ViewPager指示器"))
                Util.startActivity(MainFunctionActivity.this, ViewPagerIndicatorActivity.class);
            else if(value.equals("日期控件"))
                Util.startActivity(MainFunctionActivity.this, DatePickerDialogActivity.class);
            else if(value.equals("车辆品牌"))
                Util.startActivity(MainFunctionActivity.this, CarBrandIndexActivity.class);


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
        //OkHttp+Retrofit可以与volley配合，做volley的传输层 http://blog.csdn.net/lmj623565791/article/details/47911083
        //EventBus
        //EXJava EXAndroid
        //ButterKnife done

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
            return mData.size();
        }
        @Override
        public Object getItem(int arg0) {
            return mData.get(arg0);
        }
        @Override
        public long getItemId(int arg0) {
            return arg0;
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




    }


}
