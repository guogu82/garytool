package com.gary.garytool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/1/19.
 */
public class DummyFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER="section_number";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView=new TextView(getActivity());
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        Bundle args=getArguments();
        textView.setText(args.getInt(ARG_SECTION_NUMBER)+"");
        textView.setTextSize(30);
        return textView;
    }
}
