package com.gary.garytool.view.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.gary.garytool.R;

import java.util.ArrayList;

public class ViewPagerGuideActivity extends Activity implements OnPageChangeListener, OnClickListener {

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private ArrayList<View> mViews;
    //引导图片的资源数组
    private static final int[] mPictures={R.drawable.base_frame_welcome_guide1,R.drawable.base_frame_welcome_guide2,R.drawable.base_frame_welcome_guide3,R.drawable.base_frame_welcome_guide4};
    //底部小点的图片
    private ImageView[] mPoints;
    //记录当前选中位置
    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_guide);
        initView();
        initData();
    }

    private void initData() {
        //定义一个布局并设置参数
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导图片列表
        for(int i=0;i<mPictures.length;i++)
        {
            ImageView iv=new ImageView(this);
            iv.setLayoutParams(params);
            //防止图片不能填满屏幕
            iv.setScaleType(ScaleType.FIT_XY);
            iv.setImageResource(mPictures[i]);
            mViews.add(iv);
        }

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

        //初始化底部小点
        initPoint();
    }

    /**
     * 初始化底部小点
     */
    private void initPoint() {
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.ll_guide);
        mPoints=new ImageView[mPictures.length];
        for (int i=0;i<mPoints.length;i++)
        {
            mPoints[i]= (ImageView) linearLayout.getChildAt(i);
            //默认都设为灰色
            mPoints[i].setEnabled(true);
            //给每个小点设置监听
            mPoints[i].setOnClickListener(this);
            //设置位置tag,方便取出与当前位置对应
            mPoints[i].setTag(i);
        }

        //设置当前默认的位置
        mCurrentIndex=0;
        //设置为白色,即选中状态
        mPoints[mCurrentIndex].setEnabled(false);
    }

    private void initView() {
        mViews= new ArrayList<>();
        mViewPager= (ViewPager) findViewById(R.id.vp_guide);
        mViewPagerAdapter=new ViewPagerAdapter(mViews);
    }
    @Override
    public void onClick(View v) {

        int position= (int) v.getTag();
        setCurrentView(position);
        setCurrentDot(position);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //设置底部小点选中状态
        setCurrentDot(position);
    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setCurrentDot(int position) {

        if(position<0||position>=mPoints.length||mCurrentIndex==position)
            return;

        mPoints[position].setEnabled(false);
        mPoints[mCurrentIndex].setEnabled(true);
        mCurrentIndex=position;
    }

    private void setCurrentView(int position) {
        if(position<0||position>=mPictures.length)
            return;

        mViewPager.setCurrentItem(position);
    }

    private class ViewPagerAdapter extends PagerAdapter {
        private ArrayList<View> mViews;
        public ViewPagerAdapter(ArrayList<View> views) {
            mViews=views;
        }

        @Override
        public int getCount() {
            if(mViews!=null)
                return mViews.size();
            return 0;
        }

        /**
         * 判断是否由对象生成界面
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view==object);
        }

        /**
         * 销毁position位置的界面
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position),0);
            return mViews.get(position);
        }
    }
}
