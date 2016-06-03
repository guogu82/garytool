package com.gary.garytool.business.retrofit;




import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gary on 2016/6/2/002.
 */
public interface ILoginHttp {
    @GET("api/bas/user/login")
    Call<ResponseBody> login(@Query("username") String username, @Query("userpwd") String userpwd, @Query("type") int type);

    @GET("api/bas/user/logout")
    Call<ResponseBody> logout(@Query("username") String username, @Query("userpwd") String userpwd, @Query("type") int type);

    }
