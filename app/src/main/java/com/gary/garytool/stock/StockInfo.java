package com.gary.garytool.stock;

/**
 * Created by Gary on 2016/2/21.
 */
public class StockInfo {
    //股票名字
    private String mName;
    //股票代码
    private String mCode;
    //换手率
    private String mTurnoverRate;

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

    public String getTurnoverRate() {
        return mTurnoverRate;
    }

    public void setTurnoverRate(String mTurnoverRate) {
        this.mTurnoverRate = mTurnoverRate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" " + getName() + " ");
        sb.append(" " + getCode() + " ");
        sb.append(" " + getTurnoverRate() + "\n");
        return sb.toString();
    }

}
