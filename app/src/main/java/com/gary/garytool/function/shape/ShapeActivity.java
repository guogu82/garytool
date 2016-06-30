package com.gary.garytool.function.shape;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gary.garytool.R;
import com.gary.garytool.util.UtilToast;

/**
 * Created by Gary on 2016/6/30/015.
 */
public class ShapeActivity extends Activity {

    private LinearLayout layoutShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_shape);
        layoutShape= (LinearLayout) findViewById(R.id.layout_shape);
        layoutShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilToast.showToast(ShapeActivity.this,"hello gary");
            }
        });
    }

}
