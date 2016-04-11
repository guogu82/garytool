package com.gary.garytool.business.puzzle;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ImagePiece {
    private int index;
    private Bitmap bitmap;

    public ImagePiece()
    {

    }


    public ImagePiece(int index, Bitmap bitmap)
    {
        this.index = index;
        this.bitmap = bitmap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public String toString()
    {
        return "ImagePiece [index=" + index + ", bitmap=" + bitmap + "]";
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
