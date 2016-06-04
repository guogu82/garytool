package com.gary.garytool.business.baseframe;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gary.garytool.R;
import com.gary.garytool.business.baseframe.model.OrderDTO;
import com.gary.garytool.business.retrofit.ILoginHttp;
import com.gary.garytool.business.retrofit.IOrderHttp;
import com.gary.garytool.business.retrofit.RetrofitManager;
import com.gary.garytool.util.LogUtil;


import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gary on 2016/4/18.
 * @author gary guo
 * 本类讲解 butterknife+Retrofit2+OkHttp3 的使用
 */
public class ListFragment extends Fragment {
    private static final String TAG = "ListFragment";
    OrderDTO orderHistory;
    OrderDTO orderCancel;
    @InjectView(R.id.btOrderCancel) Button btOrderCancel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.base_frame_list_fragment,container,false);
        ButterKnife.inject(this, view);
        btOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData2();
            }
        });
        return view;
    }


    @OnClick(R.id.btLogin)
    public void login(View view) {
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

    @OnClick(R.id.btOrderHistory)
    public void getOrderHistory(View view) {
        getData();
    }

    public void getData()
    {
        IOrderHttp orderHttp =RetrofitManager.getRetrofit(getActivity()).create(IOrderHttp.class);
        orderHttp.getOrderHistory("粤R",1,40,13,"2016-05-12~2016-05-22").enqueue(new Callback<OrderDTO>()
        {

            @Override
            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {

                    if(response.body()==null)
                        return;
                    orderHistory=response.body();
                LogUtil.e(TAG, "ResponseBody: history" + orderHistory.getRows().size()+ "");
            }

            @Override
            public void onFailure(Call<OrderDTO> call, Throwable t) {
                LogUtil.e(TAG, "ResponseBody: error" + t.getMessage() + "");
            }
        });

    }

    private void getData2() {
        IOrderHttp orderHttp =RetrofitManager.getRetrofit(getActivity()).create(IOrderHttp.class);
        orderHttp.getOrderCancel("",1,40,13,"2016-05-02~2016-05-30").enqueue(new Callback<OrderDTO>()
        {

            @Override
            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                if(response.body()==null)
                    return;

                orderCancel=response.body();
                LogUtil.e(TAG, "ResponseBody: orderCancel" + orderCancel.getRows().size()+ "");

            }

            @Override
            public void onFailure(Call<OrderDTO> call, Throwable t) {
                LogUtil.e(TAG, t.getMessage());
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
