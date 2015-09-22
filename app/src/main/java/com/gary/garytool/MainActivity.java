package com.gary.garytool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        mTextView= (TextView) findViewById(R.id.tv);
        mTextView.setText("我的个人框架组合 \n Volley \n xUtils \n Gson \n" +
                " PullToRefresh ");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BaseFrameworkActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        //采用FLAG_ACTIVITY_CLEAR_TOP退出整个程序
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
        }
        /* 个人补充
        1.可以把A设置成不可见的Acitivity（方法见下面），然后在它的onCreate方法里跳转到“真正”的载入界面就可以实现在D中点退出程序按钮时看上去立即退出程序的效果
        2.A必须是程序启动的第一个Activity才能起到这种立即退出的效果，因为Intent.FLAG_ACTIVITY_CLEAR_TOP只会把目标Activity的“上面”的Activity清理掉，
        而如果目标Activity的“下面”还有Activity（换句话说，目标Activity不在栈底），则finish后只会到他下面的那个Activity，而不是立即退出的效果了
        3.不可见Activity在项目的AndroidManifest.xml文件中相应的Activity标签中添加这样一行：android:theme=”@android:style/Theme.NoDisplay”这样一来，
        当这个Activity启动的时候，就不会显示出界面了。
        */
    }
}
