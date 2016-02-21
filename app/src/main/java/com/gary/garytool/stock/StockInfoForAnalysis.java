package com.gary.garytool.stock;

/**
 * Created by Gary on 2016/2/21.
 */
public class StockInfoForAnalysis {
    //股票名字
    private String mName;
    //股票代码
    private String mCode;
    //今日换手率
    private String mTodayTurnoverRate;
    //昨天换手率
    private String mBeforeOneTurnoverRate;
    //前天换手率
    private String mBeforeTwoTurnoverRate;
    //第三天换手率
    private String mBeforeThreeTurnoverRate;
     //第四天换手率
    private String mBeforeFourRate;
     //第五天换手率
    private String mBeforeFiveTurnoverRate;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String mCode) {
        this.mCode = mCode;
    }

    public String getTodayTurnoverRate() {
        return mTodayTurnoverRate;
    }

    public void setTodayTurnoverRate(String TodayTurnoverRate) {
        this.mTodayTurnoverRate = TodayTurnoverRate;
    }

    public String getBeforeOneTurnoverRate() {
        return mBeforeOneTurnoverRate;
    }

    public void setBeforeOneTurnoverRate(String BeforeOneTurnoverRate) {
        this.mBeforeOneTurnoverRate = BeforeOneTurnoverRate;
    }

    public String getBeforeTwoTurnoverRate() {
        return mBeforeTwoTurnoverRate;
    }

    public void setBeforeTwoTurnoverRate(String BeforeTwoTurnoverRate) {
        this.mBeforeTwoTurnoverRate = BeforeTwoTurnoverRate;
    }

    public String getBeforeThreeTurnoverRate() {
        return mBeforeThreeTurnoverRate;
    }

    public void setBeforeThreeTurnoverRate(String BeforeThreeTurnoverRate) {
        this.mBeforeThreeTurnoverRate = BeforeThreeTurnoverRate;
    }

    public String getBeforeFourRate() {
        return mBeforeFourRate;
    }

    public void setBeforeFourRate(String BeforeFourRate) {
        this.mBeforeFourRate = BeforeFourRate;
    }

    public String getBeforeFiveTurnoverRate() {
        return mBeforeFiveTurnoverRate;
    }

    public void setBeforeFiveTurnoverRate(String BeforeFiveTurnoverRate) {
        this.mBeforeFiveTurnoverRate = BeforeFiveTurnoverRate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" " + getName() + " ");
        sb.append(" " + getCode() + " ");
        sb.append(" " + getTodayTurnoverRate() + " ");
        sb.append(" " + getBeforeOneTurnoverRate() + " ");
        sb.append(" " + getBeforeTwoTurnoverRate() + " ");
        sb.append(" " + getBeforeThreeTurnoverRate() + " ");
        sb.append(" " + getBeforeFourRate() + " ");
        sb.append(" " + getBeforeFiveTurnoverRate() + "\n");
        return sb.toString();
    }

}
