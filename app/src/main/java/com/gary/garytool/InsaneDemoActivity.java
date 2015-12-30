package com.gary.garytool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;


public  class InsaneDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_demo);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu prog=menu.addSubMenu("启动程序");
        prog.setHeaderIcon(R.drawable.pictures_selected);
        prog.setHeaderTitle("选择你要启动的程序");

        MenuItem item=prog.add("查看Swift");
        item.setIntent(new Intent(this,MainInsaneActivity.class));
        return super.onCreateOptionsMenu(menu);
    }
}
