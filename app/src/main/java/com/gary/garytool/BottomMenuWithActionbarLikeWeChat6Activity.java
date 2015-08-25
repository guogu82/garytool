package com.gary.garytool;


import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import com.gary.garytool.view.ChangeColorIconWithTextView;

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

        setOverflowshowingAlways();
        //getActionBar().setDisplayHomeAsUpEnabled(false);
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);

        initDatas();

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    private void initDatas() {
        for(String title:mTitiles)
        {
            Demo1Fragment tabFragment=new Demo1Fragment();
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

        initTabIndiacator();

    }

    private void initTabIndiacator() {
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view_demo1, menu);
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

    private void setOverflowshowingAlways() {
        try
        {
            //true if a permanent menu key is present,false otherwise
            //ViewConfiguration config=ViewConfiguration.get(this);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
