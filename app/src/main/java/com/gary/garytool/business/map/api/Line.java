package com.gary.garytool.business.map.api;

import java.util.ArrayList;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Line extends BaseGraphics {
	
	public Line(ArrayList<Coordinates> crds,Paint p){
		super(crds,p);		
	}
	
	
	@Override 
	public void draw(){
		if(this.points!=null&&this.points.size()>1){
			Coordinates crd=this.points.get(0);
			
			Path path=new Path();			
			Point tmp=this.pMaplet.CoordinatesToPixel(crd.X, crd.Y);			
			path.moveTo(tmp.x, tmp.y);			
			for(int i=1;i<this.points.size();i++){					
				crd=this.points.get(i);
				tmp=this.pMaplet.CoordinatesToPixel(crd.X, crd.Y);	
				path.lineTo(tmp.x, tmp.y);
			}			
			if(this.pMaplet.drawingCanvas!=null){
				this.pMaplet.drawingCanvas.drawPath(path,this.pen);
//				this.pen.setStyle(Paint.Style.STROKE);
//				this.pMaplet.drawingCanvas.drawRect(380,480,420,520,this.pen);
			}
		}
	}
}
