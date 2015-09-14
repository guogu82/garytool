package com.gary.garytool.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gary.garytool.util.LogUtil;

/**
 * Created by Gary on 2015/9/5.
 */
public class TextViewM extends TextView {
    private static final String TAG = "TextViewM";
    private int textColori=0;//控件的文字颜色，Int
    private String textColors="";//控件的文字颜色，String
    private int textColorSelectedi=0;//控件被按下后的文字颜色，Int
    private String textColorSelecteds="";//控件被按下后的文字颜色，String
    public TextViewM(Context context) {
        this(context, null);
    }

    public TextViewM(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d(TAG, "ACTION_DOWN,getY()=" + getY());
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d(TAG,"ACTION_MOVE,getY()="+getY());
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(TAG,"ACTION_UP,getY()="+getY());
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 实现TextView的构造方法
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TextViewM(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置TextView的Touch事件
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //设置颜色变化
                setColor(event.getAction());
                //注意此处的返回值，若想要设置TextView的Click事件,则返回false
                //设置了false后，一定要设置OnClick事件，否则ACTION_UP不会发生
                return false;
            }
        });
    }

    //设置颜色变化，该方法为private,不对外公开
    private void setColor(int state) {
        try {
            //根据传过来的MotionEvent值来设置文字颜色
            if(state==MotionEvent.ACTION_DOWN)
            {
                //按下
                if(textColorSelectedi!=0)

                {
                    setTextColor(textColorSelectedi);
                }else if (!textColorSelecteds.equals(""))
                {
                    setTextColor(Color.parseColor(textColorSelecteds));
                }
            }

            if(state==MotionEvent.ACTION_UP)
            {
                if(textColori==0&&textColors.equals(""))
                {
                    setTextColor(Color.BLACK);
                }else if(textColori!=0)
                {
                    setTextColor(textColori);
                }else
                {
                    setTextColor(Color.parseColor(textColors));
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 设置文字的颜色
     * 为了不造成原setTextColor的冲突，在后面加i
     * @param color
     */
    public void setTextColori(int color)
    {
        this.textColori=color;
        setTextColor(color);
    }

    /**
     * 设置文字的颜色
     * 为了不造成原setTextColor的冲突，在后面
     * @param color
     */
    public void setTextColors(String color)
    {
        this.textColors=color;
        setTextColor(Color.parseColor(color));
    }

    /**
     * 设置文字被按下后的颜色
     * @param textColorSelectedi
     */
    public void setTextColorSelected(int textColorSelectedi)
    {
        this.textColorSelectedi=textColorSelectedi;
    }

    /**
     * 设置文字被按下后的颜色
     * @param textColorSelecteds
     */
    public void setTextColorSelected(String textColorSelecteds)
    {
        this.textColorSelecteds=textColorSelecteds;
    }
}
