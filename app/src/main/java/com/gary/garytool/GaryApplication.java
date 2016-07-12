package com.gary.garytool;

import android.app.Application;

import com.gary.garytool.business.volley.VolleyManger;

/**
 * Created by Gary on 2015/9/15.
 */
public class GaryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //调用Volley的封装类，初始化
        VolleyManger.initRequestQueue(this);
    }



}
