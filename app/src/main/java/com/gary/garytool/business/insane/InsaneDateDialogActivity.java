package com.gary.garytool.business.insane;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.gary.garytool.R;

import java.util.Calendar;


public class InsaneDateDialogActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_date_dialog);

        Button dateBn= (Button) findViewById(R.id.dateBn);
        Button timeBn= (Button) findViewById(R.id.timeBn);
        dateBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                new DatePickerDialog(InsaneDateDialogActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        EditText show= (EditText) findViewById(R.id.show);
                        show.setText("您选择了："+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        timeBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                new TimePickerDialog(InsaneDateDialogActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        EditText show= (EditText) findViewById(R.id.show);
                        show.setText("您选择了："+hourOfDay+"时"+minute+"分");
                    }
                },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true).show();
            }
        });
    }


}
