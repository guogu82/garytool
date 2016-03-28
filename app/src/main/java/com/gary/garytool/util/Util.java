package com.gary.garytool.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gary.garytool.R;
import com.gary.garytool.business.guessmusic.IAlertDialogButtonListener;
import com.gary.garytool.business.guessmusic.MyPlayer;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gary on 2015/10/27.
 * @author gary guo
 */
public class Util {

    //通过id 获取 view
    public static View getView(Context context,int layoutId)
    {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(layoutId,null);
        return layout;
    }

    /**
     * 界面跳转
     * @param context
     * @param desti
     */
    public static void startActivity(Context context,Class desti)
    {
        Intent intent=new Intent();
        intent.setClass(context, desti);
        context.startActivity(intent);

        //关闭当前的Activity
        ((Activity)context).finish();
    }

    private static AlertDialog mAlertDialog;
    /**
     * 显示自定义对话框
     * @param context
     * @param message
     * @param listener 确认按钮点击事件回调
     */
    public static void showDialog(final Context context,String message, final IAlertDialogButtonListener listener)
    {
        View dialogView=null;
        //TODO:静态方法可以调用其它对象的非静态字段，不过要创建该对象实例
        //AlertDialog.Builder builder=new AlertDialog.Builder(context);
        //如果有背景框，可以用下面的主题来控制
        AlertDialog.Builder builder=new AlertDialog.Builder(context,R.style.GuessMusic_Theme_Transparent);
        dialogView=getView(context, R.layout.guess_music_dialog_view);
        ImageButton btOk= (ImageButton) dialogView.findViewById(R.id.bt_dialog_ok);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAlertDialog!=null)
                {
                    mAlertDialog.cancel();
                }

                if(listener!=null) {
                    listener.onClick();
                }
                //播放音效
                MyPlayer.playTone(context,MyPlayer.INDEX_STONE_ENTER);
            }
        });
        ImageButton btCancel= (ImageButton) dialogView.findViewById(R.id.bt_dialog_cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAlertDialog!=null)
                {
                    mAlertDialog.cancel();
                }
                //播放音效
                MyPlayer.playTone(context,MyPlayer.INDEX_STONE_CANCEL);
            }
        });
        TextView tv_message= (TextView) dialogView.findViewById(R.id.tv_dialog_message);
        tv_message.setText(message);
        //为dialog设置view
        builder.setView(dialogView);
        mAlertDialog=builder.create();
        mAlertDialog.show();


    }


    /*
    *通过反射打造可以存储任何对象的万能SharedPreferences
    * code demo
    * //创建一个内对象
    *Person p = new Person("zhangsan",23,175.0f,false,"001");
    * //将p存储到SharedPreferences的“zhangqi”名下
    * Util.putObjectToShare("zhangqi",p,context);
    * //从SharedPreferences的“zhangqi”名下取出之前存储过的Person实例
    *  Person savedPerson = Util.getObjectFromShare("zhangqi",Person.class,context);
     */

    /**
     * 根据传入的对象，取出其中用public修饰符修饰的属性
     * @param clazz 想要拿到属性的类的字节码文件
     * @return 所有用pulic修饰的属性的一个list表
     */
    public static <T> List<Field> getPublicFields(Class<?> clazz){
        if (clazz.equals(Object.class)) {
            return null;
        }
        //用来存储clazz中用public修饰的属性的list
        List<Field> list = new ArrayList<Field>();
        //获得clazz中所有用public修饰的属性
        Field[] fields = clazz.getFields();
        //将fields加入到list中
        for(int i=0 ; i<fields.length ; i++){
            list.add(fields[i]);
        }
        return list;
    }

    public static void putObjectToShare(String shareName , Object obj,Context context){
        //获得SharedPreferences实例
        SharedPreferences sharedPreferences = context.getSharedPreferences(shareName, Activity.MODE_PRIVATE);
        //获得Editor
        SharedPreferences.Editor edit = sharedPreferences.edit();
        //存储数据之前先将之前的旧数据清掉
        edit.clear();
        //调用commit提交数据(这里为了清掉数据)
        edit.commit();

        List<Field> publicFields = getPublicFields(obj.getClass());
        for(Field f : publicFields){
            String name = f.getName();
            try {
                //获得当前属性的类型和值
                //类型的话如果是基本类型，会自动装箱
                Object type = f.get(obj);
                //判断各种类型，调用各种类型的put方法将数据存储进去
                if (type instanceof String) {
                    edit.putString(name, (String) type);
                }else if (type instanceof Integer) {
                    edit.putInt(name, (Integer) type);
                }else if (type instanceof Float) {
                    edit.putFloat(name, (Float) type);
                }else if (type instanceof Long) {
                    edit.putLong(name, (Long) type);
                }else if (type instanceof Boolean) {
                    edit.putBoolean(name, (Boolean) type);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //调用commit，提交数据
            edit.commit();
        }
    }

    public static <T> T getObjectFromShare(String shareName , Class<T> clazz,Context context){
        //获得SharedPreferences实例
        SharedPreferences sharedPreferences = context.getSharedPreferences(shareName, Activity.MODE_PRIVATE);
        //T是一个泛型，根据clazz不同而不同
        T t = null;
        try {
            //获得sharedPreferences中所有的数据，数据为键值对保存在map中
            Map<String,?> map = sharedPreferences.getAll();
            //调用getPublicFields方法得到clazz中所有的公有属性
            List<Field> publicFields = getPublicFields(clazz);
            //如果两者都不为空的话
            if (map.size()>0 && publicFields.size()>0) {
                //将T实例化出来
                t = clazz.newInstance();
                //遍历map中所有的键值对
                for(Map.Entry<String,?> entry : map.entrySet()){
                    //map中的键
                    String key = entry.getKey();
                    //map中的值
                    Object value = entry.getValue();
                    //遍历clazz中的所有公有属性
                    for(Field field : publicFields){
                        //获得属性名
                        String name = field.getName();
                        //如果属性名与键相同
                        if (name.equalsIgnoreCase(key)) {
                            //相当于给对象T中的属性field赋值，值为value
                            field.set(t, value);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //整个遍历结束后，我们的对象中的属性也都有值了
        return t;
    }

    /**
     * sp to px 把当前字体的sp转换成当前的屏幕像素
     *
     * @param context
     * @param sp
     * @return
     */
    public static int spToPx(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * dp to px 把dp转换成当前的屏幕像素
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 将字节数转化为MB
     *
     * @param size
     * @return
     */
    private String byteToMB(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size > kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }


    /*以下的功能很简单，计算你的缓存总大小，不管内部缓存还是外部缓存，和清空缓存，包括内部和外部的缓存一起清空
    * 当你在项目中需要查下缓存大小，就使用getTotalCacheSize(Context)方法，清空缓存，就使用clearAllCache(Context)方法*/
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

}
