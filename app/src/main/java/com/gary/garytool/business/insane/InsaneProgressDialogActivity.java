package com.gary.garytool.business.insane;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.gary.garytool.R;


public class InsaneProgressDialogActivity extends Activity {

    static final int MAX_PROGRESS=100;
    private int[] data=new int[50];
    int progressStatus=0;
    int hasData=0;
    ProgressDialog pd1,pd2;

    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0x123)
            {
                pd2.setProgress(progressStatus);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_progress_dialog);
    }

    public void showSpinner(View view)
    {
        ProgressDialog.show(this, "任务执行中", "任务执行中，请等待", false, true);
    }

    public void showIndeterminate(View view)
    {
        pd1=new ProgressDialog(InsaneProgressDialogActivity.this);
        pd1.setTitle("任务正在执行中");
        pd1.setMessage("任务正在执行中，敬请等待...");
        //设置对话框能用“取消”按钮关闭
        pd1.setCancelable(true);
        pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd1.setIndeterminate(true);
        pd1.show();
    }

    public void showProgress(View view)
    {
        progressStatus=0;
        hasData=0;
        pd2=new ProgressDialog(InsaneProgressDialogActivity.this);
        pd2.setMax(MAX_PROGRESS);
        pd2.setTitle("任务完成百分比");
        pd2.setMessage("耗时任务的完成百分比");
        pd2.setCancelable(false);
        pd2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd2.setIndeterminate(true);
        pd2.show();
        new Thread()
        {
            @Override
            public void run() {
                while (progressStatus<MAX_PROGRESS)
                {
                    progressStatus=MAX_PROGRESS*doWork()/data.length;
                    handler.sendEmptyMessage(0x123);
                }
                if(progressStatus>=MAX_PROGRESS)
                    pd2.dismiss();
            }
        }.start();
    }

    public int doWork()
    {
        data[hasData++]= (int) (Math.random()*100);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hasData;
    }


}
