package com.gary.garytool.business.guessmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.gary.garytool.R;
import com.gary.garytool.util.Util;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25.
 * @author gary guo
 */
public class MyGridView extends GridView {

    public final static int COUNT_WORDS=24;

    private ArrayList<WordButton> mArrayList=new ArrayList<WordButton>();
    private MyGridAdapter mAdapter;
    private Context mContext;
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAdapter=new MyGridAdapter();
        setAdapter(mAdapter);
        mContext=context;
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * @param list
     */
    public void updateData(ArrayList<WordButton> list)
    {
        mArrayList=list;
        setAdapter(mAdapter);
    }


    class MyGridAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return mArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            WordButton holder;
            if(convertView ==null)
            {
                convertView= Util.getView(mContext, R.layout.guess_music_sel_gridview_item);
                holder=mArrayList.get(position);
                holder.setIndex(position);
                holder.setViewButton((Button) convertView.findViewById(R.id.bt_item));
                convertView.setTag(holder);
            }
            else
            {
                holder= (WordButton) convertView.getTag();
            }
            holder.getViewButton().setText(holder.getWordString());
            return convertView;
        }
    }
}


