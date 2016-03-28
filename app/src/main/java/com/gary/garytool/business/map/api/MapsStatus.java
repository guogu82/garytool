package com.gary.garytool.business.map.api;

public class MapsStatus {
	public MapsStatus(){
		this.Center=new Coordinates();		
	}	
	public MapsStatus(Coordinates crd,int level){
		this.Center=crd;	
		this.MapsLevel=level;
	}	
	public Coordinates Center=null;
	public int MapsLevel=0;
}
