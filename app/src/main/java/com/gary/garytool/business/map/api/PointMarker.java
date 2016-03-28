package com.gary.garytool.business.map.api;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public class PointMarker extends BaseMarker{
	public PointMarker(Bitmap img,float ox,float oy){
		this.bitmap=img;
		this.OffsetX=ox;
		this.OffsetY=oy;
	}
	
	public Bitmap bitmap=null;
	
	public void draw(){
		if(this.Visible&&this.bitmap!=null){
			Point tmp=this.pMaplet.CoordinatesToPixel(this.Cx,this.Cy);
			float px=tmp.x+this.OffsetX;
			float py=tmp.y+this.OffsetY;	
			
			this.rect=new Rect((int)px-this.buffer,(int)py-this.buffer,(int)px+this.bitmap.getWidth()+this.buffer,(int)py+this.bitmap.getHeight()+this.buffer);
			
			this.pMaplet.drawingCanvas.drawBitmap(this.bitmap,px,py,null);
		}
	}
	
	public void Dispose(){
		super.Dispose();
		this.bitmap=null;
	}
}
