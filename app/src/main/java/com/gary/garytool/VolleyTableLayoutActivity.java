package com.gary.garytool;

import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gary.garytool.util.LogUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Vector;

public class VolleyTableLayoutActivity extends AppCompatActivity {

    private static final String TAG = "VolleyTableLayoutActivity";
    private static HashSet<String> mStockIds = new HashSet<>();
    private static Vector<String> mSelectedStockItems = new Vector<>();
    private static final int mBackgroudColor = Color.WHITE;
    private static final int mHighlightColor = Color.rgb(210, 233, 255);
    private static final String mShIndex = "sh000001";
    private static final String mSzIndex = "sz399001";
    private static final String mChuangIndex = "sz399006";
    private static final String mStockIdsKey = "StockIds";
    private static final int mStockLargeTrade = 1000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_table_layout);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String idsStr = sharedPreferences.getString(mStockIdsKey, mShIndex + "," + mSzIndex + "," + mChuangIndex);

        String[] ids = idsStr.split(",");
        mStockIds.clear();
        for (String id : ids) {
            mStockIds.add(id);
        }

        Timer timer = new Timer("RefreshStocks");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshStocks();
            }
        }, 0, 10000);
    }

    private void refreshStocks() {
        String ids = "";
        for (String id : mStockIds) {
            ids += id;
            ids += ",";
        }
        querySinaStocks(ids);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveStocksToPreferences();
    }

    private void saveStocksToPreferences() {
        String ids = "";
        for (String id : mStockIds) {
            ids += id;
            ids += ",";
        }

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mStockIdsKey, ids);
        editor.commit();
    }

    /**
     * 横竖屏切换的时候，可以记住添加的股票
     *
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        saveStocksToPreferences();
        super.onSaveInstanceState(outState, outPersistentState);
    }


    //var hq_str_sz000671="阳 光 城,5.62,5.75,5.64,5.69,5.47,5.64,5.65,31011480,172984458.16,202830,5.64,1100,5.62,187000,5.60,3500,5.59,8300,5.58,382081,5.65,67900,5.66,90500,5.67,307541,5.68,254175,5.69,2015-09-23,15:05:55,00";
    private class Stock {
        public String id_, name_;
        public String open_, yesterday_, now_, high_, low_;
        public String b1_, b2_, b3_, b4_, b5_;
        public String bp1_, bp2_, bp3_, bp4_, bp5_;
        public String s1_, s2_, s3_, s4_, s5_;
        public String sp1_, sp2_, sp3_, sp4_, sp5_;
        public String time_;
    }

    public TreeMap<String, Stock> sinaResponseToStocks(String response) {
        LogUtil.d(TAG, response);
        response = response.replaceAll("\n", "");
        String[] stocks = response.split(";");

        TreeMap<String, Stock> stockMap = new TreeMap<>();
        for (String stock : stocks) {
            String[] leftRight = stock.split("=");
            if (leftRight.length < 2)
                continue;
            String right = leftRight[1].replaceAll("\"", "");
            if (right.isEmpty())
                continue;
            String left = leftRight[0];
            if (left.isEmpty())
                continue;

            Stock stockNow = new Stock();
            stockNow.id_ = left.split("_")[2];

            String[] values = right.split(",");
            stockNow.name_ = values[0];
            stockNow.open_ = values[1];
            stockNow.yesterday_ = values[2];
            stockNow.now_ = values[3];
            stockNow.high_ = values[4];
            stockNow.low_ = values[5];
            stockNow.b1_ = values[10];
            stockNow.b2_ = values[12];
            stockNow.b3_ = values[14];
            stockNow.b4_ = values[16];
            stockNow.b5_ = values[18];
            stockNow.bp1_ = values[11];
            stockNow.bp2_ = values[13];
            stockNow.bp3_ = values[15];
            stockNow.bp4_ = values[17];
            stockNow.bp5_ = values[19];
            stockNow.s1_ = values[20];
            stockNow.s2_ = values[22];
            stockNow.s3_ = values[24];
            stockNow.s4_ = values[26];
            stockNow.s5_ = values[28];
            stockNow.sp1_ = values[21];
            stockNow.sp2_ = values[23];
            stockNow.sp3_ = values[25];
            stockNow.sp4_ = values[27];
            stockNow.sp5_ = values[29];
            stockNow.time_ = values[values.length - 3] + "_" + values[values.length - 2];
            stockMap.put(stockNow.id_, stockNow);
        }
        return stockMap;
    }

    public void querySinaStocks(String list) {
        //http://hq.sinajs.cn/list=sh600000,sh600536
        String url = "http://hq.sinajs.cn/list=" + list;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                updateStockListView(sinaResponseToStocks(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        GaryApplication.getVolleyRequestQueue().add(stringRequest);
    }


    public void addStock(View view) {
        EditText editText = (EditText) findViewById(R.id.et_stock_id);
        String stockId = editText.getText().toString();
        if (stockId.length() != 6)
            return;

        if (stockId.startsWith("6")) {
            stockId = "sh" + stockId;
        } else if (stockId.startsWith("0") || stockId.startsWith("3")) {
            stockId = "sz" + stockId;
        } else {
            return;
        }
        mStockIds.add(stockId);
    }

    public void sendNotification(int id, String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pull_to_refresh_arrow);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setVibrate(new long[]{100, 100, 100});
        builder.setLights(Color.RED, 1000, 1000);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());

    }

    private void updateStockListView(TreeMap<String, Stock> stockMap) {
        //Table
        TableLayout table = (TableLayout) findViewById(R.id.tl_stock_table);
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);
        table.removeAllViews();

        //Title
        TableRow rowTitle = new TableRow(this);
        TextView nameTitle = new TextView(this);
        nameTitle.setText("名称");
        rowTitle.addView(nameTitle);
        TextView nowTitle = new TextView(this);
        nowTitle.setGravity(Gravity.CENTER);
        nowTitle.setText("最新");
        rowTitle.addView(nowTitle);
        TextView percentTitle = new TextView(this);
        percentTitle.setGravity(Gravity.CENTER);
        percentTitle.setText("涨幅");
        rowTitle.addView(percentTitle);
        TextView increaseTitle = new TextView(this);
        increaseTitle.setGravity(Gravity.CENTER);
        increaseTitle.setText("涨跌");
        rowTitle.addView(increaseTitle);
        table.addView(rowTitle);

        Collection<Stock> stocks = stockMap.values();
        for (Stock stock : stocks) {
            //如果是上证指数，深成指数，创业指数 则更新头部的标签
            if (stock.id_.equals(mShIndex) || stock.id_.equals(mSzIndex) || stock.id_.equals(mChuangIndex)) {
                Double dNow = Double.parseDouble(stock.now_);
                Double dYesterday = Double.parseDouble(stock.yesterday_);
                Double dIncrease = dNow - dYesterday;
                Double dPercent = dIncrease / dYesterday * 100;
                String change = String.format("%.2f", dPercent) + "%" + String.format("%.2f", dIncrease);

                int indexId;
                int changeId;
                if (stock.id_.equals(mShIndex)) {
                    indexId = R.id.tv_stock_sh_index;
                    changeId = R.id.tv_stock_sh_change;
                } else if (stock.id_.equals(mSzIndex)) {
                    indexId = R.id.tv_stock_sz_index;
                    changeId = R.id.tv_stock_sz_change;
                } else {
                    indexId = R.id.tv_stock_chuang_index;
                    changeId = R.id.tv_stock_chuang_change;
                }

                TextView indexText = (TextView) findViewById(indexId);
                indexText.setText(stock.now_);
                int color = Color.BLACK;
                if (dIncrease > 0) {
                    color = Color.RED;
                } else if (dIncrease < 0) {
                    color = Color.GREEN;
                }

                indexText.setTextColor(color);
                TextView changeText = (TextView) findViewById(changeId);
                changeText.setText(change);
                continue;
            }

            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER_VERTICAL);
            if (mSelectedStockItems.contains(stock.id_)) {
                row.setBackgroundColor(mHighlightColor);
            }

            //列表左边的股票名称和代码
            LinearLayout nameId = new LinearLayout(this);
            nameId.setOrientation(LinearLayout.VERTICAL);
            TextView name = new TextView(this);
            name.setText(stock.name_);
            nameId.addView(name);
            TextView id = new TextView(this);
            id.setTextSize(10);
            id.setText(stock.id_);
            nameId.addView(id);
            row.addView(nameId);

            TextView now = new TextView(this);
            now.setGravity(Gravity.RIGHT);
            now.setText(stock.now_);
            row.addView(now);

            TextView percent = new TextView(this);
            percent.setGravity(Gravity.RIGHT);
            TextView increaseValue = new TextView(this);
            increaseValue.setGravity(Gravity.RIGHT);
            Double dOpen = Double.parseDouble(stock.open_);
            Double dB1 = Double.parseDouble(stock.bp1_);
            Double dS1 = Double.parseDouble(stock.sp1_);
            if (dOpen == 0 && dB1 == 0 && dS1 == 0) {
                percent.setText("--");
                increaseValue.setText("--");
            } else {
                Double dNow = Double.parseDouble(stock.now_);
                if (dNow == 0) {
                    if (dS1 == 0) {
                        dNow = dB1;
                        now.setText(stock.bp1_);
                    } else {
                        dNow = dS1;
                        now.setText(stock.sp1_);
                    }
                }
                Double dYesterday = Double.parseDouble(stock.yesterday_);
                Double dIncrease = dNow - dYesterday;
                Double dPercent = dIncrease / dYesterday * 100;
                percent.setText(String.format("%.2f", dPercent) + "%");
                increaseValue.setText(String.format("%.2f", dIncrease));
                int color = Color.BLACK;
                if (dIncrease > 0) {
                    color = Color.RED;
                } else if (dIncrease < 0) {
                    color = Color.GREEN;
                }
                now.setTextColor(color);
                percent.setTextColor(color);
                increaseValue.setTextColor(color);
            }

            row.addView(percent);
            row.addView(increaseValue);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup group = (ViewGroup) v;
                    ViewGroup nameId = (ViewGroup) group.getChildAt(0);
                    TextView idText = (TextView) nameId.getChildAt(1);
                    if (mSelectedStockItems.contains(idText.getText().toString())) {
                        v.setBackgroundColor(mBackgroudColor);
                        mSelectedStockItems.remove(idText.getText().toString());
                    } else {
                        v.setBackgroundColor(mHighlightColor);
                        mSelectedStockItems.add(idText.getText().toString());
                    }
                }
            });

            table.addView(row);

            String sid = stock.id_;
            sid = sid.replaceAll("sh", "");
            sid = sid.replaceAll("sz", "");
            String text = "";
            String sBuy = "买";
            //String sBuy = getResources().getString(R.string.stock_buy);
            String sSell = "卖";
            if (Double.parseDouble(stock.b1_) >= mStockLargeTrade) {
                text += sBuy + "1:" + stock.b1_ + ",";
            }
            if (Double.parseDouble(stock.b2_) >= mStockLargeTrade) {
                text += sBuy + "2:" + stock.b2_ + ",";
            }
            if (Double.parseDouble(stock.b3_) >= mStockLargeTrade) {
                text += sBuy + "3:" + stock.b3_ + ",";
            }
            if (Double.parseDouble(stock.b4_) >= mStockLargeTrade) {
                text += sBuy + "4:" + stock.b4_ + ",";
            }
            if (Double.parseDouble(stock.b5_) >= mStockLargeTrade) {
                text += sBuy + "5:" + stock.b5_ + ",";
            }
            if (Double.parseDouble(stock.s1_) >= mStockLargeTrade) {
                text += sSell + "1:" + stock.s1_ + ",";
            }
            if (Double.parseDouble(stock.s2_) >= mStockLargeTrade) {
                text += sSell + "2:" + stock.s2_ + ",";
            }
            if (Double.parseDouble(stock.s3_) >= mStockLargeTrade) {
                text += sSell + "3:" + stock.s3_ + ",";
            }
            if (Double.parseDouble(stock.s4_) >= mStockLargeTrade) {
                text += sSell + "4:" + stock.s4_ + ",";
            }
            if (Double.parseDouble(stock.s5_) >= mStockLargeTrade) {
                text += sSell + "5:" + stock.s5_ + ",";
            }
            if (text.length() > 0)
                sendNotification(Integer.parseInt(sid), stock.name_, text);
        }


    }
}
