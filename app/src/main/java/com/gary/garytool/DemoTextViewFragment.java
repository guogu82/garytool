package com.gary.garytool;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gary.garytool.view.TextViewM;

public class DemoTextViewFragment extends Fragment {

    View mFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragment = inflater.inflate(R.layout.fragment_textview, container, false);
        initView();
        return mFragment;
    }

    private void initView() {
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
