package com.gary.garytool.business.retrofit;




import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gary on 2016/6/2/002.
 */
public interface IUserBiz {
    @GET("api/bas/user/login")
    Call<ResponseBody> login(@Query("username") String username, @Query("userpwd") String userpwd, @Query("type") int type);

    //get?_value=&pageNumber=1&pageSize=40&masterid=13&createtime=2016-06-01~2016-06-03";
    @GET("api/order/order_cancel/get")
    Call<ResponseBody> getOrderCancel(@Query("_value") String keyword, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize,@Query("masterid") int masterid,@Query("createtime") String createtime);
}
