package com.gary.garytool.business.map.api;


//import java.io.BufferedInputStream;
import java.io.File;
//import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.util.Log;

import com.gary.garytool.business.map.api.mapsevice.MapService;


public class ImageLoader {
//	private static final int MSG_SUCCESS = 1;//获取图片成功的标识
//    private static final int MSG_FAILURE = 0;//获取图片失败的标识

	public static final int MSG_WAIT=0;//获取图片成功的标识
	public static final int MSG_LOADING=2;//获取图片失败的标识
	public static final int MSG_SUCCESS=1;//获取图片成功的标识
	public static final int MSG_ERROR=4;//获取图片成功的标识

	public ImageLoader(){
		this.idxRcd=new ImageIndex();
	}

	public int x=0;
	public int y=0;//图片所在图层中的位置，虚拟位置；

	public TileLayer pTileLayer=null;

	//	private LoadRunnable mRunnable=null;
//	private LoadHandler mHandler=null;
//	private Thread mThread=null;
	public Bitmap bitmap=null;


	public int xGrid=0;//记录进行地图排列时所处的x网格编号
	public int yGrid=0;//记录进行地图排列时所处的y网格编号
	public int xFile=0;
	public int yFile=0;
	public int xDir=0;
	public int yDir=0;

	public int loadCount=3;//图片载入的重复次数
	public int loadIndex=0;//图片载入失败后已经重复的次数
	//	private int errCount=1;
//	private int errIndex=0;
	public String src="";
	public boolean isDraw=true;

	public void load(String url){
		this.src=url;
		this.load();
//		this.loadIndex++;
//
//		if(this.mThread != null) {
//			if(this.mThread.isAlive()){
//				try{
//					this.mThread.interrupt();
//				}
//				catch(Exception ex){}
//			}
//        }
//		this.mRunnable=new LoadRunnable();
//		this.mHandler=new LoadHandler();
//		this.bitmap=null;
//
//		Log.d("开始在入图片ImageLoader", url, null);
//		this.mThread = new Thread(this.mRunnable);
//		this.mThread.start();//线程启动
	}

	private ImageIndex idxRcd;

	public int LoadState=MSG_WAIT;
	public void load(){
		this.bitmap=null;
		this.LoadState=ImageLoader.MSG_LOADING;
        String type = this.pTileLayer.getMapType();
        String directory = MapsConstants.Directorys[this.pTileLayer.MapsLevel];
		if(this.idxRcd.loadIndex(type, directory)){
			this.bitmap=idxRcd.loadImage(this.xDir, this.yDir, this.xFile*1000+this.yFile);
		}

		if(this.bitmap==null){
//        	Log.d("文件不存在于本地，开始网络载图ImageLoader", src, null);
			String str=MapsConstants.LocalMapsRoot+this.pTileLayer.getMapType()+"/"+this.pTileLayer.MapsLevel+"/"+this.xDir+"/"+this.yDir+"/"+this.xFile+"_"+this.yFile+".png";

			File file=new File(str);
			if(file.exists()){
//    			Log.d("文件存在于本地缓存，开始读取，ImageLoader", str, null);
				this.bitmap=BitmapFactory.decodeFile(str);
				this.LoadState=ImageLoader.MSG_SUCCESS;
				this.loadIndex=0;
			}else{
				if(!MapsConstants.isLocal)
				{
					//如果是在线地图，则读取网络图片
					this.loadNetImage();
				}
				else
				{
					//如果是本地地图，则读取本地文件
					loadFromLocal();
				}

			}
		}else{
			this.LoadState=ImageLoader.MSG_SUCCESS;
		}
	}

	private void loadFromLocal()
	{
		MapService map = new MapService();

		int levels= Integer.parseInt(src);;

		byte[] tmp = map.getImageByPNGUrl(levels, xDir, yDir, xFile, yFile);

		if(tmp != null){
			this.bitmap = BitmapFactory.decodeByteArray(tmp, 0, tmp.length);
		}
		//this.saveFile(instream);
		this.LoadState=ImageLoader.MSG_SUCCESS;
		this.loadIndex=0;

	}

//	private void loadNetImage(){
//		InputStream instream=null;
//		try{
//    		String tmp=src.replaceFirst(MapsConstants.LocalMapsRoot,MapsConstants.MapsRoot);
//			URL url = new URL(tmp);
//			URLConnection urlConn = url.openConnection();
//			urlConn.setConnectTimeout(15000);
//			urlConn.setDoInput(true);
//			// 使用缓存
//			urlConn.setUseCaches(true);
//			instream = urlConn.getInputStream();
//			BufferedInputStream buffer = new BufferedInputStream(instream);
//			this.bitmap = BitmapFactory.decodeStream(buffer);
//			this.LoadState=ImageLoader.MSG_SUCCESS;
//			this.loadIndex=0;
//		}catch(IOException ioExp){
//			Log.d("图片载入失败ImageLoader对象的loadNetImage方法", "IOException happened", ioExp);
//			this.LoadState=ImageLoader.MSG_ERROR;
//			this.loadIndex++;
//		}finally{
//			try{
//    			if(instream != null){
//    				instream.close();
//    			}
//			}catch(IOException innerIoExp){
//
//			}
//		}
//	}

