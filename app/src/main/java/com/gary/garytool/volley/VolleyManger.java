package com.gary.garytool.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 本类用于封装Volley的操作。以适应未来更换http通信模块
 * Created by Administrator on 2015/10/15.
 */
public class VolleyManger {




    public static void initRequestQueue(Context context)
    {
        //理论不用双重判断锁。因为只是在app启动的时候才执行一次。
        if(mRequestQueue==null)
        {
            synchronized (VolleyManger.class) {
                if(mRequestQueue==null)
                mRequestQueue = Volley.newRequestQueue(context);
            }
        }

    }

    private static RequestQueue mRequestQueue;

    public static RequestQueue getVolleyRequestQueue()
    {
        return mRequestQueue;
    }
}
