package com.gary.garytool;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gary.garytool.info.Province;
import com.gary.garytool.info.ProvinceList;
import com.gary.garytool.info.School;
import com.gary.garytool.info.SchoolList;
import com.gary.garytool.util.LogUtil;
import com.gary.garytool.volley.GsonRequest;
import com.gary.garytool.volley.VolleyManger;

import java.util.ArrayList;
import java.util.List;


public class PopupWindowActivity extends Activity {

    private static final String TAG = "PopupWindowActivity";
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
     * Adapter相关
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
        mSelectSchool = (EditText) findViewById(R.id.et_select_school);

        initPopView();

        mSelectSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
    }

    private void initPopView() {
        parent = getWindow().getDecorView();
        View popView = View.inflate(this, R.layout.popwindow_view_select_province_list, null);
        mTitle = (TextView) popView.findViewById(R.id.tv_title);
        mProvinces = (ListView) popView.findViewById(R.id.lv_province);
        mSchools = (ListView) popView.findViewById(R.id.lv_school);
        mProvinces.setOnItemClickListener(itemListener);
        mSchools.setOnItemClickListener(itemListener);

        mProvinceAdapter = new ProvinceAdapter(this);
        mProvinces.setAdapter(mProvinceAdapter);
        mSchoolAdapter = new SchoolAdapter(this);
        mSchools.setAdapter(mSchoolAdapter);

        //设置popwindow的大小
        int width = getResources().getDisplayMetrics().widthPixels * 3 / 4;
        int height = getResources().getDisplayMetrics().heightPixels * 3 / 5;
        mPopupWindow = new PopupWindow(popView, width, height);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);//允许在外侧点击取消

        loadProvince();

        mPopupWindow.setOnDismissListener(listener);
    }

    private void loadProvince() {
        String url = "http://www.hisihi.com/app.php?s=/school/province";
        GsonRequest<Province> request = new GsonRequest<Province>(Request.Method.POST, url, Province.class, new Response.Listener<Province>() {
            @Override
            public void onResponse(Province province) {
                if (province.getData() != null && province.getError_code() == 0) {
                    mProvinceAdapter.setList(province.getData());
                    mProvinceAdapter.notifyDataSetChanged();
                    //{"success":true,"error_code":0,"message":"\u83b7\u53d6\u7701\u4fe1\u606f\u6210\u529f","data":[{"province_id":"1","province_name":"\u5317\u4eac"},
                    // {"province_id":"2","province_name":"\u5929\u6d25"},{"province_id":"3","province_name":"\u6cb3\u5317"},{"province_id":"4","province_name":"\u5c71\u897f"},
                    // {"province_id":"5","province_name":"\u5185\u8499\u53e4"},{"province_id":"6","province_name":"\u8fbd\u5b81"},{"province_id":"7","province_name":"\u5409\u6797"},
                    // {"province_id":"8","province_name":"\u9ed1\u9f99\u6c5f"},{"province_id":"9","province_name":"\u4e0a\u6d77"},{"province_id":"10","province_name":"\u6c5f\u82cf"},
                    // {"province_id":"11","province_name":"\u6d59\u6c5f"},{"province_id":"12","province_name":"\u5b89\u5fbd"},{"province_id":"13","province_name":"\u798f\u5efa"},
                    // {"province_id":"14","province_name":"\u6c5f\u897f"},{"province_id":"15","province_name":"\u5c71\u4e1c"},{"province_id":"16","province_name":"\u6cb3\u5357"},
                    // {"province_id":"17","province_name":"\u6e56\u5317"},{"province_id":"18","province_name":"\u6e56\u5357"},{"province_id":"19","province_name":"\u5e7f\u4e1c"},
                    // {"province_id":"20","province_name":"\u5e7f\u897f"},{"province_id":"21","province_name":"\u6d77\u5357"},{"province_id":"22","province_name":"\u91cd\u5e86"},
                    // {"province_id":"23","province_name":"\u56db\u5ddd"},{"province_id":"24","province_name":"\u8d35\u5dde"},{"province_id":"25","province_name":"\u4e91\u5357"},
                    // {"province_id":"26","province_name":"\u897f\u85cf"},{"province_id":"27","province_name":"\u9655\u897f"},{"province_id":"28","province_name":"\u7518\u8083"},
                    // {"province_id":"29","province_name":"\u9752\u6d77"},{"province_id":"30","province_name":"\u5b81\u590f"},{"province_id":"31","province_name":"\u65b0\u7586"},
                    // {"province_id":"32","province_name":"\u53f0\u6e7e"},{"province_id":"33","province_name":"\u9999\u6e2f"},{"province_id":"34","province_name":"\u6fb3\u95e8"}]}
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PopupWindowActivity.this, "省份列表获取失败", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyManger.getVolleyRequestQueue().add(request);
    }

    private void showPopWindow() {
        mPopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

    }


    private AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent == mProvinces) {
                ProvinceList provinceName = (ProvinceList) mProvinces.getItemAtPosition(position);
                mProvinceId = provinceName.getProvince_id();
                mTitle.setText("选择学校");
                mProvinces.setVisibility(View.GONE);
                mSchools.setVisibility(View.VISIBLE);
                loadSchool();
            } else if (parent == mSchools) {
                SchoolList schoolName = (SchoolList) mSchools.getItemAtPosition(position);
                mSelectSchool.setText(schoolName.getSchool_name());
                mPopupWindow.dismiss();
            }
        }
    };

    private void loadSchool() {
        String url = "http://www.hisihi.com/app.php?s=/school/school/provinceid/";
        GsonRequest<School> request = new GsonRequest<School>(Request.Method.POST, url + mProvinceId, School.class, new Response.Listener<School>() {
            @Override
            public void onResponse(School school) {
                if (school.getData() != null && school.getError_code() == 0) {
                    mSchoolAdapter.setList(school.getData());
                    mSchoolAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PopupWindowActivity.this, "获取学校失败", Toast.LENGTH_SHORT).show();
            }
        });

        VolleyManger.getVolleyRequestQueue().add(request);
    }

    private PopupWindow.OnDismissListener listener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            mTitle.setText("选择地区");
            mProvinces.setVisibility(View.VISIBLE);
            mSchoolAdapter.setList(new ArrayList<SchoolList>());
        }
    };

    private class ProvinceAdapter extends BaseAdapter {
        private Context mContext;
        private List<ProvinceList> mList = new ArrayList<>();

        public ProvinceAdapter(Context context) {
            mContext = context;
        }

        public void setList(List<ProvinceList> data) {
            mList = data;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_item_province_list, null, false);
                holder = new ViewHolder();
                holder.nameView = (TextView) convertView.findViewById(R.id.tv_province_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ProvinceList provinceList = mList.get(position);
            if (provinceList.getProvince_name() != null) {
                holder.nameView.setText(provinceList.getProvince_name());
            }
            return convertView;
        }

        private class ViewHolder {
            TextView nameView;
        }


    }

    private class SchoolAdapter extends BaseAdapter {


        private Context mContext;
        private List<SchoolList> mList = new ArrayList<>();

        public SchoolAdapter(Context context) {
            mContext = context;
        }

        public void setList(List<SchoolList> schoolLists) {
            mList = schoolLists;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_item_school_list, null, false);
                holder = new ViewHolder();
                holder.nameView = (TextView) convertView.findViewById(R.id.tv_school_name);
                convertView.setTag(holder);
                LogUtil.d(TAG,"converView==null, position="+position);
            } else {
                holder = (ViewHolder) convertView.getTag();
                LogUtil.d(TAG,"converView no null, position="+position);
            }
            SchoolList schoolList = mList.get(position);
            if (schoolList.getSchool_name() != null) {
                holder.nameView.setText(schoolList.getSchool_name());
            }
            return convertView;
        }

        private class ViewHolder {
            TextView nameView;
        }


    }
}
