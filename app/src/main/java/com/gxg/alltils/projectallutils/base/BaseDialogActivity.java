package com.gxg.alltils.projectallutils.base;

import android.support.v4.app.FragmentActivity;

/**
 * 作者：Administrator on 2017/11/15 10:57
 * 邮箱：android_gaoxuge@163.com
 * 所有透明activity的基类
 */
public class BaseDialogActivity  extends FragmentActivity{

    @Override
    protected void onPause() {
        super.onPause();
        this.setVisible(false);
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.setVisible(true);
    }
}
