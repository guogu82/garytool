package com.gary.garytool.business.insane;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.gary.garytool.R;


public  class InsaneDemoActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_demo);
        Button bn= (Button) findViewById(R.id.bn);
        bn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN)
                {
                    Log.d("-Listener","the onKeyDown inListener");
                }
                return true;
            }
        });
    }


}
