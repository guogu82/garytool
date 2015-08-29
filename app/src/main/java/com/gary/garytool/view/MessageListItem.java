package com.gary.garytool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.gary.garytool.R;


/**
 * Created by Gary on 2015/8/29.
 */
public class MessageListItem extends RelativeLayout{
    public MessageListItem(Context context,AttributeSet attrs) {
        super(context,attrs);
    }

    private  static final  int[] STATE_MESSAGE_READED={R.attr.state_message_readed};
    private boolean mMessageReaded=false;

    public void setMessageReaded(boolean readed)
    {
        if(this.mMessageReaded!=readed)
        {
            mMessageReaded=readed;
            //TODO:需要透切理解refreshDrawableState()方法的用法
            refreshDrawableState();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if(mMessageReaded)
        {
            final int[] drawableState=super.onCreateDrawableState(extraSpace+1);
            mergeDrawableStates(drawableState,STATE_MESSAGE_READED);
            return drawableState;
        }
        return super.onCreateDrawableState(extraSpace);
    }
}
