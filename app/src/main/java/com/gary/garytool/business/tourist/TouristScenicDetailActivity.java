package com.gary.garytool.business.tourist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
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

    private static final String FILE_PATH_VOICE= Util.getSDPath()+"/touristguide/qinghuiyuan/audio/1.mp3";
    private static final String FILE_PATH_IMAGE= Util.getSDPath() + "/touristguide/qinghuiyuan/image/";
    private static final String FILE_PATH_TEXT= Util.getSDPath()+ "/touristguide/qinghuiyuan/text/text.txt";

    private ScenicInfo mScenicInfo;
    private TextView mTvTopBarTitle;
    private TextView mTvScenicSpotIntroduction;
    private PhotoCarouselWithViewPager mTouristPhotoCarouse;
    private ArrayList<String> mImageUrl = new ArrayList<String>(); // 存储所有该景点的所有图片

    private Button mBtVoice;
    private Button mBtReadSetting;
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
        initEvent();
    }

    private void initView() {
        mTvTopBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        mTvTopBarTitle.setText(mScenicInfo.getName());

        mTvScenicSpotIntroduction = (TextView) findViewById(R.id.tv_scenic_spot_introduction);
        String filePath=Util.getSDPath()+ "/touristguide/qinghuiyuan/text/" + mScenicInfo.getName() + "/"
                + mScenicInfo.getName() + "简介.txt";
        String text = ScenicUtil.readFileContent(filePath);
        mTvScenicSpotIntroduction.setText(text == "" ? "暂无简介" : text);
        mTouristPhotoCarouse = (PhotoCarouselWithViewPager) findViewById(R.id.tourist_photo_carouse);

        mBtVoice= (Button) findViewById(R.id.bt_voice);
        mBtReadSetting= (Button) findViewById(R.id.bt_read_setting);
        initReadingSettingPopupWindow();
    }


    private void initReadingSettingPopupWindow() {
        mPopupView =Util.getView(TouristScenicDetailActivity.this, R.layout.tourist_read_setting_popupwindow);
        mPopupView.setBackgroundColor(Color.WHITE);
        mPopupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED); // 必须加上这一句，否则view的高度为0
        mPopupWindow = new PopupWindow(mPopupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(
                getResources(), (Bitmap) null));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mBtReadSetting.setBackgroundResource(R.drawable.tourist_read_setting);
            }
        });
        setScreenLuminance();
        initReadTextButton();
        setReadTextFontSize(ScenicUtil.sCurrentReadingSetting);

    }
    Button btReadSmall;
    Button btReadMiddle;
    Button btReadBig;
    Button btReadHuge;
    private void initReadTextButton() {
        btReadSmall = (Button) mPopupView
                .findViewById(R.id.bt_read_small);
        btReadMiddle = (Button) mPopupView
                .findViewById(R.id.bt_read_middle);
        btReadBig = (Button) mPopupView
                .findViewById(R.id.bt_read_big);
        btReadHuge = (Button) mPopupView
                .findViewById(R.id.bt_read_huge);
        btReadSmall.setOnClickListener(fontSizeClickListener);
        btReadMiddle.setOnClickListener(fontSizeClickListener);
        btReadBig.setOnClickListener(fontSizeClickListener);
        btReadHuge.setOnClickListener(fontSizeClickListener);
    }
    private View.OnClickListener fontSizeClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            resetReadButtonBackgroud(v);
            setReadTextFontSize(v.getId());
            ScenicUtil.sCurrentReadingSetting=v.getId();
        }
    };

    private void setReadTextFontSize(int viewId) {
        switch (viewId) {
            case R.id.bt_read_huge:
                mTvScenicSpotIntroduction.setTextSize(ScenicUtil.SETTING_READ_HUGE);
                btReadHuge.setBackgroundResource(R.drawable.tourist_reading_button_right_press);
                break;
            case  R.id.bt_read_big:
                mTvScenicSpotIntroduction.setTextSize(ScenicUtil.SETTING_READ_BIG);
                btReadBig.setBackgroundResource(R.drawable.tourist_reading_button_center_press);
                break;
            case  R.id.bt_read_middle:
                mTvScenicSpotIntroduction.setTextSize(ScenicUtil.SETTING_READ_MIDDLE);
                btReadMiddle.setBackgroundResource(R.drawable.tourist_reading_button_center_press);
                break;
            case  R.id.bt_read_small:
                mTvScenicSpotIntroduction.setTextSize(ScenicUtil.SETTING_READ_SMALL);
                btReadSmall.setBackgroundResource(R.drawable.tourist_reading_button_left_press);
                break;
            default:
                break;
        }
    }

    private void resetReadButtonBackgroud(View v) {
         btReadSmall.setBackgroundResource(R.drawable.tourist_reading_button_left);
         btReadMiddle.setBackgroundResource(R.drawable.tourist_reading_button_center);
         btReadBig.setBackgroundResource(R.drawable.tourist_reading_button_center);
         btReadHuge.setBackgroundResource(R.drawable.tourist_reading_button_right);
    }

    private SeekBar mReadingSeekBar;
    private void setScreenLuminance() {
        mReadingSeekBar = (SeekBar) mPopupView.findViewById(R.id.sb_luminance);
        mReadingSeekBar.setMax(255);
        int a = ScenicUtil.getScreenBrightness(this);
        mReadingSeekBar.setProgress(a);
        mReadingSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

    }

    private MediaPlayer mMediaPlayer = new MediaPlayer(); // 音频播放器
    private boolean mIsPlayingVoice;
    private PopupWindow mPopupWindow;
    private View mPopupView;
    private void initEvent() {
        mBtVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = Util.getSDPath()+ "/touristguide/qinghuiyuan/audio/"
                        + mScenicInfo.getName() + ".mp3";
                File voiceFile = new File(filePath);
                //File voiceFile = new File(FILE_PATH_VOICE);
                if (!voiceFile.exists()) {
                    return;
                }
                try {
                    mMediaPlayer.setDataSource(voiceFile.getAbsolutePath());
                    mMediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Button b = (Button) v;
                mIsPlayingVoice = !mIsPlayingVoice;
                if (mIsPlayingVoice) {
                    b.setBackgroundResource(R.drawable.tourist_audio_play);
                    ;
                    mMediaPlayer.start();
                } else {
                    b.setBackgroundResource(R.drawable.tourist_voice_pause);
                    mMediaPlayer.pause();
                }
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mIsPlayingVoice = false;
                mBtVoice.setBackgroundResource(R.drawable.tourist_voice_pause);
            }
        });

        mBtReadSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(ScenicSpotVideoActivity.this,popupView.getMeasuredHeight()+"",Toast.LENGTH_SHORT).show();
                int[] location=new int[2];
                v.getLocationOnScreen(location);
                Button b = (Button) v;
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0],  location[1]-mPopupView.getMeasuredHeight()-10);
                b.setBackgroundResource(R.drawable.tourist_read_setting_press);
            }
        });
    }

    private void initData() {
        mIsPlayingVoice=false;

        String filePath=Util.getSDPath()  + "/touristguide/qinghuiyuan/image/" + mScenicInfo.getName();
        File[] file = { new File(filePath) };
        //File[] file = { new File(FILE_PATH_IMAGE) };
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




    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            if (progress < 10) {
            } else {
                ScenicUtil.setBrightness(TouristScenicDetailActivity.this, progress);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        mIsPlayingVoice=false;
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
