package com.gary.garytool.business.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gary.garytool.R;
import com.gary.garytool.util.Util;
import com.gary.garytool.view.PictureChoseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 * @author gary guo
 */
public class PuzzleUtil {

    public static Bitmap getPuzzleImage(Context context) {
        Bitmap bitmap;
        String filePath= Util.getSDPath()+"/touristguide/puzzle_sd_image.jpg";
        File mFile=new File(filePath);
        //若该文件存在
        if (mFile.exists()) {
            bitmap= BitmapFactory.decodeFile(filePath);
        }
        else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.puzzle_image);
        }
        return bitmap;
    }

    /**
     *
     * @param bitmap 传入bitmap
     * @param piece 切成piece*piece块
     * @return List<ImagePiece>
     */
    public static List<ImagePiece> splitImage(Bitmap bitmap,int piece)
    {
        List<ImagePiece>  imagePieces=new ArrayList<>();
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();

        int pieceWidth=Math.min(width,height)/piece;
        for(int i=0;i<piece;i++)
        {
            for(int j=0;j< piece;j++)
            {
                ImagePiece imagePiece=new ImagePiece();
                imagePiece.setIndex(i*piece+j);

                int x=j*pieceWidth;
                int y=i*pieceWidth;

                imagePiece.setBitmap(Bitmap.createBitmap(bitmap,x,y,pieceWidth,pieceWidth));
                imagePieces.add(imagePiece);
            }
        }
        return imagePieces;
    }
}
