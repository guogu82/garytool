package com.gary.garytool.business.retrofit;

import android.content.Context;

import com.gary.garytool.lib.PersistentCookieJar.PersistentCookieJar;
import com.gary.garytool.lib.PersistentCookieJar.cache.SetCookieCache;
import com.gary.garytool.lib.PersistentCookieJar.persistence.SharedPrefsCookiePersistor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gary on 2016/6/2/002.
 */
public class RetrofitManager {
    static final String BASE_URL="http://test.jujia.ctauto.cn/";
    static Retrofit mRetrofit;
    static OkHttpClient mHttpClient;

    private RetrofitManager()
    {

    }

    public synchronized static Retrofit getRetrofit(Context context)
    {
        if(mRetrofit==null)
        {
            //mHttpClient=new OkHttpClient.Builder().cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context))).build();
            mRetrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mHttpClient)
                    .build();
        }

        return mRetrofit;
    }

}
