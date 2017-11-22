package com.gxg.alltils.projectallutils.utils;

/**
 * @author gaoxuge
 * 防止频繁点击
 * //
 */

public class ButtonUtils {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 2500) {       //2500毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return false;
        }
        lastClickTime = time;
        return true;
    }


    public static boolean isFFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {       //1000毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return false;
        }
        lastClickTime = time;
        return true;
    }

    public static boolean isSlowDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 5500) {       //5500毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return false;
        }
        lastClickTime = time;
        return true;
    }
}
