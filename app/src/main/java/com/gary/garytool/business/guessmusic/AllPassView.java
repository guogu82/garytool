package com.gary.garytool.business.guessmusic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.gary.garytool.R;

/**
 * 通关界面
 * Created by Gary on 2016/3/27.
 * @author gary guo
 */
public class AllPassView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_music_all_pass_view);

        //隐藏右上角的金币按钮
        FrameLayout view= (FrameLayout) findViewById(R.id.layout_bar_coin);
        view.setVisibility(View.INVISIBLE);
    }
}
