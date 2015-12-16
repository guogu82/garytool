package com.gary.garytool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class InsaneDemoActivity extends Activity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_demo);

        spinner= (Spinner) findViewById(R.id.spinner);
        String[] arr={"孙悟空","猪八戒","唐僧"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,arr);
        spinner.setAdapter(adapter);
    }

}
