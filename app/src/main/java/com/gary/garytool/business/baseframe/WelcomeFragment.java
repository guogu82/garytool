package com.gary.garytool.business.baseframe;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gary.garytool.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/18.
 * @author gary guo
 * 本类讲解 欢迎页的使用
 */
public class WelcomeFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private View mFragmentWelcome;

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private ArrayList<View> mViews;

    //引导图片的资源数组
    private static final int[] mPictures={R.drawable.base_frame_welcome_guide1,R.drawable.base_frame_welcome_guide2,R.drawable.base_frame_welcome_guide3,R.drawable.base_frame_welcome_guide4};
    //底部小点的图片数组
    private ImageView[] mPoints;
    //记录当前选中的位置
    private int mCurrentIndex;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentWelcome=inflater.inflate(R.layout.base_frame_welcome_fragment,container,false);
        initView();
        initData();
        return mFragmentWelcome;

    }

    private void initData() {
        //定义一个布局并设置参数
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导图片列表
        for(int i=0;i<mPictures.length;i++)
        {
            ImageView iv=new ImageView(getActivity());
            iv.setLayoutParams(params);
            //防止图片不能填满屏幕
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(mPictures[i]);
            mViews.add(iv);
        }

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

        //初始化底部小点
        initPoint();
    }

    private void initPoint() {
        LinearLayout linearLayout= (LinearLayout) mFragmentWelcome.findViewById(R.id.layout_guide);
        mPoints=new ImageView[mPictures.length];
        for (int i=0;i<mPoints.length;i++)
        {
            mPoints[i]= (ImageView) linearLayout.getChildAt(i);
            //默认都设为灰色
            mPoints[i].setEnabled(true);
            //给每个小点设置监听
            //mPoints[i].setOnClickListener(this);
            //设置位置tag,方便取出与当前位置对应
            mPoints[i].setTag(i);
        }

        //设置当前默认的位置
        mCurrentIndex=0;
        //设置为白色,即选中状态
        mPoints[mCurrentIndex].setEnabled(false);
    }

    private void initView() {
        mViews=new ArrayList<>();
        mViewPager= (ViewPager) mFragmentWelcome.findViewById(R.id.vp_guide);
        mViewPagerAdapter=new ViewPagerAdapter();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //设置底部小点选中状态
        setCurrentDot(position);
    }

    private void setCurrentDot(int position) {
        if(position<0||position>=mPoints.length||mCurrentIndex==position)
            return;

        mPoints[position].setEnabled(false);
        mPoints[mCurrentIndex].setEnabled(true);
        mCurrentIndex=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ViewPagerAdapter  extends PagerAdapter {
        @Override
        public int getCount() {
            if(mViews!=null)
                return mViews.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view==object);
        }

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
