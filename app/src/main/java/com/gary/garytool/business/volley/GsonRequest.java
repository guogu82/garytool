package com.gary.garytool.business.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gary on 2015/9/19.
 */
public class GsonRequest<T> extends Request<T> {

    private static Map<String, String> mHeader = new HashMap<String, String>();
    /**
     * 设置访问服务器时必须传递的参数，密钥等
     */
    static
    {
        mHeader.put("apikey", "3fa06fc0f9c91551e068779fb3872e10");
    }

    private final Response.Listener<T> mListener;
    private Gson mGson;
    private Class<T> mClass;
    public GsonRequest(String url,Class<T> clazz,Response.Listener<T> listener, Response.ErrorListener errorListener) {
       this(Method.GET,url,clazz,listener,errorListener);
    }

    public GsonRequest(int method, String url,Class<T> clazz,Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mGson=new Gson();
        mClass=clazz;
        setRetryPolicy(getMyDefaultRetryPolicy());
    }

    public GsonRequest(int method, String url,Class<T> clazz,Map<String, String> appendHeader,Response.Listener<T> listener, Response.ErrorListener errorListener) {
      this(method,url,clazz,listener,errorListener);
        mHeader=appendHeader;
    }

    private RetryPolicy getMyDefaultRetryPolicy() {
        RetryPolicy retryPolicy=new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return retryPolicy;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString=new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(jsonString,mClass),HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
    //列表 array 数据
    //String str="[{'id': '1','code': 'bj','name': '北京','map': '39.90403, 116.40752599999996'}, {'id': '2','code': 'sz','name': '深圳','map': '22.543099, 114.05786799999998'}, {'id': '9','code': 'sh','name': '上海','map': '31.230393,121.473704'}, {'id': '10','code': 'gz','name': '广州','map': '23.129163,113.26443500000005'}]";
    //List<City> rs=new ArrayList<City>();
    //Type type = new TypeToken<ArrayList<City>>() {}.getType();
    //rs=gson.fromJson(str, type);
    //map数据
    //String jsonStr="{'1': {'id': '1','code': 'bj','name': '北京','map': '39.90403, 116.40752599999996'},'2': {'id': '2','code': 'sz','name': '深圳','map': '22.543099, 114.05786799999998'},'9': {'id': '9','code': 'sh','name': '上海','map': '31.230393,121.473704'},'10': {'id': '10','code': 'gz','name': '广州','map': '23.129163,113.26443500000005'}}";
    //Map<String, City> citys = gson.fromJson(jsonStr, new TypeToken<Map<String, City>>() {}.getType());
    //System.out.println(citys.get("1").name+"----------"+citys.get("2").code);

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError
    {
        return mHeader;
    }
}
