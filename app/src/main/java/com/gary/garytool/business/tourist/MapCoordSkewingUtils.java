package com.gary.garytool.business.tourist;



import com.gary.garytool.business.map.api.Coordinates;

import java.util.ArrayList;
import java.util.List;


/**
 * 根据经纬度及地图级别修正坐标及判断点是否在指定范围内
 * @author 邓文斌
 *
 */
public class MapCoordSkewingUtils {
	
	/**
	 * 根据地图级别将坐标转换成清晖园特定的经纬度
	 * @param x 经度
	 * @param y 纬度
	 * @param level 地图级别
	 * @return 经纬度坐标
	 */
	public static Coordinates CoordSkewingByLevel(double x,double y, int level){
		Coordinates coordinates = new Coordinates(x, y);
		if(level == 0){
			coordinates.X = coordinates.X - 0.00040;
			coordinates.Y = coordinates.Y - 0.00014;
		} else if(level == 1){
			coordinates.X = coordinates.X + 0.00026;
			coordinates.Y = coordinates.Y - 0.00012;
		}
		return coordinates;
	}
	
	/**
	 * 判断传入的点是否在传入的范围内
	 * @param coordList 传入点的范围
	 * @param x 要判断的经度
	 * @param y 要判断的纬度
	 * @return true|false(true:在范围内，false:不在范围内)
	 */
	public static boolean isInTheRange(List<Coordinates> coordList, double x,double y){
		ArrayList<Double> lngList = new ArrayList<Double>();//所有点的经度集合
		ArrayList<Double> latList = new ArrayList<Double>();//所有点的纬度集合
		
		for (Coordinates coors : coordList) {
			lngList.add(coors.X);
			latList.add(coors.Y);
		}
		
		double maxLng = arrayListMax(lngList);//最大经度
		double minLng = arrayListMin(lngList);//最小经度
		double maxLat = arrayListMax(latList);//最大纬度
		double minLat = arrayListMin(latList);//最小纬度
		if(x >= minLng && x <= maxLng){//如果经度大于集合中最小经度且小于集合中最大经度则说明经度在范围内
			if(y >= minLat && y <= maxLat){//如果纬度大于集合中最小纬度且小于集合中最大纬度则说明纬度在范围内
				return true;
			}
		}
		return false;
	}
	
	//获取ArrayList中的最大值
	@SuppressWarnings("rawtypes")
	private static double arrayListMax(ArrayList sampleList) {
		try {
			double maxDevation = 0.0;
			int totalCount = sampleList.size();
			if (totalCount >= 1) {
				double max = Double.parseDouble(sampleList.get(0).toString());
				for (int i = 0; i < totalCount; i++) {
					double temp = Double.parseDouble(sampleList.get(i).toString());
					if (temp > max) {
						max = temp;
					}
				}
				maxDevation = max;
			}
			return maxDevation;
		} catch (Exception ex) {
			throw ex;
		}

	}
	
	//获取ArrayList中的最小值
	@SuppressWarnings("rawtypes")
	private static double arrayListMin(ArrayList sampleList) {
		try {
			double mixDevation = 0.0;
			int totalCount = sampleList.size();
			if (totalCount >= 1) {
				double min = Double.parseDouble(sampleList.get(0).toString());
				for (int i = 0; i < totalCount; i++) {
					double temp = Double.parseDouble(sampleList.get(i).toString());
					if (min > temp) {
						min = temp;
					}
				}
				mixDevation = min;
			}
			return mixDevation;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
}
