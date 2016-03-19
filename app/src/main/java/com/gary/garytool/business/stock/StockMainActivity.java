package com.gary.garytool.business.stock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gary.garytool.R;

public class StockMainActivity extends Activity implements View.OnClickListener {

    public static final String KEY = "fileDay";

    private Button mBtCollect;
    private TextView mTv;
    private EditText mEtToday;
    private EditText mEtStatistics;

    private StockManager stockManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_main);

        mBtCollect = (Button) findViewById(R.id.bt_collect);
        mBtCollect.setOnClickListener(this);

        mTv= (TextView) findViewById(R.id.tv);
        mEtToday= (EditText) findViewById(R.id.et_today);
        mEtStatistics= (EditText) findViewById(R.id.et_statistics);

        String fileDay=StockUtil.getFileDay(this,KEY);

        if(fileDay.length()>0)
        {
            mEtToday.setText(fileDay);
        }

        stockManager =new StockManager(this);
    }

    @Override
    public void onClick(View v) {
        String fileDay=mEtToday.getText().toString().trim();
        if(!checkDate(fileDay))
            return;

        //保存查询的日期
        StockUtil.saveFileDay(this,KEY,fileDay);

        //从sd卡读取外部本日数据，并写入sd卡的自身数据里面
        if(!stockManager.buildMyTodayStockData(fileDay,mTv))
            return;

        //从sd卡读取自身本日数据，并写入sd卡的分析数据里面
        stockManager.updateMyStockData(fileDay, mTv);
    }

    public void statistics(View view)
    {
        String fileDay=mEtToday.getText().toString().trim();
        if(!checkDate(fileDay))
            return;

        String statistic=mEtStatistics.getText().toString().trim();
        if(statistic.length()!=3) {
            mTv.setText("分析失败，分析规则不对。");
            return;
        }
        stockManager.statisticsStockData(fileDay,statistic, mTv);

    }

    private boolean checkDate(String fileDay)
    {
        boolean result=true;
        if(fileDay.trim().length()!=8)
            result=false;
        try {
            Integer.valueOf(fileDay);
            StockUtil.dayForWeek(fileDay);
        } catch (Exception e) {
            result=false;
            mTv.setText("您输入的日期不正确");
        }
        return result;
    }


}
