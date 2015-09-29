package com.gary.garytool.info;

/**
 * Created by Administrator on 2015/9/24.
 * 配合微信图片选择器功能创建的文件夹实体类
 */
public class ImageFolder {
    /**
     * 图片文件夹路径
     */
    private String dir;
    /**
     * 文件夹里面的第一张图片的路径
     */
    private String firstImagePath;

    /**
     * 文件夹的名称
     */
    private String name;
    /**
     * 图片的数量
     */
    private int count;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf=this.dir.lastIndexOf("/")+1;
        this.name=this.dir.substring(lastIndexOf);
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
