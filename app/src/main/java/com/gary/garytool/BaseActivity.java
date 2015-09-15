package com.gary.garytool;

import android.app.Activity;
import android.os.Bundle;


/**
 *1: PullToRefresh https://github.com/chrisbanes/Android-PullToRefresh
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

}
