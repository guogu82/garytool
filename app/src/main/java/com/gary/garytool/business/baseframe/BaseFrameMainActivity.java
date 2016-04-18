package com.gary.garytool.business.baseframe;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gary.garytool.R;

/**
 * Created by Administrator on 2016/4/18.
 * @author gary guo
 * 本类讲解 Fragment+FragmentManager的底部tab标签使用
 */
public class BaseFrameMainActivity extends Activity implements View.OnClickListener {

    private WelcomeFragment mFragmentWelcome;
    private IndicatorFragment mFragmentIndicator;
    private ListFragment mFragmentList;

    /**
     * 底部的按钮
     */
    private LinearLayout mLayoutWelcome;
    private LinearLayout mLayoutIndicator;
    private LinearLayout mLayoutList;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_frame_main_activity);

        initView();
        mFragmentManager=getFragmentManager();
        setTabSelection(R.id.layout_welcome);
    }
    private void initView() {
        mLayoutWelcome= (LinearLayout) findViewById(R.id.layout_welcome);
        mLayoutIndicator= (LinearLayout) findViewById(R.id.layout_indicator);
        mLayoutList= (LinearLayout) findViewById(R.id.layout_list);

        mLayoutWelcome.setOnClickListener(this);
        mLayoutIndicator.setOnClickListener(this);
        mLayoutList.setOnClickListener(this);

        mIvWelcome= (ImageView) findViewById(R.id.iv_welcome);
        mIvIndicator= (ImageView) findViewById(R.id.iv_indicator);
        mIvList= (ImageView) findViewById(R.id.iv_list);

        mTvWelcome= (TextView) findViewById(R.id.tv_welcome);
        mTvIndicator=(TextView) findViewById(R.id.tv_indicator);;
        mTvList=(TextView) findViewById(R.id.tv_list);

        mTextColorNormal=getResources().getColor(R.color.white);
       mTextColorPressed=getResources().getColor(R.color.greenDark);
    }

    private void setTabSelection(int resourceId) {
        //重置按钮
        resetTag();

        //开启Fragment事务
        FragmentTransaction transaction=mFragmentManager.beginTransaction();
        hideAllFragment(transaction);

        switch (resourceId)
        {
            case R.id.layout_welcome:
                mIvWelcome.setImageResource(R.drawable.base_frame_welcome_pressed);
                mTvWelcome.setTextColor(mTextColorPressed);
                if(mFragmentWelcome==null)
                {
                    mFragmentWelcome=new WelcomeFragment();
                    transaction.add(R.id.layout_content,mFragmentWelcome);
                }
                else
                {
                    transaction.show(mFragmentWelcome);
                }
                break;
            case R.id.layout_indicator:
                mIvIndicator.setImageResource(R.drawable.base_frame_indicator_pressed);
                mTvIndicator.setTextColor(mTextColorPressed);
                if(mFragmentIndicator==null)
                {
                    mFragmentIndicator=new IndicatorFragment();
                    transaction.add(R.id.layout_content,mFragmentIndicator);
                }
                else
                {
                    transaction.show(mFragmentIndicator);
                }
                break;
            case R.id.layout_list:
                mIvList.setImageResource(R.drawable.base_frame_list_pressed);
                mTvList.setTextColor(mTextColorPressed);
                if(mFragmentList==null)
                {
                    mFragmentList=new ListFragment();
                    transaction.add(R.id.layout_content,mFragmentList);
                }
                else
                {
                    transaction.show(mFragmentList);
                }
                break;
        }

        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if(mFragmentWelcome!=null)
        {
            transaction.hide(mFragmentWelcome);
        }

        if(mFragmentIndicator!=null)
        {
            transaction.hide(mFragmentIndicator);
        }
        if(mFragmentList!=null)
        {
            transaction.hide(mFragmentList);
        }
    }

    private ImageView mIvWelcome;
    private ImageView mIvIndicator;
    private ImageView mIvList;
    private TextView mTvWelcome;
    private TextView mTvIndicator;
    private TextView mTvList;
    private int mTextColorNormal;
    private int mTextColorPressed;
    private void resetTag() {
        mIvWelcome.setImageResource(R.drawable.base_frame_welcome_normal);
        mIvIndicator.setImageResource(R.drawable.base_frame_indicator_normal);
        mIvList.setImageResource(R.drawable.base_frame_list_normal);
        mTvWelcome.setTextColor(mTextColorNormal);
        mTvIndicator.setTextColor(mTextColorNormal);
        mTvList.setTextColor(mTextColorNormal);
    }


    @Override
    public void onClick(View v) {
        setTabSelection(v.getId());
    }
}
