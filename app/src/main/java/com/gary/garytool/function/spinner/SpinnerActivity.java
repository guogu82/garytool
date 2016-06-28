package com.gary.garytool.function.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gary.garytool.R;

/**
 * Created by Gary on 2016/6/15/015.
 */
public class SpinnerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_spinner);

        Spinner spinner= (Spinner) findViewById(R.id.spinner_normal);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpinnerActivity.this,android.R.layout.simple_spinner_item);
        adapter.add(" 上海 ");
        adapter.add(" 北京 ");
        adapter.add(" 南京 ");
        adapter.add(" 哈尔滨 ");
        adapter.add(" 乌鲁木齐 ");
        adapter.add(" 符拉迪沃斯托克 ");
        adapter.add(" 圣弗朗西斯科 ");
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);

    }
}
