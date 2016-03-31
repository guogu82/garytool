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
    private ImageView mIvScenicName;
    private ImageView mIvHotScenic;
    private ImageView mIvShop;
    private ImageView mIvWc;
    private ImageView mIvTeammateLocation;

    private LinearLayout mLayoutSatelliteImagery;
    private LinearLayout mLayout2DImagery;
    private boolean mIsSatellite;


    private boolean mIsScenicName,mIsHotScenic,mIsWc,mIsShop,mIsTeammateLocation;

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
                mIsSatellite=true;
                mLayoutSatelliteImagery.setBackgroundColor(getResources().getColor(R.color.greenNice));
                mLayout2DImagery.setBackgroundColor(Color.WHITE);
            }
        });

        mLayout2DImagery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsSatellite=false;
                mLayoutSatelliteImagery.setBackgroundColor(Color.WHITE);
                mLayout2DImagery.setBackgroundColor(getResources().getColor(R.color.greenNice));
            }
        });

        mIvScenicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mIsScenicName=!mIsScenicName;
                    changeImageDrawable(mIvScenicName, mIsScenicName);
            }
        });
        mIvHotScenic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsHotScenic= !mIsHotScenic;
                changeImageDrawable(mIvHotScenic, mIsHotScenic);
            }
        });
        mIvShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsShop= !mIsShop;
                changeImageDrawable(mIvShop, mIsShop);
            }
        });
        mIvWc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsWc= !mIsWc;
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

        mLayoutSatelliteImagery= (LinearLayout) findViewById(R.id.layout_satellite_imagery);
        mLayout2DImagery= (LinearLayout) findViewById(R.id.layout_2D_imagery);
        mLayoutSatelliteImagery.setBackgroundColor(getResources().getColor(R.color.greenNice));
        mLayout2DImagery.setBackgroundColor(Color.WHITE);
        mIsSatellite=true;


        mTvTopBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        mTvTopBarTitle.setText("设置");
        mIvScenicName = (ImageView) findViewById(R.id.iv_scenic_name);
        mIvScenicName.setImageResource(R.drawable.tourist_item_open);
        mIsScenicName=true;
        mIvHotScenic = (ImageView) findViewById(R.id.iv_hot_scenic);
        mIvHotScenic.setImageResource(R.drawable.tourist_item_closed);
        mIsHotScenic=false;
        mIvShop = (ImageView) findViewById(R.id.iv_shop);
        mIvShop.setImageResource(R.drawable.tourist_item_closed);
        mIsShop=false;
        mIvWc = (ImageView) findViewById(R.id.iv_wc);
        mIvWc.setImageResource(R.drawable.tourist_item_closed);
        mIsWc=false;
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
