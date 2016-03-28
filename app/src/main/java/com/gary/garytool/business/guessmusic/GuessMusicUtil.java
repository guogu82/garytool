package com.gary.garytool.business.guessmusic;

import android.content.Context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/3/28.
 */
public class GuessMusicUtil {


    public static final String FILE_NAME_SAVE_DATA="guessmusicdata.dat";
    public static final int INDEX_LOAD_DATA_STAGE=0;
    public static final int INDEX_LOAD_DATA_COIN=1;

    /**
     * 保存
     * @param context
     * @param stageIndex
     * @param coins
     */
    public static void saveData(Context context,int stageIndex,int coins)
    {
        //节点流
        FileOutputStream fos=null;
        try {
            fos=context.openFileOutput(FILE_NAME_SAVE_DATA,Context.MODE_PRIVATE);
            //处理流
            DataOutputStream dos=new DataOutputStream(fos);
            dos.writeInt(stageIndex);
            dos.writeInt(coins);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try
            {
            if(fos!=null){
                fos.close();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *读取游戏数据
     * @param context
     * @return
     */
    public static int[] loadData(Context context)
    {
        int[] datas={-1,Const.TOTAL_COINS};
        FileInputStream fis=null;
        try {
            fis=context.openFileInput(FILE_NAME_SAVE_DATA);
            DataInputStream dis=new DataInputStream(fis);
            datas[INDEX_LOAD_DATA_STAGE]=dis.readInt();
            datas[INDEX_LOAD_DATA_COIN]=dis.readInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }
}
