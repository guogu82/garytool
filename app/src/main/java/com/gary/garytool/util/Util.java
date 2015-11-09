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

    /**
     * 将字节数转化为MB
     * @param size
     * @return
     */
    private String byteToMB(long size){
        long kb = 1024;
        long mb = kb*1024;
        long gb = mb*1024;
        if (size >= gb){
            return String.format("%.1f GB",(float)size/gb);
        }else if (size >= mb){
            float f = (float) size/mb;
            return String.format(f > 100 ?"%.0f MB":"%.1f MB",f);
        }else if (size > kb){
            float f = (float) size / kb;
            return String.format(f>100?"%.0f KB":"%.1f KB",f);
        }else {
            return String.format("%d B",size);
        }
    }
}
