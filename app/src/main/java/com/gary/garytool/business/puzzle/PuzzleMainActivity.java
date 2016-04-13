package com.gary.garytool.business.puzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.util.FileUtil;
import com.gary.garytool.util.LogUtil;
import com.gary.garytool.util.Util;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016/4/11.
 */
public class PuzzleMainActivity extends Activity {

    private static final String TAG="PuzzleMainActivity";
    PuzzleLayout mPuzzleLayout;
    private TextView mTvLevel;
    private TextView mTvTime;
    private ImageView mIvAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_main_activity);

        mTvLevel= (TextView) findViewById(R.id.tv_level);
        mTvTime= (TextView) findViewById(R.id.tv_time);
        mIvAnswer= (ImageView) findViewById(R.id.iv_answer);

        mIvAnswer.setImageBitmap(PuzzleUtil.getPuzzleImage(this));

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
            public void newPicture(final int level) {
                new AlertDialog.Builder(PuzzleMainActivity.this).setTitle("游戏信息").setMessage("新图片!!").setPositiveButton("开始新拼图", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPuzzleLayout.nextLevel();
                        mTvLevel.setText(level+"");
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

    //响应选择图片按钮事件
    public void onChoseAnswer(View view)
    {
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                ImageView imageView = (ImageView) findViewById(R.id.iv_answer);
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);

                String filePath= Util.getSDPath()+"/touristguide/puzzle_sd_image.jpg";
                FileUtil.saveBitmap(bitmap, filePath);
                mTvLevel.setText("1");
                mPuzzleLayout.start();
                LogUtil.e(TAG,"start image");

            } catch (FileNotFoundException e) {
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
