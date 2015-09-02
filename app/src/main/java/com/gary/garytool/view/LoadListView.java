package com.gary.garytool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.gary.garytool.R;

/**
 * Created by Administrator on 2015/9/2.
 */
public class LoadListView extends ListView implements AbsListView.OnScrollListener {

    View m_Footer;
    int m_TotalItemCount;
    int m_LastVisibleItem;
    boolean m_IsLoading;
    ILoadListener m_LoadLister;


    public LoadListView(Context context)
    {
       super(context);
        initView(context);
    }

    public LoadListView(Context context,AttributeSet attrs)
    {
       super(context,attrs);
        initView(context);
    }

    public LoadListView(Context context,AttributeSet attrs,int defStyle)
    {
        super(context,attrs,defStyle);
       initView(context);
    }


    /**
     * 添加底部加载提示布局到ListView
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater inflater=LayoutInflater.from(context);
        m_Footer=inflater.inflate(R.layout.listview_footer,null);
        m_Footer.findViewById(R.id.ll_load_layout).setVisibility(View.GONE);
        this.addFooterView(m_Footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(m_TotalItemCount==m_LastVisibleItem&&scrollState==SCROLL_STATE_IDLE)
        {
            if(!m_IsLoading)
            {
                m_IsLoading=true;
                m_Footer.findViewById(R.id.ll_load_layout).setVisibility(View.VISIBLE);
                m_LoadLister.onLoad();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.m_LastVisibleItem=firstVisibleItem+visibleItemCount;
        this.m_TotalItemCount=totalItemCount;
    }

    /**
     * 外部数据加载完毕后，调用这个方法恢复状态
     */
    public void loadComplete()
    {
        m_IsLoading=false;
        m_Footer.findViewById(R.id.ll_load_layout).setVisibility(View.GONE);
    }

    public void setInterface(ILoadListener iLoadListener)
    {
        m_LoadLister=iLoadListener;
    }

    public interface ILoadListener{
        public void onLoad();
    }

}
