package com.gary.garytool.function;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.gary.garytool.R;

/**
 * Created by Administrator on 2016/7/1/001.
 */
public class BaasActivity extends Activity{
    TextView tv_saas;
    private static final String content="开发一个具有网络功能的移动应用，除了要开发客户端，还要开发服务端，还需要服务器。\n" +
            "为了简化移动应用的开发和部署，让开发者只专注于客户端的开发，而将后端服务整合成API提供给开发者调用，这就是BaaS（Backend as a Service）。\n" +
            "目前，国外至少已经有二十多家企业进入了这个领域，其中，提供的后端服务比较全面的有StackMob、Parse、Kinvey。\n" +
            "而国内，据我所知的，到目前为止只有三个平台，Bmob、AMTBaaS，还有我们的Xone。";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_baas);
        tv_saas= (TextView) findViewById(R.id.tv_baas);
        tv_saas.setText(content);
    }
}
