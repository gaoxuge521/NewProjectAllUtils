package com.gxg.alltils.projectallutils.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.utils.ActivityManage;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者：Administrator on 2017/11/14 15:01
 * 邮箱：android_gaoxuge@163.com
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {


    public Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置activity为无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityManage.getAppManager().addActivity(this);
        mActivity = this;

        if(getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityManage.getAppManager().finishActivity(this.getClass());
    }

    protected abstract int getLayoutId();
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
        overridePendingTransition(R.anim.activity_in,R.anim.activity_on);
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




    @Override
    public void onClick(View v) {

    }

    /**
     * 判断权限
     *
     * @param permissionsArray 权限组 根据需要在Permission类找
     *                    传的时候如    Permission.CAMERA,
     *                    Permission.STORAGE
     */
    public void permissionsJudgment(PermissionCallBack permissionCallBack,String[]... permissionsArray) {
        this.onpermissionCallBack = permissionCallBack;
        AndPermission.with(this)
                .requestCode(200)
                .permission(permissionsArray)
                .callback(listener)
                .start();
    }
    private PermissionCallBack onpermissionCallBack;

    public interface PermissionCallBack {
        void onSucceed(int requestCode, List<String> grantedPermissions);

        void onFailed(int requestCode, List<String> deniedPermissions);
    }

    /**
     * 权限申请的回调
     */
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                // TODO ...
                KLog.e("权限申请成功");
                if(onpermissionCallBack!=null){
                    onpermissionCallBack.onSucceed(requestCode,grantedPermissions);
                }
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                // TODO ...
                KLog.e("权限申请失败" + deniedPermissions.toString());
                // 是否有不再提示并拒绝的权限。
                if (!AndPermission.hasAlwaysDeniedPermission(mActivity, deniedPermissions)) {
                    KLog.e("权限申请失败222222");

                    if(onpermissionCallBack!=null){
                        onpermissionCallBack.onFailed(requestCode,deniedPermissions);
                    }
//                    // 第二种：用自定义的提示语。
//                    AndPermission.defaultSettingDialog(getActivity(), 400)
//                            .setTitle("权限申请失败")
//                            .setMessage("扫描二维码需要打开相机和散光灯的权限，请在设置中授权！")
//                            .setPositiveButton("好，去设置")
//                            .show();

//                    // 第三种：自定义dialog样式。
//                    SettingService settingService = AndPermission.defineSettingDialog(getActivity(), 400);
//                    // 你的dialog点击了确定调用：
//                    settingService.execute();
//                    // 你的dialog点击了取消调用：
//                    settingService.cancel();
                }
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 400: { // 这个400就是上面defineSettingDialog()的第二个参数。
                // 你可以在这里检查你需要的权限是否被允许，并做相应的操作。
                KLog.e("权限申请最终被拒绝了" );
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
