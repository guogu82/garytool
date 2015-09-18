package com.gary.garytool;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2015/9/15.
 */
public class GaryApplication extends Application {

    private static RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this).writeDebugLogs().build();
        //打印log信息
        ImageLoader.getInstance().init(configuration);

        mRequestQueue= Volley.newRequestQueue(this);
    }

    public static RequestQueue getVolleyRequestQueue()
    {
        return mRequestQueue;
    }
}
