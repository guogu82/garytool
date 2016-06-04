package com.gary.garytool.business.baseframe.model;

/**
 * Created by Gary on 2016/6/4/004.
 */
public class ModelReadme {
    //model 层的命名规范
    //成员变量不需前缀m
    //要有get set键值对

    //DTO：数据传输对象 --目前我们畅途使用这个与后台交互
    //BO 业务逻辑处理对象，在业务逻辑复杂的使用。
    //PO：持久对象（SQLite 使用）可以严格对应数据库表，一张表对映一个PO。
    //DAO：数据访问对象——同时还有DAO模式
    //QO：查询对象(少用，只有查询关联很多很重的时候使用)
    //VO：value object值对象、view object视图对象(android 较为少用)

}
