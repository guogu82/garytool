package com.gary.garytool.business.map.api;

public class MarkerEvent extends Event {
	public MarkerEvent(String typeStr){
		super(typeStr);
	}

	public static final String MarkerClicked="MarkerClicked";//标注被点击

    public BaseMarker pMarker=null;
}
