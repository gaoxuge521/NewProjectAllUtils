package com.gxg.alltils.projectallutils.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.gxg.alltils.projectallutils.huanxin.controller.HXController;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：Administrator on 2017/11/14 11:39
 * 邮箱：android_gaoxuge@163.com
 */
public class BaseApplication extends Application {
    private static  Context mInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);

    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //友盟调试模式
//        Config.DEBUG = true;
        //每次都调起第三方的登陆页
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);

        //初始化第三方的sdk
        initThirdSdk();
    }

    private void initThirdSdk() {
        //环信初始化
        try {
            HXController.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //极光推送
        JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        //友盟统计功能
        MobclickAgent.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);


        //友盟登陆分享
        PlatformConfig.setWeixin("wx8586895ef72169c9", "7fdacaaa05e891c9b81f9784b6be56f6"); //微信分享
        PlatformConfig.setSinaWeibo("2514514349", "86d079a9d76c688da1f465ed39b8bdc7", "http://sns.whalecloud.com"); //新浪分享
        PlatformConfig.setQQZone("1106563610", "clOXR98vvKJl8gDp"); // QQ&&QQ空间分享
    }

    public static Context getmInstance(){
        return mInstance;
    }

}
