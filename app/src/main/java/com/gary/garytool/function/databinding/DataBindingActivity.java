package com.gary.garytool.function.databinding;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;

import com.gary.garytool.R;
import com.gary.garytool.base.BaseActivity;
import com.gary.garytool.databinding.FunctionActivityDatabindingBinding;

/**
 * Created by Gary on 2016/7/12/001.
 */
public class DataBindingActivity extends BaseActivity {
    private static final String TAG = "DataBindingActivity";
    private static final String content = "一些视图逻辑放在一个ViewModel里面，让很多view重用这段视图逻辑";
    private FunctionActivityDatabindingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.function_activity_databinding);
        fetchData();
    }

    private void fetchData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showLoadingDialog();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dismissLoadingDialog();
                User user=new User("gary","18928808780");
                binding.setUser(user);
            }
        }.execute();
    }


}

