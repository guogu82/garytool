package com.gary.garytool.business.map.api;



import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MapViewGroup extends ViewGroup {
    private MapView mMapView;
    public RotateMarkerLayer mRotateMarkerLayer;

    public MapView getMapView() {
        return this.mMapView;
    }

    private Context mContext;

    public MapViewGroup(Context context) {
        super(context);
        this.mContext = context;
        this.mMapView = new MapView(context);
        init(context);
    }

    public MapViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mMapView = new MapView(context, attrs);
        init(context);
    }

    public MapViewGroup(Context context, double xc, double yc, int level, int mapWidth, int mapHeight) {
        super(context);
        this.mContext = context;
        this.mMapView = new MapView(context, xc, yc, level, mapWidth, mapHeight);
        init(context);
    }

    private void init(Context context) {
        this.addView(this.mMapView);
        this.mMapView.Parent = this;
        this.mRotateMarkerLayer = new RotateMarkerLayer(this);
        //this.addView(this.mRotateMarkerLayer);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		this.getChildAt(0).measure(widthMeasureSpec, heightMeasureSpec);
        final int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            this.getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void update() {
        //Log.d("地图刷新","地图刷新updateupdateupdateupdateupdateupdate");
        if (this.customerMarker != null) {
            this.customerMarker.refresh();
        }
        //this.mRotateMarkerLayer.refresh();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //this.getChildAt(0).layout(0, 0, this.getWidth(), this.getHeight());
        //this.getChildAt(1).layout(0, 0, this.getWidth(), this.getHeight());
        final int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = this.getChildAt(i);
            child.layout(0, 0, this.getWidth(), this.getHeight());
        }
//		Log.i("onlayout",idx+":");
//		idx++;
//		final int count = this.getChildCount();
//		for(int i=1; i < count; i++){
//		this.getChildAt(i).layout(100, 50, 100+this.getWidth(), 50+this.getHeight());
//		}
        if (this.customerMarker != null) {
            this.customerMarker.refresh();
        }
    }

    private int idx = 0;
    private int idx2 = 0;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//		final int count = this.getChildCount();
//		for(int i=1; i < count; i++){
//		this.getChildAt(i).layout(100, 50, 100+this.getWidth(), 50+this.getHeight());
//		}
//		Log.i("dispatchDraw",idx2+":");
//		idx2++;
//		if(customerMarker!=null){
//		this.customerMarker.refresh();
//		}
        //this.getChildAt(1).layout(100, 50, 100+this.getWidth(), 50+this.getHeight());
    }

    public void initMaps(double xc, double yc, int level) {
        this.mMapView.initMaps(xc, yc, level);
    }

	/*-----------------------------------------------------------------------------*/

    public MonitorLatLng getCurrentMapLatLng() {
        MonitorLatLng infoLatLng = new MonitorLatLng();
        Coordinates centerCoordinates = new Coordinates();
        centerCoordinates.X = this.getCx();
        centerCoordinates.Y = this.getCy();
        infoLatLng.setCenter(centerCoordinates);

        infoLatLng.setMin(this.mMapView.getMapsBounds().MinCrd);

        infoLatLng.setMax(this.mMapView.getMapsBounds().MaxCrd);

        return infoLatLng;
    }

    public int getMaxLevel() {
        return this.mMapView.getMaxLevel();
    }

    public double getCx() {
        return this.mMapView.getCx();
    }

    public double getCy() {
        return this.mMapView.getCy();
    }

    public int getMapsLevel() {
        return this.mMapView.getMapsLevel();
    }

    public int getMapsWidth() {
        return this.mMapView.getMapsWidth();
    }

    public int getMapsHeight() {
        return this.mMapView.getMapsHeight();
    }

    public Boolean getMapsDragEnabled() {
        return this.mMapView.getMapsDragEnabled();
    }

    public void setMapsDragEnabled(Boolean value) {
        this.mMapView.setMapsDragEnabled(value);
    }

    //公有方法
    public void show() {
        this.mMapView.show();
    }

