package com.gxg.alltils.projectallutils.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者：Administrator on 2017/11/14 17:45
 * 邮箱：android_gaoxuge@163.com
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    /**
     * 填充布局文件,格式 return R.layout.activity_main
     */
    protected abstract int setContentView();

    /**
     * 获取控件填充数据
     */
    public abstract void setupViews(View view);

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        int viewId = setContentView();
        View view = inflater.inflate(viewId, null);
        if (view != null) {
            ButterKnife.bind(this, view);
            setupViews(view);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    // long吐司
    public void showToastLong(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    // long吐司
    public void showToastShort(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        getActivity().finish();
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        openActivity(targetActivityClass, bundle);
        getActivity().finish();
    }

    /**
     * 带参数的跳转
     *
     * @param targetActivityClass
     * @param bundle
     */
    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), targetActivityClass);
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


    /**
     * 收起键盘
     */
    public void closeInputMethod() {
        // 收起键盘
        View view = getActivity().getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
                if (!AndPermission.hasAlwaysDeniedPermission(getActivity(), deniedPermissions)) {
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
