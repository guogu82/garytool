package com.gary.garytool.business.map.api;


public class BaseLayer {

	public BaseLayer(){}
	public String nameStr=null;
	public BaseMaps pMaplet=null;
	public boolean Visible=true;

	public void draw(){}

	public void show(){//显示
		this.Visible=true;
	}
	public void hide(){//隐藏
		this.Visible=false;
	}
	public void refresh(){
		//刷新图层
	}

	public void clear(){}

	public void Dispose(){
		this.pMaplet=null;
		this.nameStr=null;
	}



}
