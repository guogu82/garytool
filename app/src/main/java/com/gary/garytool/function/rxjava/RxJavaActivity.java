package com.gary.garytool.function.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.business.retrofit.IOrderHttp;
import com.gary.garytool.business.retrofit.RetrofitManager;
import com.gary.garytool.util.LogUtil;

import java.util.concurrent.TimeUnit;

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
    private static final String TAG = "RxJavaActivity";
    TextView tv_rxjava;
    Button bt_button;
    ApiManager.ApiManagerService apiManagerService;
    private static final String content = "RxJava 是一个响应式编程框架，采用观察者设计模式。";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_rxjava);
        tv_rxjava = (TextView) findViewById(R.id.tv_rxjava);
        tv_rxjava.setText(content);
        bt_button = (Button) findViewById(R.id.bt_button);
        apiManagerService = RetrofitManager.getRetrofit(this).create(ApiManager.ApiManagerService.class);
    }




    public void getData(View view) {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        LogUtil.e(TAG, "number:" + number);
                    }
                });

        //getWeatherRxjava();
    }

    private void getWeatherRxjava() {
        Observable<WeatherData> observable = apiManagerService.getWeatherData("guangzhou", "cn");
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

