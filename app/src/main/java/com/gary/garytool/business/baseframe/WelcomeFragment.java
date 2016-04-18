package com.gary.garytool.business.baseframe;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gary.garytool.R;

/**
 * Created by Administrator on 2016/4/18.
 * @author gary guo
 * 本类讲解 欢迎页的使用
 */
public class WelcomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_frame_welcome_fragment,container,false);
    }
}
