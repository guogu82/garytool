package com.gary.garytool.util;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


/**
 * 图片加载缓存类，用于仿照微信图片浏览器的功能。在CommonViewHolder里面进行加载
 * Created by Administrator on 2015/9/28.
 */
public class ImageLoader {

    /**
     * 图片缓存的核心类
     */
    private LruCache<String, Bitmap> mLruCache;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;
    /**
     * 线程池的线程数量，默认为1
     */
    private int mThreadCount = 1;

    /**
     * 队列的调度方式
     */
    private Type mType = Type.LIFO;

    /**
     * 队列的调度方式
     */
    public enum Type {
        FIFO, LIFO
    }

    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTasks;
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;

    /**
     * 运行在UI线程的handler，用于给ImageView设置图片
     */
    private Handler mHandler;

    /**
     * 引入一个值为1的信号量,防止mPoolThreadHandler未初始化完成
     */
    private volatile Semaphore mSemaphore = new Semaphore(0);
    /**
     * 引入一个信号量，由于线程池内部也有一个阻塞线程，防止加入任务的速度过快，使LIFO效果不明显
     */
    private volatile Semaphore mPoolSemaphore;

    private static ImageLoader mInstance;


    private ImageLoader(int threadCount, Type type) {
        init(threadCount, type);
    }

    private void init(int threadCount, Type type) {
        //loop thread
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        //取到UI线程的任务添加的消息，去任务队列拿任务，如果没有信号量了，就阻塞
                        mThreadPool.execute(getTask());
                        try {
                            mPoolSemaphore.acquire();
                        } catch (InterruptedException e) {

                        }
                    }
                };

                //释放一个信号量
                mSemaphore.release();
                Looper.loop();
            }
        };

        mPoolThread.start();

        //获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mPoolSemaphore = new Semaphore(threadCount);
        mTasks = new LinkedList<Runnable>();
        mType = type == null ? Type.FIFO : type;

    }

    /**
     * 加载图片
     *
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView) {
        imageView.setTag(path);
        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    ImageView imageView = holder.imageView;
                    Bitmap bm = holder.bitmap;
                    String path = holder.path;
                    if (imageView.getTag().toString().equals(path)) {
                        imageView.setImageBitmap(bm);
                    }
                }
            };
        }

        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null) {
            ImgBeanHolder holder = new ImgBeanHolder();
            holder.bitmap = bm;
            holder.imageView = imageView;
            holder.path = path;
            Message message = Message.obtain();
            message.obj = holder;
            mHandler.sendMessage(message);
        } else {
            //在UI线程向队列添加任务
            addTask(new Runnable() {
                @Override
                public void run() {
                    //下面是任务要执行的内容
                    ImageSize imageSize = getImageViewWidth(imageView);
                    int reqWidth = imageSize.width;
                    int reqHeight = imageSize.height;

                    Bitmap bm = decodeSampledBitmapFromResource(path, reqWidth, reqHeight);
                    addBitmapToLruCache(path, bm);
                    ImgBeanHolder holder = new ImgBeanHolder();
                    holder.bitmap = getBitmapFromLruCache(path);
                    holder.imageView = imageView;
                    holder.path = path;
                    Message message = Message.obtain();
                    message.obj = holder;
                    mHandler.sendMessage(message);
                    mPoolSemaphore.release();
                }
            });
        }
    }

    /**
     * 添加一个任务
     *
     * @param runnable
     */
    private synchronized void addTask(Runnable runnable) {
        try {
            //请求信号量，防止mPoolThreadHandler 为null
            if (mPoolThreadHandler == null)
                mSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //向队列添加任务
        mTasks.add(runnable);
        //发送任务添加的消息给守护线程的handler
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    /**
     * 取出一个任务
     *
     * @return
     */
    private synchronized Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTasks.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTasks.removeLast();
        }
        return null;
    }

    /**
     * 单例获得该实例对象
     *
     * @param threadCount
     * @param type
     * @return
     */
    public static ImageLoader getInstance(int threadCount, Type type) {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(threadCount, type);
                }
            }
        }
        return mInstance;
    }

    private ImageSize getImageViewWidth(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        //Get actual image width
        int width = params.width == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView.getWidth();
        if (width <= 0) {
            //Get layout width parameter
            width = params.width;
        }
        if (width <= 0) {
            //Get maxWidth parameter
            width = getImageViewFieldValue(imageView, "mMaxWidth");
        }
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }

        int height = params.height == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView.getHeight();
        if (height <= 0)
            height = params.height;
        if (height <= 0)
            height = getImageViewFieldValue(imageView, "mMaxHeight");
        if (height <= 0)
            height = displayMetrics.heightPixels;

        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param path
     */
    private Bitmap getBitmapFromLruCache(String path) {

        return mLruCache.get(path);
    }

    /**
     * 往LruCache中添加一张图片
     *
     * @param path
     * @param bm
     */
    private void addBitmapToLruCache(String path, Bitmap bm) {
        if (getBitmapFromLruCache(path) == null) {
            if (bm != null)
                mLruCache.put(path, bm);
        }
    }

    /**
     * 根据计算的inSampleSize,得到压缩后图片
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
        //第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(path,options);
        //调用上面定义的方法计算inSampleSize值
        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);
        //使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds=false;
        Bitmap bitmap=BitmapFactory.decodeFile(path,options);
        return bitmap;
    }

    /**
     * 计算inSampleSize,用于压缩图片
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //源图片的宽度
        int width=options.outWidth;
        int height=options.outHeight;
        int inSampleSize=1;
        if(width>reqWidth||height>reqHeight)
        {
            //计算出实际宽度和目标宽度的比率
            int widthRatio=Math.round(width*1.0f/reqWidth);
            int heightRatio=Math.round(height*1.0f/reqHeight);
            inSampleSize=Math.max(widthRatio,heightRatio);
        }
        return inSampleSize;
    }


    private class ImgBeanHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }

    private class ImageSize {
        int width;
        int height;
    }

    /**
     * 反射获得ImageView设置的最大宽度和高度
     * @param object
     * @param fieldName
     * @return
     */
    private int getImageViewFieldValue(Object object , String fieldName) {
        int value=0;
        try {
            Field field =ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue= (int) field.get(object);
            if(fieldValue>0&&fieldValue<Integer.MAX_VALUE)
            {
                value=fieldValue;
            }
        } catch (Exception e) {


        }
        return value;
    }

}
