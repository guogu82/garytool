package com.gary.garytool;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.gary.garytool.volley.BitmapCache;

import org.json.JSONObject;

/**
 * 1:StringRequest get post
 * 2:JsonRequest   JsonObjectRequest and JsonArrayRequest
 * 3:ImageRequest
 * 4:ImageLoader
 * 5:NetworkImageView
 * 6:XMLRequest
 * 7:GsonRequest
 * 8:LruCache
 */
public class VolleyActivity extends Activity {
    static final String TAG="VolleyActivity";

    String mUrl="http://www.baidu.com";
    String mJsonUrl="http://m.weather.com.cn/atad/101230201.html";
    Button bt_volley_quest1;
    Button bt_volley_quest2;
    Button bt_volley_quest3;
    Button bt_volley_quest4;
    Button bt_volley_quest5;

    ImageView iv_volley;
    TextView tv_response;
    ImageLoader mImageLoader;
    NetworkImageView mNetworkImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);


        tv_response= (TextView) findViewById(R.id.tv_volley_string_response);
        iv_volley= (ImageView) findViewById(R.id.iv_volley);
        //每次都会new新的imageCahce和imageLoader,所以缓存的图片相当于每次都存到新的ImageCache中所以无法显示。
        //解决办法是在Application中设置全局的ImageLoader这样断网也可以获取上次的缓存图片。
        // 而且即使在屏幕旋转时，activity重新onCreate也可以读取缓存的图片，而不用再次通过网路获取了。
        mImageLoader=new ImageLoader(GaryApplication.getVolleyRequestQueue(), new BitmapCache());
        mNetworkImageView= (NetworkImageView) findViewById(R.id.iv_volley_networkimageview);

        bt_volley_quest1= (Button) findViewById(R.id.bt_volley_quest1);
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
                GaryApplication.getVolleyRequestQueue().add(request);
            }
        });

        bt_volley_quest2= (Button) findViewById(R.id.bt_volley_quest2);
        bt_volley_quest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest request=new JsonObjectRequest(mJsonUrl, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        tv_response.setText(jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        tv_response.setText(volleyError.toString());
                    }
                });
                GaryApplication.getVolleyRequestQueue().add(request);
            }
        });

        bt_volley_quest3= (Button) findViewById(R.id.bt_volley_quest3);
        bt_volley_quest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://img.my.csdn.net/uploads/201407/17/1405567749_8669.jpg";
                ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        iv_volley.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        iv_volley.setImageResource(R.drawable.base_empty_view);
                    }
                });

                GaryApplication.getVolleyRequestQueue().add(imageRequest);
            }
        });

        bt_volley_quest4= (Button) findViewById(R.id.bt_volley_quest4);
        bt_volley_quest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv_volley, R.drawable.base_empty_view, R.drawable.pull_to_refresh_arrow);
                String url = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
                mImageLoader.get(url, listener);

            }
        });

bt_volley_quest5= (Button) findViewById(R.id.bt_volley_quest5);
        bt_volley_quest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNetworkImageView.setDefaultImageResId(R.drawable.base_empty_view);
                mNetworkImageView.setErrorImageResId(R.drawable.pull_to_refresh_arrow);
                String url="http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
                mNetworkImageView.setImageUrl(url,mImageLoader);
            }
        });

    }

}
