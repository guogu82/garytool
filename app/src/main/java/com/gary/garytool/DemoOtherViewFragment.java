package com.gary.garytool;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gary.garytool.view.TextViewM;

public class DemoOtherViewFragment extends Fragment {
    View fragment;
    TextView textView;
    TextViewM textViewM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_other, container, false);
        textView = (TextView) fragment.findViewById(R.id.tv);
        textViewM= (TextViewM) fragment.findViewById(R.id.bt_xy);

        textViewM.setTextColori(getResources().getColor(R.color.black));
        //字体大小
        textViewM.setTextSize(14);
        //文字提示
        textViewM.setText("TEXT");

        return fragment;
    }


}
