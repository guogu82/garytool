package com.gary.garytool.business.tourist;

/**
 * Created by Administrator on 2016/4/1.
 */
public class ScenicInfo {
    private String name;
    private double longitude;
    private double latitude;
    private boolean isHotSpot;
    private int id;
    private int parentId;

    public ScenicInfo(String name, double longitude, double latitude, boolean isHotSpot, int id, int parentId) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isHotSpot = isHotSpot;
        this.id = id;
        this.parentId = parentId;
    }

    public ScenicInfo(ScenicInfo scenicInfo) {
        this.name = scenicInfo.getName();
        this.longitude = scenicInfo.getLongitude();
        this.latitude = scenicInfo.getLatitude();
        this.isHotSpot = scenicInfo.isHotSpot();
        this.id = scenicInfo.getId();
        this.parentId = scenicInfo.getParentId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isHotSpot() {
        return isHotSpot;
    }

    public void setIsHotSpot(boolean isHotSpot) {
        this.isHotSpot = isHotSpot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
