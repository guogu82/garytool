布局文件 命名规范
activity_bottom_menu_with_fragment

布局文件 里面的view id命名规范
ImageView iv_about_time
Selector selector_skin_tabbar_icon_about

类  命名规范
有类型的通过最后面的代表类型
BottomMenuWithFragmentActivity

类成员命名规范
多次出现的成员变量，通过前缀区分,只是view对象有前缀
ImageView iv_about_time
类里面基本是唯一的变量，用小写开头的全名来表示
DisplayMetrics displayMetrics

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

1:Fragment  transaction.replace() VS transaction.add() hide() show()
Fragment销毁时replace和add两个方法的区别 http://m.blog.csdn.net/blog/shimiso/44677007#
那么最合适的处理方式是这样的：
在add的时候，加上一个tab参数transaction.add(R.id.content, IndexFragment,”Tab1″);
然后当IndexFragment引用被回收置空的话，先通过IndexFragment＝FragmentManager.findFragmentByTag(“Tab1″);
找到对应的引用，然后继续上面的hide,show;

2:TextView,Button,EditText 如果要做到按下去有变化，请使用selector
点击父布局 使子控件（TextView等）响应点击效果 （子控件中加上这个属性 duplicateParentState）。
例如：组件RelativeLayout上有两个TextView，这两个TextView具有不同的颜色值，现在要的效果是，当RelativeLayout被点击时，整个item有高亮背景。
同时这两个TextView要变色。就是父控件响应点击事件，子View不响应点击事件，但是颜色要随着点击而发生变化。这样就用到了属性duplicateParentState。
android:duplicateParentState，如果设置此属性，将直接从父容器中获取绘图状态（光标，按下等）。
注意仅仅是获取绘图状态，而没有获取事件，也就是你点一下LinearLayout时Button有被点击的效果，但是不执行点击事件。
在TextView中设置字体颜色一般使用，android:textColor="@color/red"，但是我们在使用selector动态修改字体颜色的时候要使用android:color="@color/red"。
代码来设置textColor 的，需要用 textView.setTextColor(getResources().getColorStateList(R.color.text_selector_color));来设置。

3:ListView
--ListView记得把layout_height属性设置成match_parent。若设置成"wrap_content"则会调用多次。
--cacheColorHint="#00000000" 自定义listview的时候，不使用#00000000会出现选中一个空间黑色底色的情况.
--当不使用android:listSelector属性，默认会显示选中的item为橙黄底色

4:坐标
坐标原点是左上角.
getRowX：触摸点相对于屏幕的坐标
getX： 触摸点相对于按钮的坐标
getTop： 按钮左上角相对于父view（LinerLayout）的y坐标
getLeft： 按钮左上角相对于父view（LinerLayout）的x坐标