package com.gary.garytool.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.util.LogUtil;

/**
 * Created by Administrator on 2015/9/9.
 * 用于展示listview的下拉刷新功能
 */
public class ReFlashListView extends ListView implements AbsListView.OnScrollListener {

    static final String TAG="ReFlashListView";

    View header;//顶部布局文件
    int headerHeight;//顶部布局文件的高度
    int firstVisibleItem;//当前第一个可见的item的位置
    int scrollState;//listview 当前的滚动状态
    boolean isRemark;//标记，当前是在listview的最顶端按下的
    int startY;//按下时的Y值

    int state;//当前的状态;
    final int NONE = 0;//正常状态
    final int PULL = 1;//提示下拉状态
    final int RELESE = 2;//提示释放状态
    final int REFLASHING = 3;//刷新状态

    IReflashListerner iReflashListerner;//刷新数据的接口

    public ReFlashListView(Context context) {
        this(context, null);
    }

    public ReFlashListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReFlashListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化界面，添加顶部布局文件到 listview
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.listview_header, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        LogUtil.d(TAG,"headerHeight="+headerHeight);
        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);
    }


    /**
     * 通知父布局，占用的宽，高
     *
     * @param view
     */
    private void measureView(View view) {
        //TODO:try 让p==null，看看结果会如何，是否能正常measure到大小。
        ViewGroup.LayoutParams p=null;// = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    /**
     * 设置header布局 上边距
     *
     * @param topPadding
     */
    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(),topPadding,header.getPaddingRight(),header.getPaddingBottom());
        header.invalidate();
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        this.scrollState=scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        this.firstVisibleItem=firstVisibleItem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtil.d(TAG,"state="+state);
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(firstVisibleItem==0)
                {
                    isRemark=true;
                    startY=(int)ev.getY();
                    LogUtil.d(TAG,"startY="+startY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if(state==RELESE)
                {
                    state=REFLASHING;
                    //加载最新数据
                    reflashViewByState();
                    iReflashListerner.onReflash();
                }
                else if(state==PULL)
                {
                    state=NONE;
                    isRemark=false;
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void reflashViewByState() {
        TextView tip= (TextView) header.findViewById(R.id.tv_tip);
        ImageView arrow= (ImageView) header.findViewById(R.id.iv_arrow);
        ProgressBar progress= (ProgressBar) header.findViewById(R.id.pb_progress);
        RotateAnimation anim=new RotateAnimation(0,180,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1=new RotateAnimation(180,0,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        arrow.clearAnimation();
        switch (state)
        {
            case NONE:
                topPadding(-headerHeight);
                break;
            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("下拉可以刷新");
                arrow.setAnimation(anim1);
                break;
            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("松开可以刷新");
                arrow.setAnimation(anim);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("正在刷新...");
                break;
        }

    }



    private void onMove(MotionEvent ev) {
        if(!isRemark)
        {
            return;
        }

        int tempY=(int)ev.getY();
        LogUtil.d(TAG, "tempY=" + tempY);
        int space=tempY-startY;
        LogUtil.d(TAG, "space=" + space);
        int topPadding=space-headerHeight;
        LogUtil.d(TAG, "topPadding=" + topPadding);
        switch(state)
        {
            case NONE:
                if(space>0&&space<headerHeight+30)
                {
                    state=PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if(space>headerHeight+30&&scrollState==SCROLL_STATE_TOUCH_SCROLL)
                {
                    state=RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if(space < headerHeight + 30)
                {
                    state=PULL;
                    reflashViewByState();
                }else if(space<=0)
                {
                    state=NONE;
                    isRemark=false;
                    reflashViewByState();
                }
                break;

        }
    }

    /**
     * 获取完数据，恢复状态
     */
    public void reflashComplate()
    {
        state=NONE;
        isRemark=false;
        reflashViewByState();
        TextView lastupdatetime= (TextView) header.findViewById(R.id.tv_last_update_time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String time=format.format(date);
        lastupdatetime.setText(time);
    }

    public void setInterface(IReflashListerner iReflashListerner)
    {
        this.iReflashListerner=iReflashListerner;
    }

    public interface IReflashListerner {
        public void onReflash();
    }
}
