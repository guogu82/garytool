package com.gary.garytool.business.map.api.mapsevice;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * 离线地图文件类
 * @author lx
 */
public class MapFileReader {

    private final int HeaderLength = 128;	// 文件头长度过
    private int pngSize;					// 图片大小，图片为正方形
    private int level;						// 地图包级别
    private int minSnX;						// 地图包里的最小SnX，即最小列数
    private int maxSnX;						// 地图包里的最大SnX，即最大列数
    private int minSnY;						// 地图包里的最小SnY，即最小行数
    private int maxSnY;						// 地图包里的最大SnY，即最大行数
    private int columnCount;				// 地图包内的列数
    private int rowCount;					// 地图包内的行数
    private int imgCount;					// 地图包支持的最大图片数
    private int dataOffset;					// 图片数据的开始偏移量
    private int[] offsets = null;			// 各图片的偏移量
    private boolean isFileExists;			// 该地图文件是否存在
    
    private RandomAccessFile stream;
    
    
    public MapFileReader(String path){
    	
    	System.out.println("离线地图路径："+path);
    	if(path != null && !"".equals(path)){
    		isFileExists = new File(path).exists();
    	}
    	
    	if(!isFileExists){
    		System.out.println("离线地图文件不存在");
    		return;
    	}
    	
    	try {
    		stream = new RandomAccessFile(new File(path), "r");
//			stream = new RandomAccessFile(new FileInputStream(new File(path)));
//			lengths = stream.available();
			readFileHeader();
			
			readImageOffsets();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 读取文件头信息
     */
    private void readFileHeader() throws Exception {
    	
    	pngSize = stream.readInt();
		level = stream.readInt();
		minSnX = stream.readInt();
		minSnY = stream.readInt();
		maxSnX = stream.readInt();
		maxSnY = stream.readInt();
		
        columnCount = maxSnX - minSnX + 1;
        rowCount = maxSnY - minSnY + 1;
        imgCount = rowCount * columnCount;
        offsets = new int[imgCount];
        dataOffset = HeaderLength + offsets.length * 4;
    }
    
    /**
     * 读取偏移量列表
     */
    private void readImageOffsets() throws Exception {
    	stream.seek(HeaderLength);
    	//byte[] buf = new byte[offsets.length * 4];
    	//stream.read(buf);
    	
    	for(int i=0;i<offsets.length;i++) {
    		offsets[i] = stream.readInt();   
    	}
    	//ByteBuffer bb = ByteBuffer.allocate(buf.length);
    	//bb.put(buf);
    	//byte[] tmp = new byte[4];
    	//for (int i = 0; i < offsets.length; i++) { 
    	//	bb.flip();
    	//	bb.get(tmp, 0, tmp.length);
    	//	offsets[i] = bb.getInt();
    	//}
    }
    
    /**
     * 读取图片
     * @param snX
     * @param snY
     * @return
     * @throws Exception
     */
    public byte[] getImage(int snX, int snY) throws Exception {
    	if (!isFileExists) return null;
        int offset = getImageOffset(snX, snY);
        if (offset < 0) return null;
        stream.seek(offset);
        int len = stream.readInt();// readInt32(stream);
        byte[] value = new byte[len];
        stream.read(value, 0, value.length);
        return value;
    }

    /**
     * 得到图片在文件中的位置
     * @param snX
     * @param snY
     * @return
     */
	private int getImageOffset(int snX, int snY) {
		int row = snY - minSnY;
        if (row < 0) return -1;
        int col = snX - minSnX;
        if (col < 0) return -1;

        int index = row * columnCount + col;
        if (index < 0 || index >= imgCount) return -1;
        return offsets[index];
	}
}
