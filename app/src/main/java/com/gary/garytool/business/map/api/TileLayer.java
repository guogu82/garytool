package com.gary.garytool.business.map.api;

import java.util.ArrayList;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class TileLayer {

	public TileLayer(double xc,double yc,int level,int mWidth,int mHeight){
		
		this.setTileLayer(xc, yc, level, mWidth, mHeight);
		
		this.loaderArr=new ArrayList<ImageLoader>();		
//		this.loadTask=new LoadTask();
//		this.loadTask.execute("");	
		this.mHandler=new LoadHandler();
		this.mThread=new LoadThread();
		this.mThread.start();
	}
	
//	public  Bitmap layer=null;

	public void setTileLayer(double xc,double yc,int level,int mWidth,int mHeight){
		this.x=0;
		this.y=0;
		this.Cx=xc;
		this.Cy=yc;
		this.MapsLevel=level;
		this.MapsWidth=mWidth;
		this.MapsHeight=mHeight;

//		this.directory=MapsConstants.Directorys[this.MapsLevel];
//		this.scalefactor=MapsConstants.ScaleFactors[this.MapsLevel];
//		this.gridfactor=MapsConstants.GridFactors[this.MapsLevel];
	}

	public int x=0;
	public int y=0;//图层所在地图的相对位置，虚拟位置，用于绘制地图；
	public ImageLoader[][] picArr=null;//图片数组

	public ArrayList<ImageLoader> loaderArr=null;
	//	private LoadTask loadTask=null;
	private LoadThread mThread=null;
	private LoadHandler mHandler=null;

	public double Cx=0;
	public  double Cy=0;
	private String m_MapsType=MapsType.Typical;
	public String getMapType(){
		return this.m_MapsType;
	}
	public void setMaptype(String v){
//		if(this.m_MapsType!=v){
//			String oldMType=this.m_MapsType;
//			this.m_MapsType=v;
//			Log.d("改变地图类型", v, null);
//			this.loaderArr.clear();
//			if(this.picArr!=null){
//				for(int i=0;i<this.picArr.length;i++){
//					for(int j=0;j<this.picArr[i].length;j++){
//						this.picArr[i][j].src=(this.picArr[i][j].src.replace(oldMType,this.m_MapsType));
//						this.picArr[i][j].bitmap=null;
//						this.loaderArr.add(this.picArr[i][j]);
//						this.picArr[i][j].LoadState=ImageLoader.MSG_WAIT;
//					}
//				}
//			}
//		}
		if(this.m_MapsType!=v){
			String oldMType=this.m_MapsType;
			this.m_MapsType=v;
			Log.d("改变地图类型", v, null);
			this.show();
		}
	}
	//地图的长度和宽度以及地图等级
	public int MapsLevel=0;
	public int MapsWidth=0;
	public int MapsHeight=0;

	//本栅格图层的地图参数
//	private String directory;
//	private double scalefactor;
//	private int gridfactor;

	private int xAbsolute;
	private int yAbsolute;

	private double centerX;
	private double centerY;

	private int xSpan;
	private int ySpan;

	//移动时图片改变相关变量
	private int xIndex;//x坐标最大的图片序列
	private int yIndex;//y坐标最小的图片序列
	public ImageView v=null;//测试用，完后删除

	public BaseMaps pMaplet=null;


	public void show(){
		this.x=0;
		this.y=0;
		this.initVariable();

		int xGrid=0;
		this.clear();

		this.picArr=new ImageLoader[xSpan*2+1][ySpan*2+1];

		while(Math.abs(xGrid)<=this.xSpan){
			int xDir=(int)Math.floor((this.xAbsolute+xGrid)/MapsConstants.GridFactors[this.MapsLevel]);
			int xFile=(int)(this.xAbsolute+xGrid-xDir*MapsConstants.GridFactors[this.MapsLevel]);
			xFile=xFile<0?(MapsConstants.GridFactors[this.MapsLevel]+xFile):xFile;

			int yGrid=0;
			int i=xGrid+this.xSpan;
			while(Math.abs(yGrid)<=this.ySpan){
				String imgName=MapsConstants.MapsRoot+"/"+this.m_MapsType+"/"+MapsConstants.Directorys[this.MapsLevel]+"/";

				int yDir=(int)Math.floor((this.yAbsolute+yGrid)/(MapsConstants.GridFactors[this.MapsLevel]));

				int yFile=(int)(this.yAbsolute+yGrid-yDir*MapsConstants.GridFactors[this.MapsLevel]);
				yFile=yFile<0?(MapsConstants.GridFactors[this.MapsLevel]+yFile):yFile;
				imgName+=xDir+"/"+yDir+"/"+xFile+"_"+yFile+MapsConstants.PicType;

				int pX=(int)(xGrid*MapsConstants.MPicWidth+this.centerX);
				int pY=(int)(-yGrid*MapsConstants.MPicHeight+this.centerY);

				//添加图片
				int j=this.ySpan-yGrid;
				this.picArr[i][j]=new ImageLoader();
				this.picArr[i][j].pTileLayer=this;

				this.picArr[i][j].x=pX;
				this.picArr[i][j].y=pY;
				this.picArr[i][j].xGrid=xGrid;
				this.picArr[i][j].yGrid=yGrid;

				this.picArr[i][j].xDir=xDir;
				this.picArr[i][j].yDir=yDir;
				this.picArr[i][j].xFile=xFile;
				this.picArr[i][j].yFile=yFile;

				this.picArr[i][j].src = imgName;

				this.picArr[i][j].LoadState=ImageLoader.MSG_WAIT;
				this.picArr[i][j].bitmap=null;
				this.loaderArr.add(picArr[i][j]);
				//y循环控制
				if(yGrid==ySpan){
					yGrid=-1;
				}else{
					if(yGrid>=0){
						yGrid++;
					}else{
						yGrid--;
					}
				}
			}
			//x循环控制
			if(xGrid==xSpan){
				xGrid=-1;
			}else{
				if(xGrid>=0){
					xGrid++;
				}else{
					xGrid--;
				}
			}
		}
	}

	//	public void draw(){
//		Log.d("地图图层绘TileLayer","开始绘制地图", null);
//		Canvas cv=null;
//		for(int i=0;i<this.picArr.length;i++){
//			for(int j=0;j<this.picArr[i].length;j++){
//				//绘制到bitmap上，然后在绘制将bitmap设成ImageView的数据显示，进行测试
//
//				try{
//					int tx=this.picArr[i][j].x+this.x;
//					int ty=this.picArr[i][j].y+this.y;
//					Rect r=new Rect(tx,ty,(int)(tx+MapsConstants.MPicWidth),(int)(ty+MapsConstants.MPicHeight));
//					cv=this.pMaplet.sfh.lockCanvas(r);// 获取画布
//					if(this.picArr[i][j].bitmap!=null){
//						cv.drawBitmap(this.picArr[i][j].bitmap, this.picArr[i][j].x+this.x, this.picArr[i][j].y+this.y, null);
//					}else{
//						cv.drawColor(Color.BLACK);//设置画布背景颜色
//						Paint p = new Paint(); //创建画笔
//						p.setColor(Color.WHITE);
//						cv.drawRect(r, p);
//					}
//				}catch(Exception e){
//					Log.d("地图图层绘制出错", e.getMessage(), null);
//				}finally{
//					this.pMaplet.sfh.unlockCanvasAndPost(cv);
//				}
//
//			}
//		}
//		Log.d("地图图层绘","绘制地图完成，应该显示地图了", null);
//	}
	public float scale=1;
	public void draw(){
		if(this.picArr.length>0){
			for(int i=0;i<this.picArr.length;i++){
				for(int j=0;j<this.picArr[i].length;j++){
					try{
						Rect src=new Rect(0,0,(int)MapsConstants.MPicWidth,(int)MapsConstants.MPicHeight);

						int dx1=this.picArr[i][j].x+this.x;
						int dy1=this.picArr[i][j].y+this.y;
						int dx2=dx1+(int)MapsConstants.MPicWidth;
						int dy2=dy1+(int)MapsConstants.MPicHeight;
						Rect dst=new Rect((int)(this.MapsWidth/2+(dx1-this.MapsWidth/2)*this.scale),
								(int)(this.MapsHeight/2+(dy1-this.MapsHeight/2)*this.scale),
								(int)(this.MapsWidth/2+(dx2-this.MapsWidth/2)*this.scale),
								(int)(this.MapsHeight/2+(dy2-this.MapsHeight/2)*this.scale));

						if(this.picArr[i][j].bitmap!=null){
							this.pMaplet.drawingCanvas.drawBitmap(this.picArr[i][j].bitmap,src,dst, null);
						}
					}catch(Exception e){
						Log.d("地图图层绘制出错", e.getMessage(), null);
					}finally{
//						this.pMaplet.sfh.unlockCanvasAndPost(cv);
					}

				}
			}
//			Log.d("地图图层绘","绘制地图完成，应该显示地图了", null);
		}
	}



	public void clear(){
		this.loaderArr.clear();
//		this.loaderArr=new ArrayList<ImageLoader>();

		if(this.picArr!=null){
			for(int i=0;i<this.picArr.length;i++){
				for(int j=0;j<this.picArr[i].length;j++){
					if(this.picArr[i][j].LoadState!=ImageLoader.MSG_LOADING){
						this.picArr[i][j].Dispose();
						this.picArr[i][j]=null;
					}
				}
				this.picArr[i]=null;
			}
			this.picArr=null;
		}

//		this.drawBack();
	}


	public void Dispose(){
//		this.loadTask.isRun=false;
//		this.loadTask.cancel(true);

		this.mHandler=null;
//		this.mThread.isRun=false;
		this.mThread.interrupt();

		this.clear();

		this.pMaplet=null;
	}

	public void refresh(){
		int txMax=this.x+this.picArr[this.xIndex][0].x+(int)MapsConstants.MPicWidth;
		int txMin=this.x+this.picArr[(this.xIndex+1)%this.picArr.length][0].x;

		if(txMin>=-MapsConstants.MoveOffset){//往左填图
			this.loadLeftPic(txMin);
		}else if(txMax<=this.MapsWidth+MapsConstants.MoveOffset){//往右填图
			this.loadRightPic(txMax);
		}

		int tyMax=this.y+this.picArr[0][this.yIndex].y+(int)MapsConstants.MPicHeight;
		int tyMin=this.y+this.picArr[0][(this.yIndex+1)%this.picArr[0].length].y;


		if(tyMin>=-MapsConstants.MoveOffset){//往上填图
			this.loadTopPic(tyMin);
		}else if(tyMax<=this.MapsHeight+MapsConstants.MoveOffset){//往下填图
			this.loadBottomPic(tyMax);
		}
	}

	private void loadBottomPic(int tyMax){
		int tmp=(int)Math.ceil((this.MapsHeight+MapsConstants.MoveOffset-tyMax)/MapsConstants.MPicHeight);
		for(int j=0;j<tmp;j++){
			int tyi=(this.yIndex+1)%this.picArr[0].length;
			int yGrid=this.picArr[0][this.yIndex].yGrid-1;

			int yDir=(int)Math.floor((this.yAbsolute+yGrid)/(MapsConstants.GridFactors[this.MapsLevel]));
			int yFile=(int)this.yAbsolute+yGrid-yDir*MapsConstants.GridFactors[this.MapsLevel];
			yFile=yFile<0?(MapsConstants.GridFactors[this.MapsLevel]+yFile):yFile;
			for(int i=0;i<this.picArr.length;i++){
				String imgName=MapsConstants.MapsRoot+"/"+this.m_MapsType+"/"+MapsConstants.Directorys[this.MapsLevel]+"/";

				int xGrid=this.picArr[i][0].xGrid;;

				int xDir=(int)Math.floor((this.xAbsolute+xGrid)/MapsConstants.GridFactors[this.MapsLevel]);
				int xFile=(int)this.xAbsolute+xGrid-xDir*MapsConstants.GridFactors[this.MapsLevel];
				xFile=xFile<0?(MapsConstants.GridFactors[this.MapsLevel]+xFile):xFile;
				imgName+=xDir+"/"+yDir+"/"+xFile+"_"+yFile+MapsConstants.PicType;

				if(this.loaderArr.contains(this.picArr[i][tyi])&&!(this.picArr[i][tyi].LoadState==ImageLoader.MSG_LOADING)){
					this.loaderArr.remove(this.picArr[i][tyi]);
				}


					this.picArr[i][tyi].src = imgName;

				this.picArr[i][tyi].y=this.picArr[0][this.yIndex].y+(int)MapsConstants.MPicHeight;
				this.picArr[i][tyi].yGrid=yGrid;

				this.picArr[i][tyi].xDir=xDir;
				this.picArr[i][tyi].yDir=yDir;
				this.picArr[i][tyi].xFile=xFile;
				this.picArr[i][tyi].yFile=yFile;

				this.picArr[i][tyi].bitmap=null;

				this.loaderArr.add(this.picArr[i][tyi]);
				this.picArr[i][tyi].LoadState=ImageLoader.MSG_WAIT;
			}
			this.yIndex=(this.yIndex+1)%this.picArr[0].length;
		}
	}
	private void loadTopPic(int tyMin){
		int tmp=(int)Math.ceil((tyMin+MapsConstants.MoveOffset)/MapsConstants.MPicHeight);
		for(int j=0;j<tmp;j++){
			int tyi=(this.yIndex+1)%this.picArr[0].length;
			int yGrid=this.picArr[0][tyi].yGrid+1;

			int yDir=(int)Math.floor((this.yAbsolute+yGrid)/(MapsConstants.GridFactors[this.MapsLevel]));
			int yFile=this.yAbsolute+yGrid-yDir*MapsConstants.GridFactors[this.MapsLevel];
			yFile=yFile<0?(MapsConstants.GridFactors[this.MapsLevel]+yFile):yFile;
			for(int i=0;i<this.picArr.length;i++){
				String imgName=MapsConstants.MapsRoot+"/"+this.m_MapsType+"/"+MapsConstants.Directorys[this.MapsLevel]+"/";

				int xGrid=this.picArr[i][0].xGrid;;

				int xDir=(int)Math.floor((this.xAbsolute+xGrid)/MapsConstants.GridFactors[this.MapsLevel]);
				int xFile=this.xAbsolute+xGrid-xDir*MapsConstants.GridFactors[this.MapsLevel];
				xFile=xFile<0?(MapsConstants.GridFactors[this.MapsLevel]+xFile):xFile;
				imgName+=xDir+"/"+yDir+"/"+xFile+"_"+yFile+MapsConstants.PicType;

				if(this.loaderArr.contains(this.picArr[i][this.yIndex])&&!(this.picArr[i][this.yIndex].LoadState==ImageLoader.MSG_LOADING)){
					this.loaderArr.remove(this.picArr[i][this.yIndex]);//如果存在并且不是正在载入，那么从队列移除
				}


					this.picArr[i][this.yIndex].src = imgName;

				this.picArr[i][this.yIndex].y=this.picArr[0][tyi].y-(int)MapsConstants.MPicHeight;
				this.picArr[i][this.yIndex].yGrid=yGrid;

				this.picArr[i][this.yIndex].xDir=xDir;
				this.picArr[i][this.yIndex].yDir=yDir;
				this.picArr[i][this.yIndex].xFile=xFile;
				this.picArr[i][this.yIndex].yFile=yFile;

				this.picArr[i][this.yIndex].bitmap=null;

				//压入下载队列，不管开始是存在队列，
				this.loaderArr.add(this.picArr[i][this.yIndex]);
				this.picArr[i][this.yIndex].LoadState=ImageLoader.MSG_WAIT;


			}
			this.yIndex=(this.yIndex-1+this.picArr[0].length)%this.picArr[0].length;
		}
	}
	private void loadLeftPic(int txMin){
		int tmp=(int)Math.ceil((txMin+MapsConstants.MoveOffset)/MapsConstants.MPicWidth);
		for(int i=0;i<tmp;i++){
			int txi=(this.xIndex+1)%this.picArr.length;
			int xGrid=this.picArr[txi][0].xGrid-1;;

			int xDir=(int)Math.floor((this.xAbsolute+xGrid)/MapsConstants.GridFactors[this.MapsLevel]);
			int xFile=this.xAbsolute+xGrid-xDir*MapsConstants.GridFactors[this.MapsLevel];
			xFile=xFile<0?(MapsConstants.GridFactors[this.MapsLevel]+xFile):xFile;
			for(int j=0;j<this.picArr[this.xIndex].length;j++){
				String imgName=MapsConstants.MapsRoot+"/"+this.m_MapsType+"/"+MapsConstants.Directorys[this.MapsLevel]+"/";

				int yGrid=this.picArr[this.xIndex][j].yGrid;
				int yDir=(int)Math.floor((this.yAbsolute+yGrid)/(MapsConstants.GridFactors[this.MapsLevel]));

				int yFile=this.yAbsolute+yGrid-yDir*MapsConstants.GridFactors[this.MapsLevel];
				yFile=yFile<0?(MapsConstants.GridFactors[this.MapsLevel]+yFile):yFile;
				imgName+=xDir+"/"+yDir+"/"+xFile+"_"+yFile+MapsConstants.PicType;

				if(this.loaderArr.contains(this.picArr[this.xIndex][j])&&!(this.picArr[this.xIndex][j].LoadState==ImageLoader.MSG_LOADING)){
					this.loaderArr.remove(this.picArr[this.xIndex][j]);
				}


					this.picArr[this.xIndex][j].src = imgName;

				this.picArr[this.xIndex][j].x=this.picArr[txi][0].x-(int)MapsConstants.MPicWidth;
				this.picArr[this.xIndex][j].xGrid=xGrid;

				this.picArr[this.xIndex][j].xDir=xDir;
				this.picArr[this.xIndex][j].yDir=yDir;
				this.picArr[this.xIndex][j].xFile=xFile;
				this.picArr[this.xIndex][j].yFile=yFile;

				this.picArr[this.xIndex][j].bitmap=null;

				this.loaderArr.add(this.picArr[this.xIndex][j]);
				this.picArr[this.xIndex][j].LoadState=ImageLoader.MSG_WAIT;
			}
			this.xIndex=(this.xIndex-1+this.picArr.length)%this.picArr.length;
		}
	}
	private void loadRightPic(int txMax){
		int tmp=(int)Math.ceil((this.MapsWidth+MapsConstants.MoveOffset-txMax)/MapsConstants.MPicWidth);
		for(int i=0;i<tmp;i++){
			int xGrid=this.picArr[this.xIndex][0].xGrid+1;

			int xDir=(int)Math.floor((this.xAbsolute+xGrid)/MapsConstants.GridFactors[this.MapsLevel]);
			int xFile=this.xAbsolute+xGrid-xDir*MapsConstants.GridFactors[this.MapsLevel];
			xFile=xFile<0?(MapsConstants.GridFactors[this.MapsLevel]+xFile):xFile;
			for(int j=0;j<this.picArr[this.xIndex].length;j++){
				String imgName=MapsConstants.MapsRoot+"/"+this.m_MapsType+"/"+MapsConstants.Directorys[this.MapsLevel]+"/";

				int yGrid=this.picArr[this.xIndex][j].yGrid;
				int yDir=(int)Math.floor((this.yAbsolute+yGrid)/(MapsConstants.GridFactors[this.MapsLevel]));
				if(yDir<0){yDir++;}
				int yFile=this.yAbsolute+yGrid-yDir*MapsConstants.GridFactors[this.MapsLevel];
				yFile=yFile<0?(MapsConstants.GridFactors[this.MapsLevel]+yFile):yFile;
				imgName+=xDir+"/"+yDir+"/"+xFile+"_"+yFile+MapsConstants.PicType;

				int txi=(this.xIndex+1)%this.picArr.length;

				if(this.loaderArr.contains(this.picArr[txi][j])&&!(this.picArr[txi][j].LoadState==ImageLoader.MSG_LOADING)){
					this.loaderArr.remove(this.picArr[txi][j]);
				}

				this.picArr[txi][j].src = imgName;

				this.picArr[txi][j].x=this.picArr[this.xIndex][0].x+(int)MapsConstants.MPicWidth;
				this.picArr[txi][j].xGrid=xGrid;

				this.picArr[txi][j].xDir=xDir;
				this.picArr[txi][j].yDir=yDir;
				this.picArr[txi][j].xFile=xFile;
				this.picArr[txi][j].yFile=yFile;

				this.picArr[txi][j].bitmap=null;

				this.loaderArr.add(this.picArr[txi][j]);
				this.picArr[txi][j].LoadState=ImageLoader.MSG_WAIT;
			}
			this.xIndex=(this.xIndex+1)%this.picArr.length;
		}
	}





	private void initVariable(){//计算栅格图片排列参数
		this.xAbsolute=(int)Math.floor((this.Cx)/MapsConstants.ScaleFactors[this.MapsLevel]);
		this.yAbsolute=(int)Math.floor((this.Cy)/MapsConstants.ScaleFactors[this.MapsLevel]);
		//Log.d("gary","this.Cx%MapsConstants.ScaleFactors[this.MapsLevel]="+this.Cx%MapsConstants.ScaleFactors[this.MapsLevel], null);
		//Log.d("gary","(this.Cy%MapsConstants.ScaleFactors[this.MapsLevel])*MapsConstants.MPicHeight="+(this.Cy%MapsConstants.ScaleFactors[this.MapsLevel])*MapsConstants.MPicHeight, null);
		//Log.d("gary","(this.Cx%MapsConstants.ScaleFactors[this.MapsLevel])*MapsConstants.MPicWidth/MapsConstants.ScaleFactors[this.MapsLevel]="+(this.Cx%MapsConstants.ScaleFactors[this.MapsLevel])*MapsConstants.MPicWidth/MapsConstants.ScaleFactors[this.MapsLevel], null);
		//Log.d("gary","Math.round((this.Cx%MapsConstants.ScaleFactors[this.MapsLevel])*MapsConstants.MPicWidth/MapsConstants.ScaleFactors[this.MapsLevel])="+Math.round((this.Cx%MapsConstants.ScaleFactors[this.MapsLevel])*MapsConstants.MPicWidth/MapsConstants.ScaleFactors[this.MapsLevel]), null);


		this.centerX=(int)(this.MapsWidth/2-Math.round((this.Cx%MapsConstants.ScaleFactors[this.MapsLevel])*MapsConstants.MPicWidth/MapsConstants.ScaleFactors[this.MapsLevel]));
		this.centerY= (int)(this.MapsHeight/2-MapsConstants.MPicHeight+Math.round((this.Cy%MapsConstants.ScaleFactors[this.MapsLevel])*MapsConstants.MPicHeight/MapsConstants.ScaleFactors[this.MapsLevel]));


		this.xSpan=(int)Math.ceil(this.MapsWidth/MapsConstants.MPicWidth/2);
		this.ySpan=(int)Math.ceil(this.MapsHeight/MapsConstants.MPicHeight/2);

		this.xIndex=this.xSpan*2;//最大x方向值
		this.yIndex=this.ySpan*2;//最大y方向值
	}



	private class LoadThread extends Thread {
		@Override
		public void run(){
			while(!Thread.interrupted()){
				if(loaderArr!=null&&loaderArr.size()>0){
					ImageLoader loader=loaderArr.get(0);
					loader.load();
					loaderArr.remove(loader);
					if(loader.isDraw){
						if(loader.LoadState==ImageLoader.MSG_SUCCESS&&loader.bitmap!=null){
							//Log.d("图片载入成功tilelayer",loaderArr.size()+","+loader.src, null);
							if(mHandler!=null){
								mHandler.obtainMessage(ImageLoader.MSG_SUCCESS).sendToTarget();
							}
						}else if(loader.LoadState==ImageLoader.MSG_ERROR&&loader.loadIndex<loader.loadCount){
							Log.d("图片载入失败tilelayer", loaderArr.size()+","+loader.src, null);
							loaderArr.add(loader);//控制重复加载
						}
					}
					loader.isDraw=true;
				}else{
					try{
						Thread.sleep(500);
					}catch(InterruptedException intExp){
						Log.w("地图图层","休眠时出错",intExp);
						//InterruptedException;当线程在活动之前或活动期间处于正在等待、休眠或占用状态且该线程被中断时，抛出该异常。
					}
				}
			}
		}
	}



	private class LoadHandler extends Handler{
		public void handleMessage(Message msg){
			if(!pMaplet.IsMoving){
				pMaplet.refreshMaps();
			}

		}
	}


//	class LoadTask extends AsyncTask<String, Integer, Integer>{
//		public boolean isRun=true;
//		@Override
//		protected Integer doInBackground(String... params){
//			while(!isCancelled()&&this.isRun){
//
//				if(loaderArr.size()>0){
//					ImageLoader loader=loaderArr.get(0);
//					loader.load();
//					loaderArr.remove(loader);
//					if(loader.isDraw){
//						if(loader.LoadState==ImageLoader.MSG_SUCCESS&&loader.bitmap!=null){
////							Log.d("图片载入成功tilelayer",loaderArr.size()+","+loader.src, null);
//							publishProgress();
//						}else if(loader.LoadState==ImageLoader.MSG_ERROR&&loader.loadIndex<loader.loadCount){
////							Log.d("图片载入失败tilelayer", loaderArr.size()+","+loader.src, null);
//							loaderArr.add(loader);//控制重复加载
//						}
//					}
//					loader.isDraw=true;
//				}else{
//					try{
//						Thread.sleep(500);
//					}catch(InterruptedException intExp){
//						Log.w("地图图层","休眠时出错",intExp);
//					}
//				}
//			}
//			return 0;
//		}
//
//		@Override
//		protected void onProgressUpdate(Integer... progress){
//			if(!pMaplet.IsMoving){
//				pMaplet.refreshMaps();
//			}
//		}
//
//		@Override
//		protected void onCancelled(){
//			this.isRun=false;
//		}
//	}
}
