package com.gary.garytool;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gary.garytool.volley.VolleyManger;

/**
 * Created by Administrator on 2015/9/15.
 */
public class GaryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //调用Volley的封装类，初始化
        VolleyManger.initRequestQueue(this);
    }


}
