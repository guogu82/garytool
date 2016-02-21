package com.gary.garytool.stock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gary.garytool.R;

public class StockMainActivity extends Activity implements View.OnClickListener {

    private Button btn;
    private TextView mTv;
    private EditText mEtToday;

    private StockManager stockManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_main);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        mTv= (TextView) findViewById(R.id.txt);
        mEtToday= (EditText) findViewById(R.id.et_today);

        stockManager =new StockManager(this);
    }

    @Override
    public void onClick(View v) {
        stockManager.buildMyTodayStockData(mEtToday.getText().toString().trim());
        mTv.setText(mEtToday.getText().toString().trim()+"归集完毕");
    }

    public void update(View view)
    {
        //从sd卡读取本日数据，并写入sd卡的分析数据里面
        stockManager.updateMyStockData(mEtToday.getText().toString().trim());
        mTv.setText(mEtToday.getText().toString().trim()+"分析完毕");
    }
}
