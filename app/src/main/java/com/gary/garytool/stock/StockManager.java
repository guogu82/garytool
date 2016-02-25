package com.gary.garytool.stock;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gary on 2016/2/20.
 */
public class StockManager {
    public static final String TAG = "StockManager";
    public static final String SEPARATOR_ATTR_TAG = "\t";
    public static final String SEPARATOR_OBJECT_TAG = "\n";

    public static final String NO_VALUE = "--";
    public static final String CHARACTER = "UTF-8";
    public static final double MINVOLUME = 0.5;

    private Context mContext;
    private String pathName = StockUtil.getSDPath("/stock") + "/";

    private String fileNameStock = "stock";
    private String fileNameStockMy = "stockMy";
    private String fileNameAnalysis = "stockAnalysis";
    private String fileNameAnalysisResult = "stockAnalysisResult";


    public StockManager(Context context) {
        this.mContext = context;
    }

    //本日数据归集
    public boolean buildMyTodayStockData(String fileToday, TextView textView) {
        StringBuffer sb = new StringBuffer("");
        try {
            File file = new File(pathName + fileToday + fileNameStock + ".txt");
            if (!file.exists()) {
                textView.setText("文件不存在" + file.getPath());
                return false;
            }
            InputStreamReader inputStreamReader;

            inputStreamReader = new InputStreamReader(new FileInputStream(file), CHARACTER);

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains("代码"))
                    continue;
                String[] infoAttrs = line.split(SEPARATOR_ATTR_TAG);
                if (infoAttrs.length > 9) {
                    sb.append(infoAttrs[0] + SEPARATOR_ATTR_TAG + infoAttrs[1] + SEPARATOR_ATTR_TAG + infoAttrs[10] + SEPARATOR_ATTR_TAG);
                    if (infoAttrs.length > 18) {
                        if (infoAttrs[18] == null || infoAttrs[18].length() == 0)
                            sb.append(NO_VALUE);
                        sb.append(infoAttrs[18]);
                    } else {
                        sb.append(NO_VALUE);
                    }

                    sb.append(SEPARATOR_OBJECT_TAG);
                }
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //写到sd卡
        StockUtil.writeSDFile(pathName, fileToday + fileNameStockMy, sb.toString());
        textView.setText(fileToday + "归集完成");
        return true;
    }


