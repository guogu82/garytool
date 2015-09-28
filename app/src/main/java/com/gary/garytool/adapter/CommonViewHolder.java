package com.gary.garytool.adapter;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gary.garytool.util.ImageLoader;
import com.gary.garytool.util.LogUtil;

/**
 * Created by Administrator on 2015/9/7.
 */
public class CommonViewHolder {
    private SparseArray<View> viewArray;


    private int position;
    private View mConvertView;
    private Object tag;

    //TODO:question 为什么要用get方法来获取实例，不是用new产生实例。为何用这个模式。
    public CommonViewHolder(Context context, View converView, ViewGroup parent, int layoutId, int position)
    {
        this.position=position;
        //使用SparseArray效率高一些
        viewArray=new SparseArray<>();
        //加载布局
        mConvertView=LayoutInflater.from(context).inflate(layoutId,parent,false);
        //降ViewHolderM赋值给View的Tag
        mConvertView.setTag(this);
    }

    public static CommonViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position)
    {
        LogUtil.d("CommonViewHolder",""+position);
        if(convertView==null)
        {
            //如果convertView为空，则实例化ViewHolderM
            return new CommonViewHolder(context,convertView,parent,layoutId,position);
        }else
        {
            //否则从converView的Tag中取出ViewHolderM，避免重复创建
            CommonViewHolder holder= (CommonViewHolder) convertView.getTag();
            holder.position=position;
            return holder;
        }
    }

    public View getConvertView()
    {
        return mConvertView;
    }

    public Object getTag()
    {
        return tag;
    }

    public void setTag(Object tag)
    {
        this.tag=tag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 通过viewId获取控件对象
     * @param viewID
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewID)
    {
        View view=viewArray.get(viewID);
        if(view==null)
        {
            view=mConvertView.findViewById(viewID);
            viewArray.put(viewID,view);
        }
        return (T)view;
    }

/**-------------------------分割线--------------------------------------------------*/
/** 以下方法为额外封装的方法，只是简单几个，以后可以慢慢完善*/
    /**
     * 设置TextView的内容
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setText(int viewId,String text)
    {
        TextView tv=getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置TextView的内容
     * @param viewId
     * @param text,Spanned类型，可设置部分字体变色
     * @return
     */
    public CommonViewHolder setText(int viewId,Spanned text)
    {
        TextView tv =getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     * @param viewId
     * @param drawableId
     * @return
     */
    public CommonViewHolder setImageResource(int viewId,int drawableId)
    {
        ImageView view=getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     * @param viewId
     * @param bm
     * @return
     */
    public CommonViewHolder setImageBitmap(int viewId,Bitmap bm)
    {
        ImageView view=getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 设置图片的可见性
     * @param viewId
     * @param visible
     * @return
     */
    public CommonViewHolder setImageViewVisible(int viewId,Boolean visible)
    {
        ImageView iv=getView(viewId);
        iv.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public CommonViewHolder setImageByUrl(int viewId,String url)
    {
        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(url, (ImageView) getView(viewId));
        return this;
    }

}
