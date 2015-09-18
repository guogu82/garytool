package com.gary.garytool.util;

/**
 * Created by Administrator on 2015/9/15.
 */
public class Constants {
    public static int displayWidth;
    public static int displayHeight;


    //TODO：note volley 的常量定义方式
    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
    }
    /* http://www.ibm.com/developerworks/cn/java/l-java-interface/
    由于java interface中声明的字段在编译时会自动加上static final的修饰符，即声明为常量。
    因而interface通常是存放常量的最佳地点。然而在java的实际应用时却会产生一些问题。

    问题的起因有两个，第一，是我们所使用的常量并不是一成不变的，而是相对于变量不能赋值改变。
    例如我们在一个工程初期定义常量∏＝3.14，而由于计算精度的提高我们可能会重新定义∏＝3.14159，
    此时整个项目对此常量的引用都应该做出改变。第二，java是动态语言。与c++之类的静态语言不同,
    java对一些字段的引用可以在运行期动态进行，这种灵活性是java这样的动态语言的一大优势。
    也就使得我们在java工程中有时部分内容的改变不用重新编译整个项目，而只需编译改变的部分重新发布就可以改变整个应用。
    */
    //TODO：note volley 的枚举定义方式
    public static enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE;

        private Priority() {
        }
    }
}
