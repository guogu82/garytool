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

import com.gary.garytool.R;
import com.gary.garytool.business.map.api.Coordinates;
import com.gary.garytool.business.map.api.MapViewGroup;
import com.gary.garytool.business.map.api.MapsConstants;
import com.gary.garytool.business.map.api.MapsType;
import com.gary.garytool.business.map.api.PointMarker;


import java.io.File;

/**
 * Created by Administrator on 2016/3/29.
 */
public class TouristMainActivity extends Activity{
    private Double initLongitude = 113.255046875; // 进入activity默认的精度
    private Double initLatitude = 22.835714843; // 进入activity默认的纬度
    private MapViewGroup mMapTourist = null;
    private String extendSdRootPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_main_activity);

        // 栅格地图 示例代码
        mMapTourist = (MapViewGroup) findViewById(R.id.map_tourist);

        setExtendSdRootPath();

        // 设置地图缓存位置
        setMapCache();

        // 将坐标转换成适应清晖园级别的坐标
        Coordinates coordinates = MapCoordSkewingUtils.CoordSkewingByLevel(initLongitude, initLatitude, 0);

        // 参数为地图 中心点位置以及地图显示等级
        mMapTourist.initMaps(coordinates.X, coordinates.Y, 0);
        mMapTourist.show();

        // 地图后台自动刷新功能
        localHandler.post(localTasks);
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

            localMarker.Cx = 113.293106;
            localMarker.Cy = 22.805604;
            localMarker.nameStr = "我的位置";
            localMarker.Info = -1;

            mMapTourist.refreshMaps();
            localHandler.postDelayed(localTasks, 2000);

        }
    };
    private void setMapCache() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）
        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）


        if (densityDpi > 320) {// 根据分辨率设置地图显示大小
            MapsConstants.MPicWidth = 512;
            MapsConstants.MPicHeight = 512;
        }

        // 设置栅格地图参数：本地地图、图片格式、本地图片缓存、图片路径
        //设置本地地图
        MapsConstants.isLocal=true;
        // 设置图片格式
        MapsConstants.PicType = ".png";
        // 设置本地图片缓存
        MapsConstants.CacheMapsRoot = "/mnt/sdcard/test/";
        // 设置图片路径
       // MapsConstants.MapsRoot = "http://mapdb.365ditu.cn/rt/mapdb/";
        MapsConstants.MapsRoot = "";
        MapsConstants.LocalMapsRoot =extendSdRootPath+"/touristguide/external_sd/cgt_maps/airscape";

        MapsConstants.Directorys = new String[] { "0","1", "2" };
        MapsConstants.ScaleFactors = new double[] {0.0008, 0.0004, 0.0002 };
        MapsConstants.GridFactors = new int[] { 100, 100, 100};
        MapsConstants.MapsLevelCount=3;
        // Toast.makeText(MainActivity.this,
        // "path=="+MapsConstants.LocalMapsRoot, Toast.LENGTH_LONG).show();
    }

    /**
     * 设置文件所在存储卡的跟路径
     */
    private void setExtendSdRootPath() {
        // TODO Auto-generated method stub
        StorageManager sm = (StorageManager) this.getSystemService(Context.STORAGE_SERVICE);
        try {
            String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths", null).invoke(sm, null);
            for (String path : paths) {
                // Toast.makeText(this,path,1).show();
                File file = new File(path + "/touristguide");
                if (file.exists()) {
                    extendSdRootPath = path;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
