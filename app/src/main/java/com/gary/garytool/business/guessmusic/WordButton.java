package com.gary.garytool.business.guessmusic;

import android.widget.Button;

/**
 * Created by Administrator on 2016/3/25.
 * @author gary guo
 * 文字按钮
 */
public class WordButton {
    private int mIndex;
    private boolean mIsVisible;
    private String mWordString;
    private Button mViewButton;

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public boolean getIsVisible() {
        return mIsVisible;
    }

    public void setIsVisible(boolean mIsVisible) {
        this.mIsVisible = mIsVisible;
    }

    public String getWordString() {
        return mWordString;
    }

    public void setWordString(String mWordString) {
        this.mWordString = mWordString;
    }

    public Button getViewButton() {
        return mViewButton;
    }

    public void setViewButton(Button mViewButton) {
        this.mViewButton = mViewButton;
    }

    public WordButton()
    {
        mIsVisible=true;
        mWordString="";
    }
}
