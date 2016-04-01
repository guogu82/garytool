package com.gary.garytool.business.tourist;

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

    public static ScenicInfo sCurrentSpotDetailScenicInfo;

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
            ScenicInfo info=new ScenicInfo("清晖园",0.0f,0.0f,false,0,-1);
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
                                    i, 0);
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
