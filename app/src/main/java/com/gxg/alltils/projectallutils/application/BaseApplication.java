package com.gxg.alltils.projectallutils.application;

import android.app.Application;
import android.content.Context;

/**
 * 作者：Administrator on 2017/11/14 11:39
 * 邮箱：android_gaoxuge@163.com
 */
public class BaseApplication extends Application {
    private static  Context mInstance;



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

    public static Context getmInstance(){
        return mInstance;
    }

}
