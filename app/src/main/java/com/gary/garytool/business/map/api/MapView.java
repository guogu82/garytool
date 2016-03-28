package com.gary.garytool.business.map.api;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/3/28.
 */
public class MapView extends BaseMaps {
    public MapView(Context context){
        super(context);
    }

    public MapView(Context context,AttributeSet attrs){
        super(context, attrs);
    }

    public MapView(Context context,double xc,double yc,int level,int mapWidth,int mapHeight){
        super(context,xc,yc,level,mapWidth,mapHeight);
    }

}
