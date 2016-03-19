package com.gary.garytool.business.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2015/9/18.
 * Volley没有实现内存缓存，所以创建BitmapCache进行图片的内存缓存
 * 利用Android提供的LruCache，实现图片的内存缓存
 */
public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        //设置缓存大小为10M
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //图片的行乘以高等于大小
                //官方的例子是getByteCount方法吧，它的内部实现也是getRowBytes() * getHeight()
                //getRowBytes()是返回图片每行的字节数，图片的size应该乘以高度
                return value.getRowBytes() * value.getHeight();
            }
        };

    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}
