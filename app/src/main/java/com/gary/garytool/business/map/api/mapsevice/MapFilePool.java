package com.gary.garytool.business.map.api.mapsevice;


import com.gary.garytool.business.map.api.MapsConstants;

public class MapFilePool {
	
	private String mapFileRoot = MapsConstants.OffMapsFileRoot;

	public MapFilePool(){
		
	}
	
    public byte[] getImageByPNGUrl(int level, int dx, int dy, int fx, int fy)
    {
    	int snX = GISConvert.iGrid[level] * dx + fx;
        int snY = GISConvert.iGrid[level] * dy + fy;
        return getImageBySn(level, snX, snY); 
    }
	
	public byte[] getImageBySn(int level, int snX, int snY) {
		String path = getFilePath(level, snX, snY);
		String fullPath = getFileFullPath(path);
		MapFileConnector conn = new MapFileConnector(fullPath);
		
		try {
			switch (conn.mapState.getValue()) {
				case 0: 
					return conn.getImage(snX, snY);
				case 1: 
				case 2: 
					return conn.getImage(snX, snY); 
				case 3: 
				case 4: 
					return getImageBySn(level, snX, snY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public byte[] GetImageByCoord(int level, double longitude, double latitude){
        int snX = GISConvert.gisToSn_Web(longitude, level);
        int snY = GISConvert.gisToSn_Web(latitude, level);
        return getImageBySn(level, snX, snY);
    }
    
    

    
    /**
     * 获取离线地图文件根路径
     * @param path
     * @return
     */
    private String getFileFullPath(String path){
        return new StringBuilder().append(FileUtils.getSDRoot() + mapFileRoot).append(path).toString();
    }
    
    /**
     * 根据级别获取离线地图文件
     * @param level
     * @param snX
     * @param snY
     * @return
     */
    private String getFilePath(int level, int snX, int snY){
        return GISConvert.getServerMapPathBySn(snX, snY, level);
    }

	public void dispose() {
		
	}
	
	

}
