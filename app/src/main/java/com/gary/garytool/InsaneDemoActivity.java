package com.gary.garytool;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gary.garytool.volley.BitmapCache;

import java.util.TreeMap;


public  class InsaneDemoActivity extends Activity {


    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_demo);
        txt= (TextView) findViewById(R.id.txt);

        registerForContextMenu(txt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.context,menu);
        menu.setHeaderIcon(R.drawable.pictures_selected);
        menu.setHeaderTitle("选择背景色");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.red:
                item.setChecked(true);
                txt.setBackgroundColor(Color.RED);
                break;
            case R.id.green:
                item.setChecked(true);
                txt.setBackgroundColor(Color.GREEN);
                break;
            case R.id.blue:
                item.setChecked(true);
                txt.setBackgroundColor(Color.BLUE);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isCheckable())
        {
            item.setChecked(true);
        }

        switch (item.getItemId())
        {
            case R.id.font_10:
                txt.setTextSize(10*2);
                break;
            case R.id.font_12:
                txt.setTextSize(12*2);
                break;
            case R.id.font_14:
                txt.setTextSize(14*2);
                break;
            case R.id.font_16:
                txt.setTextSize(16*2);
                break;
            case R.id.font_18:
                txt.setTextSize(18*2);
                break;
            case R.id.red_font:
                txt.setTextColor(Color.RED);
                item.setChecked(true);
                break;
            case R.id.green_font:
                txt.setTextColor(Color.GREEN);
                item.setChecked(true);
                break;
            case  R.id.blue_font:
                txt.setTextColor(Color.BLUE);
                item.setChecked(true);
                break;
            case R.id.plain_item:
                Toast toast=Toast.makeText(InsaneDemoActivity.this,"您单击了普通菜单项",Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
        return true;
    }
}
