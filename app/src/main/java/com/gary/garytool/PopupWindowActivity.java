package com.gary.garytool;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;


public class PopupWindowActivity extends Activity {

    private EditText mSelectSchool;
    /**
     * popView相关
     */
    private View parent;
    private ListView mProvinces;
    private ListView mSchools;
    private TextView mTitle;
    private PopupWindow mPopupWindow;

    /**
     *  Adapter相关
     */
    private ProvinceAdapter mProvinceAdapter;
    private SchoolAdapter mSchoolAdapter;
    private String mProvinceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);

        initView();
    }

    private void initView() {
        mSelectSchool= (EditText) findViewById(R.id.et_select_school);

        initPopView();

        mSelectSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
    }

    private void initPopView() {
        parent =getWindow().getDecorView();
        View popView=View.inflate(this,R.layout.popwindow_view_select_province_list,null);
        mTitle= (TextView) popView.findViewById(R.id.tv_title);
        mProvinces= (ListView) popView.findViewById(R.id.lv_province);
        mSchools= (ListView) popView.findViewById(R.id.lv_school);

        mProvinceAdapter=new ProvinceAdapter(this);
        mProvinces.setAdapter(mProvinceAdapter);
        mSchoolAdapter=new SchoolAdapter(this);
        mSchools.setAdapter(mSchoolAdapter);

        //设置popwindow的大小
        int width=getResources().getDisplayMetrics().widthPixels*3/4;
        int height=getResources().getDisplayMetrics().heightPixels*3/5;
        mPopupWindow=new PopupWindow(popView,width,height);
        ColorDrawable dw=new ColorDrawable(0x30000000);
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);//允许在外侧点击取消

        loadProvince();

        mPopupWindow.setOnDismissListener(listener);
    }

    private void loadProvince() {

    }

    private void showPopWindow() {

    }

    private class ProvinceAdapter extends BaseAdapter {
        private Context mContext;
        public ProvinceAdapter(Context context) {
                mContext=context;
        }


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private class SchoolAdapter extends BaseAdapter {


        private  Context mContext;

        public SchoolAdapter(Context context) {
             mContext = context;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private PopupWindow.OnDismissListener listener=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            mTitle.setText("选择地区");
            mProvinces.setVisibility(View.VISIBLE);
            mSchoolAdapter.setList(new ArrayList<SchoolList>());
        }
    };
}
