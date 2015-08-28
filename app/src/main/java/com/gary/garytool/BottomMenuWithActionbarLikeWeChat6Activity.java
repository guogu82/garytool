package com.gary.garytool;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.SearchView;

import com.gary.garytool.view.ChangeColorIconWithTextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class BottomMenuWithActionbarLikeWeChat6Activity extends FragmentActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {

    private ViewPager mViewPager;
    private List<Fragment> mTabs=new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private String[] mTitiles=new String[]{"First Fragment","Second Fragment","Third Fragment","Fourth Fragment"};
    private List<ChangeColorIconWithTextView> mTabIndicator=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_menu_with_actionbar_likewechat);

        //action的一个经典教程
        //http://blog.csdn.net/guolin_blog/article/details/18234477

        //设置显示加号按钮，配合styles.xml的 android:actionOverflowButtonStyle <item name="android:src">@drawable/actionbar_add_icon</item>使用
        setOverflowshowingAlways();

        //设置隐藏左边的图标
       getActionBar().setDisplayShowHomeEnabled(false);

        //显示当前activity的actionbar 的左边箭头 配合 onOptionsItemSelected 方法使用
        //ActionBar导航和Back键在设计上的区别。这块是有区别的一个是主从，一个是前后。http://blog.csdn.net/guolin_blog/article/details/18234477
       getActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initDatas();
        mViewPager.setAdapter(mAdapter);
        initEvent();

    }

    private void initEvent() {
        mViewPager.setOnPageChangeListener(this);
    }

    private void initView() {
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);

        ChangeColorIconWithTextView one= (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
        ChangeColorIconWithTextView two= (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
        ChangeColorIconWithTextView three= (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
        ChangeColorIconWithTextView four= (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

        mTabIndicator.add(one);
        mTabIndicator.add(two);
        mTabIndicator.add(three);
        mTabIndicator.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setIconAlpha(1.0f);
    }

    private void initDatas() {
        for(String title:mTitiles)
        {
            DemoTextViewFragment tabFragment=new DemoTextViewFragment();
            Bundle args=new Bundle();
            args.putString("title",title);
            mTabs.add(tabFragment);
        }

        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_chat_action_bar, menu);
        //设置actionbar的搜索按钮事件
        MenuItem searchItem=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) searchItem.getActionView();

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * 设置menu 显示左边图标
     * overflow中的Action按钮应不应该显示图标，是由MenuBuilder这个类的setOptionalIconsVisible方法来决定的，
     * 如果我们在overflow被展开的时候给这个方法传入true，那么里面的每一个Action按钮对应的图标就都会显示出来了。
     * 调用的方法当然仍然是用反射了
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(featureId== Window.FEATURE_ACTION_BAR&&menu!=null)
        {
            if (menu.getClass().getSimpleName().equals("MenuBuilder"))
            {
                try
                {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e)
                {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            //home 配合actionbar的左边箭头返回
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_group_chat:
                intent=new Intent(this,ListViewItemUIActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_friend:
                intent=new Intent(this,ListViewDemo3Activity.class);
                startActivity(intent);
                return true;
            case R.id.action_scan:
                intent=new Intent(this,ListViewItemUIActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_feedback:
                intent=new Intent(this,ListViewDemo3Activity.class);
                startActivity(intent);
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }

        //下面功能配合actionbar的左侧箭头回退父activity的
        /*
        case android.R.id.home:
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, upIntent);
        }
        return true;
        */
    }

    /**
     * overflow按钮的显示情况和手机的硬件情况是有关系的，
     * 如果手机没有物理Menu键的话，overflow按钮就可以显示，
     * 如果有物理Menu键的话，overflow按钮就不会显示出来.
     * 下面代码是设置无论如何都显示overflow按钮
     * 在ViewConfiguration这个类中有一个叫做sHasPermanentMenuKey的静态变量，系统就是根据这个变量的值来判断手机有没有物理Menu键的。
     * 当然这是一个内部变量，我们无法直接访问它，但是可以通过反射的方式修改它的值，让它永远为false就可以
     */
    private void setOverflowshowingAlways() {
        try
        {
            //true if a permanent menu key is present,false otherwise
            ViewConfiguration config=ViewConfiguration.get(this);
            Field menuKey= ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config,false);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }




    //下面是评论对这个功能实现的描述
//监听vp滑动过程，通过属性动画更改图标透明度，不是可以嘛
//解压过微信看他里边应该是2张图做的，就是个imageview就行，，背景设置为灰色的图，然后src就有色的图片，滑动的时候根据偏移量盖面imageview的透明度就可以实现
//内存方面注意到画图片渐变的地方感觉费内存了，每次都创建新图，而且刷新一下就创建好多张图
//回复mayuqing：内存倒是不费，毕竟执行完方法也就回收了，就是频繁创建回收了，你可以稍微优化下代码。
//我测试了 用两张图片，分别是未取得焦点和去的焦点之后的颜色不同但是大小相同的两张图片，设置透明度变化，比如设置第一个图的透明度为100第二张图的透明度是255-100，绘制在相同位置自然叠加就行，而且微信用的是继承的ImageVIew和一个textView实现的
//嗯，不动态去生成各种颜色图标，两张图最简单了~~
//博主您好 我想到了一个更简单的办法 在微信里面 这个其实是由两张图片实现的 一张是纯绿色的 一张是白色的，于是我就想到 用FrameLayout 白色的在下面 绿色的在上面 然后滑动的时候 根据监听器的positionOffset设置view 的alpha ，变可以达到同样的效果 且代码还很简洁
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(positionOffset>0)
        {
            ChangeColorIconWithTextView left=mTabIndicator.get(position);
            ChangeColorIconWithTextView right=mTabIndicator.get(position+1);

            left.setIconAlpha(1-positionOffset);
            right.setIconAlpha(positionOffset);
        }

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        //处理tab转换，以防范新增按钮对数据的影响。
        clickTab(v);

    }

    private void clickTab(View v) {
        resetOtherTabs();

        switch (v.getId())
        {
            case R.id.id_indicator_one:
                mTabIndicator.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.id_indicator_two:
                mTabIndicator.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.id_indicator_three:
                mTabIndicator.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2,false);
                break;
            case R.id.id_indicator_four:
                mTabIndicator.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3,false);
                break;
            default:
                break;
        }
    }

    /**
     * 重置其它的Tab
     */
    private void resetOtherTabs() {
        for (int i=0;i<mTabIndicator.size();i++)
        {
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }


}
