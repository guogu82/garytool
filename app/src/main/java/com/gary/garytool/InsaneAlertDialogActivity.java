package com.gary.garytool;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class InsaneAlertDialogActivity extends Activity {

    TextView show;
    String[] items={"疯狂java讲义","疯狂ajax讲义","轻量级java ee企业应用实战","疯狂android讲义"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_alert_dialog);
        show= (TextView) findViewById(R.id.show);
    }

   public void simple(View view)
   {
       AlertDialog.Builder builder=new AlertDialog.Builder(this)
               .setTitle("简单对话框")
               .setIcon(R.drawable.pictures_selected)
               .setMessage("对话框的测试内容\n第二行内容");

   }


}
