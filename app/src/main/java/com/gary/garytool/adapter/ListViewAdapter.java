package com.gary.garytool.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.gary.garytool.R;

import java.util.List;


/**
 * Created by Administrator on 2015/9/15.
 * 通用的ListView的BaseAdapter，所有的ListView的自定义adapter都可以继承这个类
 */
public abstract class ListViewAdapter<T> extends BaseAdapter {

    //为了让子类访问，于是将属性设置为protected
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int mLayoutId;//不同的ListView的item布局基本不同，所有要把布局单独提取出来

    public ListViewAdapter(Context context,List<T> datas,int layoutId)
    {
        mContext=context;
        mDatas=datas;
        mLayoutId=layoutId;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //初始化ViewHolder,使用通用的ViewHolder,一行代码就搞定ViewHolder的初始化。
        CommonViewHolder holder=CommonViewHolder.get(mContext,convertView,parent,mLayoutId,position);
        convert(holder,getItem(position));
        return holder.getConvertView();
    }

    //TODO:note 设置模式，模板方法的使用场景
    public abstract void convert(CommonViewHolder holder, T item);
}
