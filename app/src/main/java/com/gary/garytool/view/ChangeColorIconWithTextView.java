package com.gary.garytool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.gary.garytool.R;

/**
 * TODO: document your custom view class.
 */
public class ChangeColorIconWithTextView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;

    /**
     * 颜色
     */
    private int mColor=0xff45c01a;
    /**
     * 透明度 0.0-1.0
     */
    private float mAlpha=0f;
    /**
     * 图标
     */
    private Bitmap mIconBitmap;
    /**
     * 限制绘制icon的范围
     */
    private Rect mIconRect;
    /**
     * icon底部文本
     */
    private String mText="微信";
    /**
     * 文字的大小
     */
    private int mTextSize= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,10,getResources().getDisplayMetrics());
    /**
     * 文字的画笔
     */
    private Paint mTextPaint;
    /**
     * 文字的范围
     */
    private Rect mTextBound=new Rect();
    public ChangeColorIconWithTextView(Context context) {
        super(context);
    }

    /**
     * 初始化自定义属性值
     * @param context
     * @param attrs
     */
    public ChangeColorIconWithTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取设置的图标
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.ChangeColorIconView);
        int n=a.getIndexCount();
        for(int i=0;i<n;i++)
        {
            int attr=a.getIndex(i);
            switch (attr)
            {
                case R.styleable.ChangeColorIconView_icon:
                    BitmapDrawable drawable= (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap=drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconView_color:
                    mColor=a.getColor(attr,0x45C01A);
                    break;
                case R.styleable.ChangeColorIconView_text:
                    mText=a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconView_text_size:
                    mTextSize=a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,10,getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
            a.recycle();

            //设置文字的属性
            mTextPaint=new Paint();
            mTextPaint.setTextSize(mTextSize);
            mTextPaint.setColor(0xff555555);
            //得到文字的绘制范围
            mTextPaint.getTextBounds(mText,0,mText.length(),mTextBound);
        }
    }


    public ChangeColorIconWithTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }



}
