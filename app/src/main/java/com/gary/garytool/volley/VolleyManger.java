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

    /**
     * @param httpUrl
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "3fa06fc0f9c91551e068779fb3872e10");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


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
