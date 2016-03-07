package com.gary.garytool.stock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Administrator on 2016/2/23.
 */
public class StockUtil {

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
        try {

            File file = new File(pathName + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file.getPath());
            byte[] bytes = message.getBytes();
            fout.write(bytes);
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
