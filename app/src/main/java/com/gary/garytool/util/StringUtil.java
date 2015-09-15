package com.gary.garytool.util;

/**
 * Created by Administrator on 2015/9/15.
 */
public class StringUtil {
    /**
     * 问题：TextView会自动换行，而且排版文字参差不齐
     * 原因：半角字符与全角字符混乱所致：这种情况一般就是汉字与数字、英文字母混用
     * 解决方法：将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题了。
     * 半角转为全角的代码如下，只需调用即可
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
