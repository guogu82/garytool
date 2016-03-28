package com.gary.garytool.business.guessmusic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.gary.garytool.R;
import com.gary.garytool.util.Util;

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

        ImageButton bt_bar_back= (ImageButton) findViewById(R.id.bt_bar_back);
        bt_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessMusicUtil.saveData(AllPassView.this,-1,Const.TOTAL_COINS);
                Util.startActivity(AllPassView.this,GuessMusicMainActivity.class);
            }
        });


    }
}
