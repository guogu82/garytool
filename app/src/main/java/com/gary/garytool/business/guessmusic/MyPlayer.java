package com.gary.garytool.business.guessmusic;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/28.
 * @author gary guo
 */
public class MyPlayer {

    //音效的索引
    public static final int INDEX_STONE_ENTER=0;
    public static final int INDEX_STONE_CANCEL=1;
    public static final int INDEX_STONE_COIN=2;
    //音效的文件名
    private static final String[] SONG_NAMES={"guess_music_enter.mp3","guess_music_cancel.mp3","guess_music_coin.mp3"};
    //音效
    private static MediaPlayer[] mToneMediaPlay=new MediaPlayer[SONG_NAMES.length];

    /**
     * 播放声音效果
     * @param context
     * @param index
     */
    public static void playTone(Context context,int index)
    {
        //加载声音
        AssetManager assetManager=context.getAssets();
        if (mToneMediaPlay[index] == null) {
            mToneMediaPlay[index]=new MediaPlayer();
            try {
                AssetFileDescriptor assetFileDescriptor=assetManager.openFd(SONG_NAMES[index]);
                mToneMediaPlay[index].setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                mToneMediaPlay[index].prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mToneMediaPlay[index].start();

    }

    //歌曲播放
    private static MediaPlayer mMusicMediaPlayer;
    public static void playSong(Context context,String fileName)
    {
        if(mMusicMediaPlayer==null)
        {
            mMusicMediaPlayer=new MediaPlayer();
        }
        //强制重置，更换音乐文件,所以下面要重新prepare。
        mMusicMediaPlayer.reset();

        //加载声音文件
        AssetManager assetManager=context.getAssets();
        try {
            AssetFileDescriptor fileDescriptor=assetManager.openFd(fileName);
            mMusicMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mMusicMediaPlayer.prepare();
            mMusicMediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopTheSong(Context context)
    {
        if(mMusicMediaPlayer!=null)
        {
            mMusicMediaPlayer.stop();
        }
    }
}