    //分析数据
    public void statisticsStockData(String fileToday, String statistic, TextView textView) {
        File file = new File(pathName + fileToday + fileNameAnalysis);
        if (!file.exists()) {
            textView.setText("文件不存在" + file.getPath());
            return;
        }

        double volume = 1.5;
        double turnoverRate = 3;

        String[] statistics = statistic.split(",");
        if (statistics.length == 2) {
            Double tempVolume = Double.parseDouble(statistics[0]);
            if (tempVolume == 1) {
                volume = 1;
            } else if (tempVolume == 2) {
                volume = 1.2;
            } else {
                volume = tempVolume * 0.5;
            }
            turnoverRate = Double.parseDouble(statistics[1]);
        }

        //分析规则：
        //股价低于5块或者高过40块的忽略--没有实施。
        //量大1.5倍，量大2倍，量大2.5倍，量大3倍
        //换手率大于3%，换手率大于5%，换手率大于8%，换手率大于10%
        //分析规则 3,3 则是1.5倍的放量，大于3%的换手率。5,8则是2.5倍的放量，大于8%的换手率。
        List<StockInfoForAnalysis> stockInfoForAnalysises = getAnalysisStockInfo(file);

        //分析逻辑：最有价值是3比2
        //一共6天 5,4,3,2,1,0；
        //一天量大 观察 （比前2天都大OneDayForTwo，比前3天都大OneDayForThree）
        //二天量大 观察 （比前1天都大TwoDayForOne,比前2天都大TwoDayForTwo）
        //三天量大 观察可以在下午2点前买入 （比前2天都大ThreeDayForTwo）
        //四天量大 买入 在早上10点前买入 （比前2天都大FourDayForTwo）

        //本日比上日的分析
        List<StockInfoForAnalysis> resultOneDayForTwo = new ArrayList<>();
        List<StockInfoForAnalysis> resultOneDayForThree = new ArrayList<>();
        List<StockInfoForAnalysis> resultTwoDayForOne = new ArrayList<>();
        List<StockInfoForAnalysis> resultTwoDayForTwo = new ArrayList<>();
        List<StockInfoForAnalysis> resultThreeDayForTwo = new ArrayList<>();
        List<StockInfoForAnalysis> errors = new ArrayList<>();
        int countTotal = stockInfoForAnalysises.size();
        int countDone = 0;

        for (StockInfoForAnalysis info : stockInfoForAnalysises) {
            try {
                float todayTurnoverRate;
                float beforeOneTurnoverRate;
                float beforeTwoTurnoverRate;
                float beforeThreeTurnoverRate;
                float beforeFourTurnoverRate;

                todayTurnoverRate = Float.valueOf(info.getTodayTurnoverRate());
                beforeOneTurnoverRate = Float.valueOf(info.getBeforeOneTurnoverRate());
                beforeTwoTurnoverRate = Float.valueOf(info.getBeforeTwoTurnoverRate());
                beforeThreeTurnoverRate = Float.valueOf(info.getBeforeThreeTurnoverRate());
                beforeFourTurnoverRate = Float.valueOf(info.getBeforeFourTurnoverRate());

                if (todayTurnoverRate < MINVOLUME || beforeOneTurnoverRate < MINVOLUME || beforeTwoTurnoverRate < MINVOLUME || beforeThreeTurnoverRate < MINVOLUME || beforeFourTurnoverRate < MINVOLUME) {
                    countDone++;
                    continue;
                }

                //三天量大 观察可以在下午2点前买入 （比前2天都大ThreeDayForTwo）
                double ThreeDayForTwoFirstIncrease = todayTurnoverRate / beforeThreeTurnoverRate;
                double ThreeDayForTwoSecondIncrease = todayTurnoverRate / beforeFourTurnoverRate;
                double ThreeDayForTwoThreeIncrease = beforeOneTurnoverRate / beforeThreeTurnoverRate;
                double ThreeDayForTwoFourIncrease = beforeOneTurnoverRate / beforeFourTurnoverRate;
                double ThreeDayForTwoFiveIncrease = beforeTwoTurnoverRate / beforeThreeTurnoverRate;
                double ThreeDayForTwoSixIncrease = beforeTwoTurnoverRate / beforeFourTurnoverRate;
                if (ThreeDayForTwoFirstIncrease > volume && ThreeDayForTwoSecondIncrease > volume && ThreeDayForTwoThreeIncrease > volume && ThreeDayForTwoFourIncrease > volume && ThreeDayForTwoFiveIncrease > volume && ThreeDayForTwoSixIncrease > volume && todayTurnoverRate > turnoverRate) {
                    resultThreeDayForTwo.add(info);
                }

                //二天量大 观察 （比前1天都大TwoDayForOne）
                double TwoDayForOneThreeFirstIncrease = todayTurnoverRate / beforeTwoTurnoverRate;
                double TwoDayForOneSecondIncrease = beforeOneTurnoverRate / beforeTwoTurnoverRate;
                if (TwoDayForOneThreeFirstIncrease > volume && TwoDayForOneSecondIncrease > volume && todayTurnoverRate > turnoverRate) {
                    //不打印 resultTwoDayForOne.add(info);
                }

                //二天量大 观察 （比前2天都大TwoDayForTwo）
                double TwoDayForTwoFirstIncrease = todayTurnoverRate / beforeTwoTurnoverRate;
                double TwoDayForTwoSecondIncrease = todayTurnoverRate / beforeThreeTurnoverRate;
                double TwoDayForTwoThreeIncrease = beforeOneTurnoverRate / beforeTwoTurnoverRate;
                double TwoDayForTwoFourIncrease = beforeOneTurnoverRate / beforeThreeTurnoverRate;
                if (TwoDayForTwoFirstIncrease > volume && TwoDayForTwoSecondIncrease > volume && TwoDayForTwoThreeIncrease > volume && TwoDayForTwoFourIncrease > volume && todayTurnoverRate > turnoverRate) {
                    //不打印  resultTwoDayForTwo.add(info);
                }

                //一天量大 观察 （比前2天都大OneDayForTwo）
                double OneDayForTwoFirstIncrease = todayTurnoverRate / beforeOneTurnoverRate;
                double OneDayForTwoSecondIncrease = todayTurnoverRate / beforeTwoTurnoverRate;
                if (OneDayForTwoFirstIncrease > volume && OneDayForTwoSecondIncrease > volume && todayTurnoverRate > turnoverRate) {
                    //不打印 resultOneDayForTwo.add(info);
                }
                //一天量大 观察 （比前3天都大OneDayForThree）
                double OneDayForThreeFirstIncrease = todayTurnoverRate / beforeOneTurnoverRate;
                double OneDayForThreeSecondIncrease = todayTurnoverRate / beforeTwoTurnoverRate;
                double OneDayForThreeThreeIncrease = todayTurnoverRate / beforeThreeTurnoverRate;
                if (OneDayForThreeFirstIncrease > volume && OneDayForThreeSecondIncrease > volume && OneDayForThreeThreeIncrease > volume && todayTurnoverRate > turnoverRate) {
                    //不打印 resultOneDayForThree.add(info);
                }

                countDone++;
            } catch (Exception e) {
                errors.add(info);
            }
        }


        StringBuilder comment = new StringBuilder();
        comment.append(fileToday + "共：" + countTotal + ",完成：" + countDone + ",失败：" + errors.size() + SEPARATOR_OBJECT_TAG);
        //三天量大 观察可以在下午2点前买入 （比前2天都大ThreeDayForTwo）
        appendComment(comment, resultThreeDayForTwo, "ThreeDayForTwo");
        //统计行业动态
        statisticsIndustry(comment, resultThreeDayForTwo);


        //二天量大 观察 （比前1天都大TwoDayForTwo）
        appendComment(comment, resultTwoDayForTwo, "TwoDayForTwo");

        //二天量大 观察 （比前1天都大TwoDayForOne）
        appendComment(comment, resultTwoDayForOne, "woDayForOne");

        //一天量大 观察 （比前3天都大OneDayForThree）
        appendComment(comment, resultOneDayForThree, "OneDayForThree");

        //一天量大 观察 （比前2天都大OneDayForTwo）
        appendComment(comment, resultOneDayForTwo, "OneDayForTwo");

        //分析失败的股票
        //appendComment(comment, errors,"分析失败");


        StockUtil.writeSDFile(pathName, fileToday + fileNameAnalysisResult + statistic, comment.toString());
        textView.setText(comment.toString());
    }

