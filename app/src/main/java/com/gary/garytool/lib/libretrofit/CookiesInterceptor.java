package com.gary.garytool.lib.libretrofit;

import android.content.Context;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/2/002.
 */
public class CookiesInterceptor implements Interceptor {
    private Context context;

    public CookiesInterceptor(Context context) {
        this.context = context;
    }
    //重写拦截方法，处理自定义的Cookies信息
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request compressedRequest = request.newBuilder()
                .header("cookie", getCookies(context))
                .build();
        Response response = chain.proceed(compressedRequest);
        saveCookies(response.headers(), context);
        return response;
    }

    private void saveCookies(Headers headers, Context context) {

    }

    private String getCookies(Context context) {
        return null;
    }
}