//	@Override
//	public void draw(Canvas canvas){
//		this.draw();
//	}

    public void draw() {
        //Log.d("地图刷新","地图刷新drawdrawdrawdrawdrawdrawdrawdraw");
//		this.mMapView.draw();
//		this.mRotateMarkerLayer.refresh();
    }

    public void hide() {
        this.mMapView.hide();
    }

    public void setMapsType(String mType) {
        this.mMapView.setMapsType(mType);
    }

    public String getMapsType() {//获取地图类型
        return this.mMapView.getMapsType();
    }

    public void setCenter(double xc, double yc) {//设置地图中心坐标
        this.mMapView.setCenter(xc, yc);
    }

    public void setSize(int mwidth, int mheight, Boolean isChangeCenter) {//设置地图大小
        this.mMapView.setSize(mwidth, mheight, isChangeCenter);
    }

    public void setMapsLevel(int level) {//设置地图等级
        this.mMapView.setMapsLevel(level);
    }

    public void refreshMaps() {
        this.invalidate();
    }

    public void reLayout() {
        this.requestLayout();
    }

    public void refreshMaps(double xc, double yc, int level, int mapWidth, int mapHeight) {//重新加载地图，
        this.mMapView.refreshMaps(xc, yc, level, mapWidth, mapHeight);
    }

    public BaseGraphics addGraphicsMarker(BaseGraphics g) {//添加指定动态图形
        return this.mMapView.addGraphicsMarker(g);
    }

    public void removeGraphicsMarker(BaseGraphics g) {
        this.mMapView.removeGraphicsMarker(g);
    }

    public void removeGraphicsMarkers() {
        this.mMapView.removeGraphicsMarkers();
    }

    public void clearGraphicsMarkers() {
        this.mMapView.clearGraphicsMarkers();
    }

    public BaseMarker addMarker(BaseMarker m) {//添加指定动态图形
        return this.mMapView.addMarker(m);
    }

    public ArrayList<BaseMarker> getMarkers() {
        return this.mMapView.pMLayer.markers;
    }

    public void removeMarker(BaseMarker m) {
        this.mMapView.removeMarker(m);
    }

    public void removeMarkers() {
        this.mMapView.removeMarkers();
    }

    public void clearMarkers() {
        this.mMapView.clearMarkers();
    }

    // 旋转标注
    public void addRotateMarker(RotateMarkerView marker) {
        this.mRotateMarkerLayer.addMarker(marker);
    }

    public void removeRotateMarker(RotateMarkerView marker) {
        this.mRotateMarkerLayer.removeMarker(marker);
    }

    public void removeAllRotateMarker() {
        this.mRotateMarkerLayer.clear();
    }

    public void refreshRotateMarker() {
        this.mRotateMarkerLayer.refresh();
    }

//	public void addRotateMarker(RotateMarker marker){
//		this.mRotateMarkerLayer.Markers.add(marker);
//	}
//	public void removeRotateMarker(RotateMarker marker){
//		this.mRotateMarkerLayer.Markers.remove(marker);
//	}
//	public void removeRotateMarkers(){
//		this.mRotateMarkerLayer.clear();
//	}

    //	public void showTag(BaseMarker m){
//		this.mMapView.showTag(m);
//	}
//	public void hideTag(BaseMarker m){
//		this.mMapView.hideTag(m);
//	}
    public void Dispose() {
        this.mMapView.Dispose();
    }

    public void addEventListener(String typeStr, IEventListener listener) {
        this.mMapView.addEventListener(typeStr, listener);
    }

    public CustomerMarker customerMarker = null;

    public void setCustomerMarker(CustomerMarker m) {
        deleteCustomerMarker();
        customerMarker = m;
        this.addView(customerMarker.MarkerView);
    }

    public void deleteCustomerMarker() {
        if (customerMarker != null) {
            this.removeViewInLayout(customerMarker.MarkerView);
            customerMarker.Dispose();
            customerMarker = null;
        }
    }
}