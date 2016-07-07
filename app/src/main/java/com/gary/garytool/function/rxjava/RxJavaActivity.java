package com.gary.garytool.function.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.business.retrofit.IOrderHttp;
import com.gary.garytool.business.retrofit.RetrofitManager;
import com.gary.garytool.util.LogUtil;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/1/001.
 */
public class RxJavaActivity extends Activity {
    TextView tv_rxjava;
    ApiManager.ApiManagerService apiManagerService;
    private static final String content = "RxJava 是一个响应式编程框架，采用观察者设计模式。";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_rxjava);
        tv_rxjava = (TextView) findViewById(R.id.tv_rxjava);
        tv_rxjava.setText(content);
        apiManagerService = RetrofitManager.getRetrofit(this).create(ApiManager.ApiManagerService.class);
    }

    public void getData(View view) {
        getWeatherRxjava();
    }

    private void getWeatherRxjava() {
        Observable<WeatherData> observable = apiManagerService.getWeatherRxjava("101010100");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i("wxl", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("wxl", "e=" + e.getMessage());
                    }

                    @Override
                    public void onNext(WeatherData weatherJson) {
                        LogUtil.i("wxl", "getWeatherinfo=" + weatherJson.toString());
                    }
                });

    }

}