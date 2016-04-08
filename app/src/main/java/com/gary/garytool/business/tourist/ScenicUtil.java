package com.gary.garytool.business.tourist;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

import com.gary.garytool.R;
import com.gary.garytool.util.Util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Administrator on 2016/4/1.
 */
public class ScenicUtil {


    //用布尔数组存放景点的设置
    public static final int SETTING_INDEX_IS_SATELLITE=0;//默认显示
    public static final int SETTING_INDEX_IS_HOT_SCENIC=1;//默认显示
    public static final int SETTING_INDEX_SHOW_STOP=2;//默认隐藏
    public static final int SETTING_INDEX_SHOW_WC=3;//默认显示
    public static final boolean [] SETTING_SCENIC =new boolean[]{true,true,false,true};


    //景点类别常量
    public static final String SCENIC_TAG_SHOP="stop";
    public static final String SCENIC_TAG_HOT_SCENIC="hot_scenic";
    public static final String SCENIC_TAG_WC="wc";
    public static final String SCENIC_TAG_SCENIC="scenic";

    //用于存放景点列表点击进入景点详情页的信息
    public static ScenicInfo sCurrentSpotDetailScenicInfo;

    //存放所有景点列表
    public static List<ScenicInfo> sScenicAll;


    /**
     * 获取景区中所有景点的名称
     * @return 景区所有景点的集合.
     */
    public static ArrayList<ScenicInfo> getAllScenicSpot() {
        String xmlFilePath= Util.getSDPath()+"/touristguide/qinghuiyuan/scenic.xml";
        ArrayList<ScenicInfo> scenicSpotArrayList = new ArrayList<ScenicInfo>();
        File xmlFile = new File(xmlFilePath);
        if(xmlFile.exists()) {
            FileInputStream xmlFileInputStream = null;
            try {
                xmlFileInputStream = new FileInputStream(xmlFile);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            ScenicInfo info=new ScenicInfo("清晖园",0.0f,0.0f,false,0,-1,"");
            scenicSpotArrayList.add(info);
            try {
                db = dbf.newDocumentBuilder();
                Document doc = db.parse(xmlFileInputStream);
                NodeList scenicSpotList = doc.getElementsByTagName("scenic-spot");
                for (int i = 0; i < scenicSpotList.getLength(); i++) {

                    Element scenicSpotElement = (Element) scenicSpotList.item(i);
                    info=new ScenicInfo(
                            scenicSpotElement.getAttribute("name"),
                            Double.valueOf(scenicSpotElement.getAttribute("longitude")),
                            Double.valueOf(scenicSpotElement.getAttribute("latitude")),
                            scenicSpotElement.getAttribute("isHotScenicSpot").equals("0")?false:true,
                                    i, 0,
                            scenicSpotElement.getAttribute("isHotScenicSpot").equals("0")?SCENIC_TAG_SCENIC:SCENIC_TAG_HOT_SCENIC);
                    scenicSpotArrayList.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                xmlFileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scenicSpotArrayList;
    }

    //用存放景点阅读的设置
    public static final int SETTING_READ_SMALL=10;
    public static final int SETTING_READ_MIDDLE=20;
    public static final int SETTING_READ_BIG=25;
    public static final int SETTING_READ_HUGE=30;
    public static  int sCurrentReadingSetting= R.id.bt_read_middle;

    /**
     * 获取屏幕的亮度
     *
     * @param activity
     * @return
     */
    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /**
     * 设置亮度
     *
     * @param activity
     * @param brightness
     */
    public static void setBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }



    /**
     * 读取文本文件中的内容并自动转换编码
     *
     * @param str_filepath
     *            文件路径
     * @return 返回文件中的文字
     */
    public static String readFileContent(String str_filepath) {
        // 转码
        File file = new File(str_filepath);
        BufferedReader reader;
        String text = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(fis);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);// 找到文档的前三个字节并自动判断文档类型。
            in.reset();
            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                    && first3bytes[2] == (byte) 0xBF) {// utf-8

                reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFE) {

                reader = new BufferedReader(
                        new InputStreamReader(in, "unicode"));
            } else if (first3bytes[0] == (byte) 0xFE
                    && first3bytes[1] == (byte) 0xFF) {

                reader = new BufferedReader(new InputStreamReader(in,
                        "utf-16be"));
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFF) {

                reader = new BufferedReader(new InputStreamReader(in,
                        "utf-16le"));
            } else {

                reader = new BufferedReader(new InputStreamReader(in, "GBK"));
            }
            String str = reader.readLine();

            while (str != null) {
                text = text + str + "\n";
                str = reader.readLine();

            }
            reader.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
