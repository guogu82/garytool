package com.gary.garytool.view.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gary.garytool.R;

import java.util.List;

/**
 * Created by Gary on 2016/3/19.
 */
public class ViewPagerIndicator extends LinearLayout {
    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private static final float RADIO_TRIANGLE_WIDTH=1/6F;
    //三角形底边的最大宽度
    private  final int DIMENSION_TRIANGLE_WIDTH_MAX= (int) (getScreenWidth()/3*RADIO_TRIANGLE_WIDTH);

    private  static final int COLOR_TEXT_NORMAL=0x77ffffff;
    private  static final int COLOR_TEXT_HIGHLIGHT =0xffffffff;
    private int mInitTranslationX;
    private int mTranslationX;

    private int mTabVisibleCount;
    private static final int COUNT_DEFAULT_TAB=4;
    private int screenWidth;
    private List<String> mTitles;

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取可见TAB的数量
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount=a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count,COUNT_DEFAULT_TAB);
        if(mTabVisibleCount<0)
        {
            mTabVisibleCount=COUNT_DEFAULT_TAB;
        }
        a.recycle();
        //初始化画笔
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ffffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 2);
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth= (int) (w/mTabVisibleCount*RADIO_TRIANGLE_WIDTH);
        mTriangleWidth=Math.min(mTriangleWidth,DIMENSION_TRIANGLE_WIDTH_MAX);
        mInitTranslationX=w/mTabVisibleCount/2-mTriangleWidth/2;

        initTriangle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int cCount=getChildCount();
        if(cCount==0)
            return;

        for(int i=0;i<cCount;i++)
        {
            View view=getChildAt(i);
            LinearLayout.LayoutParams lp= (LayoutParams) view.getLayoutParams();
            lp.weight=0;
            lp.width=getScreenWidth()/mTabVisibleCount;
            view.setLayoutParams(lp);

        }
        setItemClickEvent();

    }
    public int getScreenWidth() {
        WindowManager wm= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {

        mTriangleHeight=mTriangleWidth/2;
        mPath=new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    /**
     * 指示器跟随手指进行滚动
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        int tabWidth=getWidth()/mTabVisibleCount;
        //指示器三角形移动的参照物
        mTranslationX= (int) (tabWidth*(offset+position));
        //容器移动，在tab处于移动至最后一个时
        if(position>=(mTabVisibleCount-2)&&offset>0&&getChildCount()>mTabVisibleCount)
        {
          if(mTabVisibleCount!=1) {
              this.scrollTo((int) ((position - (mTabVisibleCount - 2)) * tabWidth + tabWidth * offset), 0);
          }
            else
          {
              this.scrollTo((int) (position*tabWidth+tabWidth*offset),0);
          }
        }
        invalidate();
    }

    public  void setTabItemTitles(List<String> titles)
    {
        if(titles!=null&&titles.size()>0)
        {
            this.removeAllViews();
            mTitles=titles;
            for(String title:mTitles)
            {
                addView(generateTextView(title));
            }
            setItemClickEvent();
        }
    }



    /**
     * 设置可见的Tab数量
     * @param count
     */
    public void setVisibleCount(int count)
    {
        mTabVisibleCount=count;
    }

    /**
     * 根据title创建Tab
     * @param title
     * @return
     */
    private View generateTextView(String title) {
        TextView tv=new TextView(getContext());
        LinearLayout.LayoutParams lp =new ActionMenuView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width=getScreenWidth()/mTabVisibleCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setLayoutParams(lp);
        return tv;
    }

    private ViewPager mViewPager;
    public interface PageOnchangeListener
    {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) ;
        public void onPageSelected(int position);
        public void onPageScrollStateChanged(int state) ;
    }
    private PageOnchangeListener mListener;
    public void setOnPageOnchangeListener(PageOnchangeListener listener)
    {
        this.mListener=listener;
    }
    /**
     *设置关联的ViewPager
     * @param viewPager
     * @param pos
     */
    public void setViewPager(ViewPager viewPager,int pos)
    {
        mViewPager=viewPager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position,positionOffset);
                if(mListener!=null)
                {
                    mListener.onPageScrolled(position, positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {

                if(mListener!=null)
                {
                    mListener.onPageSelected(position);
                }
                highLightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(mListener!=null)
                {
                    mListener.onPageScrollStateChanged(state);
                }
            }
        });
        mViewPager.setCurrentItem(pos);
        highLightTextView(pos);
    }

    /**
     * c重置Tab文本颜色
     */
    private void  resetTextViewColor()
    {
        for(int i=0;i<getChildCount();i++)
        {
            View view=getChildAt(i);
            if(view instanceof TextView)
            {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }
    /**
     * 高亮某个Tab的文本
     * @param pos
     */
    private void highLightTextView(int pos)
    {
        resetTextViewColor();
        View view=getChildAt(pos);
        if(view instanceof TextView)
        {
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHT);
        }
    }

    /**
     * 设置Tab的点击事件
     */
    private void setItemClickEvent()
    {
        int cCount=getChildCount();
        for(int i=0;i<cCount;i++)
        {
            View view=getChildAt(i);
            final int j = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

}
