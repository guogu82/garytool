package com.gary.garytool.business.map.api;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MarkerLayerView extends View {
	public MapViewGroup Parent = null;
	public List<RotateMarker> Markers;
	public MarkerLayerView(Context context) {
		super(context);
		init();
	}

	public MarkerLayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		Markers = new ArrayList<RotateMarker>();
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		if(this.getVisibility()==View.VISIBLE && this.Markers!=null){
			for(int i=0;i<this.Markers.size();i++){
				this.Markers.get(i).draw(canvas);
			}
		}
	}

	public void show() {
		this.setVisibility(View.VISIBLE);
	}

	public void hide() {
		this.setVisibility(View.GONE);
	}

	public void refresh() {
		this.invalidate();
	}

	public void clear() {
		if(this.Markers!=null){
			while(this.Markers.size()>0){
				this.Markers.get(0).Dispose();
				this.Markers.remove(0);
			}
		}
	}

	public void Dispose() {
		this.clear();
		this.Parent = null;
	}
}
