package com.gary.garytool.business.map.api;

public class MapsEvent extends Event {
	public MapsEvent(String typeStr){
		super(typeStr);
	}

	public static final String TilelayerGuageChanged="TilelayerGuageChanged";//地图载入进度改变事件

	public static final String MapsSizeChanged="MapsSizeChanged";//地图大小改变事件
	public static final String MapsTypeChanged="MapsTypeChanged";//地图类型改变事件

	//地图等级改变
	public static final String MapsLevelChange="MapsLevelChange";//地图等级改变前事件
	public static final String MapsLevelChanging="MapsLevelChanging";//地图等级改变中事件
	public static final String MapsLevelChanged="MapsLevelChanged";//地图等级改变后事件

	//地图中心改变
	public static final String MapsCenterChange="MapsCenterChange";//地图中心改变前
	public static final String MapsCenterChanging="MapsCenterChanging";//地图中心改变中
	public static final String MapsCenterChanged="MapsCenterChanged";//地图中心改变后

	public static final String MapsMaxLevelChanged="MapsMaxLevelChanged";//地图的最大等级改变的事件
	public static final String MapsClicked="MapsClicked";//地图点击事件
	public static final String MapsLongPress="MapsLongPress";//地图长按事件
	
		
	public int MapsLevel=0;//地图等级
	public int MapsMaxLevel=0;//地图最大等级

	public double x=0;//地图x坐标
	public double y=0;//地图y坐标

	public double cx=0;//地图x坐标
	public double cy=0;//地图y坐标

	public String ChangeType=null;//改变类型

	public int mapsWidth=0;//地图宽度
	public int mapsHeight=0;//地图高度

	public String mapsType=null;//地图类型
}
