package com.gxg.alltils.projectallutils.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.gxg.alltils.projectallutils.application.BaseApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author yuliuyue
 */
public class AppUtil {
    /**
     * 没有网络
     */
    private static final int NETWORK_TYPE_INVALID = 0;
    /**
     * wap网络
     */
    private static final int NETWORK_TYPE_WAP = 1;
    /**
     * 2G网络
     */
    private static final int NETWORK_TYPE_2G = 2;
    /**
     * 3G和3G以上网络，或统称为快速网络
     */
    private static final int NETWORK_TYPE_3G = 3;
    /**
     * wifi网络
     */
    private static final int NETWORK_TYPE_WIFI = 4;

    /**
     * 检查网络连接状态
     *
     * @return true, 网络连接; false,无网络连接
     */
    public static boolean isConnNet() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getmInstance().getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    /**
     * 判断wifi是否打开
     *
     * @return boolean
     */
    public static boolean isWifiOpen() {
        ConnectivityManager cm = (ConnectivityManager)  BaseApplication.getmInstance().getSystemService(Service.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getTypeName().equalsIgnoreCase("WIFI") && networkInfo.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager =  BaseApplication.getmInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo( BaseApplication.getmInstance().getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "不能找到版本号";
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        try {
            PackageManager manager = BaseApplication.getmInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo( BaseApplication.getmInstance().getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取手机机型
     */
    public static String getMachineModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 判断是否纯数字
     *
     * @param str
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 获取手机系统版本
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机系统SDK版本
     */
    public static String getSdkVersion() {
        return android.os.Build.VERSION.SDK;
    }

    /**
     * 获取MAC地址
     */
//    public static String getMacAddress() {
//        String macAddress;
//        WifiManager wifiManager = (WifiManager) MainApplication.getContext().getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        macAddress = wifiInfo.getMacAddress();
//        return macAddress;
//    }

    /**
     * 获得手机的唯一编码
     */
    public static String getImIeNum(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }

    public static boolean isBackground() {
        Context context =  BaseApplication.getmInstance();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                android.util.Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    android.util.Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    android.util.Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * 返回系统当前时间
     */
    public static String getSystemTime() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());//设置日期格式
        return df.format(new Date());
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale =  BaseApplication.getmInstance().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static String getNetWorkTypeStr(Context context) {

        String netType;
        switch (getNetWorkType(context)) {
            case NETWORK_TYPE_INVALID:
                netType = "无网络";
                break;
            case NETWORK_TYPE_WAP:
                netType = "wap";
                break;
            case NETWORK_TYPE_2G:
                netType = "2G";
                break;
            case NETWORK_TYPE_3G:
                netType = "3G";
                break;
            case NETWORK_TYPE_WIFI:
                netType = "WIFI";
                break;
            default:
                netType = "其他";
        }

        return netType;
    }

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context 上下文
     * @return int 网络状态 {@link #NETWORK_TYPE_2G},{@link #NETWORK_TYPE_3G},          *{@link #NETWORK_TYPE_INVALID},{@link #NETWORK_TYPE_WAP}* <p>{@link #NETWORK_TYPE_WIFI}
     */
    private static int getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        int mNetWorkType = -1;
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORK_TYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost)
                        ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
                        : NETWORK_TYPE_WAP;
            }
        } else {
            mNetWorkType = NETWORK_TYPE_INVALID;
        }
        return mNetWorkType;
    }

    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 判断指定包名的进程是否运行
     *
     * @param context
     * @param packageName 指定包名
     * @return 是否运行
     */
    public static boolean isRunning(Context context, String packageName) {
        /*ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();

		for (RunningAppProcessInfo rapi : infos) {
			YmLog.e("isRunning", "" + rapi.processName);
			if (rapi.processName.equals(packageName))
				return true;
		}
		return false;*/

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }


}

