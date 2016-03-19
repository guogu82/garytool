package com.gary.garytool.business.insane;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.gary.garytool.R;


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
       setPositiveButton(builder);
       setNegativeButton(builder)
               .create()
               .show();

   }

    public void simpleList(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("简单列表对话框")
                .setIcon(R.drawable.pictures_selected)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show.setText("你选中了《" + items[which] + "》");
                    }
                });
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    public void singleChoice(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("单选列表项对话框")
                .setIcon(R.drawable.pictures_selected)
                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show.setText("你选中了《" + items[which] + "》");
                    }
                });
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    public void multiChoice(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("多选列表项对话框")
                .setIcon(R.drawable.pictures_selected)
                .setMultiChoiceItems(items, new boolean[]{false, true, false, true}, null);
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    public void customList(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle("自定义列表项对话框")
                .setIcon(R.drawable.pictures_selected)
                .setAdapter(new ArrayAdapter<String>(this, R.layout.insane_alert_dialog_array_item, items), null);
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    public void customView(View view)
    {
        TableLayout loginForm= (TableLayout) getLayoutInflater().inflate(R.layout.insane_alert_dialog_login,null);
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.pictures_selected)
                .setTitle("自定义View对话框")
                .setView(loginForm)
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder)
    {
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                show.setText("单击了【确定】按钮！");
            }
        });
    }

    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder)
    {
        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                show.setText("单击了【取消】按钮！");
            }
        });
    }


}
