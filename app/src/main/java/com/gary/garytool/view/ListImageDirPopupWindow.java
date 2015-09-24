package com.gary.garytool.view;

import android.view.View;
import android.widget.ListView;

import com.gary.garytool.info.ImageFolder;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 * 用于微信图片选择功能的文件夹选择菜单
 */
public class ListImageDirPopupWindow extends BasePopupWindowForListView<ImageFolder> {
    private ListView mListDir;

    public ListImageDirPopupWindow(int width,int height,List<ImageFolder> datas,View contentView) {
        super(contentView,width,height,true,datas);
    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void init() {

    }


    public interface OnImageDirSelected
    {
        void selected(ImageFolder folder);
    }
    private OnImageDirSelected mImageDirSelected;

    public void setOnImageDirSelected(OnImageDirSelected imageDirSelected)
    {
        mImageDirSelected=imageDirSelected;
    }
}
