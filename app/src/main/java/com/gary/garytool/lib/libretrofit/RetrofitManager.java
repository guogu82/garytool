package com.gary.garytool.lib.libretrofit;

import android.content.Context;

import com.gary.garytool.lib.libpersistentcookiejar.com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.gary.garytool.lib.libpersistentcookiejar.com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.gary.garytool.lib.libpersistentcookiejar.com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
            mHttpClient=new OkHttpClient.Builder().cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context))).build();
            mRetrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(mHttpClient)
                    .build();
        }

        return mRetrofit;
    }

}
