package com.gary.garytool.business.insane;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gary.garytool.R;

public class InsaneToastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_toast);
        Button simple= (Button) findViewById(R.id.simple);
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InsaneToastActivity.this, "简单的提示信息", Toast.LENGTH_SHORT).show();
            }
        });

        Button bn= (Button) findViewById(R.id.bn);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(InsaneToastActivity.this);
                toast.setGravity(Gravity.CENTER, 0, 0);
                ImageView image = new ImageView(InsaneToastActivity.this);
                image.setImageResource(R.drawable.pictures_selected);
                LinearLayout ll = new LinearLayout(InsaneToastActivity.this);
                ll.addView(image);
                TextView textView = new TextView(InsaneToastActivity.this);
                textView.setText("带图片的提示信息");
                textView.setTextSize(24);
                textView.setTextColor(Color.MAGENTA);
                ll.addView(textView);
                toast.setView(ll);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

            }
        });
    }


}
