package com.gary.garytool.business.map.api.mapsevice;

public class MapService {

	private MapFilePool mfp = null;
	
	public MapService(){
		mfp = new MapFilePool();
	}
	
	
	public byte[] getImageBySn(int level, int snx, int sny){
		return mfp.getImageBySn(level, snx, sny);
	}
	
	public byte[] getImageByCoord(int level, double longitude, double latitude){
		int snX = GISConvert.gisToSn_Web(longitude, level);
    	int snY = GISConvert.gisToSn_Web(latitude, level);
		return getImageBySn(level, snX, snY);
	}
	
	public byte[] getImageByPNGUrl(int level, int dx, int dy, int fx, int fy){
		int snX = GISConvert.iGrid[level] * dx + fx;
    	int snY = GISConvert.iGrid[level] * dy + fy;
		return getImageBySn(level, snX, snY);
	}
	
	public void dispose(){
        mfp.dispose();
    }
}
