package com.gary.garytool.business.map.api;

import java.util.ArrayList;
import java.util.List;

public class RotateMarkerLayer extends BaseLayer {
	public MapViewGroup Parent;
	public List<RotateMarkerView> Markers;
	public RotateMarkerLayer(MapViewGroup parent){
		this.Parent = parent;
		this.Markers=new ArrayList<RotateMarkerView>();
	}
	
	public void addMarker(RotateMarkerView marker){
		this.Parent.addView(marker);
		this.Markers.add(marker);
	}
	public void removeMarker(RotateMarkerView marker){
		for(int i=0;i<this.Markers.size();i++){
			if(this.Markers.get(i) == marker){
				this.Markers.remove(marker);
				this.Parent.removeView(marker);
				break;
			}
		}
	}
	
	//private BaseMarker topMarker=null;
	
	public void refresh(){
		if(this.Visible&&this.Markers!=null){
			for(int i=0;i<this.Markers.size();i++){
				this.Markers.get(i).refresh();
				//Log.d("地图刷新","layerlayerlayerlayerlayerlayerlayerlayer");
			}
		}
	}	
	@Override	
	public void clear(){
		if(this.Markers!=null){
			while(this.Markers.size()>0){
				this.Markers.get(0).Dispose();
				this.Markers = null;
			}
		}
	}
	@Override
	public void Dispose(){
		super.Dispose();
		this.clear();
	}

}
