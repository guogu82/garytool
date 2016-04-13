package com.gary.garytool.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by gary on 2015/10/27.
 */
public class ImageUtil {

    //图片的压缩后加载，包括本地图片压缩加载和网络图片压缩加载
    //http://blog.csdn.net/lmj623565791/article/details/41874561
    //Android 框架练成 教你打造高效的图片加载框架
    //---一：本地图片的压缩加载------
    //------1-----
    // 获得图片的宽和高，并不把图片加载到内存中
    //BitmapFactory.Options options = new BitmapFactory.Options();
    //options.inJustDecodeBounds = true;
    //BitmapFactory.decodeFile(path, options);
    //-------2------
    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                           int reqHeight)
    {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight)
        {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);
        }

        return inSampleSize;
    }

    //---------------------3--------
    //    options.inSampleSize = ImageSizeUtil.caculateInSampleSize(options,width, height);
    //    // 使用获得到的InSampleSize再次解析图片
    //    options.inJustDecodeBounds = false;
    //    Bitmap bitmap = BitmapFactory.decodeFile(path, options);
    //    return bitmap;
    //图片的压缩后加载，包括本地图片压缩加载和网络图片压缩加载 end

    //----二：网络图片的压缩
    //a、直接下载存到sd卡，然后采用本地的压缩方案。这种方式当前是在硬盘缓存开启的情况下，如果没有开启呢？
    //b、使用BitmapFactory.decodeStream(is, null, opts);


    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitamp(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