	private void loadNetImage(){
		InputStream instream=null;
		try{
			// 1.通过url创建HttpGet对象
			HttpGet httpGet=new HttpGet(this.src);
			// 2.通过DefaultClient的excute方法执行返回一个HttpResponse对象
			HttpClient httpClient=new DefaultHttpClient();
			HttpResponse httpResponse=httpClient.execute(httpGet);
			// 3.取得相关信息  取得HttpEntiy
			HttpEntity httpEntity=httpResponse.getEntity();
			// 4.通过HttpEntiy.getContent得到一个输入流
			instream=httpEntity.getContent();
			//通过传入的流再通过Bitmap工厂创建一个Bitmap
			this.bitmap=BitmapFactory.decodeStream(instream);

			this.saveFile(instream);

			this.LoadState=ImageLoader.MSG_SUCCESS;
			this.loadIndex=0;

		}catch(Exception Exp){
			//Log.d("图片载入失败ImageLoader对象的loadNetImage方法", "图片路径："+this.src, Exp);
			this.LoadState=ImageLoader.MSG_ERROR;
			this.loadIndex++;
		}finally{
			try{
				if(instream != null){
					instream.close();
				}
			}catch(IOException innerIoExp){

			}
		}
	}

	private void saveFile(InputStream instream) throws Exception{

		File destDir = new File(MapsConstants.CacheMapsRoot);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		String str=MapsConstants.CacheMapsRoot+this.pTileLayer.getMapType()+"_"+this.pTileLayer.MapsLevel+"_"+this.xDir+"_"+this.yDir+"_"+this.xFile+"_"+this.yFile;
		File file=new File(str);
		if(!file.exists()){
			FileOutputStream fouts=new FileOutputStream(file);
			if (fouts!= null){
				this.bitmap.compress(Bitmap.CompressFormat.JPEG,90,fouts);
				fouts.close();
			}
		}
	}

	public void draw(){
//		Log.d("绘制图片ImageLoader", src, null);
//		if(this.bitmap!=null){
//			Canvas cv=null;
//			try{
//				int tx=this.x+this.pTileLayer.x;
//				int ty=this.y+this.pTileLayer.y;
//				cv=this.pTileLayer.pMaplet.sfh.lockCanvas(new Rect(tx,ty,(int)(tx+MapsConstants.MPicWidth),(int)(ty+MapsConstants.MPicHeight)));// 获取画布
//				cv.drawBitmap(this.bitmap, this.x, this.y, null);
//			}catch(Exception e){
//				Log.d("ImageLoader绘制地图图图片出错", e.getMessage(), null);
//			}finally{
//				this.pTileLayer.pMaplet.sfh.unlockCanvasAndPost(cv);
//			}
//		}
		this.pTileLayer.draw();
	}

	public void Dispose(){
		this.src=null;
		this.bitmap=null;
		this.pTileLayer=null;
		if(this.idxRcd!=null){
			this.idxRcd.dispose();
			this.idxRcd=null;
		}

	}

//	private class LoadRunnable implements Runnable {
//		public boolean isRunning=true;
//		public void run(){
////			try{
////	            int a=0;
////	        }catch (InterruptedException e){
////
////	        }
//			InputStream instream=null;
//            bitmap=null;
//            int isload=MSG_FAILURE;
//    		try{
//    			URL url = new URL(src);
//    			URLConnection urlConn = url.openConnection();
//    			urlConn.connect();
//    			instream = urlConn.getInputStream();
//    			BufferedInputStream buffer = new BufferedInputStream(instream);
//    			bitmap = BitmapFactory.decodeStream(buffer);
//    			isload=MSG_SUCCESS;
//    			if(this.isRunning){
//    				draw();
//    			}
//    		}catch(IOException ioExp){
//    			Log.d("ImageLoader", "IOException happened", ioExp);
//    			isload=MSG_FAILURE;
//
//    		}finally{
//    			try{
//        			if(instream != null){
//        				instream.close();
//        			}
//    			}catch(IOException innerIoExp){
//
//    			}
//    			if(this.isRunning){
//    				mHandler.obtainMessage(isload).sendToTarget();
//    			}
//    		}
//		}
//	}

//	private class LoadHandler extends Handler{
//		 public void handleMessage(Message msg){
//			switch(msg.what) {
//				case MSG_SUCCESS:
//					loadIndex=0;
////					draw();
//					break;
//				case MSG_FAILURE:
//					if(loadCount>loadIndex){
//						Log.d("载入图片第“+loadIndex+”次失败，开始重新载入ImageLoader", src, null);
//						load(src);
//					}else{
//						loadIndex=0;
//					}
//					break;
//			}
//		 }
//	}
}
class IndexRecord{
	public void LoadFromBytes(ByteBuffer buffer,
							  int offset){
		this.key = readBufferLittleEndian(buffer, offset);
		offset += this.INT_SIZE;
		this.position = readBufferLittleEndian(buffer, offset);
		offset += this.INT_SIZE;
		this.length = readBufferLittleEndian(buffer, offset);
	}

