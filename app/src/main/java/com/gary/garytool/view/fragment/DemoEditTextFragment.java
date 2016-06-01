package com.gary.garytool.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gary.garytool.R;


public class DemoEditTextFragment extends Fragment {


    //1:EditText加入图标 更改边框颜色 设置透明 代码  2015-09-14
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               return inflater.inflate(R.layout.fragment_edittext,container,false);
    }



}
