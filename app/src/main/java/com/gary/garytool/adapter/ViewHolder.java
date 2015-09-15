package com.gary.garytool.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/9/15.
 * 通用的ViewHolder类
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context,ViewGroup parent,int layoutId,int position)
    {
        mPosition=position;
        mViews=new SparseArray<>();
        mConvertView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position)
    {
        if(convertView==null)
            return new ViewHolder(context,parent,layoutId,position);

        ViewHolder holder= (ViewHolder) convertView.getTag();
        holder.mPosition=position;
        return holder;
    }

    /**
     * 通过viewId获取控件
     * 使用的是泛型T，返回的是View的子类
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view=mViews.get(viewId);
        if(view==null)
        {
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }
}
