package com.gary.garytool.business.map.api;

public class ListenerCouple {

	public ListenerCouple(){}
	public ListenerCouple(String typeStr,IEventListener listener){
		this.Type=typeStr;
		this.Listener=listener;
	}
	public String Type=null;
	public IEventListener Listener=null;
}
