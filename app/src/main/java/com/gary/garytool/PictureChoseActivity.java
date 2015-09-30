package com.gary.garytool;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gary.garytool.adapter.PictureChoseAdapter;
import com.gary.garytool.info.ImageFolder;
import com.gary.garytool.util.LogUtil;
import com.gary.garytool.view.ListImageDirPopupWindow;
import com.gary.garytool.view.ListImageDirPopupWindow.OnImageDirSelected;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PictureChoseActivity extends Activity implements OnImageDirSelected {

    private static final String TAG = "PictureChoseActivity";
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
    //异步扫描手机图片，扫描完毕回调handle,在UI线程进行数据绑定和展示
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

        mImgs=Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if(filename.endsWith(".jpg")||filename.endsWith(".png")||filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));

        mAdapter=new PictureChoseAdapter(this,mImgs,R.layout.picture_chose_grid_item,mImgDir.getAbsolutePath());
        mGridView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "");
    }

    /**
     * 初始化展示文件夹菜单列表的popupWindow
     */
    private void initListDirPopupWindow() {
        View frame=LayoutInflater.from(this).inflate(R.layout.picture_chose_list_dir, null);

        mListImageDirPopupWindow=new ListImageDirPopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mScreenHeight*0.7),mImageFolders,frame);

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);

            }
        });

        //设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
        //设置选择文件夹菜单的弹出收回动画
        mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
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

    /**
     * 初始化view
     */
    private void initView() {
        mGridView= (GridView) findViewById(R.id.gv_pictures);
        mChooseDir= (TextView) findViewById(R.id.tv_choose_dir);
        mImageCount= (TextView) findViewById(R.id.tv_total_count);
        mBottomLy= (RelativeLayout) findViewById(R.id.rl_picture_bottom);

    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中，完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {

        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this,"暂无外部存储",Toast.LENGTH_SHORT).show();
        }

        //显示进度条
        mProgressDialog=ProgressDialog.show(this,null,"正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                Uri mImageUri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver=PictureChoseActivity.this.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor=mContentResolver.query(mImageUri,null,MediaStore.Images.Media.MIME_TYPE+"=? or "+MediaStore.Images.Media.MIME_TYPE+"=?",new String[]{"image/jpeg","image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext())
                {
                    //获取图片的路径
                    String path=mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    //拿到第一张图片的路径
                    File parentFile=new File(path).getParentFile();
                    if(parentFile==null)
                        continue;
                    String dirPath=parentFile.getAbsolutePath();
                    ImageFolder imageFolder=null;
                    //利用一个hashset防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当的恐怖的）
                    if(mDirPaths.contains(dirPath))
                    {
                        continue;
                    }
                    else
                    {
                        mDirPaths.add(dirPath);
                        //初始化imageFolder
                        imageFolder=new ImageFolder();
                        imageFolder.setDir(dirPath);
                        imageFolder.setFirstImagePath(path);
                    }

                    int picSize=parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if(filename.endsWith(".jpg")||filename.endsWith(".png")||filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;

                    totalCount+=picSize;
                    imageFolder.setCount(picSize);
                    mImageFolders.add(imageFolder);

                    if(picSize>mPicsSize)
                    {
                        mPicsSize=picSize;
                        mImgDir=parentFile;
                    }
                }
                mCursor.close();

                //扫描完成，辅助的hashset也就可以释放内存了
                mDirPaths=null;

                //通知handler 扫描图片完成
                mHandler.sendEmptyMessage(0x110);
            }
        }).start();


    }

    private void initEven() {
        /**
         * 为底部的布局设置点击事件，弹出PopupWindow
         */
        mBottomLy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListImageDirPopupWindow.showAsDropDown(mBottomLy,0,0);

                //设置背景颜色变暗
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=.3f;
                getWindow().setAttributes(lp);
            }
        });

    }

    @Override
    public void selected(ImageFolder folder) {
        mImgDir=new File(folder.getDir());
        mImgs= Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if(filename.endsWith(".jpg")||filename.endsWith(".png")||filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));

        mAdapter=new PictureChoseAdapter(getApplicationContext(),mImgs,R.layout.picture_chose_grid_item,mImgDir.getAbsolutePath());
        mGridView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();       全部刷新，或者下面局部刷新
        mImageCount.setText(folder.getCount()+"张");
        mChooseDir.setText(folder.getName());
        mListImageDirPopupWindow.dismiss();
    }
}
