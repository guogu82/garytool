package com.gary.garytool.stock;

import android.content.Context;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public static final double INCREASE = 1.5;

    private Context mContext;
    private String pathName = "/sdcard/stock/";



    private String fileNameStock =  "stock";
    private String fileNameStockMy =  "stockMy";
    private String fileNameAnalysis =  "stockAnalysis";



    public StockManager(Context context) {
        this.mContext = context;
    }

    //分析数据整理
    public void statisticsStockData(String fileToday,TextView textView) {
        File file = new File(pathName + fileToday+fileNameAnalysis);
        if (!file.exists()) {
            textView.setText("文件不存在"+file.getPath());
            return;
        }
        List<StockInfoForAnalysis> stockInfoForAnalysises = getAnalysisStockInfo(file);
        List<StockInfoForAnalysis> result=new ArrayList<>();
        List<StockInfoForAnalysis> errors=new ArrayList<>();
        int countTotal=stockInfoForAnalysises.size();
        int countDone=0;
        for (StockInfoForAnalysis info:stockInfoForAnalysises)
        {
            float todayTurnoverRate;
            float beforeOneTurnoverRate;
            float beforeTwoTurnoverRate;
            float beforeThreeTurnoverRate;
            float beforeFourTurnoverRate;
            float beforeFiveTurnoverRate;
            try {
                todayTurnoverRate=Float.valueOf(info.getTodayTurnoverRate());
                beforeOneTurnoverRate=Float.valueOf(info.getBeforeOneTurnoverRate());
                beforeTwoTurnoverRate=Float.valueOf(info.getBeforeTwoTurnoverRate());
                beforeThreeTurnoverRate=Float.valueOf(info.getBeforeThreeTurnoverRate());
                beforeFourTurnoverRate=Float.valueOf(info.getBeforeFourRate());
                beforeFiveTurnoverRate=Float.valueOf(info.getBeforeFiveTurnoverRate());

                double firstIncrease=todayTurnoverRate/beforeOneTurnoverRate;
                if(firstIncrease>INCREASE)
                {
                    result.add(info);
                }
                countDone++;
            }
            catch (Exception e)
            {
                errors.add(info);
            }
        }
        StringBuilder comment=new StringBuilder();
        comment.append("共："+countTotal+",完成："+countDone+",失败："+errors.size()+SEPARATOR_OBJECT_TAG);

        if(result.size()>0)
        {
            comment.append("注意如下"+result.size()+"个优质股票"+SEPARATOR_OBJECT_TAG);
            for(StockInfoForAnalysis good:result)
            {
                comment.append(good.toString());
            }
        }

        if(errors.size()>0)
        {
            comment.append("注意如下"+errors.size()+"个分析失败股票"+SEPARATOR_OBJECT_TAG);
            for(StockInfoForAnalysis errorInfo:errors)
            {
                comment.append(errorInfo.toString());
            }
        }
        textView.setText(comment.toString());
    }

    //更新历史数据
    public void updateMyStockData(String fileToday,TextView textView) {
        //读取历史分析数据
        String yestesday=fileToday;
        File file = null;
        for(int i=0;i<5;i++)
        {
            yestesday=getYesterday(yestesday);
            file = new File(pathName + yestesday+fileNameAnalysis);
            if (file.exists()) {
                break;
            }
        }




        //读取本日最新数据
        List<StockInfo> stockInfos = getTodayStockInfo(fileToday);

        //更新最新分析数据
        StringBuffer sb = new StringBuffer("");
        if (!file.exists()) {
            for (StockInfo stock : stockInfos) {
                sb.append(stock.getCode() + SEPARATOR_ATTR_TAG + stock.getName() + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + stock.getTurnoverRate() + SEPARATOR_OBJECT_TAG);
            }
        } else {
            List<StockInfoForAnalysis> stockInfoForAnalysises = getAnalysisStockInfo(file);

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

        writeSDFile(fileToday + fileNameAnalysis, sb.toString());
        textView.setText(fileToday + "更新完成");
    }

    private String getYesterday(String today)
    {
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        Date  d = null;
        try {
            d = df.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal=Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, -1);  //减1天
        return df.format(cal.getTime());
    }

    private List<StockInfoForAnalysis> getAnalysisStockInfo(File fileDay) {
        List<StockInfoForAnalysis> stockInfos = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileDay), CHARACTER);
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
    public boolean buildMyTodayStockData(String fileToday,TextView textView) {
        StringBuffer sb = new StringBuffer("");
        try {
            File file = new File(pathName + fileToday+fileNameStock + ".txt");
            if (!file.exists()) {
                textView.setText("文件不存在"+file.getPath());
                return false;
            }
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), CHARACTER);
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
        writeSDFile(fileToday + fileNameStockMy, sb.toString());
        textView.setText(fileToday + "归集完成");
        return true;
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
            Toast.makeText(mContext, TAG + ":" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }



}
