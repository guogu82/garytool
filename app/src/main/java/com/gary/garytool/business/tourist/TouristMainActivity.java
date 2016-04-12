package com.gary.garytool.business.tourist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.gary.garytool.R;
import com.gary.garytool.business.map.api.Coordinates;
import com.gary.garytool.business.map.api.MapViewGroup;
import com.gary.garytool.business.map.api.MapsConstants;
import com.gary.garytool.business.map.api.MapsType;
import com.gary.garytool.business.map.api.PointMarker;
import com.gary.garytool.util.Util;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class TouristMainActivity extends Activity{
    private Double initLongitude = 113.255146875; // 进入activity默认的精度
    private Double initLatitude = 22.835714843; // 进入activity默认的纬度
    private Coordinates mCenterCoordinates;
    private MapViewGroup mMapTourist = null;


    List<ScenicInfo> mScenicList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_main_activity);
        //初始化地图
        initMap();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMainMap();
    }

    private void initData() {
        ScenicUtil.sScenicAll=ScenicUtil.getAllScenicSpot();
        mScenicList=ScenicUtil.getAllScenicSpot();
        mScenicList.add(new ScenicInfo("商店",113.255573f,22.835100f,false,101,0,ScenicUtil.SCENIC_TAG_SHOP));
        mScenicList.add(new ScenicInfo("洗手间A",113.254613f,22.836230f,false,201,0,ScenicUtil.SCENIC_TAG_WC));
        mScenicList.add(new ScenicInfo("洗手间B", 113.255933f, 22.835590f, false, 202, 0,ScenicUtil.SCENIC_TAG_WC));
        addAroundMarker();
        refreshAroundMarker();
    }



    private void addAroundMarker() {
        Bitmap pointBitmap = null;// 标注图片
        for (ScenicInfo info : mScenicList) {
            if (ScenicUtil.SCENIC_TAG_SHOP.equals(info.getTag())) {
                pointBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tourist_shop);;
            } else if (ScenicUtil.SCENIC_TAG_WC.equals(info.getTag())) {
                pointBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tourist_wc);
            } else{
                if (info.isHotSpot()) {
                    pointBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tourist_hot_scenic);
                } else {
                    pointBitmap =BitmapFactory.decodeResource(getResources(), R.drawable.tourist_scenic);
                }
            }
            info.setTagIcon(pointBitmap);             ;
            PointMarker pointMarker = new PointMarker(pointBitmap, pointBitmap.getWidth() / -2, pointBitmap.getHeight() / -2);
            Coordinates coordinates = MapCoordSkewingUtils.CoordSkewingByLevel(info.getLongitude(),info.getLatitude() , mMapTourist.getMapsLevel());
            pointMarker.Cx = coordinates.X;
            pointMarker.Cy = coordinates.Y;
            pointMarker.nameStr = info.getName();
            info.setTagMarker(pointMarker);
            mMapTourist.addMarker(pointMarker);
        }
    }



    //设置按钮响应事件
    public void onSetting(View view)
    {
        Util.startActivity(TouristMainActivity.this, TouristSettingActivity.class);
    }
    //团队按钮响应事件
    public void onTeam(View view)
    {
        Util.startActivity(TouristMainActivity.this,TouristBuildTeamActivity.class);
    }
    //景点按钮响应事件
    public void onScenicSpot(View view) {
        Util.startActivity(TouristMainActivity.this, TouristScenicSpotActivity.class);
     }

    private void initMap() {
        // 栅格地图 示例代码
        mMapTourist = (MapViewGroup) findViewById(R.id.map_tourist);

        // 设置地图缓存位置
        setMapCache();

        currentMapType=ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_AIRSCAPE];

        // 将坐标转换成适应清晖园级别的坐标
        mCenterCoordinates = MapCoordSkewingUtils.CoordSkewingByLevel(initLongitude, initLatitude, 1);

        // 参数为地图 中心点位置以及地图显示等级
        mMapTourist.initMaps(mCenterCoordinates.X, mCenterCoordinates.Y, 1);
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
                setCenter();

            }
        });
    }

    private void setCenter() {
        // 定位操作
        mCenterCoordinates= MapCoordSkewingUtils.CoordSkewingByLevel(initLongitude, initLatitude, mMapTourist.getMapsLevel());
        mMapTourist.setCenter( mCenterCoordinates.X, mCenterCoordinates.Y);// 设置地图中心点位置
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
                if (level>= 1) {
                    zoomIn.setVisibility(View.INVISIBLE);
                }
                zoomOut.setVisibility(View.VISIBLE);
                setCenter();
                refreshMainMap();

            }

        });

        zoomOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                int level = mMapTourist.getMapsLevel();
                mMapTourist.setMapsLevel(level - 1);
                if (level <= 1) {
                    zoomOut.setVisibility(View.INVISIBLE);
                }
                zoomIn.setVisibility(View.VISIBLE);
                setCenter();
                refreshMainMap();

            }
        });
    }

    // 地图刷新线程
    private Handler localHandler = new Handler();
    private PointMarker localMarker = null;
    private Bitmap localBitmap;
    private boolean currentMapType;

    private Runnable localTasks = new Runnable() {
        public void run() {
            refreshMainMap();
            localHandler.postDelayed(localTasks, 2000);

        }
    };

    public void refreshMainMap() {
        refreshMyLocation();

        refreshAroundMarker();
        if(currentMapType!= ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_AIRSCAPE])
        {
            mMapTourist.setMapsType(ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_AIRSCAPE]? MapsType.Airscape:MapsType.Typical);
            currentMapType=ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_AIRSCAPE];
        }

        mMapTourist.refreshMaps();
    }

    private void refreshMyLocation() {
        if (localBitmap == null)
            localBitmap = BitmapFactory.decodeResource(TouristMainActivity.this.getResources(), R.drawable.map_icon_locr_light);

        if (localMarker == null) {
            localMarker = new PointMarker(localBitmap, localBitmap.getWidth() / -2, localBitmap.getHeight() / -2);
            mMapTourist.addMarker(localMarker);
        }
        mCenterCoordinates= MapCoordSkewingUtils.CoordSkewingByLevel(initLongitude, initLatitude, mMapTourist.getMapsLevel());
        localMarker.Cx = mCenterCoordinates.X;
        localMarker.Cy = mCenterCoordinates.Y;
        localMarker.nameStr = "我的位置";
        localMarker.Info = -1;
    }

    private void refreshAroundMarker() {
        for (ScenicInfo info:mScenicList)
        {
            if(info.getTagMarker()!=null)
            {
                PointMarker pointMarker = info.getTagMarker();
                Coordinates coordinates = MapCoordSkewingUtils.CoordSkewingByLevel(info.getLongitude(),info.getLatitude() , mMapTourist.getMapsLevel());
                pointMarker.Cx = coordinates.X;
                pointMarker.Cy = coordinates.Y;
                if(info.getTag().equals(ScenicUtil.SCENIC_TAG_SHOP))
                {
                    if(ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_SHOW_STOP])
                    {
                        pointMarker.show();
                    }
                    else
                    {
                        pointMarker.hide();
                    }
                } else if(info.getTag().equals(ScenicUtil.SCENIC_TAG_SCENIC))
                {
                    if(ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_IS_HOT_SCENIC])
                    {
                        pointMarker.hide();
                    }
                    else
                    {
                        pointMarker.show();
                    }
                } else if(info.getTag().equals(ScenicUtil.SCENIC_TAG_WC))
                {
                    if(ScenicUtil.SETTING_SCENIC[ScenicUtil.SETTING_INDEX_SHOW_WC])
                    {
                        pointMarker.show();
                    }
                    else
                    {
                        pointMarker.hide();
                    }
                }

            }
        }
    }

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
