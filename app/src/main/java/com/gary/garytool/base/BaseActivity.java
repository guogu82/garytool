package com.gary.garytool.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by gary on 2016/6/30/030.
 */
public class BaseActivity extends Activity {

    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 显示loading
     */
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setMessage("数据加载中,请稍后...");
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
        } else if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * 取消显示loading
     */
    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }
}
