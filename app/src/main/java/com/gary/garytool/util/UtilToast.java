package com.gary.garytool.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by gary on 2016/06/29.
 * @author gary guo
 */
public class UtilToast {

    //在ui线程显示toast，text为显示的文字
    public static void showToast(Context context, String text)
    {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    //在ui线程显示toast，resId为显示的文字的资源id
    public static void showToast(Context context, int resId)
    {

    }

    //在子线程中显示toast，context需要传入activity的context，text为显示的文字
    public static void showToastInThread(Activity context, String text)
    {

    }

    //在子线程中显示toast，context需要传入activity的context，resId为显示的文字的资源id
    public static void showToastInThread(Activity context,  int resId)
    {

    }

}
