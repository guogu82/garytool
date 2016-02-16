package com.gary.garytool;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class InsanePlaneActivity extends Activity {
    private int speed=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final PlaneView planeView = new PlaneView(this);
        setContentView(planeView);

        planeView.setBackgroundResource(R.drawable.back);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        planeView.currentX = metrics.widthPixels / 2;
        planeView.currentY = metrics.heightPixels - 100;
        planeView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_S:
                        planeView.currentY += speed;
                        break;
                    case KeyEvent.KEYCODE_W:
                        planeView.currentY -= speed;
                        break;
                    case KeyEvent.KEYCODE_A:
                        planeView.currentX -= speed;
                        break;
                    case KeyEvent.KEYCODE_D:
                        planeView.currentX += speed;
                        break;
                    case KeyEvent.KEYCODE_BACK:
                        finish();
                        break;
                }
                planeView.invalidate();
                return true;
            }
        });
    }
}
