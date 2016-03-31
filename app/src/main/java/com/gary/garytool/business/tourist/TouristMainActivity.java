package com.gary.garytool.business.tourist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.gary.garytool.R;
import com.gary.garytool.business.map.api.Coordinates;
import com.gary.garytool.business.map.api.MapViewGroup;
import com.gary.garytool.business.map.api.MapsConstants;
import com.gary.garytool.business.map.api.MapsType;
import com.gary.garytool.business.map.api.PointMarker;
import com.gary.garytool.util.Util;


import java.io.File;

/**
 * Created by Administrator on 2016/3/29.
 */
public class TouristMainActivity extends Activity{
    private Double initLongitude = 113.255046875; // 进入activity默认的精度
    private Double initLatitude = 22.835714843; // 进入activity默认的纬度
    private Coordinates mCenterCoordinates;
    private MapViewGroup mMapTourist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_main_activity);
        //初始化地图
        initMap();
    }

    //设置按钮响应事件
    public void onSetting(View view)
    {
        Util.startActivity(TouristMainActivity.this, TouristSettingActivity.class);
    }
    //设置按钮响应事件
    public void onTeam(View view)
    {
        Util.startActivity(TouristMainActivity.this,TouristBuildTeamActivity.class);
    }

    private void initMap() {
        // 栅格地图 示例代码
        mMapTourist = (MapViewGroup) findViewById(R.id.map_tourist);

        // 设置地图缓存位置
        setMapCache();

        // 将坐标转换成适应清晖园级别的坐标
        mCenterCoordinates = MapCoordSkewingUtils.CoordSkewingByLevel(initLongitude, initLatitude, 0);

        // 参数为地图 中心点位置以及地图显示等级
        mMapTourist.initMaps(mCenterCoordinates.X, mCenterCoordinates.Y, 0);
        mMapTourist.show();

        // 地图放大缩小
        zoomInOut();
        // 定位
        location();

        // 地图后台自动刷新功能
        localHandler.post(localTasks);
    }

    private void location() {
        Button location = (Button) findViewById(R.id.bt_location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 定位操作
                mMapTourist.setCenter(localMarker.Cx, localMarker.Cy);// 设置地图中心点位置
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localHandler.removeCallbacks(localTasks);
        localHandler = null;
        mMapTourist.Dispose();
        mMapTourist = null;
    }

    private void zoomInOut() {

        final Button zoomIn = (Button) findViewById(R.id.bt_zoom_in);
        final Button zoomOut = (Button) findViewById(R.id.bt_zoom_out);

        zoomIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                int level = mMapTourist.getMapsLevel();
                int max = mMapTourist.getMaxLevel();
                mMapTourist.setMapsLevel(level + 1);
                if (level == max) {
                    zoomIn.setEnabled(false);
                }
                zoomOut.setEnabled(true);
            }
        });

        zoomOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int level = mMapTourist.getMapsLevel();
                mMapTourist.setMapsLevel(level - 1);
                if (level == 0) {
                    zoomOut.setEnabled(false);
                }
                zoomIn.setEnabled(true);
            }
        });
    }

    // 地图刷新线程
    private Handler localHandler = new Handler();
    private PointMarker localMarker = null;
    private Bitmap localBitmap;

    private Runnable localTasks = new Runnable() {
        public void run() {
            if (localBitmap == null)
                localBitmap = BitmapFactory.decodeResource(TouristMainActivity.this.getResources(), R.drawable.map_icon_locr_light);

            if (localMarker == null) {
                localMarker = new PointMarker(localBitmap, localBitmap.getWidth() / -2, localBitmap.getHeight() / -2);
                mMapTourist.addMarker(localMarker);
            }
            mCenterCoordinates=MapCoordSkewingUtils.CoordSkewingByLevel(initLongitude, initLatitude, mMapTourist.getMapsLevel());
            localMarker.Cx = mCenterCoordinates.X;
            localMarker.Cy = mCenterCoordinates.Y;
            localMarker.nameStr = "我的位置";
            localMarker.Info = -1;

            mMapTourist.refreshMaps();
            localHandler.postDelayed(localTasks, 2000);

        }
    };
    private void setMapCache() {
        // 设置栅格地图参数：本地地图、图片格式、本地图片缓存、图片路径
        //设置本地地图
        MapsConstants.isLocal=true;
        // 设置图片格式
        MapsConstants.PicType = ".png";
        // 设置本地图片缓存
        MapsConstants.CacheMapsRoot = "";
        // 设置图片路径
        MapsConstants.MapsRoot = "";
        MapsConstants.LocalMapsRoot = Util.getSDPath()+"/touristguide/external_sd/cgt_maps/";

        //设置地图的因子，控制读取文件目录路径
        MapsConstants.Directorys = new String[] { "0","1", "2" };
        MapsConstants.ScaleFactors = new double[] {0.0008, 0.0004, 0.0002 };
        MapsConstants.GridFactors = new int[] { 100, 100, 100};
        MapsConstants.MapsLevelCount=3;
    }


}