    private void statisticsIndustry(StringBuilder comment, List<StockInfoForAnalysis> list) {
        comment.append(SEPARATOR_OBJECT_TAG).append("行业归类统计:共" + list.size() + "个").append(SEPARATOR_OBJECT_TAG);
        Map<String, Integer> count = new HashMap<String, Integer>();

        for (StockInfoForAnalysis info : list) {
            if (count.containsKey(info.getIndustry())) {
                //1、用Object remove(Object key)方法从HashMap或者HashTable中移去待修改的key值对，该方法同时会返回该Key所对应的Object
                // 2、用put(Object key, Object value) 将新的Key-Value重新放入HashMap(HashTable)
                Integer result = count.remove(info.getIndustry());
                result++;
                count.put(info.getIndustry(), result);
            }
            else
            {
                count.put(info.getIndustry(), 1);
            }
        }

        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            if(entry.getValue()>1)
            comment.append(entry.getKey()).append(SEPARATOR_ATTR_TAG).append(entry.getValue()).append(SEPARATOR_OBJECT_TAG);
        }
    }

    private void appendComment(StringBuilder comment, List<StockInfoForAnalysis> resultTwoDayForTwo, String name) {
        if (resultTwoDayForTwo.size() > 0) {
            comment.append(SEPARATOR_OBJECT_TAG + "注意如下" + resultTwoDayForTwo.size() + "个" + name + "股票" + SEPARATOR_OBJECT_TAG);
            for (StockInfoForAnalysis good : resultTwoDayForTwo) {
                comment.append(good.toString());
            }
        }
    }


    //更新历史数据
    public void updateMyStockData(String fileToday, TextView textView) {
        //读取历史分析数据
        String yestesday = fileToday;
        File fileForYesterday = null;
        for (int i = 0; i < 5; i++) {
            yestesday = StockUtil.getYesterday(yestesday);
            fileForYesterday = new File(pathName + yestesday + fileNameAnalysis);
            if (fileForYesterday.exists()) {
                break;
            }
        }

        //读取本日最新数据
        List<StockInfo> stockInfos = getTodayStockInfo(fileToday);

        //更新最新分析数据
        StringBuffer sb = new StringBuffer("");

        //打印提示日志
        StringBuilder comment = new StringBuilder("");
        comment.append(fileToday).append("更新完成").append(SEPARATOR_OBJECT_TAG);

        if (!fileForYesterday.exists()) {
            for (StockInfo stock : stockInfos) {
                sb.append(stock.getCode() + SEPARATOR_ATTR_TAG + stock.getName() + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_ATTR_TAG + stock.getTurnoverRate() + SEPARATOR_ATTR_TAG + NO_VALUE + SEPARATOR_OBJECT_TAG);
            }
        } else {
            List<StockInfoForAnalysis> stockInfoForAnalysises = getAnalysisStockInfo(fileForYesterday);
            comment.append("整理如下：").append(SEPARATOR_OBJECT_TAG);
            comment.append("上次 ").append(yestesday + SEPARATOR_ATTR_TAG).append(stockInfoForAnalysises.size()).append("个").append(SEPARATOR_OBJECT_TAG);
            ;
            comment.append("本次 ").append(fileToday + SEPARATOR_ATTR_TAG).append(stockInfos.size()).append("个").append(SEPARATOR_OBJECT_TAG);
            for (StockInfo stock : stockInfos) {
                sb.append(stock.getCode() + SEPARATOR_ATTR_TAG);
                sb.append(stock.getName() + SEPARATOR_ATTR_TAG);
                boolean hasNewStock = true;
                for (StockInfoForAnalysis old : stockInfoForAnalysises) {
                    if (old.getCode().equals(stock.getCode())) {
                        hasNewStock = false;
                        sb.append(old.getBeforeFourTurnoverRate() + SEPARATOR_ATTR_TAG);
                        sb.append(old.getBeforeThreeTurnoverRate() + SEPARATOR_ATTR_TAG);
                        sb.append(old.getBeforeTwoTurnoverRate() + SEPARATOR_ATTR_TAG);
                        sb.append(old.getBeforeOneTurnoverRate() + SEPARATOR_ATTR_TAG);
                        sb.append(old.getTodayTurnoverRate() + SEPARATOR_ATTR_TAG);

                        if (!old.getName().equals(stock.getName())) {
                            comment.append(stock.getCode()).append(" 名字变更 ").append(old.getName()).append(" 改为 ").append(stock.getName()).append(SEPARATOR_OBJECT_TAG);
                        }
                    }

                }
                if (hasNewStock) {
                    sb.append(NO_VALUE + SEPARATOR_ATTR_TAG);
                    sb.append(NO_VALUE + SEPARATOR_ATTR_TAG);
                    sb.append(NO_VALUE + SEPARATOR_ATTR_TAG);
                    sb.append(NO_VALUE + SEPARATOR_ATTR_TAG);
                    sb.append(NO_VALUE + SEPARATOR_ATTR_TAG);


                    comment.append(stock.getCode()).append(SEPARATOR_ATTR_TAG).append(stock.getName()).append(SEPARATOR_ATTR_TAG).append("新增").append(SEPARATOR_OBJECT_TAG);
                }
                sb.append(stock.getTurnoverRate()).append(SEPARATOR_ATTR_TAG);
                sb.append(stock.getIndustry());
                //一行结束，需要换行。
                sb.append(SEPARATOR_OBJECT_TAG);
            }

        }

        StockUtil.writeSDFile(pathName, fileToday + fileNameAnalysis, sb.toString());
        textView.setText(comment.toString());
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
                if (stockAttrs.length > 8) {
                    info = new StockInfoForAnalysis();
                    info.setCode(stockAttrs[0]);
                    info.setName(stockAttrs[1]);
                    info.setBeforeFiveTurnoverRate(stockAttrs[2]);
                    info.setBeforeFourTurnoverRate(stockAttrs[3]);
                    info.setBeforeThreeTurnoverRate(stockAttrs[4]);
                    info.setBeforeTwoTurnoverRate(stockAttrs[5]);
                    info.setBeforeOneTurnoverRate(stockAttrs[6]);
                    info.setTodayTurnoverRate(stockAttrs[7]);
                    info.setIndustry(stockAttrs[8]);
                    stockInfos.add(info);
                }
            }
        } catch (Exception e) {
            Toast.makeText(mContext, TAG + ":" + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return stockInfos;
    }

    private List<StockInfo> getTodayStockInfo(String today) {
        List<StockInfo> stockInfos = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(pathName + today + fileNameStockMy), CHARACTER);
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
                    info.setIndustry(stockAttrs[3]);
                    stockInfos.add(info);
                }
            }
        } catch (Exception e) {

        }
        return stockInfos;
    }


}
