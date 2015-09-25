package com.gary.garytool.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gary.garytool.R;
import com.gary.garytool.info.ImageFolder;
import com.gary.garytool.volley.BitmapCache;

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
    public void initViews() {
        mListDir= (ListView) findViewById(R.id.lv_list_dir);
        mListDir.setAdapter(new CommonAdapter<ImageFolder>(context, R.layout.picture_chose_list_dir_item, mDatas) {
            @Override
            public void convert(ViewHolderM holder, ImageFolder item) {
                holder.setText(R.id.tv_dir_item_name, item.getName());
                holder.setImageByUrl(R.id.iv_dir_item_image, item.getFirstImagePath());
                holder.setText(R.id.tv_dir_item_count, item.getCount() + "张");
            }
        });

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


    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mImageDirSelected!=null)
                {

                }
            }
        });

    }

    @Override
    public void init() {

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {

    }


}
