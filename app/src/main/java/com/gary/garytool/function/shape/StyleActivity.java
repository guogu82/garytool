package com.gary.garytool.function.shape;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gary.garytool.R;
import com.gary.garytool.util.UtilToast;

/**
 * Created by Gary on 2016/6/30/015.
 * style -->shape selector anim theme
 * shape是用来定义形状的，gradient定义该形状里面为渐变色填充，startColor起始颜色，endColor结束颜色，angle表示方向角度。
 * 当angle=0时，渐变色是从左向右。然后逆时针方向转，当angle=90时为从下往上。
 */
public class StyleActivity extends Activity {

    private LinearLayout layoutShape;

    //---------------style----------------
    //Android的样式一般定义在res/values/styles.xml文件中，其中有一个根元素<resource>，而具体的每种样式定义则是通过<resource>下的子标签<style>来完成，<
    // style>通过添加多个<item>来设置样式不同的属性。
    //另外，样式是可以继承的，可通过<style>标签的parent属性声明要继承的样式，也可通过点前缀 (.) 继承，点前面为父样式名称，后面为子样式名称。
    // 点前缀方式只适用于自定义的样式，若要继承Android内置的样式，则只能通过parent属性声明。


    //------------theme-------------------
    //不过在实际应用中，因为大部分都采用兼容包的，一般都会采用兼容包提供的一套主题：Theme.AppCompat。
    // AppCompat主题默认会根据不同版本的系统自动匹配相应的主题，比如在Android 5.0系统，它会继承Material主题。
    // 不过这也会导致一个问题，不同版本的系统使用不同主题，就会出现不同的体验。因此，为了统一用户体验，最好还是自定义主题。

    //------------shape-----------------------
    //填充：设置填充的颜色
    //<!-- 填充 -->  <solid/>

    //<!-- 间隔 --> <padding/>
    //间隔：设置四个方向上的间隔

    // <!-- 大小 -->
    //大小：设置大小     <size />

    // <!-- 圆角 --> <corners/>
    //圆角：同时设置五个属性，则Radius属性无效
    //android:Radius="20dp"                           设置四个角的半径
    //android:topLeftRadius="20dp"              设置左上角的半径
    //android:topRightRadius="20dp"           设置右上角的半径
    //android:bottomLeftRadius="20dp"      设置右下角的半径
    //android:bottomRightRadius="20dp"    设置左下角的半径

    //<!-- 描边 -->  <stroke/>
    //描边：dashWidth和dashGap属性，只要其中一个设置为0dp，则边框为实现边框
    //android:width="20dp"                               设置边边的宽度
    //android:color="@android:color/black"  设置边边的颜色
    //android:dashWidth="2dp"                         设置虚线的宽度
    //android:dashGap="20dp"                          设置虚线的间隔宽度

    // <!-- 渐变 --> <gradient/>
    //渐变：当设置填充颜色后，无渐变效果。angle的值必须是45的倍数（包括0），仅在type="linear"有效，不然会报错。android:useLevel 这个属性不知道有什么用。

//    --------------selector注意事项----------
//    那么，在使用过程中，有几点还是需要注意和了解的：
//    selector作为drawable资源时，item指定android:drawable属性，并放于drawable目录下；
//    selector作为color资源时，item指定android:color属性，并放于color目录下；
//    color资源也可以放于drawable目录，引用时则用@drawable来引用，但不推荐这么做，drawable资源和color资源最好还是分开；
//    android:drawable属性除了引用@drawable资源，也可以引用@color颜色值；但android:color只能引用@color；
//    item是从上往下匹配的，如果匹配到一个item那它就将采用这个item，而不是采用最佳匹配的规则；所以设置默认的状态，一定要写在最后，如果写在前面，则后面所有的item都不会起作用了。
//
//    另外，selector标签下有两个比较有用的属性要说一下，添加了下面两个属性之后，则会在状态改变时出现淡入淡出效果，但必须在API Level 11及以上才支持：
//    android:enterFadeDuration 状态改变时，新状态展示时的淡入时间，以毫秒为单位
//    android:exitFadeDuration 状态改变时，旧状态消失时的淡出时间，以毫秒为单位

    //---------------anim---------------------
    // 视图动画可以通过xml文件定义，xml文件放于res/anim/目录下，根元素可以为：<alpha>, <scale>, <translate>, <rotate>, 或者<set>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity_shape);
        layoutShape= (LinearLayout) findViewById(R.id.layout_shape);
        layoutShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilToast.showToast(StyleActivity.this,"hello gary");
            }
        });
    }

}
