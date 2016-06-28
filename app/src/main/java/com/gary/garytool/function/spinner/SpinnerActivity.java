package com.gary.garytool.function.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.lib.fancyindexer.adapter.CarBrandAdapter;
import com.gary.garytool.lib.fancyindexer.domain.CarBrand;
import com.gary.garytool.lib.fancyindexer.domain.Cheeses;
import com.gary.garytool.lib.fancyindexer.domain.GoodMan;
import com.gary.garytool.lib.fancyindexer.ui.FancyIndexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gary on 2016/6/15/015.
 */
public class SpinnerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_spinner);


    }
}
