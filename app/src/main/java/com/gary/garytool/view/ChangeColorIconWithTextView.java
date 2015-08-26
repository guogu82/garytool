package com.gary.garytool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.gary.garytool.R;



public class ChangeColorIconWithTextView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;

    /**
     * 颜色 绿色
     */
    private int mColor = 0xff45c01a;
    /**
     * 透明度 0.0-1.0
     */
    private float mAlpha = 0f;
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
    private String mText = "微信";
    /**
     * 文字的大小
     */
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());
    /**
     * 文字的画笔
     */
    private Paint mTextPaint;
    /**
     * 文字的范围
     */
    private Rect mTextBound = new Rect();

    public ChangeColorIconWithTextView(Context context) {
        super(context);
    }

    /**
     * 初始化自定义属性值
     *
     * @param context
     * @param attrs
     */
    public ChangeColorIconWithTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取设置的图标
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconView);

        //原代码里面的轮询出现了指向不明的情况
        BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(R.styleable.ChangeColorIconView_iconattr);
        mIconBitmap = drawable.getBitmap();
        mColor = a.getColor(R.styleable.ChangeColorIconView_colorattr, 0x45C01A);
        mText = a.getString(R.styleable.ChangeColorIconView_textattr);
        mTextSize  = a.getDimensionPixelSize(R.styleable.ChangeColorIconView_text_sizeattr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));

        a.recycle();

        //设置文字的属性
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff555555);
        //得到文字的绘制范围
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //得到绘制icon的宽
        int bitmapWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());
        int left = getMeasuredWidth() / 2 - bitmapWidth / 2;
        int top = getMeasuredHeight() / 2 - mTextBound.height() / 2 - bitmapWidth / 2;

        //设置icon的绘制范围
        mIconRect = new Rect(left, top, left + bitmapWidth, top + bitmapWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int alpha = (int) Math.ceil(255 * mAlpha);
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        setupTargetBitmap(alpha);
        drawSourceText(canvas, alpha);
        drawTragetText(canvas, alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    private void setupTargetBitmap(int alpha) {
        //TODO 尝试改一下画图的顺序，采用SrcIn模式 20150824 gary
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2, mIconRect.bottom + mTextBound.height(), mTextPaint);
    }

    /*
       关于绘制文本区域的计算，首先是起点x：
       mIconRect.left + mIconRect.width() / 2- mTextBound.width() / 2 有点长哈，文本mIconRect.left + mIconRect.width() / 2这个位置，
       在图标水平区域的中心点，这个应该没有疑问；图标水平区域的中点- mTextBound.width()/ 2 开始绘制文本，是不是就是居中在图标的下面；
       有人可能会问：你怎么知道文本宽度小于图标，我有5个字咋办？5个字怎么了，照样是居中显示，不信你试试~~
       */
    private void drawTragetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2, mIconRect.bottom + mTextBound.height(), mTextPaint);
    }

    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

}
