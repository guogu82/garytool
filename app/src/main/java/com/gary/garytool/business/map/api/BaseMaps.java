package com.gary.garytool.business.map.api;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BaseMaps extends View {
    public MapViewGroup Parent;
    public BaseMaps(Context context){
        super(context);
    }
    public BaseMaps(Context context,AttributeSet attrs){
        super(context, attrs);
    }
    public BaseMaps(Context context,double xc,double yc,int level,int mapWidth,int mapHeight) {
        super(context);

        this.initMaps(xc,yc,level,mapWidth,mapHeight);
    }
    public void initMaps(double xc,double yc,int level,int mapWidth,int mapHeight){
//		sfh=this.getHolder();
        this.initMaps(xc, yc, level);
        this.m_MWidth=mapWidth;
        this.m_MHeight=mapHeight;
    }
    public void initMaps(double xc,double yc,int level){
        this.m_Cx=xc;
        this.m_Cy=yc;
        this.m_MapsLevel=level;

        this.m_MaxLevel=MapsConstants.MapsLevelCount-1;
    }

    //	public SurfaceHolder sfh;
    public TileLayer pTLayer;
    public boolean isShow=false;



    protected int m_MaxLevel=MapsConstants.MapsLevelCount-1; //地图最大等级
    public int getMaxLevel(){
        return this.m_MaxLevel;
    }

    protected double m_Cx=0;//地图中心坐标
    public double getCx(){
        return this.m_Cx;
    }
    protected double m_Cy=0;
    public double getCy(){
        return this.m_Cy;
    }

    protected int m_MapsLevel=0;//地图等级
    public int getMapsLevel(){
        return this.m_MapsLevel;
    }

    protected int m_MWidth=0;//地图宽度
    public int getMapsWidth(){
        return this.m_MWidth;
    }
    protected int m_MHeight=0;//地图高度	0
    public int getMapsHeight(){
        return this.m_MHeight;
    }
    protected String m_MapsType=MapsType.Typical;//地图类型

    //私有属性
    protected int OldMapsLevel=0;
    protected double Oldcx=0;
    protected double Oldcy=0;

    //内部的状态标量
    protected Boolean IsZooming=false;//是否正在缩放地图等级
    protected Boolean IsMoving=false;//是否正在移动地图

    //公有状态控制属性
    public Boolean mapsMoveEnabled=true;//是否允许地图平滑移动
    public Boolean mapsZoomEnabled=true;//是否允许地图平滑缩放
    //			public Boolean mapsRangeEnabled=false;//地图四至范围是否有效
//			public Bounds mapsRange=new Bounds(null,null);//地图的四至范围
    public Boolean isAdjustMapsMaxLevel=true;//是否自动校正地图的最大等级

    private Boolean m_mapsDragEnabled=true;//是否添加拖动地图的事件监听
    public Boolean getMapsDragEnabled(){
        return this.m_mapsDragEnabled;
    }
    public void setMapsDragEnabled(Boolean value){
        if(this.m_mapsDragEnabled!=value){
            this.m_mapsDragEnabled=value;
        }
    }
    public Canvas drawingCanvas=null;
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        if(this.m_MWidth==0||this.m_MHeight==0){
            this.m_MWidth=this.getWidth();
            this.m_MHeight=this.getHeight();
        }

        if(this.isShow){
            drawingCanvas = canvas;
            if(this.m_MWidth!=0||this.m_MHeight!=0){
                if(this.pTLayer==null){
                    this.pTLayer=new TileLayer(this.m_Cx,this.m_Cy,this.m_MapsLevel,this.m_MWidth,this.m_MHeight);
                    this.pTLayer.pMaplet=this;
                    this.pTLayer.show();
                }
                this.draw();
            }

            drawingCanvas = null;
        }
//		Log.d("绘制地图map.onDraw",this.getWidth()+";"+this.getHeight(), null);
    }

    public boolean onTouchEvent (MotionEvent e){

        int action = e.getActionMasked();
        if(e.getPointerCount()>1){
            switch(action){
                case MotionEvent.ACTION_MOVE:
                    this.Zooming(e);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    this.startZoom(e);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    this.endZoom(e);
                    break;
                default:
                    break;
            }
        }else{
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    this.startDrag(e);
                    break;
                case MotionEvent.ACTION_MOVE:
                    this.darging(e);
                    break;
                case MotionEvent.ACTION_UP:
                    this.endDrag(e);
                    break;
                default:
                    break;
            }
        }
        return true;
    }


