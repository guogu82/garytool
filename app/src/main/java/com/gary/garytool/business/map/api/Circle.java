package com.ritu.mapapi;

import java.util.ArrayList;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import com.gary.garytool.business.map.api.BaseGraphics;
import com.gary.garytool.business.map.api.Coordinates;

public class Circle extends BaseGraphics {

	public Circle(ArrayList<Coordinates> crds, Paint p) {
		super(crds, p);
	}

	@Override
	public void draw() {
		if (this.points != null && this.points.size() == 2) {

			Coordinates crd1 = this.points.get(0);
			Coordinates crd2 = this.points.get(1);
			Point point1 = this.pMaplet.CoordinatesToPixel(crd1.X, crd1.Y);
			Point point2 = this.pMaplet.CoordinatesToPixel(crd2.X, crd2.Y);

			RectF oval = new RectF(point1.x, point2.y, point2.x, point1.y);
			Path path = new Path();
			path.moveTo(point2.x, point2.y + (point1.y - point2.y) / 2);
			path.arcTo(oval, 0, 359);

			if (this.pMaplet.drawingCanvas != null) {
				this.pMaplet.drawingCanvas.drawPath(path, this.pen);
				// this.pen.setStyle(Paint.Style.STROKE);
				// this.pMaplet.drawingCanvas.drawRect(380,480,420,520,this.pen);
			}
		}
	}
}
