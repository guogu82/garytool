package com.gary.garytool.business.map.api;

import java.util.ArrayList;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Paint.Cap;

public class Polygon extends BaseGraphics{
	public Polygon(ArrayList<Coordinates> crds,Paint p,Paint fp){
		super(crds,p);		
		if(fp!=null){			
			this.FillPen=fp;
		}else{
			this.FillPen=new Paint();
			this.FillPen.setAntiAlias(true);
			this.FillPen.setARGB(150,1,75,128);	
//			this.FillPen.setColor(0x3300FF00);
			this.FillPen.setStyle(Paint.Style.FILL);
			this.FillPen.setStrokeWidth(3);
			this.FillPen.setStrokeCap(Cap.ROUND);
		}
	}

	public void updatePolygon(ArrayList<Coordinates> crds) {
		if(crds!=null){
			this.points=crds;
		}
	}
	//图形的填充画笔
	public Paint FillPen=null;	
	
	@Override 
	public void draw(){		
		if(this.Visible&&this.points!=null&&this.points.size()>1){
			Coordinates crd=this.points.get(0);
			
			Path path=new Path();			
			Point tmp=this.pMaplet.CoordinatesToPixel(crd.X, crd.Y);			
			path.moveTo(tmp.x, tmp.y);			
			for(int i=1;i<this.points.size();i++){					
				crd=this.points.get(i);
				tmp=this.pMaplet.CoordinatesToPixel(crd.X, crd.Y);	
				path.lineTo(tmp.x, tmp.y);
			}	
			path.close();
			if(this.pMaplet.drawingCanvas!=null){
				//绘制填充色
				this.pMaplet.drawingCanvas.drawPath(path,this.FillPen);
				//绘制边界
				this.pMaplet.drawingCanvas.drawPath(path,this.pen);
			}
			
//			Paint tmpp=new Paint();
//			tmpp.setAntiAlias(true);
//			tmpp.setARGB(255,0,0,0);			
//			tmpp.setStyle(Paint.Style.FILL);
//			tmpp.setStrokeWidth(5);
//			tmpp.setStrokeCap(Cap.ROUND);
//			
//			this.pMaplet.drawingCanvas.drawPoint(400, 500,tmpp);
		}
	}
	
	@Override
	public void Dispose(){
		super.Dispose();
		this.FillPen=null;		
	}

}
