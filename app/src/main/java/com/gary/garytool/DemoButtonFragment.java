package com.gary.garytool;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gary.garytool.view.ButtonM;

public class DemoButtonFragment extends Fragment {
    View mFragmentButton;

    //--------for function two custom button-------------------------------
    //定义三个空布局用来装载自定义Button控件，只为演示效果，实际开发中不推荐使用
    private LinearLayout ll_CustomButton1,ll_CustomButton2,ll_CustomButton3;
    //定义三个Button数组
    private  ButtonM[] buttonM1,buttonM2,buttonM3;
    //定义两组颜色值，按下与未按下的按钮背景色
    private static final String[] colorList = {"#7067E2","#FF618F","#B674D2","#00C2EB"};
    private static final String[] colorSelectedList = {"#3C3779","#88354C","#613E70","#00677D"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentButton = inflater.inflate(R.layout.fragment_button, container, false);

        //demo1
        Button button = (Button) mFragmentButton.findViewById(R.id.bt_finish_app);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
                startActivity(intent);
                getActivity().finish();//关掉自己
            }
        });

        //demo2
        initView();
        return mFragmentButton;
    }

    private void initView() {
        //实例化布局控件
        ll_CustomButton1= (LinearLayout) mFragmentButton.findViewById(R.id.ll_custom_button_1);
        ll_CustomButton2= (LinearLayout) mFragmentButton.findViewById(R.id.ll_custom_button_2);
        ll_CustomButton3= (LinearLayout) mFragmentButton.findViewById(R.id.ll_custom_button_3);
        //实例化控件数组，各定义4个
        buttonM1=new ButtonM[4];
        buttonM2=new ButtonM[4];
        buttonM3=new ButtonM[4];
        //获取屏幕的宽度，每行四个Button，间隙为60共300，除4为每个控件的宽度
        @SuppressWarnings("deprecation")
        int btnWidth=(getActivity().getWindowManager().getDefaultDisplay().getWidth()-300)/4;
        //定义第一个布局
        ll_CustomButton1.setPadding(60, 0, 0, 0);
        LinearLayout.LayoutParams lp1;
        for (int i=0;i<4;i++)
        {
            //为buttonM1设置样式，直角矩形
            buttonM1[i]=new ButtonM(getActivity());
            //字体颜色 没有调用i后缀的话，就没有设置textcolor，会导致按下后恢复不了颜色，永久置为黑色
            buttonM1[i].setTextColori(getResources().getColor(R.color.white));
            //字体大小
            buttonM1[i].setTextSize(14);
            //背景色
            buttonM1[i].setBackColor(Color.parseColor(colorList[i]));
            //选中的背景色
            buttonM1[i].setBackColorSelected(Color.parseColor(colorSelectedList[i]));
            //文字提示
            buttonM1[i].setText("TEXT"+i);
            //此处设置ID的值为i,否则onClick中v.getId()将永远为-1
            buttonM1[i].setId(i);
            //定义buttonM的布局，宽度自适应，高度为宽度的0.6倍，权重为1
            //也可以写成lp1=new LinearLayout.LayoutParams(btnWidth,(int)(btnWidth*0.6));
            lp1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,(int)(btnWidth*0.6),1.0f);
            //控件距离其右侧控件的距离，此处为60
            lp1.setMargins(0,0,60,0);
            buttonM1[i].setLayoutParams(lp1);
            //设置buttonM的点击事件
            buttonM1[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "您选择了第" + v.getId() + "个", Toast.LENGTH_SHORT).show();
                }
            });

            ll_CustomButton1.addView(buttonM1[i]);
        }

        //定义第二个布局
        ll_CustomButton2.setPadding(60, 0, 0, 0);
        LinearLayout.LayoutParams lp2;
        for(int i=0;i<4;i++)
        {
            //为buttonM2设置样式，圆角矩形
            buttonM2[i]=new ButtonM(getActivity());
            buttonM2[i].setTextColori(getResources().getColor(R.color.white));
            buttonM2[i].setTextSize(14);
            //设置是否为圆角
            buttonM2[i].setFillet(true);
            //设置圆角的半径大小
            buttonM2[i].setRadius(18);
            buttonM2[i].setBackColor(Color.parseColor(colorList[i]));
            buttonM2[i].setBackColorSelected(Color.parseColor(colorSelectedList[i]));
            buttonM2[i].setText("TEXT" + i);
            buttonM2[i].setId(i);
            lp2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (btnWidth * 0.6), 1.0f);
            lp2.setMargins(0,0,60,0);
            buttonM2[i].setLayoutParams(lp2);
            buttonM2[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "您选择圆角第" + v.getId() + "个", Toast.LENGTH_SHORT).show();
                }
            });

            ll_CustomButton2.addView(buttonM2[i]);

        }

        //定义第三个布局
        ll_CustomButton3.setPadding(60, 0, 0, 0);
        LinearLayout.LayoutParams lp3;
        for (int i=0;i<4;i++)
        {
            //为buttonM3设置样式，圆形
            buttonM3[i]=new ButtonM(getActivity());
            buttonM3[i].setTextColori(getResources().getColor(R.color.white));
            buttonM3[i].setTextSize(14);
            //设置为圆形,默认为矩形，GradientDrawable.RECTNGLE
            buttonM3[i].setShape(GradientDrawable.OVAL);
            buttonM3[i].setFillet(true);
            buttonM3[i].setBackColor(Color.parseColor(colorList[i]));
            buttonM3[i].setBackColorSelected(Color.parseColor(colorSelectedList[i]));
            buttonM3[i].setText("TEXT" + i);
            buttonM3[i].setId(i);
            lp3=new LinearLayout.LayoutParams(btnWidth, btnWidth);
            lp3.setMargins(0,0,60,0);
            buttonM3[i].setLayoutParams(lp3);
            buttonM3[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "您选择了圆形第" + v.getId() + "个", Toast.LENGTH_SHORT).show();
                }
            });

            ll_CustomButton3.addView(buttonM3[i]);
        }
    }


}
