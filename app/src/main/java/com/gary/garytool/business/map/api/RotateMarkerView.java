package com.gary.garytool.business.map.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

public class RotateMarkerView extends View {
	public MapViewGroup mMapViewGroup;
	public Bitmap bitmap=null;
	public float Degree;
	public double Cx=0;
	public double Cy=0;
	public double Cx2=0;
	public double Cy2=0;
	public float OffsetX=0;
	public float OffsetY=0;

	public int buffer=0;
	private boolean isVisible = true;
	private Matrix matrix = new Matrix();
	
	public RotateMarkerView(Context context, MapViewGroup mapViewGroup, Bitmap img, double cx, double cy, double cx2, double cy2, float ox,float oy) {
		super(context);
		this.mMapViewGroup = mapViewGroup;
		this.bitmap=img;
		this.OffsetX=ox;
		this.OffsetY=oy;
		this.Cx = cx;
		this.Cy = cy;
		this.Cx2 = cx2;
		this.Cy2 = cy2;
		//this.rotate();
	}


//	public void update(double cx, double cy){
//		if(cx > 0){
//			this.Cx = cx;
//		}
//		if(cy > 0){
//			this.Cy = cy;
//		}
//		this.refresh();
//	}

	public void updateRotate(double cx1, double cy1, double cx2, double cy2){
		this.Cx = cx1;
		this.Cx2 = cx2;
		this.Cy = cy1;
		this.Cy2 = cy2;
		//this.rotate();
		this.refresh();
		//Log.d("地图刷新","updateRotateupdateRotateupdateRotateupdateRotateupdateRotate");

	}
	public void update(double cx1, double cy1, double cx2, double cy2){
		this.Cx = cx1;
		this.Cx2 = cx2;
		this.Cy = cy1;
		this.Cy2 = cy2;
		this.refresh();
	}

	private void rotate(){
		if (this.Cx > 0 && this.Cy > 0 && this.Cx2 > 0 && this.Cy2 > 0) {
			this.Degree = (float)(Math.atan2(this.Cx2 - this.Cx, this.Cy2 - this.Cy)*180/Math.PI);
		}else{
			this.Degree = 0;
		}

		matrix.setRotate(
				this.Degree,
				(float) bitmap.getWidth() / 2,
				(float) bitmap.getHeight() / 2);
//
//		Bitmap bm = Bitmap.createBitmap(
//				bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//		this.bitmap = bm;
		//this.OffsetX = this.bitmap.getWidth()/-2;
		//this.OffsetY = this.bitmap.getHeight()/-2;
	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		if(this.isVisible && this.bitmap!=null){
			Point tmp=this.mMapViewGroup.getMapView().CoordinatesToPixel(this.Cx,this.Cy);
			float px=tmp.x+this.OffsetX;
			float py=tmp.y+this.OffsetY;

			//Rect rect=new Rect((int)px-this.buffer,(int)py-this.buffer,(int)px+bitmap.getWidth()+this.buffer,(int)py+bitmap.getHeight()+this.buffer);
			//Log.d("旋转标注",""+px+";"+py+"___"+this.Cx+";"+this.Cy);
			//canvas.drawBitmap(bitmap,px,py,null);

			Paint paint = new Paint();
			if (this.Cx > 0 && this.Cy > 0 && this.Cx2 > 0 && this.Cy2 > 0 && (this.Cx!=this.Cx2 ||this.Cy!=this.Cy2) ) {
				this.Degree = (float)(Math.atan2(this.Cx2 - this.Cx, this.Cy2 - this.Cy)*180/Math.PI);
			}

			matrix.setRotate(this.Degree, 0-this.OffsetX, 0-this.OffsetY);
			//matrix.setTranslate(px, py);
			//matrix.postRotate(this.Degree, 0-this.OffsetX, 0-this.OffsetY);
			matrix.postTranslate(px, py);
			canvas.drawBitmap(bitmap, matrix, paint);
		}
	}

	public void show() {
		this.setVisibility(View.VISIBLE);
		this.isVisible = true;
	}

	public void hide() {
		this.setVisibility(View.GONE);
		this.isVisible = false;
	}

	public void refresh() {
		//Log.d("地图刷新","refreshrefreshrefreshrefreshrefreshrefresh");
		this.invalidate();
	}

	public void Dispose() {
		this.bitmap=null;
		this.mMapViewGroup = null;
	}
}
