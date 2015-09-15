package com.gary.garytool.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Administrator on 2015/9/15.
 */
public class TextUtil {


    /**
     * 让Android支持自定义的ttf字体
     * 必须事先在assets底下创建一fonts文件夹 并放入要使用的字体文件(.ttf)
     * 并提供相对路径给creatFromAsset()来创建Typeface对象
     * @param uri
     * @return
     */
    public static Typeface getFontType(Context context, String uri) {
        if (uri.isEmpty())
            uri = "fonts/STXINGKA.TTF";
        Typeface fontFace = Typeface.createFromAsset(context.getAssets(), uri);

        // 字体文件必须是true type font的格式(ttf)；
        // 当使用外部字体却又发现字体没有变化的时候(以 Droid Sans代替)，通常是因为
        // 这个字体android没有支持,而非你的程序发生了错误

        //下面是TextView的调用实例
        //TextView text = (TextView) findViewById(R.id.ttf);
        //text.setTypeface(fontFace);

        return fontFace;
    }

}
