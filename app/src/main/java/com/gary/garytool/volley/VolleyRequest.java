package com.gary.garytool.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gary.garytool.GaryApplication;

import java.util.Map;


/**
 * Created by Gary on 2015/9/19.
 */
public class VolleyRequest {
    private static StringRequest mRequest;
    private static Context mContext;

    public static void RequestGet(Context context, String url, String tag, VolleyInterface vif) {
        mContext = context;
        VolleyManger.getVolleyRequestQueue().cancelAll(tag);
        mRequest = new StringRequest(Request.Method.GET, url, vif.loadingListener(), vif.errorListener());
        mRequest.setTag(tag);
        VolleyManger.getVolleyRequestQueue().add(mRequest);
        VolleyManger.getVolleyRequestQueue().start();
    }

    public static void RequestPost(Context context, String url, String tag, final Map<String, String> params, VolleyInterface vif) {
        VolleyManger.getVolleyRequestQueue().cancelAll(tag);
        mRequest = new StringRequest(url, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        mRequest.setTag(tag);
        VolleyManger.getVolleyRequestQueue().add(mRequest);
        VolleyManger.getVolleyRequestQueue().start();
    }
}
