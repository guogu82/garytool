package com.gary.garytool.business.datepicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.gary.garytool.R;
import com.gary.garytool.view.DoubleDatePickerDialog;

import java.util.Calendar;

/**
 * Created by Gary on 2016/6/14/014.
 */
public class DatePickerDialogActivity extends Activity{
    EditText etDateFrom;
    EditText etDateTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_date_picker);

        etDateFrom= (EditText) findViewById(R.id.etDateFrom);
        etDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateFrom((EditText) view);
            }
        });
        etDateTo= (EditText) findViewById(R.id.etDateTo);
        etDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateFrom((EditText) view);
            }
        });
    }

    private void showDateFrom(final EditText view) {
        Calendar c = Calendar.getInstance();
        new DoubleDatePickerDialog(this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                  int startDayOfMonth) {
                String textString = String.format("%d-%02d-%02d", startYear,
                        startMonthOfYear + 1, startDayOfMonth);
                view.setText(textString);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
    }
}
