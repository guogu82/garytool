package com.gary.garytool.adapter;

import android.content.Context;
import com.gary.garytool.view.CommonAdapter;
import com.gary.garytool.view.ViewHolderM;
import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 * 配合微信图片选择功能的创建的数据适配器
 */
public class PictureChoseAdapter extends CommonAdapter<String> {
    /**
     * 文件夹路径
     */
    private String mDirPath;
    public PictureChoseAdapter(Context context, List<String> dataList, int layoutId,String dirPath) {
        super(context, layoutId, dataList);
        mDirPath=dirPath;
    }



    @Override
    public void convert(ViewHolderM holder, String item) {

    }
}
