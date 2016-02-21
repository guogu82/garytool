package com.gary.garytool.stock;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.gary.garytool.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gary on 2016/2/20.
 */
public class StockManager {
    public static final String TAG = "StockManager";
    public static final String SEPARATOR_ATTR_TAG = "\t";
    public static final String SEPARATOR_OBJECT_TAG = "\n";
    public static final String NO_VALUE = "--";
    public static final String CHARACTER = "UTF-8";

    private Context mContext;
    private String pathName = "/sdcard/stock/";



    private String fileNameStock =  "stock";
    private String fileNameStockMy =  "stockMy";
    private String fileNameAnalysis =  "stockAnalysis";
    private String fileNameAnalysisYesterday =  "stockAnalysis";


    public StockManager(Context context) {
        this.mContext = context;
    }

    //分析数据整理
    public void updateMyStockData(String fileToday) {
        //读取历史分析数据
        int yestesday = Integer.valueOf(fileToday) - 1;
        List<StockInfoForAnalysis> stockInfoForAnalysises = getYesterdayAnalysisStockInfo(yestesday+"");

        //读取本日最新数据
        List<StockInfo> stockInfos = getTodayStockInfo(fileToday);

        //更新最新分析数据
        StringBuffer sb = new StringBuffer("");
        if (stockInfoForAnalysises.size() == 0) {
            for (StockInfo stock : stockInfos) {
                sb.append(stock.getCode() + SEPARATOR_ATTR_TAG + stock.getName() + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + stock.getTurnoverRate() + SEPARATOR_OBJECT_TAG);
            }
        } else {
            for (StockInfoForAnalysis stock : stockInfoForAnalysises) {
                sb.append(stock.getCode() + SEPARATOR_ATTR_TAG);
                sb.append(stock.getName() + SEPARATOR_ATTR_TAG);
                sb.append(stock.getBeforeFourRate() + SEPARATOR_ATTR_TAG);
                sb.append(stock.getBeforeThreeTurnoverRate() + SEPARATOR_ATTR_TAG);
                sb.append(stock.getBeforeTwoTurnoverRate() + SEPARATOR_ATTR_TAG);
                sb.append(stock.getBeforeOneTurnoverRate() + SEPARATOR_ATTR_TAG);
                sb.append(stock.getTodayTurnoverRate() + SEPARATOR_ATTR_TAG);
                for (StockInfo stockinfo : stockInfos) {
                    if(stockinfo.getCode().equals(stock.getCode()))
                    {
                        sb.append(stockinfo.getTurnoverRate());
                    }
                }
                //一行结束，需要换行。
                sb.append(SEPARATOR_OBJECT_TAG);
            }
        }

        writeSDFile(fileToday+fileNameAnalysis, sb.toString());

    }

    private List<StockInfoForAnalysis> getYesterdayAnalysisStockInfo(String yestesday) {
        List<StockInfoForAnalysis> stockInfos = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(pathName + yestesday+fileNameAnalysisYesterday), CHARACTER);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] stockAttrs = line.split(SEPARATOR_ATTR_TAG);
                StockInfoForAnalysis info;
                if (stockAttrs.length > 7) {
                    info = new StockInfoForAnalysis();
                    info.setCode(stockAttrs[0]);
                    info.setName(stockAttrs[1]);
                    info.setBeforeFiveTurnoverRate(stockAttrs[2]);
                    info.setBeforeFourRate(stockAttrs[3]);
                    info.setBeforeThreeTurnoverRate(stockAttrs[4]);
                    info.setBeforeTwoTurnoverRate(stockAttrs[5]);
                    info.setBeforeOneTurnoverRate(stockAttrs[6]);
                    info.setTodayTurnoverRate(stockAttrs[7]);
                    stockInfos.add(info);
                }
            }
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(mContext, TAG + ":" + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(mContext, TAG + ":" + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return stockInfos;
    }

    private List<StockInfo> getTodayStockInfo(String today) {
        List<StockInfo> stockInfos = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(pathName +today +fileNameStockMy), CHARACTER);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] stockAttrs = line.split(SEPARATOR_ATTR_TAG);
                StockInfo info;
                if (stockAttrs.length > 2) {
                    info = new StockInfo();
                    info.setCode(stockAttrs[0]);
                    info.setName(stockAttrs[1]);
                    info.setTurnoverRate(stockAttrs[2]);
                    stockInfos.add(info);
                }
            }
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(mContext, TAG + ":" + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(mContext, TAG + ":" + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return stockInfos;
    }

    //本日数据归集
    public void buildMyTodayStockData(String fileToday) {
        StringBuffer sb = new StringBuffer("");
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(pathName + fileToday+fileNameStock + ".txt"), CHARACTER);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] infoAttrs = line.split(SEPARATOR_ATTR_TAG);
                if (infoAttrs.length > 9) {
                    if (infoAttrs[0].equals("代码"))
                        continue;
                    sb.append(infoAttrs[0] + "\t" + infoAttrs[1] + "\t" + infoAttrs[10] + "\n");
                }
            }
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(mContext, TAG + ":" + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(mContext, TAG + ":" + e.toString(), Toast.LENGTH_SHORT).show();
        }
        //写到sd卡
        writeSDFile(fileToday+fileNameStockMy, sb.toString());

    }


    // 写在/mnt/sdcard/目录下面的文件
    public void writeSDFile(String fileName, String message) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(mContext, "SD卡不能用", Toast.LENGTH_SHORT).show();
            return;
        }
        try {

            File fileDir = new File(pathName);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File file = new File(pathName + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file.getPath());
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
