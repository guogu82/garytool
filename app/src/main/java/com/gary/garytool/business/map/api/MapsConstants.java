package com.gary.garytool.business.map.api;

public class MapsConstants {public MapsConstants(){}

	public static int EarthRadius=6378137;//地球半径

	//public static String MapsRoot="http://cgmap.shunde.gov.cn/";//地图图片路径
	public static String MapsRoot = "http://202.104.25.195/rt/mapdb/"; // 图片路径
	public static String LocalMapsRoot="/mnt/sdcard/external_sd/cgt_maps/";
	//public static String CacheMapsRoot="/mnt/sdcard/external_sd/cgt_maps/cache/";
	public static String CacheMapsRoot = "/mnt/sdcard/"; // 本地图片缓存

//	public static String MapsCoordinatesType=CoordinatesType.BeiJing54;//地图坐标系类型

	public static double MPicWidth=256;//地图图片的宽度
	public static double MPicHeight=256;//地图图片的高度

	public static int MapsLevelCount=9;

	//public static String[] Directorys={"0","1","2","3","4","5","6","7","8"};//地图等级编号
	public static String[] Directorys = new String[] { "6","7", "8", "9", "10", "11", "12", "13", "14" };
	//public static double[] ScaleFactors={21675,10837,5419,2709,1355,677,339,169,81};//地图图片坐标跨度
	public static double[] ScaleFactors = new double[] { 0.4,0.2, 0.1, 0.04, 0.02, 0.01, 0.004, 0.002, 0.001 };
	//public static int[] GridFactors={10,10,10,10,10,40,40,40,40};//网格x和y方向图片数目
	public static int[] GridFactors = new int[] {  10, 10, 10, 10, 40, 40, 40, 40, 40 };

	public static String PicType=".png";//图片类型

	public static int MoveOffset=20;//地图移动时的坐标的偏移量

}
