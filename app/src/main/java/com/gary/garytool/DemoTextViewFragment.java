package com.gary.garytool;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gary.garytool.util.StringUtil;
import com.gary.garytool.view.TextViewM;

public class DemoTextViewFragment extends Fragment {

    View mFragment;
    TextView tv_demo2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragment = inflater.inflate(R.layout.fragment_textview, container, false);
        initViewForDemo1();
        initViewForDemo2();
        return mFragment;
    }

    /**
     * 问题：TextView会自动换行，而且排版文字参差不齐
     * 原因：半角字符与全角字符混乱所致：这种情况一般就是汉字与数字、英文字母混用
     * 解决方法：将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节，
     * 这样就可以避免由于占位导致的排版混乱问题了。
     * 半角转为全角的代码,调用StringUtil的toBCD方法
     */
    private void initViewForDemo2() {
        tv_demo2= (TextView) mFragment.findViewById(R.id.tv_demo2);
        String string="自动换行但排版文字参差不齐。将字符全角化。即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节这样就可以避免由于占位导致的排版混乱问题了。";
        tv_demo2.setText(StringUtil.toDBC(string));
    }

    /**
     *TextView 按下后文字会变色
     */
    private void initViewForDemo1() {
        TextViewM textView1= (TextViewM) mFragment.findViewById(R.id.tv_custom1);
        textView1.setTextColori(Color.WHITE);
        textView1.setTextColorSelected(Color.GRAY);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"您点击了text1",Toast.LENGTH_SHORT).show();
            }
        });

        TextViewM textView2= (TextViewM) mFragment.findViewById(R.id.tv_custom2);
        textView2.setTextColors("#ffffffff");
        textView2.setTextColorSelected("#ff888888");
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
