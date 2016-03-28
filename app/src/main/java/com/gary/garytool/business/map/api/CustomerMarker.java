package com.gary.garytool.business.map.api;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;

public class CustomerMarker{
	public View MarkerView = null;
	public MapViewGroup mMapViewGroup = null;
	public int mWidth;
	public int mHeight;
	public double Cx;
	public double Cy;
	public float OffsetX=0;
	public float OffsetY=0;
	public boolean isVisible = false;
	public CustomerMarker(MapViewGroup mapViewGroup,double cx, double cy, int width,int height,float ox, float oy){
		this.mMapViewGroup = mapViewGroup;
		this.Cx = cx;
		this.Cy = cy;
		this.mWidth=width;
		this.mHeight=height;
		this.OffsetX = ox;
		this.OffsetY = oy;
		this.isVisible = true;
	}
	
	public void refresh(double cx, double cy, int width,int height,float ox, float oy){
		if(cx>0){
			this.Cx = cx;
		}
		if(cy>0){
			this.Cy = cy;
		}
		if(width>0){
			this.mWidth = width;
		}
		if(height>0){
			this.mHeight = height;
		}
		this.OffsetX = ox;
		this.OffsetY = oy;
		
		this.refresh();
	}
	
	public void refresh(){
		Point point=this.mMapViewGroup.getMapView().CoordinatesToPixel(this.Cx,this.Cy);
		int px=(int)(point.x+this.OffsetX);
		int py=(int)(point.y+this.OffsetY);		
		//Toast.makeText(this.Parent.getContext(), ""+this.MarkerView.getWidth()+"-"+this.MarkerView.getHeight(), Toast.LENGTH_LONG).show();
		this.MarkerView.layout(px, py, px+mWidth, py+mHeight);
	}
	
	public void setView(View view){
		MarkerView = view;
	}
	public void setView(int xmlId){
		LayoutInflater inflater = 
				(LayoutInflater) mMapViewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(xmlId, null);
		MarkerView = view;
	}
	
	public void show(){
		this.isVisible = true;
		this.MarkerView.setVisibility(View.VISIBLE);
	}
	public void hide(){
		this.isVisible = false;
		this.MarkerView.setVisibility(View.GONE);
	}
	
	public void Dispose(){
		MarkerView = null;
		this.mMapViewGroup = null;
	}
}
