package com.gary.garytool.business.guessmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private Animation mScaleAnimation;
    private IWordButtonClickListener mWordButtonListener;
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

    /**
     * 注册监听接口
     * @param listener
     */
    public void registOnWordButtonClick(IWordButtonClickListener listener)
    {
        mWordButtonListener=listener;
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

            final WordButton holder;
            if(convertView ==null)
            {
                convertView= Util.getView(mContext, R.layout.guess_music_sel_gridview_item);
                holder=mArrayList.get(position);
                //加载动画
                mScaleAnimation= AnimationUtils.loadAnimation(mContext,R.anim.guess_music_scale);
                //设置动画延时时间
                mScaleAnimation.setStartOffset(position * 100);

                holder.setIndex(position);
                Button button=(Button) convertView.findViewById(R.id.bt_item);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mWordButtonListener!=null)
                        {
                            mWordButtonListener.onWordButtonClick(holder);
                        }
                    }
                });
                holder.setViewButton(button);
                convertView.setTag(holder);
            }
            else
            {
                holder= (WordButton) convertView.getTag();
            }
            holder.getViewButton().setText(holder.getWordString());
            //播放动画
            convertView.startAnimation(mScaleAnimation);
            return convertView;
        }
    }
}


