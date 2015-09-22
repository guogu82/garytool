package com.gary.garytool;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class VolleyTableLayoutActivity extends AppCompatActivity {

    private static HashSet<String> mStockIds=new HashSet<>();
    private static Vector<String> mSelectedStockItems=new Vector<>();
    private static final int mBackgroudColor= Color.WHITE;
    private static final int mHighlightColor=Color.rgb(210,233,255);
    private static final String mShIndex="sh000001";
    private static final String mSzIndex="sz000001";
    private static final String mChuangIndex="sz399006";
    private static final String mStockIdsKey="StockIds";
    private static final int mStockLargeTrade=1000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_table_layout);

        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        String idsStr=sharedPreferences.getString(mStockIdsKey,mShIndex+","+mSzIndex+","+mChuangIndex);

        String[] ids=idsStr.split(",");
        mStockIds.clear();
        for (String id:ids)
        {
            mStockIds.add(id);
        }

        Timer timer=new Timer("RefreshStocks");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshStocks();
            }
        },0,2000);
    }

    private void refreshStocks() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveStocksToPreferences();
    }

    private void saveStocksToPreferences() {
        String ids="";
        for (String id:mStockIds)
        {
            ids+=id;
            ids+=",";
        }

        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(mStockIdsKey,ids);
        editor.commit();
    }

    /**
     * 横竖屏切换的时候，可以记住添加的股票
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        saveStocksToPreferences();
        super.onSaveInstanceState(outState, outPersistentState);
    }

    // 浦发银行,15.06,15.16,15.25,15.27,14.96,15.22,15.24,205749026,3113080980,
    // 51800,15.22,55979,15.21,1404740,15.20,1016176,15.19,187800,15.18,300,15.24,457700,15.25,548900,15.26,712266,15.27,1057960,15.28,2015-09-10,15:04:07,00
    private class Stock
    {
        public String id_, name_;
        public String open_, yesterday_, now_, high_, low_;
        public String b1_, b2_, b3_, b4_, b5_;
        public String bp1_, bp2_, bp3_, bp4_, bp5_;
        public String s1_, s2_, s3_, s4_, s5_;
        public String sp1_, sp2_, sp3_, sp4_, sp5_;
        public String time_;
    }
}
