package com.gary.garytool.function.toast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.gary.garytool.R;

/**
 * Created by Gary on 2016/6/30/015.
 */
public class ToastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_toast);
    }

    public void onClickDefaultToast(View v) {
        Toast.makeText(this, "默认位置的Toast", Toast.LENGTH_LONG).show();
    }

    public void onClickCenterToast(View v) {
        Toast toast = Toast.makeText(this, "居中位置的Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void onClickTopToast(View v) {
        Display display = getWindowManager().getDefaultDisplay();
        // 获取屏幕高度
        int height = display.getHeight();
        Toast toast = Toast.makeText(this, "居中上部位置的Toast", Toast.LENGTH_LONG);
        // 这里给了一个1/4屏幕高度的y轴偏移量
        toast.setGravity(Gravity.TOP, 0, height / 4);
        toast.show();
    }
}
