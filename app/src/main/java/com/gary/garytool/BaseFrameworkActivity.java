package com.gary.garytool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gary.garytool.info.Student;
import com.gary.garytool.util.RandomUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;


/**
 * 1:PullToRefresh https://github.com/chrisbanes/Android-PullToRefresh
 * 2:Volley 用于网络请求.缺点-它不适合数据的上传和下载。大文件上传。 https://github.com/Tim9Liu9/volley_demo
 * 3:XUtils 用于数据库也支持大文件上传 https://github.com/wyouflf/xUtils
 */
public class BaseFrameworkActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


    }




}
