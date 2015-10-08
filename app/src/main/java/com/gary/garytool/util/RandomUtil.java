package com.gary.garytool.util;

import java.util.Random;

/**
 * Created by Administrator on 2015/10/8.
 */
public class RandomUtil {
    public static int getRandomInt()
    {
        Random rand = new Random();
        return rand.nextInt(100);
    }
}
