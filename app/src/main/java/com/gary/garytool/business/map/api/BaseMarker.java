package com.gary.garytool.business.map.api;


import android.graphics.Rect;
public class BaseMarker {
	public BaseMarker(){}

	public BaseMaps pMaplet=null;
	public String nameStr=null;

	public double Cx=0;
	public double Cy=0;
	public Rect rect=null;

	public float OffsetX=0;
	public float OffsetY=0;
	public boolean TagVisible=false;

	public int buffer=0;

	public boolean isTop=false;//是否在最上面

	public boolean Visible=true;

	public Object Info=null;

	public void draw(){

	}

	public void show(){//显示
		this.Visible=true;
	}
	public void hide(){//隐藏
		this.Visible=false;
	}

	public void Dispose(){
		this.nameStr=null;
		this.pMaplet=null;
		this.Info=null;
		this.rect=null;
	}

}
