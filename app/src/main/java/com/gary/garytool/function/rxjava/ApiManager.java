package com.gary.garytool.function.rxjava;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * 接口
 * Created by Hal on 15/4/26.
 */
public class ApiManager {

    private static final String ENDPOINT = "http://api.openweathermap.org/data/2.5";


    /**
     * 服务接口
     */
    public interface ApiManagerService {
        @GET("/weather")
        WeatherData getWeather(@Query("q") String place, @Query("units") String units);

        /**
         * retrofit 支持 rxjava 整合
         * 这种方法适用于新接口
         */
        @GET("/weather")
        Observable<WeatherData> getWeatherData(@Query("q") String place, @Query("units") String units);

        @GET("adat/sk/{cityId}.html")
        Observable<WeatherData> getWeatherRxjava(@Path("cityId") String cityId);

    }

    //private static final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).setLogLevel(RestAdapter.LogLevel.FULL).build();

    //private static final ApiManagerService apiManager = restAdapter.create(ApiManagerService.class);

    /**
     * 将服务接口返回的数据，封装成{@link Observable}
     * 这种写法适用于将旧代码封装
     * @param city
     * @return
     */
    public static Observable<WeatherData> getWeatherData(final String city) {
        return Observable.create(new Observable.OnSubscribe<WeatherData>() {
            @Override
            public void call(Subscriber<? super WeatherData> subscriber) {
                //订阅者回调 onNext 和 onCompleted
                //subscriber.onNext(apiManager.getWeather(city, "metric"));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }
}
