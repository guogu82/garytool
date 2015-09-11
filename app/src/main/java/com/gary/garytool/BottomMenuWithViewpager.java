package com.gary.garytool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BottomMenuWithViewpager extends Activity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    // 底部菜单4个Linearlayout
    private LinearLayout ll_home;
    private LinearLayout ll_address;
    private LinearLayout ll_friend;
    private LinearLayout ll_setting;

    // 底部菜单4个ImageView
    private ImageView iv_home;
    private ImageView iv_address;
    private ImageView iv_friend;
    private ImageView iv_setting;

    // 底部菜单4个菜单标题
    private TextView tv_home;
    private TextView tv_address;
    private TextView tv_friend;
    private TextView tv_setting;

    // 中间内容区域
    private ViewPager viewPager;

    // ViewPager适配器ContentAdapter
    private ContentAdapter adapter;

    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bottom_menu_with_viewpager);

        //初始化控件
        initView();

        //初始化底部按钮事件
        initEvent();
    }

    private void initEvent() {
        //设置按钮监听
        ll_home.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_friend.setOnClickListener(this);
        ll_setting.setOnClickListener(this);

        //设置ViewPager滑动监听
        viewPager.setOnPageChangeListener(this);
    }

    private void initView() {
        //底部菜单4个Linearlayout
        this.ll_home= (LinearLayout) findViewById(R.id.ll_home);
        this.ll_address= (LinearLayout) findViewById(R.id.ll_address);
        this.ll_friend= (LinearLayout) findViewById(R.id.ll_friend);
        this.ll_setting= (LinearLayout) findViewById(R.id.ll_setting);

        //底部菜单4个ImageView
        this.iv_home= (ImageView) findViewById(R.id.iv_home);
        this.iv_address= (ImageView) findViewById(R.id.iv_address);
        this.iv_friend= (ImageView) findViewById(R.id.iv_friend);
        this.iv_setting= (ImageView) findViewById(R.id.iv_setting);


        //底部菜单4个ImageView
        this.tv_home= (TextView) findViewById(R.id.tv_home);
        this.tv_address= (TextView) findViewById(R.id.tv_address);
        this.tv_friend= (TextView) findViewById(R.id.tv_friend);
        this.tv_setting= (TextView) findViewById(R.id.tv_setting);

        //中间内容区域ViewPager
        this.viewPager= (ViewPager) findViewById(R.id.vp_content);

        //适配器
        View page_01=View.inflate(BottomMenuWithViewpager.this,R.layout.fragment_textview,null);
        View page_02=View.inflate(BottomMenuWithViewpager.this,R.layout.fragment_button,null);
        View page_03=View.inflate(BottomMenuWithViewpager.this,R.layout.layout_page_03,null);
        View page_04=View.inflate(BottomMenuWithViewpager.this,R.layout.fragment_other,null);

        //配合完全退出APP
        Button button = (Button) page_02.findViewById(R.id.bt_finish_app);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BottomMenuWithViewpager.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
                startActivity(intent);
                finish();//关掉自己
            }
        });

        views=new ArrayList<View>();
        views.add(page_01);
        views.add(page_02);
        views.add(page_03);
        views.add(page_04);

        this.adapter =new ContentAdapter(views);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {

        //在每次点击后将所有的底部按钮（ImageView,TextView）颜色改为灰色，然后根据点击着色
        restartButton();
        //ImageView 和 TextView 设置为绿色，页面随之跳转
        switch (v.getId())
        {
            case R.id.ll_home:
                iv_home.setImageResource(R.drawable.tab1_press);
                tv_home.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_address:
                iv_address.setImageResource(R.drawable.tab2_press);
                tv_address.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_friend:
                iv_friend.setImageResource(R.drawable.tab3_press);
                tv_friend.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_setting:
                iv_setting.setImageResource(R.drawable.tab4_press);
                tv_setting.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    private void restartButton() {
        //ImageView设置为灰色
        iv_home.setImageResource(R.drawable.tab1);
        iv_address.setImageResource(R.drawable.tab2);
        iv_friend.setImageResource(R.drawable.tab3);
        iv_setting.setImageResource(R.drawable.tab4);
        //TextView 设置为白色
        tv_home.setTextColor(0xffffffff);
        tv_address.setTextColor(0xffffffff);
        tv_friend.setTextColor(0xffffffff);
        tv_setting.setTextColor(0xffffffff);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        restartButton();
        //当前view被选择的时候，改变底部菜单图片，文字颜色
        switch (position)
        {
            case 0:
                iv_home.setImageResource(R.drawable.tab1_press);
                tv_home.setTextColor(0xff1B940A);
                break;
            case 1:
                iv_address.setImageResource(R.drawable.tab2_press);
                tv_address.setTextColor(0xff1B940A);
                break;
            case 2:
                iv_friend.setImageResource(R.drawable.tab3_press);
                tv_friend.setTextColor(0xff1B940A);
                break;
            case 3:
                iv_setting.setImageResource(R.drawable.tab4_press);
                tv_setting.setTextColor(0xff1B940A);
                break;
            default:
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * ViewPager的适配器是PagerAdapter，它是基类提供适配器来填充页面ViewPager内部，你很可能想要使用一个更具体的实现,
     * 如FragmentPagerAdapter或FragmentStatePagerAdapter。在这里需要说明一下，其实ViewPager应该和Fragment一起使用，至少谷歌官方是这么想的，
     * 但是在3.0之下，我们没有必要这么做。下面要注意，当你实现一个PagerAdapter,你必须至少覆盖以下方法:
     * instantiateItem(ViewGroup, int)
     * destroyItem(ViewGroup, int, Object)
     * getCount()
     * isViewFromObject(View, Object)
     */
    public class ContentAdapter extends PagerAdapter {

        private List<View> views;
        public ContentAdapter(List<View> views)
        {
            this.views=views;
        }
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container,int position)
        {
            View view=views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container,int position,Object object)
        {
            container.removeView(views.get(position));
        }
    }
}
