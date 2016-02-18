package com.gary.garytool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;


public class MainInsaneActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_insane);

        }

 public void toIntentService(View view)
 {
     Intent intent=new Intent(MainInsaneActivity.this,IntentServiceActivity.class);
     startActivity(intent);
 }

    public void toInsaneDemo(View view)
    {
        Intent intent=new Intent(MainInsaneActivity.this,InsaneDemoActivity.class);
        startActivity(intent);
    }
    //3.2 PlaneView
    public void toPlane(View view)
    {
        Intent intent=new Intent(MainInsaneActivity.this,InsanePlaneActivity.class);
        startActivity(intent);
    }

    //2.9 toProgressDialog
    public void toProgressDialog(View view)
    {
        Intent intent=new Intent(MainInsaneActivity.this,InsaneProgressDialogActivity.class);
        startActivity(intent);
    }
    //2.9 DateDialog
    public void toDateDialog(View view)
    {
        Intent intent=new Intent(MainInsaneActivity.this,InsaneDateDialogActivity.class);
        startActivity(intent);
    }
    //2.9 AlertDialog
    public void toAlertDialog(View view)
    {
        Intent intent=new Intent(MainInsaneActivity.this,InsaneAlertDialogActivity.class);
        startActivity(intent);
    }

    //2.8 Toast
    public void toToast(View view)
    {
        Intent intent=new Intent(MainInsaneActivity.this,InsaneToastActivity.class);
        startActivity(intent);
    }



}
