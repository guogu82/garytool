package com.gary.garytool.view.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Gary on 2016/3/19.
 */
public class VpSimpleFragment extends Fragment {
    private String mTitle;
    public static final String BUNDLE_TITLE="title";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            mTitle=bundle.getString(BUNDLE_TITLE);
        }

        TextView tv=new TextView(getActivity());
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    public static VpSimpleFragment newInstance(String title)
    {
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        VpSimpleFragment fragment=new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
