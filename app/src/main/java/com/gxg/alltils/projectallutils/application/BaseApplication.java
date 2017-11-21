package com.gxg.alltils.projectallutils.application;

import android.app.Application;
import android.content.Context;

import com.gxg.alltils.projectallutils.huanxin.controller.HXController;
import com.socks.library.KLog;

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

        //极光推送
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        //环信初始化
        try {
            KLog.e("环信初始化111111111");
            HXController.init(this);
        } catch (Exception e) {
            KLog.e("环信初始化11111111   1"+e.toString());
            e.printStackTrace();
        }
    }

    public static Context getmInstance(){
        return mInstance;
    }

}
