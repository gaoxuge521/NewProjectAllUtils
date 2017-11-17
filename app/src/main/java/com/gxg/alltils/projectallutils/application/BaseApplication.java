package com.gxg.alltils.projectallutils.application;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

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

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    public static Context getmInstance(){
        return mInstance;
    }

}
