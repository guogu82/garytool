package com.gary.garytool.business.map.api.mapsevice;

public class MapFileConnector {

	private MapFileReader map;
	public MapFileConnectorStatus mapState;
	
	public MapFileConnector(String path){
		this.mapState = MapFileConnectorStatus.Creating;
		map = new MapFileReader(path);
		this.mapState = MapFileConnectorStatus.Empty;
	}
	


	public byte[] getImage(int snX, int snY) throws Exception {
		if(MapFileConnectorStatus.Closed == this.mapState || MapFileConnectorStatus.Closing == this.mapState){
			return null;
		}
		return map.getImage(snX, snY);
	}
    
    public enum MapFileConnectorStatus {
        Creating(0),
        Empty(1),
        Connecting(2),
        Closing(3),
        Closed(4);
        
        private int value;
        
        private MapFileConnectorStatus(int value){
        	this.value = value;
        }
        
        public int getValue(){
        	return this.value;
        }
        
        public void setValue(int value){
        	this.value = value;
        }
    }
}
