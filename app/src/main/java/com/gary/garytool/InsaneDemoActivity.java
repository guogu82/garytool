package com.gary.garytool;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gary.garytool.util.HanziToPinyin;
import com.gary.garytool.util.LogUtil;

import java.util.ArrayList;


public class InsaneDemoActivity extends Activity {

   TextView textView;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_demo);

        textView= (TextView) findViewById(R.id.textView);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        bt= (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtil.d("TAG", getPinYin(textView.getText().toString()));
            }
        });
    }

    public static String getPinYin(String hanzi)
    {
        ArrayList<HanziToPinyin.Token> tokens=HanziToPinyin.getInstance().get(hanzi);
        StringBuilder sb=new StringBuilder();
        if(tokens!=null&&tokens.size()>0)
        {
            for(HanziToPinyin.Token token: tokens)
            {
                if(HanziToPinyin.Token .PINYIN==token.type)
                {
                    sb.append(token.target);
                }
                else
                {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toUpperCase();
    }

}
