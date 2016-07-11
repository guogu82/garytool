package com.gary.garytool.function.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.business.retrofit.RetrofitManager;
import com.gary.garytool.util.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Gary on 2016/7/1/001.
 */
public class RxJavaActivity extends Activity {
    private static final String TAG = "RxJavaActivity";
    TextView tv_rxjava;
    Button bt_button;
    MovieService movieService;
    private static final String content = "RxJava 是一个响应式编程框架，采用观察者设计模式。";
    String BASE_URL="https://api.douban.com/v2/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_rxjava);
        tv_rxjava = (TextView) findViewById(R.id.tv_rxjava);
        tv_rxjava.setText(content);
        bt_button = (Button) findViewById(R.id.bt_button);
        movieService = RetrofitManager.getRetrofit(BASE_URL).create(MovieService.class);
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

        getMovie();
    }

    private void getMovie()
    {
        movieService.getTopMovie(0,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.e(TAG,"get top move Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        tv_rxjava.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        tv_rxjava.setText(movieEntity.toString());
                    }
                });
    }


}

