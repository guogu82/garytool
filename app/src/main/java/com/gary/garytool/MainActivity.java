package com.gary.garytool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gary.garytool.business.guessmusic.GuessMusicMainActivity;
import com.gary.garytool.business.insane.MainInsaneActivity;
import com.gary.garytool.business.map.MapMainActivity;
import com.gary.garytool.business.stock.StockMainActivity;
import com.gary.garytool.util.Util;
import com.gary.garytool.view.listview.ListViewActivity;
import com.gary.garytool.view.pulltorefresh.PullToRefreshListViewActivity;


public class MainActivity extends Activity {


    public static void  main(String[] args)
    {
        System.out.print("hello garyts");
        System.out.print(2<<3);
    }

    Button mFunctions;
    TextView mTextView;
    Button mPullToRefresh;
    Button bt_insane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFunctions = (Button) findViewById(R.id.bt_functions);
        mFunctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        mTextView= (TextView) findViewById(R.id.tv);
        mTextView.setText("个人框架 \n Volley \n XUtils \n" +
                " PullToRefresh ");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BaseFrameworkActivity.class);
                startActivity(intent);
            }
        });
        //PullToRefresh 控件专题
        //https://github.com/chrisbanes/Android-PullToRefresh
        //http://blog.csdn.net/lmj623565791/article/details/38238749
        mPullToRefresh= (Button) findViewById(R.id.bt_pull_to_refresh);
        mPullToRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PullToRefreshListViewActivity.class);
                startActivity(intent);
            }
        });

        bt_insane= (Button) findViewById(R.id.bt_insane);
        bt_insane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MainInsaneActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 进入股票分析功能
     * @param view
     */
    public void stockMain(View view)
    {
        Intent intent=new Intent(MainActivity.this, StockMainActivity.class);
        startActivity(intent);
    }

    public void guessMusic(View view)
    {
        Intent intent=new Intent(MainActivity.this, GuessMusicMainActivity.class);
        startActivity(intent);
    }

    public void map(View view)
    {
        Util.startActivity(MainActivity.this, MapMainActivity.class);
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
