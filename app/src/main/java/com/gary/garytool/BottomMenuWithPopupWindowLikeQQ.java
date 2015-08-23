package com.gary.garytool;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.*;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


public class BottomMenuWithPopupWindowLikeQQ extends FragmentActivity implements View.OnClickListener {

    //定义Fragment 页面
    private Demo1Fragment demo1Fragment;
    private Demo2Fragment demo2Fragment;
    private Demo3Fragment demo3Fragment;
    private Demo4Fragment demo4Fragment;

    //定义布局对象
    private FrameLayout fl_at, fl_auth, fl_space, fl_more;

    //定义图片组件对象
    private ImageView iv_at, iv_auth, iv_space, iv_more;

    //定义按钮图片组件
    private ImageView iv_toogle, iv_plus;

    //定义PopupWindow
    private PopupWindow popWindow;

    //获取手机屏幕分辨率的类
    private DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bottom_menu_with_popup_window_like_qq);

        initView();

        initData();

        //初始化默认选中点击了“动态”按钮
        clickAtBtn();
    }

    private void initData() {
        //给布局对象设置监听
        fl_at.setOnClickListener(this);
        fl_auth.setOnClickListener(this);
        fl_space.setOnClickListener(this);
        fl_more.setOnClickListener(this);

        //给按钮图片设置监听
        iv_toogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.fl_at:
                clickAtBtn();
                break;
            case R.id.fl_auth:
                clickAuthBtn();
                break;
            case R.id.fl_space:
                clickSpaceBtn();
                break;
            case R.id.fl_more:
                clickMoreBtn();
                break;
            case R.id.iv_toggle:
                clickToggleBtn();
                break;
            default:
                break;
        }
    }






    /**
     *点击了“动态”按钮
     */
    private void clickAtBtn() {
        //实例化Fragmenty页面
        demo1Fragment =new Demo1Fragment();
        //得到Fragment事务管理器
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        //替换当前的页面
        fragmentTransaction.replace(R.id.fl_content, demo1Fragment);
        //事务管理提交
        fragmentTransaction.commit();
        //改变选中状态
        fl_at.setSelected(true);
        iv_at.setSelected(true);

        fl_auth.setSelected(false);
        iv_auth.setSelected(false);

        fl_space.setSelected(false);
        iv_space.setSelected(false);

        fl_more.setSelected(false);
        iv_more.setSelected(false);

    }

    /**
     * 点击了“与我相关”按钮
     */
    private void clickAuthBtn() {
        //实例化Fragment页面
        demo2Fragment =new Demo2Fragment();
        //得到Fragment事务管理器
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                //替换当前的页面
        fragmentTransaction.replace(R.id.fl_content, demo2Fragment);
        //事务管理提交
        fragmentTransaction.commit();

        fl_at.setSelected(false);
        iv_at.setSelected(false);

        fl_auth.setSelected(true);
        iv_auth.setSelected(true);

        fl_space.setSelected(false);
        iv_space.setSelected(false);

        fl_more.setSelected(false);
        iv_more.setSelected(false);
    }

    /**
     * 点击了“我的空间”按钮
     */
    private void clickSpaceBtn() {
        //实例化Fragment页面
        demo3Fragment =new Demo3Fragment();
        //得到Fragment事务管理器
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        //替换当前的页面
        fragmentTransaction.replace(R.id.fl_content, demo3Fragment);
        //事务管理提交
        fragmentTransaction.commit();

        fl_at.setSelected(false);
        iv_at.setSelected(false);

        fl_auth.setSelected(false);
        iv_auth.setSelected(false);

        fl_space.setSelected(true);
        iv_space.setSelected(true);

        fl_more.setSelected(false);
        iv_more.setSelected(false);

    }

    /**
     * 点击了“更多”按钮
     */
    private void clickMoreBtn() {
        //实例化Fragment页面
        demo4Fragment =new Demo4Fragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_content, demo4Fragment);
        fragmentTransaction.commit();

        fl_at.setSelected(false);
        iv_at.setSelected(false);

        fl_auth.setSelected(false);
        iv_auth.setSelected(false);

        fl_space.setSelected(false);
        iv_space.setSelected(false);

        fl_more.setSelected(true);
        iv_more.setSelected(true);
    }

    /**
     * 点击了中间按钮
     */
    private void clickToggleBtn() {
        showPopupWindow(iv_toogle);
        //改变按钮显示的图片为按下时的状态
        iv_plus.setSelected(true);

    }

    /**
     * 显示PopupWindows弹出菜单
     * @param parent
     */
    private void showPopupWindow(View parent) {
        if(popWindow==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.popwindow_layout,null);
            displayMetrics =new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            //创建一个PopuWindow对象
            popWindow=new PopupWindow(view, displayMetrics.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.ll_popup_journal);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(BottomMenuWithPopupWindowLikeQQ.this,ListViewDemo1Activity.class);
                    startActivity(intent);
                }
            });
        }

        //使其聚焦，要想监听菜单里控件的事件就必须调用此方法
        popWindow.setFocusable(true);
        //设置允许在外点击消失
        popWindow.setOutsideTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        //设置背景，这个是为了点击“返回Back”也能使其消失，并且不会影响你的背景.
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //PopupWindow的显示及位置设置
        //popWindow。showAtLocation(parent,Gravity.FILL,0,0)
        popWindow.showAsDropDown(parent,0,0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //改变显示的按钮图片为正常状态
                changeButtonImage();
            }
        });

        // 监听触屏事件
//        popWindow.setTouchInterceptor(new View.OnTouchListener() {
//            public boolean onTouch(View view, MotionEvent event) {
//                // 改变显示的按钮图片为正常状态
//                changeButtonImage();
//                popWindow.dismiss();
//                return false;
//            }
//        });

    }
    private void changeButtonImage() {
        iv_plus.setSelected(false);
    }
    private void initView() {
        //实例化布局对象
        fl_at = (FrameLayout) findViewById(R.id.fl_at);
        fl_auth = (FrameLayout) findViewById(R.id.fl_auth);
        fl_space = (FrameLayout) findViewById(R.id.fl_space);
        fl_more = (FrameLayout) findViewById(R.id.fl_more);

        //实例化图片组件对象
        iv_at = (ImageView) findViewById(R.id.iv_at);
        iv_auth = (ImageView) findViewById(R.id.iv_auth);
        iv_space = (ImageView) findViewById(R.id.iv_space);
        iv_more = (ImageView) findViewById(R.id.iv_more);

        //实例化按钮图片组件
        iv_toogle = (ImageView) findViewById(R.id.iv_toggle);
        iv_plus = (ImageView) findViewById(R.id.iv_plus);
    }


}
