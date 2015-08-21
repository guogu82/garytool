package com.gary.garytool;

import android.app.*;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.*;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


public class BottomMenuWithPopupWindowLikeQQ extends FragmentActivity implements View.OnClickListener {

    //定义Fragment 页面
    private HomeFragment fragmentAt;
    private AddressFragment fragmentAuth;
    private FriendFragment fragmentSpace;
    private SettingFragment fragmentMore;

    //定义布局对象
    private FrameLayout atFl, authFl, spaceFl, moreFl;

    //定义图片组件对象
    private ImageView atIv, authIv, spaceIv, moreIv;

    //定义按钮图片组件
    private ImageView toogleImageView, plusImageView;

    //定义PopupWindow
    private PopupWindow popWindow;

    //获取手机屏幕分辨率的类
    private DisplayMetrics dm;

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
        atFl.setOnClickListener(this);
        authFl.setOnClickListener(this);
        spaceFl.setOnClickListener(this);
        moreFl.setOnClickListener(this);

        //给按钮图片设置监听
        toogleImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.layout_at:
                clickAtBtn();
                break;
            case R.id.layout_auth:
                clickAuthBtn();
                break;
            case R.id.layout_space:
                clickSpaceBtn();
                break;
            case R.id.layout_more:
                clickMoreBtn();
                break;
            case R.id.toggle_btn:
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
        fragmentAt=new HomeFragment();
        //得到Fragment事务管理器
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        //替换当前的页面
        fragmentTransaction.replace(R.id.frame_content,fragmentAt);
        //事务管理提交
        fragmentTransaction.commit();
        //改变选中状态
        atFl.setSelected(true);
        atIv.setSelected(true);

        authFl.setSelected(false);
        authIv.setSelected(false);

        spaceFl.setSelected(false);
        spaceIv.setSelected(false);

        moreFl.setSelected(false);
        moreIv.setSelected(false);

    }

    /**
     * 点击了“与我相关”按钮
     */
    private void clickAuthBtn() {
        //实例化Fragment页面
        fragmentAuth =new AddressFragment();
        //得到Fragment事务管理器
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                //替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentAuth);
        //事务管理提交
        fragmentTransaction.commit();

        atFl.setSelected(false);
        atIv.setSelected(false);

        authFl.setSelected(true);
        authIv.setSelected(true);

        spaceFl.setSelected(false);
        spaceIv.setSelected(false);

        moreFl.setSelected(false);
        moreIv.setSelected(false);
    }

    /**
     * 点击了“我的空间”按钮
     */
    private void clickSpaceBtn() {
        //实例化Fragment页面
        fragmentSpace=new FriendFragment();
        //得到Fragment事务管理器
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        //替换当前的页面
        fragmentTransaction.replace(R.id.frame_content,fragmentSpace);
        //事务管理提交
        fragmentTransaction.commit();

        atFl.setSelected(false);
        atIv.setSelected(false);

        authFl.setSelected(false);
        authIv.setSelected(false);

        spaceFl.setSelected(true);
        spaceIv.setSelected(true);

        moreFl.setSelected(false);
        moreIv.setSelected(false);

    }

    /**
     * 点击了“更多”按钮
     */
    private void clickMoreBtn() {
        //实例化Fragment页面
        fragmentMore =new SettingFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragmentMore);
        fragmentTransaction.commit();

        atFl.setSelected(false);
        atIv.setSelected(false);

        authFl.setSelected(false);
        authIv.setSelected(false);

        spaceFl.setSelected(false);
        spaceIv.setSelected(false);

        moreFl.setSelected(true);
        moreIv.setSelected(true);
    }

    /**
     * 点击了中间按钮
     */
    private void clickToggleBtn() {
        showPopupWindow(toogleImageView);
        //改变按钮显示的图片为按下时的状态
        plusImageView.setSelected(true);

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
            dm=new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            //创建一个PopuWindow对象
            popWindow=new PopupWindow(view,dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        //监听触屏事件
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //改变显示的按钮图片为正常状态
                changeButtonImage();
                popWindow.dismiss();
                return false;

            }
        });
    }
    private void changeButtonImage() {
        plusImageView.setSelected(false);
    }
    private void initView() {
        //实例化布局对象
        atFl = (FrameLayout) findViewById(R.id.layout_at);
        authFl = (FrameLayout) findViewById(R.id.layout_auth);
        spaceFl = (FrameLayout) findViewById(R.id.layout_space);
        moreFl = (FrameLayout) findViewById(R.id.layout_more);

        //实例化图片组件对象
        atIv= (ImageView) findViewById(R.id.image_at);
        authIv= (ImageView) findViewById(R.id.image_space);
        spaceIv= (ImageView) findViewById(R.id.image_space);
        moreIv= (ImageView) findViewById(R.id.image_more);

        //实例化按钮图片组件
        toogleImageView= (ImageView) findViewById(R.id.toggle_btn);
        plusImageView= (ImageView) findViewById(R.id.plus_btn);
    }


}
