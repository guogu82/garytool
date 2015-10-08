package com.gary.garytool;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2015/9/15.
 */
public class GaryApplication extends Application {

    private static RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue= Volley.newRequestQueue(this);
    }

    public static RequestQueue getVolleyRequestQueue()
    {
        return mRequestQueue;
    }
}
