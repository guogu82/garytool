package com.gary.garytool.business.guessmusic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gary.garytool.R;
import com.gary.garytool.util.Util;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25.
 */
public class GuessMusicMainActivity extends Activity {

    //唱片相关动画
    private Animation mPanAnim;
    private LinearInterpolator mPanLin;
    private Animation mBarInAnim;
    private LinearInterpolator mBarInLin;
    private Animation mBarOutAnim;
    private LinearInterpolator mBarOutLin;
    private ImageView mViewPan;
    private ImageView mViewPanBar;

    //Play 按键事件
    private ImageButton mBtPlayStart;

    //是否处于播放状态
    private boolean mIsRunning=false;

    //文字框容器
    private ArrayList<WordButton> mAllWords;
    private ArrayList<WordButton> mSelectWords;
    private MyGridView mMyGridView;

    //已现在文字框UI容器
    private LinearLayout mViewWordsContainer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guess_music);

        //初始化控件
        mViewPan= (ImageView) findViewById(R.id.iv1);
        mViewPanBar= (ImageView) findViewById(R.id.iv2);

        mMyGridView= (MyGridView) findViewById(R.id.gridview);

        mViewWordsContainer= (LinearLayout) findViewById(R.id.word_select_container);

        //初始化唱片动画
        mPanAnim= AnimationUtils.loadAnimation(this,R.anim.guess_music_rotate);
        mPanLin=new LinearInterpolator();
        mPanAnim.setInterpolator(mPanLin);
        mPanAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //盘片播完，拨杆回去
                mViewPanBar.startAnimation(mBarOutAnim);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBarInAnim= AnimationUtils.loadAnimation(this,R.anim.guess_music_rotate_45);
        mBarInLin=new LinearInterpolator();
        mBarInAnim.setFillAfter(true);
        mBarInAnim.setInterpolator(mBarInLin);
        mBarInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //启动盘片动画
                mViewPan.startAnimation(mPanAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBarOutAnim= AnimationUtils.loadAnimation(this,R.anim.guess_music_rotate_d_45);
        mBarOutLin=new LinearInterpolator();
        mBarOutAnim.setFillAfter(true);
        mBarOutAnim.setInterpolator(mBarOutLin);
        mBarOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //恢复可以播放状态
                mIsRunning = false;
                mBtPlayStart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        mBtPlayStart= (ImageButton) findViewById(R.id.bt_play_start);
        mBtPlayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              handlePlayButton();
            }
        });

        //初始化游戏数据
        initCurrentStateData();
    }

    /**
     * 盘片按钮的点击事件处理
     */
    private void handlePlayButton()
    {
        if(mViewPan!=null) {
            if (!mIsRunning) {
                mIsRunning = true;
                mViewPanBar.startAnimation(mBarInAnim);
                mBtPlayStart.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onPause() {
        mViewPan.clearAnimation();
        super.onPause();
    }

    private void initCurrentStateData()
    {
        //初始化已选择文字框
        mSelectWords= initWordSelect();

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(140,140);
        for(int i=0;i<mSelectWords.size();i++)
        {
            mViewWordsContainer.addView(mSelectWords.get(i).getViewButton(),params);
        }
        //获得数据
        mAllWords=initAllWord();
        //更新数据 MyGridView
        mMyGridView.updateData(mAllWords);
    }

    /**
     * 初始化待选文字框
     * @return
     */
    private ArrayList<WordButton> initAllWord()
    {
        //获得所有待选文字
        ArrayList<WordButton> data=new ArrayList<WordButton>();
        for(int i=0;i<MyGridView.COUNT_WORDS;i++)
        {
            WordButton button=new WordButton();
            button.setWordString("8");
           data.add(button);
        }
        return data;
    }

    /**
     * 初始化已选择文字框
     * @return
     */
    private ArrayList<WordButton> initWordSelect()
    {
        ArrayList<WordButton> data=new ArrayList<WordButton>();
        for(int i=0;i<4;i++)
        {
            View view= Util.getView(GuessMusicMainActivity.this,R.layout.guess_music_sel_gridview_item);
            WordButton holder=new WordButton();
            Button button=(Button) view.findViewById(R.id.bt_item);
            holder.setViewButton(button);
            button.setTextColor(Color.WHITE);
            button.setText("");
            holder.setIsVisible(false);
            button.setBackgroundResource(R.drawable.guess_music_game_wordblank);
            data.add(holder);
        }
        return data;
    }
}
