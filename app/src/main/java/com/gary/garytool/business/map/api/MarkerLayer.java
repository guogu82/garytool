package com.gary.garytool.business.map.api;

import java.util.ArrayList;

public class MarkerLayer extends BaseLayer {
	public MarkerLayer(){
		//this.tag=new Tag();
	}
	
	public ArrayList<BaseMarker> markers=new ArrayList<BaseMarker>();
	
	private BaseMarker topMarker=null;
	//private Tag tag=null;
	
//	public void showTag(BaseMarker m){
//		if(this.tag.pMarker!=null){
//			this.hideTag(this.tag.pMarker);
//		}
//		this.tag.pMarker=m;
//		m.TagVisible=true;
//		this.tag.Text=m.nameStr;
//		this.tag.Visible=true;	
//	}
//	public void hideTag(BaseMarker m){
//		if(m.TagVisible){
//			m.TagVisible=false;
//			if(this.tag.pMarker==m){
//				this.tag.pMarker=null;
//				this.tag.Visible=false;
//			}
//		}
//		
//		
//	}
//	public void hideTag(){
//		if(this.tag.pMarker!=null){
//			this.hideTag(this.tag.pMarker);
//		}
//	}
	
	
	@Override
	public void draw(){
		if(this.Visible&&this.markers!=null){
			this.topMarker=null;
			for(int i=0;i<this.markers.size();i++){
				BaseMarker tmp=this.markers.get(i);
				if(tmp.isTop){
					this.topMarker=tmp;
				}else{
					this.markers.get(i).draw();
				}
			}
			if(this.topMarker!=null){
				this.topMarker.draw();
			}
//			if(this.tag!=null){
//				this.tag.draw();
//			}
		}
	}	
	@Override	
	public void clear(){
		if(this.markers!=null){
			while(this.markers.size()>0){
				this.markers.get(0).Dispose();
				this.markers.remove(0);
			}
			this.topMarker=null;
//			this.tag.Dispose();
//			this.tag=null;
		}
	}
	@Override
	public void Dispose(){
		super.Dispose();
		this.clear();
	}

}
