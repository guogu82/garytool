package com.gary.garytool.business.map.api;

public class Bounds {
	public Bounds(Coordinates max,Coordinates min){
		this.MaxCrd=max;
		this.MinCrd=min; 
	}
	public Bounds(){
		this.MaxCrd=new Coordinates();
		this.MinCrd=new Coordinates(); 
	}
	public Coordinates MaxCrd=null;
	public Coordinates MinCrd=null;
}
