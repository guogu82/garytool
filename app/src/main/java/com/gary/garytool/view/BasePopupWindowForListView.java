package com.gary.garytool.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/9/24.
 */
public abstract class BasePopupWindowForListView<T> extends PopupWindow {

    /**
     * 布局文件的最外层View
     */
    protected View mContentView;
    protected Context context;

    /**
     * ListView的数据集
     */
    protected List<T> mDatas;
    public BasePopupWindowForListView(View contentView, int width, int height, boolean focusable) {
        this(contentView,width,height,focusable,null);
    }

    public BasePopupWindowForListView(View contentView, int width, int height, boolean focusable, List<T> datas) {
        this(contentView,width,height,focusable,datas,new Object[0]);
    }

    public BasePopupWindowForListView(View contentView, int width, int height, boolean focusable, List<T> datas,Object... params) {
        super(contentView, width, height, focusable);
        mContentView=contentView;
        context=contentView.getContext();
        if(datas!=null)
            mDatas=datas;

        if(params!=null&&params.length>0)
        {
            beforeInitWeNeedSomeParams(params);
        }

        //popupWindow 的常规设置 例如 点击外面可以消失
        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_OUTSIDE)
                {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        initViews();
        initEvents();
        init();
    }

    protected abstract void beforeInitWeNeedSomeParams(Object... params);

    public abstract void initViews();
    public abstract void initEvents();
    public abstract void init();

    public View findViewById(int id)
    {
        return mContentView.findViewById(id);
    }

    protected static int dpToPx(Context context,int dp)
    {
        return (int) (context.getResources().getDisplayMetrics().density*dp+0.5f);
    }
}
