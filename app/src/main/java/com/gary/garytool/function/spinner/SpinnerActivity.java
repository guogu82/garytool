package com.gary.garytool.function.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gary.garytool.R;
import com.gary.garytool.util.UtilToast;

/**
 * Created by Gary on 2016/6/15/015.
 */
public class SpinnerActivity extends Activity {

    String[] mData=new String[]{" 上海 "," 北京 "," 南京 "," 哈尔滨 "," 乌鲁木齐 "," 符拉迪沃斯托克 "," 圣弗朗西斯科 "};
    Spinner spinnerNormal;
    Spinner spinnerOne;
    Spinner spinnerTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_spinner);

        spinnerNormal= (Spinner) findViewById(R.id.spinner_normal);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpinnerActivity.this,android.R.layout.simple_spinner_item);
        adapter.addAll(mData);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerNormal.setAdapter(adapter);

        //改变了选中背景，弹出框的title，点击按钮事件捕获。
        spinnerOne= (Spinner) findViewById(R.id.spinner_one);
        adapter = new ArrayAdapter<String>(SpinnerActivity.this, R.layout.function_simple_spinner_item, mData)
        {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                /*
                //设置自定义下拉的选择图案
                View view = inflate(getContext(), R.layout.spinner_item_layout,null);
                TextView label = (TextView) view.findViewById(R.id.spinner_item_label);
                ImageView check = (ImageView) view.findViewById(R.id.spinner_item_checked_image);
                label.setText(gradeList.get(position));
                if (gradeSpinner.getSelectedItemPosition() == position) {
                    view.setBackgroundColor(getResources().getColor(R.color.spinner_green));
                    check.setImageResource(R.drawable.check_selected);
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.spinner_light_green));
                    check.setImageResource(R.drawable.check_unselect);
                }
                return view;
                */

                return super.getDropDownView(position, convertView, parent);
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerOne.setAdapter(adapter);
        //需在xml文件里的spinner下添加属性android:spinnerMode="dialog"之后便可setPrompt函数才起作用里显示文字了
        spinnerOne.setPrompt("请选择城市：");
        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String str=parent.getItemAtPosition(position).toString();
                UtilToast.showToast(SpinnerActivity.this,"你点击的是:"+str);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //改变了选中背景，通过selector，去图片设置。
        spinnerTwo= (Spinner) findViewById(R.id.spinner_two);
        adapter = new ArrayAdapter<String>(SpinnerActivity.this,android.R.layout.simple_spinner_item);
        adapter.addAll(mData);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerTwo.setAdapter(adapter);

        }
}
