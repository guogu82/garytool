package com.gary.garytool.business.guessmusic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gary.garytool.R;
import com.gary.garytool.util.LogUtil;
import com.gary.garytool.util.Util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/3/25.
 * @author gary guo
 */
public class GuessMusicMainActivity extends Activity implements IWordButtonClickListener{

    public static final String TAG="GuessMusicMainActivity";

    /** 答案状态 --正确*/
    public static final int STATUS_ANSWER_RIGHT=1;
    /** 答案状态 --错误*/
    public static final int STATUS_ANSWER_WRONG=2;
    /** 答案状态 --不完整*/
    public static final int STATUS_ANSWER_LACK=3;
    //闪烁次数
    public static final int SPARD_TIMES=6;

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

    //过关界面
    private View mPassView;

    //是否处于播放状态
    private boolean mIsRunning=false;

    //文字框容器
    private ArrayList<WordButton> mAllWords;
    private ArrayList<WordButton> mSelectWords;
    private MyGridView mMyGridView;

    //已现在文字框UI容器
    private LinearLayout mViewWordsContainer;

    //当前的歌曲
    private Song mCurrentSong;
    //当前关的索引
    private int mCurrentStageIndex=-1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guess_music);

        //初始化控件
        mViewPan= (ImageView) findViewById(R.id.iv1);
        mViewPanBar= (ImageView) findViewById(R.id.iv2);

        mMyGridView= (MyGridView) findViewById(R.id.gridview);

        mMyGridView.registOnWordButtonClick(this);

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
    @Override
    public void onWordButtonClick(WordButton wordButton) {
        //填充文字到文本框
        setSelectWord(wordButton);

        //监测答案是否正确
        int checkResult=checkTheAnswer();
        //根据答案结果显示动态提示
        if(checkResult==STATUS_ANSWER_RIGHT)
        {
            // 过关并获得奖励
            handlePassEvent();
        }else  if(checkResult==STATUS_ANSWER_WRONG)
        {
            //闪烁文字，提示用户
            sparkTheWords();
        }else if(checkResult==STATUS_ANSWER_LACK)
        {
            //就是一般正常状态，恢复状态。设置文字为白色
            for(int i=0;i<mSelectWords.size();i++)
            {
                mSelectWords.get(i).getViewButton().setTextColor(Color.WHITE);
            }
        }
    }

    /**
     * 处理过关界面及事件
     */
    private void handlePassEvent()
    {
        mPassView=(LinearLayout)findViewById(R.id.pass_view);
        mPassView.setVisibility(View.VISIBLE);
    }
    /**
     * 清除已选框的可见
     * @param wordButton
     */
    private  void clearTheAnswer(WordButton wordButton)
    {
        wordButton.getViewButton().setText("");
        wordButton.setWordString("");
        wordButton.setIsVisible(false);
        //恢复待选框的可见
        setButtonVisible(mAllWords.get(wordButton.getIndex()), View.VISIBLE);
    }
    /**
     * 设置答案
     * @param wordButton
     */
    private void setSelectWord(WordButton wordButton)
    {
        for(int i=0;i<mSelectWords.size();i++)
        {
            if(mSelectWords.get(i).getWordString().length()==0)
            {
                //设置答案文字框内容及可见性
                mSelectWords.get(i).getViewButton().setText(wordButton.getWordString());
                mSelectWords.get(i).setIsVisible(true);
                mSelectWords.get(i).setWordString(wordButton.getWordString());
                mSelectWords.get(i).setIndex(wordButton.getIndex());

                LogUtil.d(TAG,mSelectWords.get(i).getIndex()+"");

                //设置待选框的可见性
                setButtonVisible(wordButton, View.INVISIBLE);
                return;
            }
        }
    }

    /**
     * 设置待选文字框是否可见
     * @param button
     * @param visibility
     */
    private void setButtonVisible(WordButton button,int visibility)
    {
        button.getViewButton().setVisibility(visibility);
        button.setIsVisible(visibility == View.VISIBLE ? true : false);

        LogUtil.d(TAG,button.getIsVisible()+"");
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

    /**
     * 读取当前关歌曲信息
     * @param stageIndex
     * @return
     */
    private Song loadStageSongInfo(int stageIndex)
    {
        Song song=new Song();
        String[] stage=Const.SONG_INFO[stageIndex];
        song.setSongFileName(stage[Const.INDEX_FILE_NAME]);
        song.setSongName(stage[Const.INDEX_SONG_NAME]);
        return song;
    }

    /**
     * 初始化当前关卡的数据
     */
    private void initCurrentStateData()
    {
        //读取当前关的歌曲信息
        mCurrentSong=loadStageSongInfo(++mCurrentStageIndex);

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
        String[] words=generateWords();

        ArrayList<WordButton> data=new ArrayList<WordButton>();
        for(int i=0;i<MyGridView.COUNT_WORDS;i++)
        {
            WordButton button=new WordButton();
            button.setWordString(words[i]);
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
        for(int i=0;i<mCurrentSong.getNameLength();i++)
        {
            View view= Util.getView(GuessMusicMainActivity.this,R.layout.guess_music_sel_gridview_item);
            final WordButton holder=new WordButton();
            Button button=(Button) view.findViewById(R.id.bt_item);
            holder.setViewButton(button);
            button.setTextColor(Color.WHITE);
            button.setText("");
            holder.setIsVisible(false);
            button.setBackgroundResource(R.drawable.guess_music_game_wordblank);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTheAnswer(holder);
                }
            });
            data.add(holder);
        }
        return data;
    }

    /**
     * 生成所有的待选文字
     * @return
     */
    private String[] generateWords()
    {
        String[] words=new String[MyGridView.COUNT_WORDS];
        //存入歌名
        for(int i=0;i<mCurrentSong.getNameLength();i++)
        {
            words[i]=mCurrentSong.getNameCharacters()[i]+"";
        }
        //获取随机文字并存入数组
        for (int i=mCurrentSong.getNameLength();i<MyGridView.COUNT_WORDS;i++)
        {
            words[i]=getRandomChar()+"";
        }

        //打乱文字顺序,首先从所有元素中随机选取一个与最后一个元素进行交换，
        //然后在第二个周选择一个元素与倒数第二个元素交换，直到第一个元素。
        //这样能够确保每个元素在每个位置的概率都是1/n
        Random random=new Random();
        for(int i=MyGridView.COUNT_WORDS-1;i>=0;i--)
        {
            int index=random.nextInt(i + 1);
            String buf=words[index];
            words[index]=words[i];
            words[i]=buf;
        }
        return words;
    }
    /**
     * 生成随机汉字
     * @return
     */
    private char getRandomChar()
    {
        String str="";
        int hightPos;
        int lowPos;
        Random random=new Random();
        hightPos=(176+Math.abs(random.nextInt(39)));
        lowPos=(161+Math.abs(random.nextInt(93)));
        //TODO:byte数组低位对应数字的高位吗？
        byte[] b=new byte[2];
        b[0]= Integer.valueOf(hightPos).byteValue();
        b[1]=Integer.valueOf(lowPos).byteValue();
        try {
            str=new String(b,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str.charAt(0);
    }

    /**
     * 检查答案
     * @return
     */
    private int checkTheAnswer()
    {
        //判断长度
        for(int i=0;i<mSelectWords.size();i++)
        {
            //如果有空的，说明答案还没完整
            if(mSelectWords.get(i).getWordString().length()==0)
            {
                return STATUS_ANSWER_LACK;
            }
        }

        //检查答案是否正确
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<mSelectWords.size();i++)
        {
            sb.append(mSelectWords.get(i).getWordString());
        }
     return sb.toString().equals(mCurrentSong.getSongName())?STATUS_ANSWER_RIGHT:STATUS_ANSWER_WRONG;
    }

    /**
     * 文字闪烁
     */
    private void sparkTheWords()
    {
        //定时器相关
        TimerTask timerTask=new TimerTask() {

            boolean mChange=false;
            int mSparkTimes=0;
            @Override
            public void run() {
                //在ui线程刷新界面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //闪烁的次数
                        if(++mSparkTimes>SPARD_TIMES)
                        {
                            return;
                        }

                        //执行闪烁逻辑，交替显示红色和白色文字
                        for(int i=0;i<mSelectWords.size();i++)
                        {
                            mSelectWords.get(i).getViewButton().setTextColor(mChange?Color.RED:Color.WHITE);
                        }
                        mChange=!mChange;
                    }
                });
            }
        };
        Timer timer=new Timer();
        timer.schedule(timerTask,1,150);
    }

}
