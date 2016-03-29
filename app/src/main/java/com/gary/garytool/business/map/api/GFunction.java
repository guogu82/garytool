package com.gary.garytool.business.map.api;

import java.util.List;

public class GFunction {
	public GFunction(){}


	public static Bounds getBoundsCoordinates(Coordinates[] arr){//获取一个点集合的边框
		Bounds reBound=null;
		if(arr!=null&&arr.length>1){
			reBound = new Bounds(null,null);
			reBound.MaxCrd=new Coordinates(arr[0].X,arr[0].Y);
			reBound.MinCrd=new Coordinates(arr[0].X,arr[0].Y);
			for(int i=1;i<arr.length;i++){
				if(reBound.MinCrd.Y>arr[i].Y){
					reBound.MinCrd.Y=arr[i].Y;
				}
				if(reBound.MaxCrd.Y<arr[i].Y){
					reBound.MaxCrd.Y=arr[i].Y;
				}
				if(reBound.MinCrd.X>arr[i].X){
					reBound.MinCrd.X=arr[i].X;
				}
				if(reBound.MaxCrd.X<arr[i].X){
					reBound.MaxCrd.X=arr[i].X;
				}
			}
		}
		return reBound;
	}

	public static Bounds getBoundsCoordinates(List<Coordinates> arrs){//获取一个点集合的边框
		Bounds reBound = null;
		if(null != arrs && arrs.size() > 1){
			reBound = new Bounds(null,null);
			reBound.MaxCrd = new Coordinates(arrs.get(0).X, arrs.get(0).Y);
			reBound.MinCrd = new Coordinates(arrs.get(0).X, arrs.get(0).Y);
			for (int i = 1; i < arrs.size(); i++) {
				if(reBound.MinCrd.Y > arrs.get(i).Y){
					reBound.MinCrd.Y = arrs.get(i).Y;
				}
				if(reBound.MaxCrd.Y < arrs.get(i).Y){
					reBound.MaxCrd.Y = arrs.get(i).Y;
				}
				if(reBound.MinCrd.X > arrs.get(i).X){
					reBound.MinCrd.X = arrs.get(i).X;
				}
				if(reBound.MaxCrd.X < arrs.get(i).X){
					reBound.MaxCrd.X = arrs.get(i).X;
				}
			}
		}
		return reBound;
	}
	public static Coordinates getMaxDistanceCoordinates(Coordinates[] arr,Coordinates center){//获取一个点集合中离一个固定点最远的点
		Coordinates crd=null;
		if(arr!=null&&arr.length>0){
			crd=new Coordinates(arr[0].X,arr[0].Y);
			for(int i=1;i<arr.length;i++){
				if(Math.abs(arr[i].Y-center.Y)>Math.abs(crd.Y-center.Y)){crd.Y=arr[i].Y;}
				if(Math.abs(arr[i].X-center.X)>Math.abs(crd.X-center.X)){crd.X=arr[i].X;}
			}
		}
		return crd;
	}

}
