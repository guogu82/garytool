package com.gary.garytool;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.gary.garytool.util.LogUtil;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void  test()
    {
       int i=0;
        i++;
        int j;
        j=i;
    }
}