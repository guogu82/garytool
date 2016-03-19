package com.gary.garytool.view.pulltorefresh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.gary.garytool.R;

import java.util.ArrayList;
import java.util.List;


public class PullToRefreshListViewActivity extends Activity {
    ListView mListView;
    ArrayAdapter mAdapter;
    List<String> mDatas;
    LayoutInflater mInfrater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_list_view);
        //http://blog.csdn.net/lmj623565791/article/details/38238749 --鸿洋PullToRefresh demo
        //当然了，pull-to-refresh在xml中还能定义一些属性：
        //ptrMode，ptrDrawable，ptrAnimationStyle这三个上面已经介绍过。
        //ptrRefreshableViewBackground 设置整个mPullRefreshListView的背景色
        //ptrHeaderBackground 设置下拉Header或者上拉Footer的背景色
        //ptrHeaderTextColor 用于设置Header与Footer中文本的颜色
        //ptrHeaderSubTextColor 用于设置Header与Footer中上次刷新时间的颜色
        //ptrShowIndicator如果为true会在mPullRefreshListView中出现icon，右上角和右下角，挺有意思的。
        //ptrHeaderTextAppearance ， ptrSubHeaderTextAppearance分别设置拉Header或者上拉Footer中字体的类型颜色等等。
        //ptrRotateDrawableWhilePulling当动画设置为rotate时，下拉是是否旋转。
        //ptrScrollingWhileRefreshingEnabled刷新的时候，是否允许ListView或GridView滚动。觉得为true比较好。
        //ptrListViewExtrasEnabled 决定了Header，Footer以何种方式加入mPullRefreshListView，true为headView方式加入，就是滚动时刷新头部会一起滚动。
        //最后2个其实对于用户体验还是挺重要的，如果设置的时候考虑下~。其他的属性自己选择就好。

        mDatas=new ArrayList<>();
        mDatas.add("下拉加载");
        mDatas.add("上拉下拉都加载");
        mDatas.add("GradView");
        mListView= (ListView) findViewById(R.id.lv);
        mInfrater=LayoutInflater.from(this);
        mAdapter=new ArrayAdapter(this,-1,mDatas){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View item=mInfrater.inflate(R.layout.listview_item,null);
                Button text= (Button) item.findViewById(R.id.list_item);
                text.setText(mDatas.get(position).toString());
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(position);
                    }
                });
                return item;
            }


        };
        mListView.setAdapter(mAdapter);
    }
    //仅仅实现了下拉刷新数据
    private static final int BASE=0;
    //实现了上拉和下拉刷新数据
    private static final int BOTH=1;
    //实现gradView
    private static final int GRADVIEW=2;
    private void onItemClick(int position) {
        Intent intent;
        switch (position)
        {
            case BASE:
                intent = new Intent(PullToRefreshListViewActivity.this, PullToRefreshBaseActivity.class);
                startActivity(intent);
                break;
            case BOTH:
                intent =new Intent(PullToRefreshListViewActivity.this,PullToRefreshBothActivity.class);
                startActivity(intent);
                break;
            case GRADVIEW:
                intent =new Intent(PullToRefreshListViewActivity.this,PullToRefreshGradViewActivity.class);
                startActivity(intent);
                break;
        }
    }


}
