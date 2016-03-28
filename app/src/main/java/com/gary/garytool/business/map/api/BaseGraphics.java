package com.gary.garytool.business.map.api;

import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/28.
 */
public class BaseGraphics {
    public BaseGraphics(ArrayList<Coordinates> crds,Paint p){
        if(crds!=null){
            this.points=crds;
        }
        if(p!=null){
            this.pen=p;
        }else{
            this.pen=new Paint();
            this.pen.setAntiAlias(true);
            this.pen.setARGB(200, 255, 0, 0);
//			this.pen.setColor(0xFFFF0000);
            this.pen.setStyle(Paint.Style.STROKE);
            this.pen.setStrokeWidth(3);
            this.pen.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    public String nameStr=null;
    public ArrayList<Coordinates> points=new ArrayList<Coordinates>();
    public boolean Visible=true;

    //图形的画笔
    public Paint pen=null;

    public BaseMaps pMaplet=null;

    public void draw(){}

    public void show(){//显示
        this.Visible=true;
    }
    public void hide(){//隐藏
        this.Visible=false;
    }


    public void Dispose(){
        this.nameStr=null;
        this.pMaplet=null;
        if(this.points!=null){
            while(this.points.size()>0){
                this.points.remove(0);
            }
            this.points=null;
        }
    }
}
