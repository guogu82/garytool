package com.gary.garytool.business.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gary.garytool.R;
import com.gary.garytool.util.LogUtil;
import com.gary.garytool.util.Util;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 * @author gray guo
 */
public class PuzzleLayout extends RelativeLayout implements View.OnClickListener {
    private static final String TAG="PuzzleLayout";
    private int mColumn=3;
    /**
     *  容器的内边距
     */
    private int mPadding;

    /**
     * 每张小图之间的距离(横，纵)
     */
   private int mMargin=3;
    private ImageView[] mPuzzleItems;
    private int mItemWidth;
    /**
     * 游戏的图片
     */
    private Bitmap mBitmap;
    private List<ImagePiece> mItemBitmaps;

    private boolean once;

    /**
     * 游戏面板的宽度
     */
    private int mWidth;


    private  boolean isGameSuccess;
    private boolean isGameOver;


    public interface PuzzleListener
    {
        void nextLevel(int nextLevel);
        void timeChanged(int currentTime);
        void gameOver();
        void newPicture(int level);
    }

    private PuzzleListener mListener;
    public void setOnPuzzleListener(PuzzleListener listener)
    {
        mListener=listener;
    }

    private int mLevel =1;

    private static final int TIME_CHANGED=0x110;
    private static final int NEXT_LEVEL=0x111;
    private static final int NEW_PICTURE=0x112;



    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what)
            {
                case TIME_CHANGED:
                {
                    if(isGameSuccess||isGameOver||isPause)
                        return;
                    if(mListener!=null)
                    {
                        mListener.timeChanged(mTime);
                    }

                    if(mTime==0)
                    {
                        isGameOver=true;
                        mListener.gameOver();
                        return;
                    }
                    mTime--;
                    mHandler.sendEmptyMessageDelayed(TIME_CHANGED,1000);
                }
                    break;
                case NEXT_LEVEL:
                {
                    mLevel = mLevel +1;
                    if(mListener!=null) {
                        mListener.nextLevel(mLevel);
                    }
                    else
                    {
                        nextLevel();
                    }
                }
                    break;
                case NEW_PICTURE:
                {
                    mBitmap=null;
                    mLevel = 1;
                    mColumn=2;
                    if(mListener!=null) {
                        mListener.newPicture(mLevel);
                    }
                }
                break;
            }
        }
    };

    public void restart()
    {
        isGameOver=false;
        mColumn--;
        nextLevel();
    }

    public void start()
    {
        isGameSuccess = true;
        mHandler.removeMessages(TIME_CHANGED);
        mHandler.sendEmptyMessage(NEW_PICTURE);
    }

    private boolean isPause;
    public void pause()
    {
        isPause=true;
        mHandler.removeMessages(TIME_CHANGED);
    }


    public void resume()
    {
        if(isPause)
        {
            isPause=false;
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    public void nextLevel()
    {
        this.removeAllViews();
        mAnimLayout=null;
        mColumn++;
        isGameSuccess=false;
        initBitmap();
        initItem();
        checkTimeEnable();
    }


    private boolean isTimeEnabled=false;
    private int mTime;

    /**
     * 设置是否开启时间计时
     * @param isTimeEnabled
     */
    public void setTimeEnabled(boolean isTimeEnabled)
    {
        this.isTimeEnabled=isTimeEnabled;
    }

    public PuzzleLayout(Context context) {
        this(context, null);
    }

    public PuzzleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mMargin= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics());
        mPadding=min(getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //取宽和高中的最小值
        mWidth= Math.min(getMeasuredHeight(), getMeasuredWidth());
        if (!once)
        {
            //进行切图，以及排序
            initBitmap();

            //设置ImageView（item）的宽高等属性
            initItem();

            //判断是否开启时间
            checkTimeEnable();
            once=true;
        }
        setMeasuredDimension(mWidth, mWidth);
    }

    private void checkTimeEnable() {
        if(isTimeEnabled)
        {
            //根据当前等级设置时间
            countTimeBaseLevel();

            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    private void countTimeBaseLevel() {
        //mTime= 10;
        mTime= (int) (Math.pow(2, mLevel)*60);
    }

    /**
     * 进行切图，以及排序
     * 利用Comparator 进行乱序排列
     */
    private void initBitmap() {

        if(mBitmap==null)
        {
            mBitmap= PuzzleUtil.getPuzzleImage(this.getContext());
        }
        mItemBitmaps= PuzzleUtil.splitImage(mBitmap, mColumn);
        Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece a, ImagePiece b) {
                return Math.random() > 0.5 ? 1 : -1;
            }
        });
    }




    /**
     * 设置ImageView（item）的宽高等属性
     */
    private void initItem() {
        //计算每个item的宽度,界面宽度减去两边的padding减去item之间的空隙
        mItemWidth=(mWidth-mPadding*2-mMargin*(mColumn-1))/mColumn;
        mPuzzleItems=new ImageView[mColumn*mColumn];

        //生成我们的Item，设置Rule
        for (int i=0;i<mPuzzleItems.length;i++)
        {
            ImageView item=new ImageView(getContext());
            item.setOnClickListener(this);
            item.setImageBitmap(mItemBitmaps.get(i).getBitmap());
            item.setId(i + 1);
            item.setTag(i + "_" + mItemBitmaps.get(i).getIndex());
            mPuzzleItems[i] =item;
            LayoutParams lp=new LayoutParams(mItemWidth,mItemWidth);

            //设置item间横向间隙

            //不是第一列 ，通过leftMargin
            if(i%mColumn!=0)
            {
                lp.leftMargin=mMargin;
                lp.addRule(RelativeLayout.RIGHT_OF,mPuzzleItems[i-1].getId());
            }
            //不是第一行 ，通过topMargin
            if(i+1>mColumn)
            {
                lp.topMargin=mMargin;
                lp.addRule(RelativeLayout.BELOW,mPuzzleItems[i-mColumn].getId());
            }

            addView(item,lp);

        }
    }


    /**
     * 获取多个参数的最小值
     * @return
     */
    private int min(int...params) {
        int min =params[0];
        for (int param:params)
        {
            if(param<min)
                min=param;
        }
        return min;
    }


    private ImageView mFirst;
    private ImageView mSecond;
    @Override
    public void onClick(View v) {

        if(isAniming)
            return;

        //两次点击同一个view，取消选中
        if(mFirst==v)
        {
            mFirst.setColorFilter(null);
            mFirst=null;
            return;
        }

        if(mFirst==null)
        {
            mFirst= (ImageView) v;
            mFirst.setColorFilter(Color.parseColor("#55ff0000"));

        }else
        {
            mSecond= (ImageView) v;
            exchangeView();
        }

    }

    /**
     * 动画层
     */
    private RelativeLayout mAnimLayout;
    private boolean isAniming;

    /**
     * 交换我们的item
     */
    private void exchangeView() {
        mFirst.setColorFilter(null);

        //构造我们的动画层
        setUpAnimLayout();
        ImageView first=new ImageView(getContext());
       final Bitmap firstBitmap=mItemBitmaps.get(getImageIdByTag((String) mFirst.getTag())).getBitmap();
        first.setImageBitmap(firstBitmap);
        LayoutParams lp=new LayoutParams(mItemWidth,mItemWidth);
        lp.leftMargin=mFirst.getLeft()-mPadding;
        lp.topMargin=mFirst.getTop()-mPadding;
        first.setLayoutParams(lp);
        mAnimLayout.addView(first);

        ImageView second=new ImageView(getContext());
        final Bitmap secondBitmap=mItemBitmaps.get(getImageIdByTag((String) mSecond.getTag())).getBitmap();
        second.setImageBitmap(secondBitmap);
        LayoutParams lp2=new LayoutParams(mItemWidth,mItemWidth);
        lp2.leftMargin=mSecond.getLeft()-mPadding;
        lp2.topMargin=mSecond.getTop()-mPadding;
        second.setLayoutParams(lp2);
        mAnimLayout.addView(second);


        //设置动画
        TranslateAnimation anim=new TranslateAnimation(0,mSecond.getLeft()-mFirst.getLeft(),0,mSecond.getTop()-mFirst.getTop());
        anim.setDuration(300);
        anim.setFillAfter(true);
        first.startAnimation(anim);

        TranslateAnimation anim2=new TranslateAnimation(0,-mSecond.getLeft()+mFirst.getLeft(),0,-mSecond.getTop()+mFirst.getTop());
        anim2.setDuration(300);
        anim2.setFillAfter(true);
        second.startAnimation(anim2);

        //监听动画
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFirst.setVisibility(View.INVISIBLE);
                mSecond.setVisibility(View.INVISIBLE);
                isAniming=true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                String firstTag= (String) mFirst.getTag();
                String secondTag= (String) mSecond.getTag();
                mFirst.setImageBitmap(secondBitmap);
                mSecond.setImageBitmap(firstBitmap);

                mFirst.setTag(secondTag);
                mSecond.setTag(firstTag);

                mFirst.setVisibility(View.VISIBLE);
                mSecond.setVisibility(View.VISIBLE);
                mFirst=mSecond=null;

                mAnimLayout.removeAllViews();
                isAniming=false;

                checkSuccess();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




    }

    /**
     * 判断用户游戏是否成功
     */
    private void checkSuccess() {

        boolean isSuccess=true;
        for(int i=0;i<mPuzzleItems.length;i++ )
        {
            ImageView imageView=mPuzzleItems[i];
            if(getImageIndexByTag((String) imageView.getTag())!=i)
            {
                isSuccess=false;
            }
        }

        if(isSuccess)
        {
            isGameSuccess=true;
            mHandler.removeMessages(TIME_CHANGED);
            mHandler.sendEmptyMessage(NEXT_LEVEL);
        }
    }

    /**
     * 根据tag获得id
     * @param tag
     * @return
     */
    public int getImageIdByTag(String tag)
    {
        String[] split = tag.split("_");
        return Integer.parseInt(split[0]);
    }

    /**
     *
     * @param tag
     * @return
     */
    public int getImageIndexByTag(String tag)
    {
        String[] split = tag.split("_");
        return Integer.parseInt(split[1]);
    }

    /**
     *构造我们的动画层
     */
    private void setUpAnimLayout() {
        if(mAnimLayout==null)
        {
            mAnimLayout=new RelativeLayout(getContext());
            addView(mAnimLayout);
        }
    }
}
