package com.gary.garytool.business.baseframe;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gary.garytool.R;
import com.gary.garytool.business.retrofit.ILoginHttp;
import com.gary.garytool.business.retrofit.IOrderHttp;
import com.gary.garytool.business.retrofit.RetrofitManager;
import com.gary.garytool.util.LogUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        IOrderHttp orderHttp =RetrofitManager.getRetrofit(getActivity()).create(IOrderHttp.class);
        orderHttp.getOrderCancel2("",1,40,13,"2016-05-02~2016-05-30").enqueue(new Callback<Map<String, Object>>()
        {

            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if(response.body()==null)
                    {
                        LogUtil.e(TAG, "ResponseBody: is null");
                        return;
                    }
                    LogUtil.e(TAG, "ResponseBody:" + response.body().get("rows") + "");


            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                LogUtil.e(TAG, "ResponseBody Error:" + t.getMessage() + "");
            }
        });;

    }

    private void login() {
        ILoginHttp loginHttp=RetrofitManager.getRetrofit(getActivity()).create(ILoginHttp.class);
        loginHttp.login("王浩远","123",0).enqueue(new Callback<ResponseBody>()
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
        IOrderHttp orderHttp =RetrofitManager.getRetrofit(getActivity()).create(IOrderHttp.class);
        orderHttp.getOrderCancel("",1,40,13,"2016-05-12~2016-05-22").enqueue(new Callback<ResponseBody>()
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