//	public var isChangeDragingCursor:Boolean=true;//是否改变拖动时的鼠标指针

    private int ex=0;
    private int ey=0;
    protected int tOldx;
    protected int tOldy;

    private void startDrag(MotionEvent e){
        if(this.mapsMoveEnabled&&this.isCanZoomingAndMoving()){
            this.IsMoving=true;

            this.Oldcx=this.m_Cx;
            this.Oldcy=this.m_Cy;
            this.tOldx=this.pTLayer.x;
            this.tOldy=this.pTLayer.y;
            this.ex=(int)e.getX()-this.pTLayer.x;
            this.ey=(int)e.getY()-this.pTLayer.y;
//			Log.d("地图拖动", "开始拖动:"+e.getPointerCount(), null);
            //广播事件
            MapsEvent et=new MapsEvent(MapsEvent.MapsCenterChange);
            et.cx=this.m_Cx;
            et.cy=this.m_Cy;
            et.MapsLevel=this.m_MapsLevel;
            this.dispatchEvent(et);
        }
    }

    private void darging(MotionEvent e)
    {
        if(this.IsMoving){
            double tmptx=e.getX()-this.ex;
            double tmpty=e.getY()-this.ey;
            double tmpCy=this.Oldcy-(this.tOldy-tmpty)*MapsConstants.ScaleFactors[this.m_MapsLevel]/MapsConstants.MPicHeight;
            double tmpCx=this.Oldcx+(this.tOldx-tmptx)*MapsConstants.ScaleFactors[this.m_MapsLevel]/MapsConstants.MPicWidth;

            this.pTLayer.x=(int)tmptx;
            this.pTLayer.y=(int)tmpty;

            //计算当前地图中心经纬度
            this.m_Cy=tmpCy;
            this.m_Cx=tmpCx;
            this.pTLayer.refresh();
            this.refreshMaps();

//			Log.d("地图拖动进行中", "地图拖动进行中", null);


            //广播事件
            MapsEvent et=new MapsEvent(MapsEvent.MapsCenterChanging);
            et.cx=this.m_Cx;
            et.cy=this.m_Cy;
            et.MapsLevel=this.m_MapsLevel;
            this.dispatchEvent(et);
        }
    }
    private void endDrag(MotionEvent e){
        if(this.IsMoving){
            this.IsMoving=false;
            double tmpx=Math.abs(this.tOldy-this.pTLayer.y);
            double tmpy=Math.abs(this.tOldx-this.pTLayer.x);
            this.m_Cy=this.Oldcy-(this.tOldy-this.pTLayer.y)*MapsConstants.ScaleFactors[this.m_MapsLevel]/MapsConstants.MPicHeight;
            this.m_Cx=this.Oldcx+(this.tOldx-this.pTLayer.x)*MapsConstants.ScaleFactors[this.m_MapsLevel]/MapsConstants.MPicWidth;
            this.refreshMaps();

            if(tmpx>1||tmpy>1){

                //广播事件
                MapsEvent et=new MapsEvent(MapsEvent.MapsCenterChanged);
                et.cx=this.m_Cx;
                et.cy=this.m_Cy;
                et.MapsLevel=this.m_MapsLevel;
                et.ChangeType=ChangeType.SmoothChange;
                this.dispatchEvent(et);
            }else{
                BaseMarker m=this.getMarker(e.getX(),e.getY());
                if(m==null){
//					Log.d("地图被点击", "lk", null);
                    MapsEvent et=new MapsEvent(MapsEvent.MapsClicked);
                    et.cx=this.m_Cx;
                    et.cy=this.m_Cy;
                    et.MapsLevel=this.m_MapsLevel;
                    //Log.d("markerpoint", "map click x"+e.getX()+"y"+e.getY());
                    this.dispatchEvent(et);
                }else{
//					Log.d("地图marker被点击", "lk", null);
                    MarkerEvent me=new MarkerEvent(MarkerEvent.MarkerClicked);
                    me.pMarker=m;
                    //Log.d("markerpoint", "mark click click x"+e.getX()+"y"+e.getY());
                    this.dispatchEvent(me);
                }
            }
        }
    }

    private BaseMarker getMarker(float px,float py){
        int count=this.pMLayer.markers.size();
        BaseMarker bm=null;
        float dis=-1;

        for(int i=0;i<count;i++){
            BaseMarker m=this.pMLayer.markers.get(i);
            if(m.rect!=null&&m.Visible){
                if(px>m.rect.left&&px<m.rect.right&&py>m.rect.top&&py<m.rect.bottom){
//					return m;
                    float d=(m.rect.left+m.rect.right-px)*(m.rect.left+m.rect.right-px)+(m.rect.top+m.rect.bottom-py)*(m.rect.top+m.rect.bottom-py);
                    if(dis==-1||dis>d){
                        dis=d;
                        bm=m;
                    }
                }
            }
        }

        return bm;
    }

    private void hideLayer(){
        this.pGLayer.Visible=false;
        this.pMLayer.Visible=false;
    }
    private void showLayer(){
        this.pGLayer.Visible=true;
        this.pMLayer.Visible=true;
    }

    private float oldDisX=0;
    private float oldDisY=0;
    private void startZoom(MotionEvent e){
        this.IsZooming=true;
        this.IsMoving=false;
        this.oldDisX=e.getX(0)-e.getX(1);
        this.oldDisY=e.getY(0)-e.getY(1);
        this.hideLayer();

        //广播事件
        MapsEvent et=new MapsEvent(MapsEvent.MapsLevelChange);
        et.cx=this.m_Cx;
        et.cy=this.m_Cy;
        et.MapsLevel=this.m_MapsLevel;
        et.ChangeType=ChangeType.SmoothChange;
        this.dispatchEvent(et);
    }

    private void Zooming(MotionEvent e){
        if(this.IsZooming){
            float scaleX=(e.getX(0)-e.getX(1))/this.oldDisX;
            float scaleY=(e.getY(0)-e.getY(1))/this.oldDisY;
            this.pTLayer.scale=((1-scaleX)<(1-scaleY)?scaleX:scaleY);
            this.refreshMaps();
            //广播事件
            MapsEvent et=new MapsEvent(MapsEvent.MapsLevelChanging);
            et.cx=this.m_Cx;
            et.cy=this.m_Cy;
            et.MapsLevel=this.m_MapsLevel;
            et.ChangeType=ChangeType.SmoothChange;
            this.dispatchEvent(et);
        }
    }

    private void endZoom(MotionEvent e){
        float tls=this.pTLayer.scale;
        this.pTLayer.scale=1;

        if(tls>1){
            this.setMapsLevel(this.m_MapsLevel+(int)Math.ceil(tls)-1);
        }else if(tls<1){
            this.setMapsLevel(this.m_MapsLevel-(int)Math.ceil(1/tls)+1);
        }

        this.showLayer();
        this.refreshMaps();
        this.IsZooming=false;

        //广播事件
        MapsEvent et=new MapsEvent(MapsEvent.MapsLevelChanging);
        et.cx=this.m_Cx;
        et.cy=this.m_Cy;
        et.MapsLevel=this.m_MapsLevel;
        et.ChangeType=ChangeType.SmoothChange;
        this.dispatchEvent(et);
    }




    //公有方法
    public void show(){//显示地图
//		Log.d("显示地图", this.m_MWidth+"lk"+this.m_MHeight, null);

        if(!this.isShow){
            this.isShow=true;

            if(this.m_MWidth!=0||this.m_MHeight!=0){
                if(this.pTLayer==null){
                    this.pTLayer=new TileLayer(this.m_Cx,this.m_Cy,this.m_MapsLevel,this.m_MWidth,this.m_MHeight);
                    this.pTLayer.pMaplet=this;
                }
                this.pTLayer.show();
            }
        }
        this.setVisibility(View.VISIBLE);
    }


    public void draw(){
        if(this.isShow){
            this.pTLayer.draw();
            if(this.pGLayer.Visible){
                this.pGLayer.draw();
            }
            if(this.pMLayer.Visible){
                this.pMLayer.draw();
            }
            Parent.update();
        }
    }

    public void hide(){//隐藏地图
//		this.pTLayer.clear();

        this.setVisibility(View.INVISIBLE);
    }

    public void setMapsType(String mType){//设置地图类型
        if(this.m_MapsType!=mType){
            this.m_MapsType=mType;
            this.changeMapsType(mType);
        }
    }

    public String getMapsType(){//获取地图类型
        return this.m_MapsType;
    }



    public void setCenter(double xc,double yc){//设置地图中心坐标
        if(xc!=this.m_Cx||yc!=this.m_Cy){
            this.m_Cx=xc;
            this.m_Cy=yc;
            this.pTLayer.Cx=xc;
            this.pTLayer.Cy=yc;

            this.pTLayer.show();

            //广播事件
            MapsEvent e=new MapsEvent(MapsEvent.MapsCenterChanged);
            e.cx=this.m_Cx;
            e.cy=this.m_Cy;
            e.MapsLevel=this.m_MapsLevel;
            e.ChangeType=ChangeType.DirectlyChange;
            this.dispatchEvent(e);
        }
    }

    public void setSize(int mwidth,int mheight,Boolean isChangeCenter){//设置地图大小
        if(this.m_MWidth!=mwidth||this.m_MHeight!=mheight){
            this.m_MWidth=mwidth;
            this.m_MHeight=mheight;

            this.pTLayer.MapsWidth=this.m_MWidth;
            this.pTLayer.MapsHeight=this.m_MHeight;

            this.pTLayer.x=0;
            this.pTLayer.y=0;
            this.pTLayer.show();

            //广播事件
            MapsEvent e=new MapsEvent(MapsEvent.MapsSizeChanged);
            e.cx=this.m_Cx;
            e.cy=this.m_Cy;
            e.MapsLevel=this.m_MapsLevel;
            e.mapsWidth=this.m_MWidth;
            e.mapsHeight=this.m_MHeight;
            this.dispatchEvent(e);
        }
    }

    public void setMapsLevel(int level){//设置地图等级
        if(level<0){level=0;}
        if(level>this.m_MaxLevel){level=this.m_MaxLevel;}

        if(this.m_MapsLevel!=level){
            this.OldMapsLevel=this.m_MapsLevel;
            this.m_MapsLevel=level;
            this.pTLayer.setTileLayer(this.m_Cx,this.m_Cy,this.m_MapsLevel,this.m_MWidth,this.m_MHeight);
            this.pTLayer.show();
            this.refreshMaps();

            //广播事件
            MapsEvent e=new MapsEvent(MapsEvent.MapsMaxLevelChanged);
            e.cx=this.m_Cx;
            e.cy=this.m_Cy;
            e.MapsLevel=this.m_MapsLevel;
            this.dispatchEvent(e);
        }
    }

    private void changeMapsType(String mType){
        this.pTLayer.setMaptype(mType);

        //广播事件
        MapsEvent e=new MapsEvent(MapsEvent.MapsTypeChanged);
        e.cx=this.m_Cx;
        e.cy=this.m_Cy;
        e.MapsLevel=this.m_MapsLevel;
        e.mapsType=this.m_MapsType;
        this.dispatchEvent(e);
    }

    public void refreshMaps(){
        this.invalidate();
    }

    public void refreshMaps(double xc,double yc,int level,int mapWidth,int mapHeight){//重新加载地图，
        this.m_Cx=xc;
        this.m_Cy=yc;

        if(level<0){level=0;}
        if(level>this.m_MaxLevel){level=this.m_MaxLevel;}

        if(mapWidth>0){
            this.m_MWidth=mapWidth;
        }
        if(mapHeight>0){
            this.m_MHeight=mapHeight;
        }
        this.refreshMaps();
        //广播事件


    }

    //添加标注和图形相关
    public GraphicsLayer pGLayer=new GraphicsLayer();
    public BaseGraphics addGraphicsMarker(BaseGraphics g){//添加指定动态图形
        g.pMaplet=this;
        this.pGLayer.gs.add(g);
        return g;
    }
    public void removeGraphicsMarker(BaseGraphics g){
        this.pGLayer.gs.remove(g);
    }
    public void removeGraphicsMarkers(){
        if(this.pGLayer.gs!=null){
            this.pGLayer.gs.clear();
        }
    }
    public void clearGraphicsMarkers(){
        if(this.pGLayer.gs!=null){
            int count=this.pGLayer.gs.size();
            for(int i=0;i<count;i++){
                this.pGLayer.gs.get(i).Dispose();
            }
            this.pGLayer.gs.clear();
        }
    }
    public MarkerLayer pMLayer=new MarkerLayer();
    public BaseMarker addMarker(BaseMarker m){//添加指定动态图形
        m.pMaplet=this;
        this.pMLayer.markers.add(m);
        return m;
    }
    public void removeMarker(BaseMarker m){
        this.pMLayer.markers.remove(m);
    }
    public void removeMarkers(){
        if(this.pMLayer.markers!=null){
//			this.pMLayer.hideTag();
            this.pMLayer.markers.clear();
        }
    }
    public void clearMarkers(){
        if(this.pMLayer.markers!=null){
//			this.pMLayer.hideTag();
            int count=this.pMLayer.markers.size();
            for(int i=0;i<count;i++){
                this.pMLayer.markers.get(i).Dispose();
            }
            this.pMLayer.markers.clear();
        }
    }

