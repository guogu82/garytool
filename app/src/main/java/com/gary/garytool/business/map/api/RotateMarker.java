package com.gary.garytool.business.map.api;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

public class RotateMarker extends BaseMarker {
	public RotateMarker(MapViewGroup mapViewGroup, Bitmap img, double cx, double cy, double cx2, double cy2, float ox,float oy){
		this.mMapViewGroup = mapViewGroup;
		this.bitmap=img;
		this.OffsetX=ox;
		this.OffsetY=oy;
		this.Cx = cx;
		this.Cy = cy;
		this.Cx2 = cx2;
		this.Cy2 = cy2;
	}
	
	public MapViewGroup mMapViewGroup;
	public Bitmap bitmap=null;
	public float Degree;
	public double Cx2=0;
	public double Cy2=0;
	
	public void updateRotate(double cx1, double cy1, double cx2, double cy2){
		this.Cx = cx1;
		this.Cx2 = cx2;
		this.Cy = cy1;
		this.Cy2 = cy2;
	}
	
	private Bitmap rotate(){
		if (this.Cx > 0 && this.Cy > 0 && this.Cx2 > 0 && this.Cy2 > 0) {
			this.Degree = (float)(Math.atan2(this.Cx2 - this.Cx, this.Cy2 - this.Cy)*180/Math.PI);
		}else{
			this.Degree = 0;
		}
		
		Matrix matrix = new Matrix();
		matrix.setRotate(
				this.Degree,
				(float) bitmap.getWidth() / 2, 
				(float) bitmap.getHeight() / 2);
		Bitmap bm = Bitmap.createBitmap(
				bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return bm;
	}
	
	public void draw(Canvas canvas){
		if(this.Visible&&this.bitmap!=null){
			Bitmap bm = this.rotate();
			Point tmp=this.mMapViewGroup.getMapView().CoordinatesToPixel(this.Cx,this.Cy);
			float px=tmp.x+this.OffsetX;
			float py=tmp.y+this.OffsetY;	
			
			this.rect=new Rect((int)px-this.buffer,(int)py-this.buffer,(int)px+bm.getWidth()+this.buffer,(int)py+bm.getHeight()+this.buffer);
			
			canvas.drawBitmap(bm,px,py,null);
		}
	}
	
	public void Dispose(){
		super.Dispose();
		this.bitmap=null;
		this.mMapViewGroup = null;
		
	}
}
