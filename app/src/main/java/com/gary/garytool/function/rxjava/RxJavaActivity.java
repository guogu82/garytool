package com.gary.garytool.function.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.business.retrofit.RetrofitManager;
import com.gary.garytool.util.LogUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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

    //http://www.jianshu.com/p/cb837b531a31
    //subscribeOn是影响生产者（Observable）生产数据的线程的，通常我们只需要指定生产者在某一个特定的线程生产数据就可以满足我们的需求，
    // 至少我还没遇到过需要在生产数据的过程中去切换生产者所在的线程的情况。
    // 绝大多数我们需要变化线程的场景都是在数据生产之后，Rx里面就使用observeOn来指定各种operator和subcriber的线程，因为这些本质上都是数据的消费者。
    // 消费者可以任意切换自己接受处理数据的线程，足以满足我们的需求。

    //在不指定线程的情况下，RxJava遵循的是线程不变的原则，即：在哪个线程调用 subscribe(),就在哪个线程生产事件；
    // 在哪个线程生产事件，就在哪个线程消费事件。如果需要切换线程，就需要用到Scheduler(调度器),下面是Scheduler的API：
    //chedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
    //Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
    //Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
    //Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
    //Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
    //有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。
    //subscribeOn(): 指定subscribe()所发生的线程，即 Observable.OnSubscribe被激活时所处的线程。或者叫做事件产生的线程。
    //observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。


}

