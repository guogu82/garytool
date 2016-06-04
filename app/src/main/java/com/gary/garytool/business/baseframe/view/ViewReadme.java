package com.gary.garytool.business.baseframe.view;

/**
 * Created by Gary on 2016/6/4/004.
 */
public class ViewReadme {
    //XML文件布局的命名规范 全小写 单词用_分开
    //demo： station_order_history_activity.xml
    //demo： station_order_history_item.xml

    //控件的id命名规范 简称前缀
    //demo： Button --> btLogin
    //demo： TextView --> tvName
    //demo： EditText -->etUserNumber
    //demo： ListView --> lvOrder
    //demo： LinearLayout --> layoutOrder (所有类型的布局容器都只用layout前缀，如FrameLayout TableLayout RelativeLayout)

    //类命名规范 所有字母大写开头
    //demo： OrderHelper

    //类成员命名规范
    //1:是页面控件 采用前缀
    //demo： Button --> btLogin
    //2:成员变量，采用前缀m
    //demo： DisplayMetrics mDisplayMetrics

    //类临时变量命名规范,用小写开头的全名来表示
    //demo： List<UserName> userName

    //类方法 必须是动词+名词，首字母小写
    //demo： initView
    //demo： getHistoryOrders

    //常量 全大写，词之间用_分开
    //demo： static final String BASE_URL="http://test.jujia.ctauto.cn/";


}
