package com.gary.garytool;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gary.garytool.adapter.PictureChoseAdapter;
import com.gary.garytool.info.ImageFolder;
import com.gary.garytool.view.ListImageDirPopupWindow;
import com.gary.garytool.view.ListImageDirPopupWindow.OnImageDirSelected;

public class PictureChoseActivity extends Activity implements OnImageDirSelected {

    private ProgressDialog mProgressDialog;
    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     *所有的图片
     */
    private List<String> mImgs;
    private GridView mGridView;
    private PictureChoseAdapter mAdapter;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths=new HashSet<String>();

    private List<ImageFolder> mImageFolders=new ArrayList<>();

    private RelativeLayout mBottomLy;
    private TextView mChooseDir;
    private TextView mImageCount;
    int totalCount=0;

    private int mScreenHeight;

    private ListImageDirPopupWindow mListImageDirPopupWindow;
    private Handler mHandler =new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();
            //为View绑定数据；
            data2View();
            //初始化展示文件夹的popupWindows
            initListDirPopupWindow();
        }
    };

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if(mImgDir==null)
        {
            Toast.makeText(getApplicationContext(),"一张图片没扫描到",Toast.LENGTH_SHORT).show();
            return;
        }

        mImgs=Arrays.asList(mImgDir.list());

        mAdapter=new PictureChoseAdapter(getApplicationContext(),mImgs,R.layout.picture_chose_grid_item,mImgDir.getAbsolutePath());
        mGridView.setAdapter(mAdapter);
        mImageCount.setText(totalCount+"");
    }

    /**
     * 初始化展示文件夹菜单列表的popupWindow
     */
    private void initListDirPopupWindow() {

        mListImageDirPopupWindow=new ListImageDirPopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, (int) (mScreenHeight*0.7),mImageFolders,LayoutInflater.from(getApplicationContext()).inflate(R.layout.picture_chose_list_dir,null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置背景颜色变暗
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=1.0f;
                getWindow().setAttributes(lp);

            }
        });

        //设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_chose);

        DisplayMetrics outMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight=outMetrics.heightPixels;

        initView();
        getImages();
        initEven();

    }

    private void initView() {

    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中，完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {

        if(!Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this,"暂无外部存储",Toast.LENGTH_SHORT).show();
        }

    }

    private void initEven() {

    }

    @Override
    public void selected(ImageFolder folder) {

    }
}
