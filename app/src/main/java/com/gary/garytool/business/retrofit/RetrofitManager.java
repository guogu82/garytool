package com.gary.garytool.business.retrofit;

import android.content.Context;


import com.gary.garytool.lib.PersistentCookieJar.PersistentCookieJar;
import com.gary.garytool.lib.PersistentCookieJar.cache.SetCookieCache;
import com.gary.garytool.lib.PersistentCookieJar.persistence.SharedPrefsCookiePersistor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gary on 2016/6/2/002.
 */
public class RetrofitManager {
    //static String BASE_URL="http://test.jujia.ctauto.cn/";
    static String BASE_URL="http://api.openweathermap.org/data/2.5/";

    static Retrofit mRetrofitWithCookie;
    static Retrofit mRetrofit;
    static OkHttpClient mHttpClient;

    public synchronized static Retrofit getRetrofit(Context context)
    {
        if(mRetrofitWithCookie==null)
        {
            mHttpClient=new OkHttpClient.Builder().cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context))).build();
            mRetrofitWithCookie=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mHttpClient)
                    .build();
        }

        return mRetrofitWithCookie;
    }

    public synchronized static Retrofit getRetrofit(String baseUrl)
    {

        if(mRetrofit==null)
        {
            BASE_URL=baseUrl;
            mRetrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(new OkHttpClient())
                    .build();
        }
        return mRetrofit;
    }

}
