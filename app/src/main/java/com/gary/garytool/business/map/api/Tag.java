package com.gary.garytool.business.map.api;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Paint.Cap;

public class Tag{
	public Tag(){
		this.pen=new Paint();
		this.pen.setAntiAlias(true);
		this.pen.setStrokeWidth(3);
		this.pen.setStrokeCap(Cap.ROUND);

		this.pen.setARGB(200,255,0,0);
		this.pen.setStyle(Paint.Style.STROKE);
		
		this.textPen=new Paint();
		this.textPen.setAntiAlias(true);
		this.textPen.setARGB(200,0,0,0);
		this.textPen.setTextSize(20);
		
		
	}
	
	public String Text=null;
	
	public boolean Visible=false;
	public BaseMarker pMarker=null;
	
	public void draw(){
		if(this.Visible&&this.Text!=null&&this.Text!=""){
			this.drawSkin();
			this.drawText();
		}
	}
	public Paint pen=null;
	public Paint textPen=null;
	private int offest=8;
	private void drawSkin(){
		this.pen.setARGB(250, 255, 251, 255);
		this.pen.setStyle(Paint.Style.FILL);
		Path path=new Path();
		Point tmp=this.pMarker.pMaplet.CoordinatesToPixel(this.pMarker.Cx,this.pMarker.Cy);
		
		float tx=tmp.x;
		float ty=tmp.y+this.pMarker.OffsetY;
		
		FontMetrics fm=this.textPen.getFontMetrics();// �õ�ϵͳĬ����������  
        int th=(int)Math.ceil(fm.descent-fm.top);// �������߶�  
		int w=(int)(this.Text.length()*th*0.89);
		
		
		path.moveTo(tx,ty);
		
		path.lineTo(tx-offest,ty-th);
		
		path.lineTo(tx-w/2,ty-th);
		path.lineTo(tx-w/2,ty-th*2-offest*2);
		path.lineTo(tx+w/2,ty-th*2-offest*2);
		path.lineTo(tx+w/2,ty-th);
		path.lineTo(tx+offest,ty-th);
		path.close();	
		
		this.pMarker.pMaplet.drawingCanvas.drawPath(path,this.pen);
		
		this.pen.setARGB(250,150,152,150);
		this.pen.setStyle(Paint.Style.STROKE);
		this.pMarker.pMaplet.drawingCanvas.drawPath(path,this.pen);		
		
		
		
		this.pMarker.pMaplet.drawingCanvas.drawText(this.Text,0,this.Text.length(),tx-w/2+offest,ty-th-offest*2,this.textPen);
		
	}
	
	private void drawText(){
		
	}
	public void Dispose(){
		this.Text=null;
		this.pMarker=null;		
	}
	
	
}
