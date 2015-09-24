package com.gary.garytool.view;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * Created by Administrator on 2015/9/7.
 * 公共的Adapter List适配器
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context context;
    //为丰富程序功能，提供了两种常见的数据类型
    private List<T> dataList=null;
    private T[] dataArray=null;
    //布局文件ID
    private int layoutId;

    public CommonAdapter(Context context, int layoutId, List<T> dataList)
    {
        this.context=context;
        this.layoutId=layoutId;
        this.dataList=dataList;
    }

    public CommonAdapter(Context context, int layoutId, T[] dataArray)
    {
        this.context=context;
        this.layoutId=layoutId;
        this.dataArray=dataArray;
    }

    @Override
    public int getCount() {
        if(dataList!=null)
        {
            return dataList.size();
        }
        else
        {
            return dataArray.length;
        }
    }

    @Override
    public T getItem(int position) {
      if(dataList!=null)
      {
          return dataList.get(position);
      }
        else
      {
          return dataArray[position];
      }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO:CHECK this function
        ViewHolderM holder=ViewHolderM.get(context,convertView,parent,layoutId,position);
        //ViewHolderM holder=new ViewHolderM(context,convertView,parent,layoutId,position);
        convert(holder,getItem(position));
        return holder.getConvertView();
    }

    /**
     * 需要实现的抽象方法
     * @param holder
     * @param item
     */
    public abstract void convert(ViewHolderM holder, T item);
}
