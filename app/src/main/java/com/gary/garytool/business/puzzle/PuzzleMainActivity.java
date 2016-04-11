package com.gary.garytool.business.puzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.gary.garytool.R;

/**
 * Created by Administrator on 2016/4/11.
 */
public class PuzzleMainActivity extends Activity {

    PuzzleLayout mPuzzleLayout;
    private TextView mTvLevel;
    private TextView mTvTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_main_activity);

        mTvLevel= (TextView) findViewById(R.id.tv_level);
        mTvTime= (TextView) findViewById(R.id.tv_time);

        mPuzzleLayout= (PuzzleLayout) findViewById(R.id.puzzle);
        mPuzzleLayout.setTimeEnabled(true);
        mPuzzleLayout.setOnPuzzleListener(new PuzzleLayout.PuzzleListener() {
            @Override
            public void nextLevel(final int nextLevel) {
                new AlertDialog.Builder(PuzzleMainActivity.this).setTitle("游戏信息").setMessage("下一关!!").setPositiveButton("下一关", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         mPuzzleLayout.nextLevel();
                        mTvLevel.setText(nextLevel+"");
                    }
                }).show();
            }

            @Override
            public void timeChanged(int currentTime) {
                mTvTime.setText(currentTime+"");
            }

            @Override
            public void gameOver() {

                new AlertDialog.Builder(PuzzleMainActivity.this).setTitle("游戏信息").setMessage("游戏结束!!").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPuzzleLayout.restart();
                    }
                }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPuzzleLayout.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPuzzleLayout.pause();
    }
}