	private int readBufferLittleEndian(ByteBuffer wrapper, int offset){
		return wrapper.getInt(offset);
	}

	public final int INT_SIZE=4;
	public static final int ByteLength=12;
	public int key, position, length;
}

class ImageIndex {

	public ImageIndex() {
		head = new IndexRecord();
		record = new IndexRecord();
		med = new IndexRecord();
		buffer = new byte[1600 * 12];
		bufferWrapper = ByteBuffer.wrap(buffer);
	}

//	public String getBasePath(){
//		return basepath;
//	}
//
//	public void setBasePath(String path){
//		basepath = path;
//	}

	public void dispose() {
		closeStream();
	}

	public boolean loadIndex(String type, String level) {
		if (type == null || level == null) {
			return false;
		}
		if (type == this.type && level == this.level) {
			return true;
		}
		String filename = String.format("%s/%s/%s", MapsConstants.LocalMapsRoot, type, level);
		boolean streamOpened = openStream(filename);
		if (streamOpened) {
			this.type = type;
			this.level = level;
			loadIndex(0, buffer.length);
			head.LoadFromBytes(bufferWrapper, 0);
		} else {
			this.type = null;
			this.level = null;
		}

		return streamOpened;
	}

	public Bitmap loadImage(int xgrid, int ygrid, int index) {
		boolean found = lookupIndex(head.position, head.length, xgrid, record) &&
				lookupIndex(record.position, record.length, ygrid, record) &&
				lookupIndex(record.position, record.length, index, record);
		if (found) {
			return loadImage(record.position, record.length);
		} else {
			Log.d(TAG, String.format("image not found:%d,%d,%d", xgrid, ygrid, index));
		}
		return null;
	}

	private boolean lookupIndex(
			int position,
			int length,
			int key,
			IndexRecord record) {
		if (!isSegmentRead(position, length)) {
			loadIndex(position, length);
		}

		if (length == 0) {
			return false;
		}

		int byteLength = IndexRecord.ByteLength;
		int start = position - startOffset,
				end = position + length - startOffset - byteLength,
				middle = 0;
		if (length == 1) {
			med.LoadFromBytes(bufferWrapper, start);
			record.position = med.position;
			record.length = med.length;
			return med.key == key;
		}

		start /= byteLength;
		end /= byteLength;

		while (start <= end) {
			middle = (start + end) >> 1;
			med.LoadFromBytes(bufferWrapper, middle * byteLength);
			if (med.key == key) {
				record.position = med.position;
				record.length = med.length;
				return true;
			}
			if (med.key > key) {
				end = middle - 1;
			} else {
				start = middle + 1;
			}
		}
		return false;
	}

	private void loadIndex(int position, int length) {
		startOffset = position;
		try {
			if (length != 0) {
				indexStream.seek(position);
				totalLength = indexStream.read(buffer, 0, length);
				bufferWrapper.rewind();
				bufferWrapper.put(buffer).order(ByteOrder.LITTLE_ENDIAN);
			}
		} catch (IOException ioexp) {
			Log.e(TAG, "Error on loadIndex(int, int) ", ioexp);
		} catch (RuntimeException exp) {
			Log.e(TAG, "Error on loadIndex(int, int) ", exp);
			throw exp;
		}
	}

	private Bitmap loadImage(int position, int length) {
		Bitmap result = null;
		try {
			if (length != 0 && (position + length) <= mapStream.length()) {
				byte[] buffer = new byte[length];
				mapStream.seek(position);
				mapStream.read(buffer, 0, length);
				result = BitmapFactory.decodeByteArray(buffer, 0, length);
			}
		} catch (IOException ioexp) {
			Log.d(TAG, "Error on loadImage(int, int) ", ioexp);
		}
		return result;
	}

	private boolean isSegmentRead(int position, int length) {
		return (position >= startOffset) &&
				((startOffset + totalLength) >= (position + length));
	}

	private boolean openStream(String filename) {
		closeStream();
		String indfilename = filename + ".ind";
		String mapfilename = filename + ".map";
		File indfile = new File(indfilename);
		File mapfile = new File(mapfilename);
//		Log.d("本地文件读取路径", indfilename+";;"+mapfilename, null);
		if (indfile.exists() && mapfile.exists()) {
			try {
				indexStream = new RandomAccessFile(indfilename, "r");
				mapStream = new RandomAccessFile(mapfilename, "r");
				return true;
			} catch (IOException ioe) {
				Log.d(TAG, "Error on openStream(String) ", ioe);
				closeStream();
			}
		}
		return false;
	}

	private void closeStream() {
		try {
			if (indexStream != null) {
				indexStream.close();
			}
			if (mapStream != null) {
				mapStream.close();
			}
		} catch (IOException ioexp) {
			Log.d(TAG, "Error on closeStream() ", ioexp);
		}
		indexStream = null;
		mapStream = null;
	}

	private RandomAccessFile indexStream, mapStream;
	private IndexRecord head, record, med;
	private String type, level;
	private byte[] buffer;
	private ByteBuffer bufferWrapper;
	private int startOffset, totalLength;
//	private String basepath;

	private final String TAG = "ImageIndex";
}