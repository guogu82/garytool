package com.gary.garytool.volley;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;


/**
 * Created by Gary on 2015/9/19.
 */
public abstract class VolleyInterface {
    private Context mContext;
    private Response.Listener<String> mListener;
    private Response.ErrorListener mErrorListener;
    public VolleyInterface(Context context)
    {
        mContext=context;
    }
    public abstract void onMySuccess(String result);
    public abstract void onMyError(VolleyError error);

    public Response.Listener<String> loadingListener()
    {
        mListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
onMySuccess(s);
            }
        };
                return mListener;
    }

    public Response.ErrorListener errorListener()
    {
        mErrorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
onMyError(volleyError);
            }
        };
        return  mErrorListener;
    }
}
