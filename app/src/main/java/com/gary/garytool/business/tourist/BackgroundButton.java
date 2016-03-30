package com.gary.garytool.business.tourist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.gary.garytool.R;

/**
 * Created by Administrator on 2016/3/30.
 * @author gary guo
 */
public class BackgroundButton extends Button implements View.OnTouchListener {
    private Context mContext;
    private Drawable mDrawablePress;
    private Drawable mDrawableNormal;
    public BackgroundButton(Context context) {
        super(context);
        mContext = context;
        this.setOnTouchListener(this);
    }

    public BackgroundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        this.setOnTouchListener(this);
        setCustomAttributes(attrs);
    }

    public BackgroundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        this.setOnTouchListener(this);
        setCustomAttributes(attrs);
    }

    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs,
                R.styleable.tourist_button);
        mDrawableNormal= ta
                .getDrawable(R.styleable.tourist_button_background_normal);
         mDrawablePress= ta
                .getDrawable(R.styleable.tourist_button_background_press);
        ta.recycle();
        this.setBackground(mDrawableNormal);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // v.getBackground().setAlpha(190);
            v.setBackground(mDrawablePress);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // v.getBackground().setAlpha(255);
            v.setBackground(mDrawableNormal);
        }
        v.invalidate();
        return false;
    }
}
