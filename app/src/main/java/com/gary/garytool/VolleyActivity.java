package com.gary.garytool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gary.garytool.util.LogUtil;


public class VolleyActivity extends Activity {
    String mUrl="http://www.baidu.com";
    Button bt_volley_quest1;
    TextView tv_response;
    RequestQueue mQueue;
    static final String TAG="VolleyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        if(mQueue==null) {
            mQueue = Volley.newRequestQueue(this);
        }
        bt_volley_quest1= (Button) findViewById(R.id.bt_volley_quest1);
        tv_response= (TextView) findViewById(R.id.tv_volley_string_response);
        bt_volley_quest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest request = new StringRequest(mUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        tv_response.setText(s);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        tv_response.setText(volleyError.toString());
                    }
                });
                mQueue.add(request);
            }
        });


    }

}
