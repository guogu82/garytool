package com.gary.garytool.util;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/3/19.
 */
public class DBUtil {
    /**
     * 利用泛型，用实体类建表的方法
     * @param clazz 实体类
     * @param tableName 表名
     * @return sql建表语句
     * demo
     * private <T> String createTable(Class<T> clazz)
     * {    return createTable(clazz, clazz.getSimpleName());    }
     */
     public static  <T> String createTable(Class<T> clazz , String tableName){
        //实例化一个容器，用来拼接sql语句
        StringBuffer sBuffer = new StringBuffer();
        //sql语句，第一个字段为_ID 主键自增，这是通用的，所以直接写死
        sBuffer.append("create table if not exists "+ tableName + " "+
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
        //得到实体类中所有的公有属性
        Field[] fields = clazz.getFields();
        //遍历所有的公有属性
        for(Field field : fields){
            //如果属性不为_id的话，说明是新的字段
            if (!field.getName().equals("_id")) {
                //得到属性的基本数据类型
                String type = field.getType().getSimpleName();
                //如果是String类型的属性，就把字段类型设置为TEXT
                if (type.equals("String")) {
                    sBuffer.append(field.getName()+" TEXT,");
                    //如果是int类型的属性，就把字段类型设置为INTEGER
                }else if (type.equals("int")) {
                    sBuffer.append(field.getName()+" INTEGER,");
                }
            }
        }
        //将最后的逗号删除
        sBuffer.deleteCharAt(sBuffer.length()-1);
        //替换成); 表明sql语句结束
        sBuffer.append(");");
        //返回这条sql语句
        return sBuffer.toString();
    }
}
