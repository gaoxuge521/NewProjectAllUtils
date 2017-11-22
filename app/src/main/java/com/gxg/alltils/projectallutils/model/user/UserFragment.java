package com.gxg.alltils.projectallutils.model.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.model.ZxingActivity;
import com.gxg.alltils.projectallutils.model.loginregister.InformationActivity;
import com.gxg.alltils.projectallutils.model.loginregister.LoginActivity;
import com.gxg.alltils.projectallutils.model.user.activity.SettingActivity;
import com.gxg.alltils.projectallutils.utils.Contants;
import com.gxg.alltils.projectallutils.utils.ScreenSizeUtil;
import com.gxg.alltils.projectallutils.utils.SharedPreferencesUtils;
import com.gxg.alltils.projectallutils.widght.ZoomInScrollView;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ░░░░░░░░░░░░░░░░░░░░░░░░▄░░
 * ░░░░░░░░░▐█░░░░░░░░░░░▄▀▒▌░
 * ░░░░░░░░▐▀▒█░░░░░░░░▄▀▒▒▒▐
 * ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐
 * ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐
 * ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌
 * ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒
 * ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐
 * ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄
 * ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒
 * ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒
 * 单身狗就这样默默地看着你，一句话也不说。
 */
/**
 * 作者：Administrator on 2017/11/14 17:45
 * 邮箱：android_gaoxuge@163.com
 */
public class UserFragment extends BaseFragment {

    @Bind(R.id.layout_login)
    LinearLayout layout_login;//登陆注册布局
    @Bind(R.id.img_register)
    ImageView img_register;//登陆注册图片
    @Bind(R.id.layout_user_info)
    LinearLayout layout_user_info;//个人信息
    @Bind(R.id.img_avatar)
    ImageView img_avatar;//头像
    @Bind(R.id.layout_count)
    LinearLayout layout_count;//关注、粉丝、访客数的父容器
    @Bind(R.id.iv_scanning)
    ImageView ivScanning;
    @Bind(R.id.rl_scanning)
    RelativeLayout rlScanning;
    @Bind(R.id.scroll)
    ZoomInScrollView scroll;
    @Bind(R.id.fm_head)
    FrameLayout fmHead;
    @Bind(R.id.iv_setting)
    ImageView iv_setting;


    private int height;


    @Override
    protected int setContentView() {
        return R.layout.fragment_user;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setUserStatus();
        }
    }


    //判断登陆状态显示不同的布局
    private void setUserStatus() {
        if(SharedPreferencesUtils.isLogin(getActivity())){
            layout_user_info.setVisibility(View.VISIBLE);
            layout_login.setVisibility(View.GONE);
            String avatar = SharedPreferencesUtils.get(getActivity(), Contants.IMG_AVATAR, "").toString();
            KLog.e(avatar);
            if (!TextUtils.isEmpty(avatar)) {
                Glide.with(getActivity()).load(new File(avatar)).into(img_avatar);
            } else {
                Glide.with(getActivity()).load(R.drawable.ic_my_avatar).into(img_avatar);
            }
        }else{
            layout_user_info.setVisibility(View.GONE);
            layout_login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserStatus();
    }
    @Override
    public void setupViews(View view) {
        //手动给容器赋值，防止随着头部的下拉而放大
        int screenWidth = ScreenSizeUtil.getScreenWidth(getActivity());
        ViewGroup.LayoutParams layoutParams = layout_count.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = screenWidth - (ScreenSizeUtil.Dp2Px(getActivity(), 50));
        layout_count.setLayoutParams(layoutParams);



        setData();

        initListener();
    }

    /**
     * 添加各种监听事件
     */
    private void initListener() {
        rlScanning.post(new Runnable() {
            @Override
            public void run() {
                height = rlScanning.getBottom();
            }
        });

        scroll.setOnScrollListener(new ZoomInScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY>height){
                    rlScanning.setVisibility(View.GONE);
                }else{
                    rlScanning.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setData() {
        // where this is an Activity instance


    }


    public static UserFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id", id);
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_setting,R.id.iv_scanning, R.id.img_avatar, R.id.img_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                Bundle bundle = new Bundle();
                bundle.putString("id", "10");
                openActivity(InformationActivity.class, bundle);
                break;
            case R.id.img_register:
                openActivity(LoginActivity.class);
                break;
            case R.id.iv_scanning://二维码
                scanning();
                break;
            case R.id.iv_setting:
                openActivity(SettingActivity.class);
                break;
        }
    }

    /**
     * 二维码扫描，先判断权限
     */
    private void scanning() {
        AndPermission.with(UserFragment.this)
                .requestCode(200)
                .permission(
                        // 申请多个权限组方式：
                        Permission.CAMERA,
                        Permission.STORAGE
                )
//                    .rationale(new RationaleListener() {//自定义对话框
//                        @Override
//                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
//
//                        }
//                    })
                .callback(listener)
                .start();
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

                openActivity(ZxingActivity.class);
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
                    // 第一种：用AndPermission默认的提示语。
//                    AndPermission.defaultSettingDialog(getActivity(), 400).show();

//                    // 第二种：用自定义的提示语。
                    AndPermission.defaultSettingDialog(getActivity(), 400)
                            .setTitle("权限申请失败")
                            .setMessage("扫描二维码需要打开相机和散光灯的权限，请在设置中授权！")
                            .setPositiveButton("好，去设置")
                            .show();

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
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
