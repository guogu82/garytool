package com.gary.garytool.business.map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gary.garytool.R;
import com.gary.garytool.business.map.api.MapViewGroup;
import com.gary.garytool.business.map.api.MapsConstants;
import com.gary.garytool.business.map.api.PointMarker;

/**
 * Created by Administrator on 2016/3/28.
 * @author garytool
 */
public class MapMainActivity extends Activity {
    protected MapViewGroup map = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_main_activity);

        if (isTabletDevice()) {
            // 判断是否为pad 调整相对布局的位置
            //ImageView locationImageView = (ImageView) findViewById(R.id.mapview_location);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 5, 65);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            //locationImageView.setLayoutParams(lp);
        }

        // 栅格地图 示例代码
        this.map = (MapViewGroup) findViewById(R.id.mapview_map);
        // 参数为地图 中心点位置以及地图显示等级
        map.initMaps(113.293106, 22.805604, 5);
        map.show();

        // 放大 缩小 示例代码
        zoomInOut();

        // 定位 示例代码
        location();

        // 地图后台自动刷新功能
        localHandler.post(localTasks);

        // 设置栅格地图参数：本地地图、图片格式、本地图片缓存、图片路径
        // 示例代码
        //设置本地地图
        MapsConstants.isLocal=false;
        //设置图片格式
        MapsConstants.PicType=".png";
        //设置本地图片缓存
        MapsConstants.CacheMapsRoot="/mnt/sdcard/map/";
        //设置图片路径
        //MapsConstants.MapsRoot = "http://202.104.25.195/rt/mapdb/";
        MapsConstants.MapsRoot = "http://mapdb.365ditu.cn/rt/mapdb/";

        MapsConstants.Directorys = new String[] { "6","7", "8", "9", "10", "11", "12", "13", "14" };
        MapsConstants.ScaleFactors = new double[] { 0.4,0.2, 0.1, 0.04, 0.02, 0.01, 0.004, 0.002, 0.001 };
        MapsConstants.GridFactors = new int[] {  10, 10, 10, 10, 40, 40, 40, 40, 40 };
        MapsConstants.MapsLevelCount=9;
        //屏幕坐标转换经纬度
        //Coordinates monitorCoordinates=map.getMapView().PixelToCoordinates(100, 200);
        //android.graphics.Point monitorPixel=map.getMapView().CoordinatesToPixel(113.293106, 22.805604);
    }

    private void zoomInOut() {

        // 获取放大，缩小按钮，并设置按钮事件
        final ImageView zoomInBtn = (ImageView) findViewById(R.id.mapview_zoomin);
        final ImageView zoomOutBtn = (ImageView) findViewById(R.id.mapview_zoomout);

        zoomInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // 放大操作 示例代码
                int level = map.getMapsLevel();
                map.setMapsLevel(level + 1);
                if (level == 6) {
                    zoomInBtn.setEnabled(false);
                }
                zoomOutBtn.setEnabled(true);
            }
        });

        zoomOutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 缩小操作 示例代码
                int level = map.getMapsLevel();
                map.setMapsLevel(level - 1);
                if (level == 1) {
                    zoomOutBtn.setEnabled(false);
                }
                zoomInBtn.setEnabled(true);
            }
        });
    }

    private void location() {
        // 获取定位按钮，并设置按钮事件
        ImageView mapview_location = (ImageView) findViewById(R.id.mapview_location);
        mapview_location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // 定位操作 示例代码
                // 获取定位图片
                Bitmap pointBitmap = BitmapFactory.decodeResource(MapMainActivity.this.getResources(), R.drawable.map_icon_locr_light);
                // 画点的位置
                PointMarker pointMarker = new PointMarker(pointBitmap, pointBitmap.getWidth() / -2, pointBitmap.getHeight() / -2);
                pointMarker.Cx = 113.293106;
                pointMarker.Cy = 22.805604;
                pointMarker.nameStr = "我的位置";
                pointMarker.Info = -1;
                map.setCenter(pointMarker.Cx, pointMarker.Cy);
                map.addMarker(pointMarker);

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
                localBitmap = BitmapFactory.decodeResource(MapMainActivity.this.getResources(), R.drawable.map_icon_locr_light);

            if (localMarker == null) {
                localMarker = new PointMarker(localBitmap, localBitmap.getWidth() / -2, localBitmap.getHeight() / -2);
                map.addMarker(localMarker);
            }

            localMarker.Cx = 113.293106;
            localMarker.Cy = 22.805604;
            localMarker.nameStr = "我的位置";
            localMarker.Info = -1;

            map.refreshMaps();
            localHandler.postDelayed(localTasks, 2000);

        }
    };
    // 判断当前设备是否pad
    public boolean isTabletDevice() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2.0) + Math.pow(dm.heightPixels, 2.0));
        double screenSize = diagonalPixels / (160 * dm.density);

        return screenSize > 7 ? true : false;
    }
}
