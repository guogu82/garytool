package com.gary.garytool.business.map.api;

import java.util.ArrayList;

public class GraphicsLayer extends BaseLayer {

	public GraphicsLayer(){}
	
	public ArrayList<BaseGraphics> gs=new ArrayList<BaseGraphics>();
	
	
	@Override
	public void draw(){
		if(this.Visible&&this.gs!=null){
			for(int i=0;i<this.gs.size();i++){
				this.gs.get(i).draw();
			}
		}
	}	
	@Override	
	public void clear(){
		if(this.gs!=null){
			while(this.gs.size()>0){
				this.gs.get(0).Dispose();
				this.gs.remove(0);
			}
		}
	}
	@Override
	public void Dispose(){
		super.Dispose();
		if(this.gs!=null){
			while(this.gs.size()>0){
				this.gs.get(0).Dispose();
				this.gs.remove(0);
			}
		}
	}
	
	
}
