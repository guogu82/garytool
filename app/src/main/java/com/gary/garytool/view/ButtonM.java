package com.gary.garytool.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * 重写Button,自定义Button样式
 * Created by Gary on 2015/9/2.
 * http://www.cnblogs.com/landptf/p/4562203.html
 */
public class ButtonM extends Button {

    private GradientDrawable gradientDrawable;//控件的样式
    private String backColors="";//背景色  String
    private int backColor=0;//背景色 int
    private String backColorSelecteds="";//按下后的背景色 String
    private int backColorSelected=0;//按下后的的背景色 int
    private int backgroundImage=0;//背景图，只提供了id
    private int backgroundImageSelected=0;//按下后的背景图，只提供了id
    private String textColors="";//文字颜色，String
    private int textColor=0;//文字颜色，int
    private String textColorSelecteds="";//按下后的文字颜色，String
    private int textColorSelected=0;//按下后的文字颜色，int
    private float radius=8;//圆角半径
    private int shape=0;//圆的样式，矩形、圆形，由于矩形的id为0，默认为矩形
    private Boolean fillet=false;//是否设置圆角




    public ButtonM(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ButtonM(Context context, AttributeSet attrs) {
       this(context, attrs, 0);
    }

    public ButtonM(Context context) {
        this(context, null);
    }

    private void init() {
            //将Button的默认背景色改为透明，本人不喜欢原来的颜色
        if(fillet)
        {
            if(gradientDrawable==null)
            {
                gradientDrawable=new GradientDrawable();
            }
            gradientDrawable.setColor(Color.TRANSPARENT);

        }else
        {
            setBackgroundColor(Color.TRANSPARENT);
        }
        //设置文字默认居中
        setGravity(Gravity.CENTER);
        //设置Touch事件
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //按下改变样式

                setColor(event.getAction());
                //此处设置为false，防止Click事件被屏蔽
                return false;
            }
        });

    }

    /**
     * 改变样式
     * @param state
     */
    private void setColor(int state) {
        if(state==MotionEvent.ACTION_DOWN)
        {
            //按下
            if(backColorSelected!=0)
            {
                if(fillet)
                {
                    if(gradientDrawable==null)
                    {
                        gradientDrawable=new GradientDrawable();
                    }
                    gradientDrawable.setColor(backColorSelected);
                }
                else
                {
                   setBackgroundColor(backColorSelected);
                }
            } else if(!backColorSelecteds.equals(""))
            {
                if(fillet)
                {
                    if(gradientDrawable==null)
                    {
                        gradientDrawable=new GradientDrawable();
                    }
                    gradientDrawable.setColor(Color.parseColor(backColorSelecteds));
                }else {
                    setBackgroundColor(Color.parseColor(backColorSelecteds));
                }
            }

            //判断是否设置了按下后文字的颜色
            if(textColorSelected!=0)
            {
                setTextColor(textColorSelected);
            }
            else if(!textColorSelecteds.equals(""))
            {
                setTextColor(Color.parseColor(textColorSelecteds));
            }

            //判断是否设置了按下后的背景图
            if(backgroundImage!=0)
            {
                setBackgroundResource(backgroundImageSelected);
            }
        }

        if(state==MotionEvent.ACTION_UP)
        {
            //抬起
            if(backColor==0&&backColors.equals(""))
            {
                //如果没有设置背景色，默认改为透明
                if(fillet)
                {
                    if(gradientDrawable==null)
                    {
                        gradientDrawable=new GradientDrawable();
                    }
                    gradientDrawable.setColor(Color.TRANSPARENT);
                }
                else {
                    setBackgroundColor(Color.TRANSPARENT);
                }
            }else if(backColor!=0)
            {
                if(fillet)
                {
                    if(gradientDrawable==null)
                    {
                        gradientDrawable=new GradientDrawable();
                    }
                    gradientDrawable.setColor(backColor);
                }
                else {
                    setBackgroundColor(backColor);
                }
            }
            else  if(!backColors.equals(""))
            {
                if(fillet)
                {
                    if(gradientDrawable==null)
                    {
                        gradientDrawable=new GradientDrawable();
                    }
                    gradientDrawable.setColor(Color.parseColor(backColors));
                }
                else {
                    setBackgroundColor(Color.parseColor(backColors));
                }
            }

            //如果没设置字体颜色，默认黑色
            if(textColor==0&&textColors.equals(""))
            {
                setTextColor(Color.BLACK);
            }else  if(textColor!=0)
            {
                setTextColor(textColor);
            }else if (!textColors.equals(""))
            {
                setTextColor(Color.parseColor(textColors));
            }

            if(backgroundImage!=0)
            {
                setBackgroundResource(backgroundImage);
            }
        }

    }

    /**
     * 设置按钮的背景色,如果没设置则默认为透明
     * @param backColor
     */
    public void setBackColor(String backColor)
    {
        this.backColors=backColor;
        if(backColor.equals(""))
        {
            if(fillet)
            {
                if(gradientDrawable==null) {
                    gradientDrawable = new GradientDrawable();
                }
                gradientDrawable.setColor(Color.TRANSPARENT);
            }
            else
            {
                setBackgroundColor(Color.TRANSPARENT);
            }
        }else
        {
            if(fillet)
            {
                if(gradientDrawable==null)
                {
                    gradientDrawable=new GradientDrawable();
                }
                gradientDrawable.setColor(Color.parseColor(backColor));
            }
            else
            {
                setBackgroundColor(Color.parseColor(backColor));
            }
        }
    }

    /**
     * 设置按钮的背景色，如果没设置则默认为透明
     * @param backColor
     */
    public void setBackColor(int backColor)
    {
        this.backColor=backColor;
        if(backColor==0)
        {
            if(fillet)
            {
                if(gradientDrawable==null)
                {
                    gradientDrawable=new GradientDrawable();
                }
                gradientDrawable.setColor(Color.TRANSPARENT);
            }
            else
            {
                setBackgroundColor(Color.TRANSPARENT);
            }
        }
        else {
            if(fillet)
            {
                if(gradientDrawable==null)
                {
                    gradientDrawable=new GradientDrawable();
                }
                gradientDrawable.setColor(backColor);
            }
            else
            {
                setBackgroundColor(backColor);
            }
        }
    }

    /**
     * 设置按钮按下后的颜色
     * @param backColorSelected
     */
    public void setBackColorSelected(int backColorSelected)
    {
        this.backColorSelected=backColorSelected;
    }

    /**
     * 设置按钮按下后的颜色
     * @param backColorSelected
     */
    public void setBackColorSelected(String backColorSelected)
    {
        this.backColorSelecteds=backColorSelected;
    }

    /**
     * 设置按钮的背景图
     * @param backgroundImage
     */
    public void setBackgroundImage(int backgroundImage)
    {
        this.backgroundImage=backgroundImage;
        if(backgroundImage!=0)
        {
            setBackgroundResource(backgroundImage);
        }
    }

    /**
     * 设置按钮按下的背景图
     * @param backgroundImageSelected
     */
    public void setBackgroundImageSelected(int backgroundImageSelected)
    {
        this.backgroundImageSelected=backgroundImageSelected;
    }

    /**
     * 设置按钮圆角半径大小
     * @param radius
     */
    public void setRadius(float radius)
    {
        if(gradientDrawable==null)
        {
            gradientDrawable=new GradientDrawable();
        }
        gradientDrawable.setCornerRadius(radius);
    }

    /**
     * 设置按钮文字颜色
     * @param textColors
     */
    public void setTextColors(String textColors)
    {
        this.textColors=textColors;
        setTextColor(Color.parseColor(textColors));
    }

    /**
     * 设置按钮文字颜色---没有
     * @param textColor
     */
    public void setTextColori(int textColor)
    {
        this.textColor=textColor;
        setTextColor(textColor);
    }

    /**
     * 设置按钮按下的文字颜色
     * @param textColor
     */
    public void setTextColorSelected(String textColor)
    {
        this.textColorSelecteds=textColor;
    }

    /**
     * 设置按钮按下的文字颜色
     * @param textColor
     */
    public void setTextColorSelected(int textColor)
    {
        this.textColorSelected=textColor;
    }

    /**
     * 按钮的形状
     * @param shape
     */
    public void setShape(int shape)
    {
        this.shape=shape;
    }

    @SuppressWarnings("deprecation")
    public void setFillet(Boolean fillet)
    {
        this.fillet=fillet;
        if(fillet)
        {
            if(gradientDrawable==null)
            {
                gradientDrawable=new GradientDrawable();
            }
            //GradientDrawable.RECTANLE
            gradientDrawable.setShape(shape);
            gradientDrawable.setCornerRadius(radius);
            setBackgroundDrawable(gradientDrawable);
        }
    }
}
