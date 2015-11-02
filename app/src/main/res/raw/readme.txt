本文档记录了android开发中长期要看的备忘。

 android.os
 android.app
 android.content
 android.view
 android.widget
 android.util
 android.text

--------------------------命名规范-----------------------------
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

-------------------------我的快捷键---------------------
ctrl+d 复制一行
shift+enter 任意地方换行并新开一行
ctrl+w 选中当前单词
alt+enter 修复当前问题，view类型强转
先敲“/”在敲两个**，然后回车  为方法添加注释
ctrl+q 查看方法的文档
ctrl+shift+f12 代码编辑区全屏

ctrl+TAB  编辑器文档切换
alt+左右箭头 编辑器文档左右切换
ctrl+insert 生产get set 构造函数 重构方法
ctrl+shift+enter  自动补充错误
ctrl+alt+o  自动清理import
alt+enter 错误修正提示列表
ctrl+alt+T  包裹if或者try
ctrl+J   生成for循环
ctrl+P 显示方法的参数
f11  将当前位置添加到书签中或者从书签中移除。
shift+f11 显示有哪些书签

------------------adb 启动失败-----------------
netstat -aon|findstr "5037"      （netstat -aon|findstr "5037" | more--分屏）
 TCP    127.0.0.1:5037         0.0.0.0:0              LISTENING       18212
tasklist|findstr "18212"
 kadb.exe          18212 Console                    1      1,260 K
 1.关闭kadb.exe
 2.adb kill-server
 3.adb start-server
 4.重启Eclipse

 ----------------------------git----------------------------
 git add --al
 git commit -m ""
 git push
 git pull
 3个人协同开发，那么用Git同步代码，会将模块中的大量iml文件同步，每次都会提交和更新，一个一个的去忽略他们，显然是最笨的方法。
 那些ide文件和iml文件根本不需要同步，如何设置呢？？
git update-index --assume-unchanged $(git ls-files *.iml   idea/*.*)

 ---------------------设计模式的使用场景--------------------
 1： 装饰者--io stream
 2：观察者--回调
 3：单例--全局变量
 4：建造者--按需构造实例，例如dialog，参数太多了。
 5：模板方法--就是抽取共同代码，父类与子类

-------------------------软件版本规则---------------------------
 V3.5.12   左边是大版本，中间是策划评审通过的版本，右边是策划版本

 -----------------我的框架主体-----------------
volley Gson xUtils PullToRefresh LruCache

--------------------------我的技术发展规划。----------------------
第1年内熟悉安卓开发细节。控件的熟悉，框架的使用。

第2年改动开源框架，重构应用场景。
不管team有多少人，一开始写代码尽量写伪代码(TODO)，把整体逻辑写完，TODO的地方慢慢加上，一个Activity就写完了。
而且如果不做了，或者让别人来做，能很快顺着你的思路继续下去。
如果你有很好的画流程图的习惯。把它们变成TODO试试。你会发现代码写起来很轻松。把写代码当成一种享受。

第3年用mindmanager画自己的想法，用axure，mockup设计原型，用Visio，gliffy设计框架流程，然后用代码实现。
在产品的周期里，coding是最不起眼的角色，伪脑力的体力劳动者。如何让自己折腾出这个圈子，站在更高的角度看自己。


----------------------常用语句片段----------------------
TextUtils.isEmpty() 简单的工具类,用于检测是否为空。
Build.VERSION_CODES 这个标明了当前的版本号,在处理兼容性问题的时候经常会用到.点进去可以看到各个版本的不同特性。
LayoutInflater.from() 顾名思义,用于Inflate一个layout,参数是layout的id.这个经常写Adapter的人会用的比较多。
Context.getCacheDir() 获取缓存数据文件夹的路径,很简单但是知道的人不多,这个路径通常在SD卡上(这里的SD卡指的是广义上的SD卡,包括外部存储和内部存储)Adnroid/data/您的应用程序包名/cache/ 下面.
测试的时候,可以去这里面看是否缓存成功.缓存在这里的好处是:不用自己再去手动创建文件夹,不用担心用户把自己创建的文件夹删掉,在应用程序卸载的时候,这里会被清空,使用第三方的清理工具的时候,这里也会被清空。
ContextThemeWrapper 方便在运行的时候修改主题。
apache提供的一系列jar包：commons-lang.jar，commons-collections.jar，commons-beanutils.jar等，里面很多方法可能是你曾经用几十几百行代码实现过的，但是执行效率或许要差很多，比如：ArrayUtils，StringUtils……


-----------代码功能讲解（功能，完成日期，参考文章和源码，使用的技术和控件，把各个activity的关联文件说明清楚。特点讲解，疑惑点，引申的问题。）-----------
1：地区、学校选择二级联动 (20150922)
demo文章 http://www.cnblogs.com/tonycheng93/p/4823860.html
demo代码 https://github.com/tonycheng93/PopWindow
使用控件 PopupWindow  ListView
使用框架 Volley Gson
相关文件
类
PopupWindowActivity
ProvinceList
SchoolList
Province
School
资源文件
popwindow_layout.xml
popwindow_view_select_province_list.xml
popwindow_item_school_list.xml
popwindow_item_province_list.xml
特点讲解，疑惑点，引申的问题。
 ListView 要设置match_parent,否则getView()会执行多次。convertView是一屏所有view都是空，只有滑出去后的view才不为空。

 2：一个实时获取股票数据的安卓应用程序 (20150923)
 demo文章 http://www.cnblogs.com/ldlchina/p/4822406.html
 demo代码 https://github.com/ldlchina/Mystock
 使用控件 TableLayout ScrollView Timer(定时刷新数据) SharedPreferences AppCompatActivity HashSet Vector Notification
 使用框架 Volley Gson
 相关文件
 类
VolleyTableLayoutActivity
 资源文件
 activity_volley_table_layout.xml
 特点讲解，疑惑点，引申的问题。
EditText 抢焦点弹键盘。所以要在它的父类设置    android:focusable="true" android:focusableInTouchMode="true"。

3:PullToRefresh控件的使用例子
demo文章 http://www.cnblogs.com/zhaoyanjun/p/4555401.html
使用控件 PullToRefresh android.text.format.DateUtils.formatDateTime AsyncTask
相关文件
类
PullToRefreshActivity
资源文件
activity_pull_to_refresh.xml
特点讲解，疑惑点，引申的问题。
DateUtils的好好运用

4：超高仿微信图片选择器  (201509)
  demo文章 http://blog.csdn.net/lmj623565791/article/details/39943731
  demo代码 http://blog.csdn.net/lmj623565791/article/details/39943731
  使用控件 GridView PopupWindow ProgressDialog
  使用框架
  相关文件
  类
  PictureChoseActivity
  PictureChoseAdapter
  ImageFolder
  ListImageDirPopupWindow
  BasePopupWindowForListView
  ImageLoader
  CommonViewHolder
  资源文件
  activity_picture_chose.xml
  picture_chose_grid_item.xml
  picture_chose_list_dir.xml
  picture_chose_list_dir_item.xml
  slide_in.xml
  slide_out.xml

  特点讲解，疑惑点，引申的问题。
  OOM，图片的压缩，缓存。popupwindow的弹出动画
   android:scaleType="centerCrop"





