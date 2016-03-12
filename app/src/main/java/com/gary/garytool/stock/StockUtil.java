package com.gary.garytool.stock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Administrator on 2016/2/23.
 */
public class StockUtil {

    public static final String CHARACTER = "UTF-8";
    public static final String SEPARATOR_ATTR_TAG = "\t";
    public static final String SEPARATOR_OBJECT_TAG = "\n";

    public static void saveFileDay(Context context,String key,String value){

        SharedPreferences sp =context.getSharedPreferences("config.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getFileDay(Context context,String key){
            SharedPreferences sp =context.getSharedPreferences("config.txt",  Context.MODE_PRIVATE);
            return sp.getString(key, "");
    }


    // 写在/mnt/sdcard/目录下面的文件
    public static void writeSDFile(String pathName,String fileName, String message) {
        writeSDFile(pathName,fileName,message,false);
    }

    public   static   void  writeSDFile(String pathName,String fileName, String message,boolean isAdd) {
        try {

            File file = new File(pathName + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }



            StringBuilder before=new StringBuilder();
            //读取旧数据
            if(isAdd)
            {
                InputStreamReader   inputStreamReader = new InputStreamReader(new FileInputStream(file), StockUtil.CHARACTER);
                BufferedReader   reader= new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    if(line.contains("4-2"))
                        continue;
                    before.append(StockUtil.SEPARATOR_OBJECT_TAG+line );
                }
                reader.close();
                inputStreamReader.close();
            }

            FileOutputStream fout= new FileOutputStream(file.getPath());

            //写入新数据
            fout.write(message.getBytes());

            //写入旧数据
            if(isAdd&&before.length()>0)
            {
                fout.write(before.toString().getBytes());
            }


            fout.close();
        } catch (Exception e) {

        }

    }

    public static String getSDPath(String dir){

        File sdDir;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if(!sdCardExist) {
            return "";
        }

        sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        File fileDir = new File(sdDir+dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }


        return fileDir.getPath();
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    public static String getYesterday(String today) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date d = null;
        try {
            d = df.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, -1);  //减1天
        return df.format(cal.getTime());
    }
}
