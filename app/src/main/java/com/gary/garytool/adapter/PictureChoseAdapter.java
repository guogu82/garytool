package com.gary.garytool.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.gary.garytool.R;
import com.gary.garytool.view.CommonAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 * 配合微信图片选择功能的创建的数据适配器
 */
public class PictureChoseAdapter extends CommonAdapter<String> {
    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static List<String> mSelectedImage=new LinkedList<>();
    /**
     * 文件夹路径
     */
    private String mDirPath;
    public PictureChoseAdapter(Context context, List<String> dataList, int layoutId,String dirPath) {
        super(context, layoutId, dataList);
        mDirPath=dirPath;
    }



    @Override
    public void convert(CommonViewHolder holder, String item) {
        //设置no_pic
        holder.setImageResource(R.id.iv_image,R.drawable.pictures_no);
        //设置no_selected
        holder.setImageResource(R.id.ib_item_select, R.drawable.picture_unselected);
        //设置图片
        holder.setImageByUrl(R.id.iv_image,mDirPath+"/"+item);

        final ImageView mImageView=holder.getView(R.id.iv_image);
        final ImageView mSelect=holder.getView(R.id.ib_item_select);

        final String fullPath=mDirPath+"/"+item;

        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mImageView.setOnClickListener(new View.OnClickListener() {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {
                //已经选择该图片
                if(mSelectedImage.contains(fullPath))
                {
                    mSelectedImage.remove(fullPath);
                    mSelect.setImageResource(R.drawable.picture_unselected);
                    mImageView.setColorFilter(null);
                }
                else //未选择该图片
                {
                    mSelectedImage.add(fullPath);
                    mSelect.setImageResource(R.drawable.pictures_selected);
                    //变暗
                    mImageView.setColorFilter(Color.parseColor("#77000000"));
                }
            }
        });

        if(mSelectedImage.contains(fullPath))
        {
            mSelect.setImageResource(R.drawable.pictures_selected);
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        }
    }
}
