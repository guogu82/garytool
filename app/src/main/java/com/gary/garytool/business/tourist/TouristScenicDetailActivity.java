package com.gary.garytool.business.tourist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.util.Util;

/**
 * Created by Administrator on 2016/4/1.
 */
public class TouristScenicDetailActivity extends Activity{

    private ScenicInfo mScenicInfo;
    private TextView mTvTopBarTitle;
    private TextView mTvScenicSpotIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_scenic_detail_activity);


        if(ScenicUtil.sCurrentSpotDetailScenicInfo!=null)
        {
            mScenicInfo=ScenicUtil.sCurrentSpotDetailScenicInfo;
        }

        initView();
    }

    private void initView() {
        mTvTopBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        mTvTopBarTitle.setText(mScenicInfo.getName());

        mTvScenicSpotIntroduction = (TextView) findViewById(R.id.tv_scenic_spot_introduction);
        //String filePath=Util.getSDPath()+ "/touristguide/qinghuiyuan/text/" + mScenicInfo.getName() + "/"
        //        + mScenicInfo.getName() + "简介.txt";
        String filePath=Util.getSDPath()+ "/touristguide/qinghuiyuan/text/text.txt";
        String text = ScenicUtil.readFileContent(filePath);
        mTvScenicSpotIntroduction.setText(text == "" ? "暂无简介" : text);
    }

    /*
    private float setBtnStyleAndRuturnFontSize(String currentFontStyle) {
        String setting="";
        int fontSize = 0;
        if (setting.currentFontStyle.equals("小")) {
            fontSize = myApp.minFontSize;
            smallBtn.setBackgroundResource(R.drawable.scenic_spot_video_btn_left_select);
        } else if (setting.currentFontStyle.equals("中")) {
            fontSize = myApp.middleFontSize;
            middleBtn
                    .setBackgroundResource(R.drawable.scenic_spot_video_btn_center_select);
        } else if (myApp.currentFontStyle.equals("大")) {
            fontSize = myApp.maxFontSize;
            bigBtn.setBackgroundResource(R.drawable.scenic_spot_video_btn_center_select);
        } else if (myApp.currentFontStyle.equals("超大")) {
            fontSize = myApp.hugeFontSize;
            hugeBtn.setBackgroundResource(R.drawable.scenic_spot_video_btn_right_select);
        }
        return fontSize;
    }
    */
    public void onBarBack(View view)
    {
        finish();
    }
}
