package com.gary.garytool.business.volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.gary.garytool.R;
import com.gary.garytool.model.Weather;
import com.gary.garytool.model.WeatherInfo;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 1:StringRequest get post
 * 2:JsonRequest   JsonObjectRequest and JsonArrayRequest
 * 3:ImageRequest
 * 4:ImageLoader and LruCache
 * 5:NetworkImageView
 * 6:XMLRequest
 * 7:GsonRequest
 * 8:activity volley request 生命周期控制
 */
public class VolleyActivity extends Activity {
    static final String TAG = "VolleyActivity";
    static final String REQUESTTAG = "VolleyActivityGet";

    String mUrl = "http://www.baidu.com";
    String mJsonUrl = "http://m.weather.com.cn/atad/101230201.html";
    Button bt_volley_quest1;
    Button bt_volley_quest2;
    Button bt_volley_quest3;
    Button bt_volley_quest4;
    Button bt_volley_quest5;
    Button bt_volley_quest6;
    Button bt_volley_quest7;

    ImageView iv_volley;
    TextView tv_response;
    ImageLoader mImageLoader;
    NetworkImageView mNetworkImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);


        tv_response = (TextView) findViewById(R.id.tv_volley_string_response);
        iv_volley = (ImageView) findViewById(R.id.iv_volley);
        //每次都会new新的imageCahce和imageLoader,所以缓存的图片相当于每次都存到新的ImageCache中所以无法显示。
        //解决办法是在Application中设置全局的ImageLoader这样断网也可以获取上次的缓存图片。
        // 而且即使在屏幕旋转时，activity重新onCreate也可以读取缓存的图片，而不用再次通过网路获取了。
        mImageLoader = new ImageLoader(VolleyManger.getVolleyRequestQueue(), new BitmapCache());
        mNetworkImageView = (NetworkImageView) findViewById(R.id.iv_volley_networkimageview);

        //StringRequest demo
        bt_volley_quest1 = (Button) findViewById(R.id.bt_volley_quest1);
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
                //activity volley request 生命周期控制
                request.setTag(REQUESTTAG);
                VolleyManger.getVolleyRequestQueue().add(request);
            }
        });

        //JsonRequest demo
        bt_volley_quest2 = (Button) findViewById(R.id.bt_volley_quest2);
        bt_volley_quest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest request = new JsonObjectRequest(mJsonUrl, null, new Response.Listener<JSONObject>() {
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
                VolleyManger.getVolleyRequestQueue().add(request);
            }
        });

        //ImageRequest demo
        bt_volley_quest3 = (Button) findViewById(R.id.bt_volley_quest3);
        bt_volley_quest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://img.my.csdn.net/uploads/201407/17/1405567749_8669.jpg";
                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
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

                VolleyManger.getVolleyRequestQueue().add(imageRequest);
            }
        });

        //ImageLoader demo
        bt_volley_quest4 = (Button) findViewById(R.id.bt_volley_quest4);
        bt_volley_quest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv_volley, R.drawable.base_empty_view, R.drawable.pull_to_refresh_arrow);
                String url = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
                mImageLoader.get(url, listener);

            }
        });

        //NetworkImageView demo
        bt_volley_quest5 = (Button) findViewById(R.id.bt_volley_quest5);
        bt_volley_quest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNetworkImageView.setDefaultImageResId(R.drawable.base_empty_view);
                mNetworkImageView.setErrorImageResId(R.drawable.pull_to_refresh_arrow);
                String url = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
                mNetworkImageView.setImageUrl(url, mImageLoader);
            }
        });

        //XmlRequest demo
        bt_volley_quest6 = (Button) findViewById(R.id.bt_volley_quest6);
        bt_volley_quest6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://flash.weather.com.cn/wmaps/xml/china.xml";
                XMLRequest xmlRequest = new XMLRequest(url, new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser response) {
                        StringBuilder stringBuilder = new StringBuilder();
                        try {
                            int eventType = response.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        String nodeName = response.getName();
                                        if ("city".equals(nodeName)) {
                                            stringBuilder.append(response.getAttributeValue(0));
                                        }
                                        break;
                                }
                                eventType = response.next();
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        tv_response.setText(stringBuilder.toString());
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        tv_response.setText(volleyError.toString());
                    }
                });
                VolleyManger.getVolleyRequestQueue().add(xmlRequest);
            }
        });

        //GsonRequest demo
        bt_volley_quest7 = (Button) findViewById(R.id.bt_volley_quest7);
        bt_volley_quest7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.weather.com.cn/data/sk/101010100.html";
                //{"weatherinfo":{"city":"北京","cityid":"101010100","temp":"9","WD":"西南风","WS":"2级","SD":"22%","WSE":"2","time":"10:35","isRadar":"1","Radar":"JC_RADAR_AZ9010_JB","njd":"暂无实况","qy":"1015"}}
                GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(url, Weather.class, new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather weather) {

                        WeatherInfo weatherInfo = weather.getWeatherinfo();
                        tv_response.setText("city=" + weatherInfo.getCity() + ";temp=" + weatherInfo.getTemp() + ";time=" + weatherInfo.getTime());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        tv_response.setText(volleyError.toString());
                    }
                });

                VolleyManger.getVolleyRequestQueue().add(gsonRequest);
            }
        });



    }

    @Override
    protected void onStop() {
        super.onStop();
        //activity volley request 生命周期控制.退出activity时，清除所有请求。
        VolleyManger.getVolleyRequestQueue().cancelAll(REQUESTTAG);
    }
}
