package com.gary.garytool;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;

/**
 * Created by Administrator on 2016/2/17.
 */
public class MyButton extends Button{
    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        Log.d("-MyButton","the onKeyDown in MyButton");
        return false;
    }
}
