package com.gary.garytool.business.tourist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.gary.garytool.R;

/**
 * Created by Administrator on 2016/3/30.
 */
public class TouristSettingActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_setting_activity);
    }

    public void toPersonalInformation(View view)
    {
        finish();
    }
}
