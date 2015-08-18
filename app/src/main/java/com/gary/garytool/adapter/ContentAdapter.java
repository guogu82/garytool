package com.gary.garytool.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Gary on 2015/8/17.
 */
public class ContentAdapter extends PagerAdapter {

    private List<View> views;
    public ContentAdapter(List<View> views)
    {
        this.views=views;
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container,int position)
    {
        View view=views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object)
    {
        container.removeView(views.get(position));
    }
}
