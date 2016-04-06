package com.gary.garytool.business.tourist;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.util.Util;
import com.gary.garytool.view.viewpager.PhotoCarouselWithViewPager;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/1.
 */
public class TouristScenicDetailActivity extends Activity{

    private ScenicInfo mScenicInfo;
    private TextView mTvTopBarTitle;
    private TextView mTvScenicSpotIntroduction;
    private PhotoCarouselWithViewPager mTouristPhotoCarouse;
    private ArrayList<String> mImageUrl = new ArrayList<String>(); // 存储所有该景点的所有图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_scenic_detail_activity);


        if(ScenicUtil.sCurrentSpotDetailScenicInfo!=null)
        {
            mScenicInfo=ScenicUtil.sCurrentSpotDetailScenicInfo;
        }

        initView();
        initData();
    }

    private void initData() {
        File[] file = { new File(Util.getSDPath() + "/touristguide/qinghuiyuan/image/") };
        searchFile(file);
        mTouristPhotoCarouse.setImageResources(mImageUrl, new PhotoCarouselWithViewPager.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(imageURL));
            }

            @Override
            public void onImageClick(int position, View imageView) {

            }
        });
    }

    private void searchFile(File[] files) {
        for (File file : files) {
            if (file.isDirectory())// 若为目录则递归查找
            {
                searchFile(file.listFiles());
            } else if (file.isFile()) {
                String path = file.getPath();
                if (path.endsWith(".jpg"))// 查找指定扩展名的文件
                {
                    mImageUrl.add(path);
                }
            }
        }
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
        mTouristPhotoCarouse = (PhotoCarouselWithViewPager) findViewById(R.id.tourist_photo_carouse);

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
