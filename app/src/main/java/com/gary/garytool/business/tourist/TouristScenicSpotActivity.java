package com.gary.garytool.business.tourist;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.gary.garytool.R;
import com.gary.garytool.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public class TouristScenicSpotActivity extends Activity{
    private TextView mTvTopBarTitle;
    private ListView mLvScenic;
    private ScenicAdapter mAdapter;
    private List<ScenicInfo> mData;
    private ImageView mIvScenic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_scenic_activity);

        init();
        initData();
        initEvent();
    }

    private void initEvent() {
        mLvScenic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScenicUtil.sCurrentSpotDetailScenicInfo=new ScenicInfo(mData.get(position));
                Util.startActivity(TouristScenicSpotActivity.this, TouristScenicDetailActivity.class);
            }
        });
    }

    private void initData() {
        buildLvData();
        mAdapter=new ScenicAdapter();
        mLvScenic.setAdapter(mAdapter);
    }

    private void init() {
        mTvTopBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        mTvTopBarTitle.setText("景点");
        mLvScenic= (ListView) findViewById(R.id.lv_scenic);
        mIvScenic= (ImageView) findViewById(R.id.iv_scenic);
        String path=Util.getSDPath() + "/touristguide/qinghuiyuan/image/p.jpg";
        mIvScenic.setBackground(new BitmapDrawable(getResources(),BitmapFactory.decodeFile(path)));
    }

    public void buildLvData() {
        mData=new ArrayList<>();
        for(ScenicInfo info:ScenicUtil.sScenicAll)
        {
            //根目录清晖园的id==0
            if(info.getParentId()==0)
                mData.add(info);
        }
    }

    public class ScenicAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView==null)
            {
                convertView= Util.getView(TouristScenicSpotActivity.this,R.layout.tourist_scenic_item);
                holder=new Holder();
                holder.id= (Button) convertView.findViewById(R.id.bt_scenic_spot_item_id);
                holder.title= (TextView) convertView.findViewById(R.id.tv_scenic_spot_item_title);
                convertView.setTag(holder);
            }
            else
            {
                holder= (Holder) convertView.getTag();
            }

            ScenicInfo info=mData.get(position);
            holder.id.setText(info.getId() + "");
            holder.title.setText(info.getName());
            return convertView;
        }


    }
    public static class Holder
    {
        public Button id;
        public TextView title;
    }

    public void onBarBack(View view) {
        finish();
    }
}
