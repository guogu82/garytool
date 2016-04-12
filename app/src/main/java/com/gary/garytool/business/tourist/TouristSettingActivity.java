package com.gary.garytool.business.tourist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gary.garytool.R;

/**
 * Created by Administrator on 2016/3/30.
 */
public class TouristSettingActivity extends Activity {

    private TextView mTvTopBarTitle;
    private ImageView mIvHotScenic;
    private ImageView mIvShop;
    private ImageView mIvWc;
    private ImageView mIvTeammateLocation;

    private LinearLayout mLayoutSatelliteImagery;
    private LinearLayout mLayout2DImagery;
    private boolean mIsAirscape;


    private boolean mIsHotScenic,mIsWc,mIsShop,mIsTeammateLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_setting_activity);

        initView();
        initEven();

    }

    private void initEven() {
        mLayoutSatelliteImagery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAirscape =true;
                ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_AIRSCAPE]= mIsAirscape;
                mLayoutSatelliteImagery.setBackgroundColor(getResources().getColor(R.color.greenNice));
                mLayout2DImagery.setBackgroundColor(Color.WHITE);
            }
        });

        mLayout2DImagery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAirscape =false;
                ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_AIRSCAPE]= mIsAirscape;
                mLayoutSatelliteImagery.setBackgroundColor(Color.WHITE);
                mLayout2DImagery.setBackgroundColor(getResources().getColor(R.color.greenNice));
            }
        });


        mIvHotScenic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsHotScenic= !mIsHotScenic;
                ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_HOT_SCENIC]=mIsHotScenic;
                changeImageDrawable(mIvHotScenic, mIsHotScenic);
            }
        });
        mIvShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsShop= !mIsShop;
                ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_SHOW_STOP]=mIsShop;
                changeImageDrawable(mIvShop, mIsShop);
            }
        });
        mIvWc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsWc= !mIsWc;
                ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_SHOW_WC]=mIsWc;
                changeImageDrawable(mIvWc, mIsWc);
            }
        });
        mIvTeammateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsTeammateLocation= !mIsTeammateLocation;
                changeImageDrawable(mIvTeammateLocation, mIsTeammateLocation);
            }
        });
    }

    private void initView() {
        mIsAirscape =ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_AIRSCAPE];
        mLayout2DImagery= (LinearLayout) findViewById(R.id.layout_2D_imagery);
        mLayoutSatelliteImagery= (LinearLayout) findViewById(R.id.layout_satellite_imagery);

        if(mIsAirscape)
        {
            mLayout2DImagery.setBackgroundColor(Color.WHITE);
            mLayoutSatelliteImagery.setBackgroundColor(getResources().getColor(R.color.greenNice));
        }
        else {
            mLayout2DImagery.setBackgroundColor(getResources().getColor(R.color.greenNice));
            mLayoutSatelliteImagery.setBackgroundColor(Color.WHITE);
        }



        mTvTopBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        mTvTopBarTitle.setText("设置");
        mIvHotScenic = (ImageView) findViewById(R.id.iv_hot_scenic);
        mIsHotScenic=ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_HOT_SCENIC];
        if(mIsHotScenic)
        {
            mIvHotScenic.setImageResource(R.drawable.tourist_item_open);
        }
        else
        {
            mIvHotScenic.setImageResource(R.drawable.tourist_item_closed);
        }

        mIvShop = (ImageView) findViewById(R.id.iv_shop);
        mIsShop=ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_SHOW_STOP];
        if(mIsShop)
        {
            mIvShop.setImageResource(R.drawable.tourist_item_open);
        }
        else
        {
            mIvShop.setImageResource(R.drawable.tourist_item_closed);
        }

        mIvWc = (ImageView) findViewById(R.id.iv_wc);
        mIsWc=ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_SHOW_WC];
        if(mIsWc)
        {
            mIvWc.setImageResource(R.drawable.tourist_item_open);
        }
        else
        {
            mIvWc.setImageResource(R.drawable.tourist_item_closed);
        }

        mIvTeammateLocation = (ImageView) findViewById(R.id.iv_teammate_location);
        mIvTeammateLocation.setImageResource(R.drawable.tourist_item_closed);
        mIsTeammateLocation=false;



    }

    public void onBarBack(View view) {
        finish();
    }

    public void toPersonalInformation(View view) {
        finish();
    }

    /**
     * 更换ImageView对象的drawable
     *
     * @param image 需要更换的ImageView对象
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void changeImageDrawable(ImageView image, boolean tag) {
        if (tag) {
            image.setImageResource(R.drawable.tourist_item_open);
        } else {
            image.setImageResource(R.drawable.tourist_item_closed);
        }
    }
}
