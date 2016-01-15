package com.gary.garytool;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;


public  class InsaneDemoActivity extends Activity implements ActionBar.OnNavigationListener {


    private static final String SELECTED_ITEM="selected_item";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insane_demo);

        final ActionBar actionBar=getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,new String[]{"第一页","第二页","第三页"}),this);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey(SELECTED_ITEM))
        {
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_ITEM));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM,getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Fragment fragment=new DummyFragment();
        Bundle args=new Bundle();
        args.putInt(DummyFragment.ARG_SECTION_NUMBER,itemPosition+1);
        fragment.setArguments(args);
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.commit();
        return true;
    }
}
