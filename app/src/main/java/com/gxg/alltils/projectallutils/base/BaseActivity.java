package com.gxg.alltils.projectallutils.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.gxg.alltils.projectallutils.utils.ActivityManage;

/**
 * 作者：Administrator on 2017/11/14 15:01
 * 邮箱：android_gaoxuge@163.com
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    private long exitTime;
    private boolean isBackExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置activity为无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        ActivityManage.getAppManager().addActivity(this);

        initView();
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManage.getAppManager().finishActivity(this);
    }

    // 初始化ui
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();

    // 添加监听器
    protected abstract void initListener();

    // short吐司
    public void showToastShort(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    // long吐司
    public void showToastLong(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        this.finish();
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        openActivity(targetActivityClass, bundle);
        this.finish();
    }

    /**
     * 带参数的跳转
     *
     * @param targetActivityClass
     * @param bundle
     */
    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 不带参数的跳转
     *
     * @param targetActivityClass
     */
    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }


    /** 收起键盘 */
    public void closeInputMethod() {
        // 收起键盘
        View view = getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * [是否连续两次返回退出]
     */
    public void setBackExit(boolean isBackExit) {
        this.isBackExit = isBackExit;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isBackExit) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    System.exit(0);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

    }
}
