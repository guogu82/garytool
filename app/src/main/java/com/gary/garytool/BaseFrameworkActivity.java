package com.gary.garytool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gary.garytool.adapter.CommonViewHolder;
import com.gary.garytool.adapter.ListViewAdapter;
import com.gary.garytool.info.Video;
import com.gary.garytool.util.Constants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;


/**
 * 1:PullToRefresh https://github.com/chrisbanes/Android-PullToRefresh
 * 2:Universal-Image-Loader  用于大图片加载 https://github.com/nostra13/Android-Universal-Image-Loader
 * 3:Volley 用于网络请求.缺点-它不适合数据的上传和下载。大文件上传。
 * https://github.com/Tim9Liu9/volley_demo
 * 4：xUtils 用于数据库也支持大文件上传 vs GreenDAO vs LitePal
 */
public class BaseFrameworkActivity extends Activity {

    private PullToRefreshListView mListView;
    private Context mContext;
    private List<Video> mVideos;//用于存放视频列表的集合

    private int mPage = 0;//当前页码
    private int mSize = 10;//每页显示10个视频
    private int mCount = 0;//当前页面有多少个视频

    private VideoListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //第一个Activity加载进来时，我们就获取屏幕的宽度和高度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.displayWidth = displayMetrics.widthPixels;
        Constants.displayHeight = displayMetrics.heightPixels;

        initView();
    }

    private void initView() {
        mListView = (PullToRefreshListView) findViewById(R.id.lv);
        /*
        设置刷新模式：
         可选值为：disabled（禁用下拉刷新）
                   pullFromStart（仅支持下拉刷新）
                   pullFromEnd（仅支持上拉刷新）
                   both(两者都支持)
                   manualOnly(只允许手动触发)
         */
        mListView.setMode(PullToRefreshBase.Mode.BOTH);//上拉下拉都支持
        mListView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不允许刷新，要不然会很乱
        //很重要，刷新是做回调
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //在这里做数据加载的操作
                loadData(refreshView.getScrollY() < 0);
            }
        });

        //首次打开页面时，延时200ms后自动加载数据
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mListView.setRefreshing();
                return true;
            }
        }).sendEmptyMessageDelayed(0, 200);
    }

    private void loadData(boolean direction) {
        //http://172.24.1.49:8081/video/getVideos?apikey=  &typeid=1&page=1
        //TODO:get data by xutils and gson
//        HttpUtils().send(HttpRequest.HttpMethod.GET, Constants.VIDEO_LIST + "typeid=1&", params, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                lv.onRefreshComplete();
//                Log.d("json", "---video的json数据>" + responseInfo.result);
//
//                //解析服务器端的json数据
//                Gson gson = new GsonBuilder().create();
//                ResponseObject<VideoResponse> object = gson.fromJson(responseInfo.result, new TypeToken<ResponseObject<VideoResponse>>() {
//                }.getType());
///*                ResponseObject<VideoResponse> object = new GsonBuilder().create().fromJson(responseInfo.result, new TypeToken<VideoResponse>() {
//                }.getType());*/
//                page = Integer.parseInt(object.getResult().getPage()); //获取服务器端返回来的当前页码
//                count = object.getResult().getCnt(); //获取当前页面有多少个视频
//                Log.d("json","---当前页面的item的个数>"+count);
//                if (direction) { //下拉刷新
//                    videoList = object.getResult().getVideos();  //获取视频信息的集合，并存放
//
//                    videoListViewAdapter = new VideoListViewAdapter(MainActivity.this,videoList);
//                    lv.setAdapter(videoListViewAdapter); //为这个listView绑定适配器
//
//                } else {//尾部加载更多
//                    videoList.addAll(object.getResult().getVideos());
//
//                }
//
//                if (count == 0) { //如果当前页面已经没有视频了，那就告诉客户端，不要再拉了,因为后面没有数据了。
//                    lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                }
//
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                lv.onRefreshComplete();//不管是请求成功还是请求失败，我们都停止加载数据
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }

    }

    private class VideoListViewAdapter extends ListViewAdapter<Video> {
        DisplayImageOptions options;//用于设置图片显示的类

        public VideoListViewAdapter(Context context, List<Video> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(CommonViewHolder holder, Video video) {
            //1：作者的头像
            ImageButton videoAvatar = holder.getView(R.id.ib_video_avatar);
            String imageUrl = "http://t11.baidu.com/it/u=287161387,919419279&fm=76";
            //TODO: 设计模式 建造者模式的使用场景
            //尤其要注意imageScaleType属性哦，这样可以让图片缩放到当前控件的大小。（如果没有这一行，图片大小就是包裹内容；如果加了这一行，图片大小就是匹配当前控件的大小，
            // 因为我在params设置了这个ImageButton的宽度是手机屏幕的宽度，高度是手机屏幕宽度的一半，这样的话，不管网络上的 图片是多大，都能够保证显示出来的图片比例是2:1）
            options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.pull_to_refresh_arrow)    //设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.arrow_left_n)      //设置图片Uri为空或者错误的时候显示的图片
                    .showImageOnFail(R.drawable.arrow_right_w)          //设置图片加载或解码过程发生错误显示的图片
                    .cacheInMemory(true)                                   //设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)                                      //设置下载的图片是否缓存在SD卡中
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)      //图片会缩放到目标大小，很重要，view多大，图片就多大。
                    .build();
            ImageLoader.getInstance().displayImage(imageUrl,videoAvatar,options);

            //2:作者的昵称
            TextView nickName=holder.getView(R.id.tv_nick_name);
            nickName.setText("具体的昵称");

            //3:视频的缩略图
            ImageButton videoImg=holder.getView(R.id.ib_video_img);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (Constants.displayWidth*0.5f+0.5f));
            videoImg.setLayoutParams(params);

            String picUrl="";
            ImageLoader.getInstance().displayImage(picUrl,videoImg,options);

        }
    }
}
