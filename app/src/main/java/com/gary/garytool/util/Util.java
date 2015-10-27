package com.gary.garytool.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by gary on 2015/10/27.
 */
public class Util {


    /**
     * sp to px 把当前字体的sp转换成当前的屏幕像素
     * @param context
     * @param sp
     * @return
     */
    public static int spToPx(Context context,int sp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * dp to px 把dp转换成当前的屏幕像素
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context,int dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, context.getResources().getDisplayMetrics());
    }
}
