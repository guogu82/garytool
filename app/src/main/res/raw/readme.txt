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

1:Fragment  transaction.replace() VS transaction.add() hide() show()
Fragment销毁时replace和add两个方法的区别 http://m.blog.csdn.net/blog/shimiso/44677007#
那么最合适的处理方式是这样的：
在add的时候，加上一个tab参数transaction.add(R.id.content, IndexFragment,”Tab1″);
然后当IndexFragment引用被回收置空的话，先通过IndexFragment＝FragmentManager.findFragmentByTag(“Tab1″);
找到对应的引用，然后继续上面的hide,show;