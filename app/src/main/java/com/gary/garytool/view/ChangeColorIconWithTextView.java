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
//下面是评论对这个功能实现的描述
//监听vp滑动过程，通过属性动画更改图标透明度，不是可以嘛
//解压过微信看他里边应该是2张图做的，就是个imageview就行，，背景设置为灰色的图，然后src就有色的图片，滑动的时候根据偏移量盖面imageview的透明度就可以实现
//内存方面注意到画图片渐变的地方感觉费内存了，每次都创建新图，而且刷新一下就创建好多张图
//回复mayuqing：内存倒是不费，毕竟执行完方法也就回收了，就是频繁创建回收了，你可以稍微优化下代码。
//我测试了 用两张图片，分别是未取得焦点和去的焦点之后的颜色不同但是大小相同的两张图片，设置透明度变化，比如设置第一个图的透明度为100第二张图的透明度是255-100，绘制在相同位置自然叠加就行，而且微信用的是继承的ImageVIew和一个textView实现的
//嗯，不动态去生成各种颜色图标，两张图最简单了~~
//博主您好 我想到了一个更简单的办法 在微信里面 这个其实是由两张图片实现的 一张是纯绿色的 一张是白色的，于是我就想到 用FrameLayout 白色的在下面 绿色的在上面 然后滑动的时候 根据监听器的positionOffset设置view 的alpha ，变可以达到同样的效果 且代码还很简洁


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
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 + mTextBound.width() / 2, mIconRect.bottom + mTextBound.height(), mTextPaint);
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
