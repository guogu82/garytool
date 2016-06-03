package com.gary.garytool.business.baseframe;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gary.garytool.R;
import com.gary.garytool.business.retrofit.IUserBiz;
import com.gary.garytool.lib.libpersistentcookiejar.com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.gary.garytool.lib.libpersistentcookiejar.com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.gary.garytool.lib.libpersistentcookiejar.com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.gary.garytool.lib.libretrofit.RetrofitManager;
import com.gary.garytool.util.LogUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by gary on 2016/4/18.
 * @author gary guo
 * 本类讲解 RecycleView 的使用
 */
public class ListFragment extends Fragment {
    private static final String TAG = "ListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.base_frame_list_fragment,container,false);
        Button btnYesterday= (Button) view.findViewById(R.id.btnYesterday);

         btnYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }


        });
        Button btnThisWeek= (Button) view.findViewById(R.id.btnThisWeek);

        btnThisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        Button btnThisMonth= (Button) view.findViewById(R.id.btnThisMonth);
        btnThisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData2();
            }
        });

        return view;
    }

    private void getData2() {
        Call<ResponseBody> call =RetrofitManager.getRetrofit(getActivity()).create(IUserBiz.class).getOrderCancel("",1,40,13,"2016-05-02~2016-05-30");
        call.enqueue(new Callback<ResponseBody>()
        {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body()==null)
                    {
                        LogUtil.e(TAG, "ResponseBody: is null");
                        return;
                    }

                    LogUtil.e(TAG, "ResponseBody:" + response.body().string() + "");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtil.e(TAG, "ResponseBody:" + t.getMessage() + "");
            }
        });;

    }

    private void login() {
        //login?username=王浩远&userpwd=123&type=0
        //{"status":false,"msg_type":1,"msg_code":null,"msg":"技师才能使用该系统","state":0,"data":null}
        RetrofitManager.getRetrofit(getActivity()).create(IUserBiz.class).login("王浩远","123",0).enqueue(new Callback<ResponseBody>()
        {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    LogUtil.e(TAG, "ResponseBody:" + response.body().string() + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtil.e(TAG, "ResponseBody:" + t.getMessage() + "");
            }
        });

    }

    public void getData()
    {
        Call<ResponseBody> call =RetrofitManager.getRetrofit(getActivity()).create(IUserBiz.class).getOrderCancel("",1,40,13,"2016-05-12~2016-05-22");
        call.enqueue(new Callback<ResponseBody>()
        {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body()==null)
                    {
                        LogUtil.e(TAG, "ResponseBody: is null");
                        return;
                    }

                    LogUtil.e(TAG, "ResponseBody:" + response.body().string() + "");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtil.e(TAG, "ResponseBody:" + t.getMessage() + "");
            }
        });

    }



}
