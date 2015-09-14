本文档记录了android开发中长期要看的备忘。

 android.os
 android.app
 android.content
 android.view
 android.widget
 android.util
 android.text

布局文件 命名规范
activity_bottom_menu_with_fragment

布局文件 里面的view id命名规范
ImageView iv_about_time

资源文件命名规范
类型_模块_描述_状态
Selector selector_skin_tabbar_icon_about

类  命名规范
有类型的通过最后面的代表类型
BottomMenuWithFragmentActivity

类成员命名规范
多次出现的成员变量，通过前缀区分,只是view对象有前缀
ImageView iv_about_time
类里面基本是唯一的变量，用小写开头的全名来表示
DisplayMetrics mDisplayMetrics

类临时变量命名规范
ImageView aboutTimeInDay

类方法
必须是动词+名词
initView

我的快捷键
ctrl+d 复制一行
shift+enter 任意地方换行并新开一行
ctrl+w 选中当前单词
alt+enter 修复当前问题，view类型强转
先敲“/”在敲两个**，然后回车  为方法添加注释
ctrl+q 查看方法的文档

ctrl+TAB  编辑器文档切换
alt+左右箭头 编辑器文档左右切换
ctrl+insert 生产get set 构造函数 重构方法
ctrl+shift+enter  自动补充错误
ctrl+alt+o  自动清理import
alt+enter 错误修正提示列表
ctrl+alt+T  包裹if或者try
ctrl+J   生成for循环

adb 启动失败
netstat -aon|findstr "5037"      （netstat -aon|findstr "5037" | more--分屏）
 TCP    127.0.0.1:5037         0.0.0.0:0              LISTENING       18212
tasklist|findstr "18212"
 kadb.exe          18212 Console                    1      1,260 K
 1.关闭kadb.exe
 2.adb kill-server
 3.adb start-server
 4.重启Eclipse

 设计模式的使用场景
 1： 装饰者--io stream
 2：观察者--回调
 3：单例--全局变量
 4：建造者--按需构造实例，例如dialog，参数太多了。
 5：模板方法--就是抽取共同代码，父类与子类

 软件版本规则
 V3.5.12   左边是大版本，中间是策划评审通过的版本，右边是策划版本

 我的框架主体
volley  OkHttp Gson universal-image-loader DiskLruCache xUtils GreenDAO
PullToRefresh



