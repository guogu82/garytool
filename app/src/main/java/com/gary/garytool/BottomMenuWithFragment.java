package com.gary.garytool;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


public class BottomMenuWithFragment extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bottom_menu_with_fragment);
    }


}
