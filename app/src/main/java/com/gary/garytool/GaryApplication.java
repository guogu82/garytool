package com.gary.garytool;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2015/9/15.
 */
public class GaryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this).writeDebugLogs().build();
        //打印log信息

        ImageLoader.getInstance().init(configuration);

    }
}