//	public void showTag(BaseMarker m){
//		this.pMLayer.showTag(m);
//	}
//	public void hideTag(BaseMarker m){
//		this.pMLayer.hideTag(m);
//	}



    //私有的辅助函数
    private Boolean isCanZoomingAndMoving(){
        if(this.IsMoving||this.IsZooming){
            return false;
        }else{
            return true;
        }
    }

    //公有的辅助函数
    public Point CoordinatesToPixel(double xc,double yc){//地理坐标转换为像素坐标

        int ty=(int)(this.m_MHeight/2-this.pTLayer.y+(this.m_Cy-yc)*MapsConstants.MPicHeight/MapsConstants.ScaleFactors[this.m_MapsLevel]);
        int tx=(int)(this.m_MWidth/2-this.pTLayer.x-(this.m_Cx-xc)*MapsConstants.MPicWidth/MapsConstants.ScaleFactors[this.m_MapsLevel]);
        Point tmp=new Point(tx,ty);

        tmp.x=tmp.x+this.pTLayer.x;//由图层坐标转换成屏幕的坐标
        tmp.y=tmp.y+this.pTLayer.y;

        return tmp;
    }
    public Coordinates PixelToCoordinates(int tx,int ty){//像素坐标转换为地理坐标
        int tmpX=tx-this.pTLayer.x;//由屏幕坐标转换成图层坐标
        int tmpY=ty+this.pTLayer.y;

        double xc=this.m_Cx-(this.m_Cx-this.pTLayer.Cx)-(this.m_MWidth/2-tmpX)*MapsConstants.ScaleFactors[this.m_MapsLevel]/MapsConstants.MPicWidth;
        double yc=this.m_Cy-(this.pTLayer.Cy-this.m_Cy)+(this.m_MHeight/2-tmpY)*MapsConstants.ScaleFactors[this.m_MapsLevel]/MapsConstants.MPicHeight;

        Coordinates crd=new Coordinates(xc,yc);
        return crd;
    }

    public Bounds getMapsBounds(){//获取当前地图的边界坐标
        double sxc=MapsConstants.ScaleFactors[this.m_MapsLevel]/MapsConstants.MPicHeight;

        Coordinates min=new Coordinates(this.m_Cx-sxc*this.m_MWidth/2,this.m_Cy-sxc*this.m_MHeight/2);
        Coordinates max=new Coordinates(this.m_Cx+sxc*this.m_MWidth/2,this.m_Cy+sxc*this.m_MHeight/2);
        return new Bounds(max,min);
    }

    public MapsStatus getAppropriateZoomCenter(Coordinates[] arr){//得到显示传入点集合的最合适的地图等级和中心
        MapsStatus re=null;
        if(arr!=null&&arr.length>1){
            Bounds tmp=GFunction.getBoundsCoordinates(arr);
            re=new MapsStatus(new Coordinates(this.m_Cx,this.m_Cy),0);
            re.Center=new Coordinates((tmp.MaxCrd.X+tmp.MinCrd.X)/2,(tmp.MaxCrd.Y+tmp.MinCrd.Y)/2);

            double dyc=tmp.MaxCrd.Y-tmp.MinCrd.Y;
            double dxc=tmp.MaxCrd.X-tmp.MinCrd.X;

            for(int i=MapsConstants.MapsLevelCount-1;i>=0;i--){
                if(MapsConstants.ScaleFactors[i]/MapsConstants.MPicHeight*this.m_MHeight>dyc&&MapsConstants.ScaleFactors[i]/MapsConstants.MPicWidth*this.m_MWidth>dxc){
                    re.MapsLevel=i+1;
                    break;
                }
            }
        }
        return re;
    }

    public int getAppropriateMinZoomLevel(Coordinates[] arr){//得到显示点集合的小地图等级
        int zoomlevel=this.m_MaxLevel;
        if(arr.length>0){
            Coordinates max=GFunction.getMaxDistanceCoordinates(arr,new Coordinates(this.m_Cx,this.m_Cy));

            double dyc=Math.abs(max.Y-this.m_Cy)*2;
            double dxc=Math.abs(max.X-this.m_Cx)*2;
            for(int i=MapsConstants.MapsLevelCount-1;i>=0;i--){
                if(MapsConstants.ScaleFactors[i]/MapsConstants.MPicHeight*this.m_MHeight>dyc&&MapsConstants.ScaleFactors[i]/MapsConstants.MPicWidth*this.m_MWidth>dxc){
                    zoomlevel=i;
                    break;
                }
            }
        }
        return zoomlevel;
    }

    //事件相关
    private ArrayList<ListenerCouple> Couples=new ArrayList<ListenerCouple>();
    private void dispatchEvent(Event e){
        for(int i=0;i<this.Couples.size();i++){
            if(Couples.get(i).Type==e.type){
                Couples.get(i).Listener.Run(e);
            }
        }
    }

    public void removeEventListener(String typeStr,IEventListener listener){
        for(int i=0;i<this.Couples.size();i++){
            if(Couples.get(i).Type==typeStr&&Couples.get(i).Listener==listener){
                Couples.remove(i);
                return;
            }
        }
    }

    public void addEventListener(String typeStr,IEventListener listener){
        if(typeStr!=null&&listener!=null){
            this.Couples.add(new ListenerCouple(typeStr,listener));
        }
    }

    public void Dispose(){
        if(this.pTLayer!=null){
            this.pTLayer.Dispose();
            this.pGLayer=null;
        }
        if(this.pMLayer!=null){
            this.pMLayer.Dispose();
            this.pMLayer=null;
        }
        if(this.pGLayer!=null){
            this.pGLayer.Dispose();
            this.pGLayer=null;
        }
        if(this.Couples!=null){
            int num=this.Couples.size();
            for(int i=0;i<num;i++){
                this.Couples.get(i).Listener=null;
                this.Couples.get(i).Type=null;
            }
            this.Couples=null;
        }
    }

}

