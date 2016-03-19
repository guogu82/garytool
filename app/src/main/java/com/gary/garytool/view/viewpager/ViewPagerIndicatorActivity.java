package com.gary.garytool.view.viewpager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Window;


import com.gary.garytool.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPagerIndicatorActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;

    private List<String> mTitles= Arrays.asList("短信1","收藏2","推荐3","短信4","收藏5","推荐6","短信7","收藏8","推荐9");
    private List<VpSimpleFragment> mContents=new ArrayList<VpSimpleFragment>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_pager_indicator);

        initViews();
        initDatas();

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        mViewPager= (ViewPager) findViewById(R.id.id_viewpager);
        mIndicator= (ViewPagerIndicator) findViewById(R.id.id_indicator);
    }

    private void initDatas() {
        for(String title:mTitles)
        {
            VpSimpleFragment fragment= VpSimpleFragment.newInstance(title);
            mContents.add(fragment);
        }
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }


}
