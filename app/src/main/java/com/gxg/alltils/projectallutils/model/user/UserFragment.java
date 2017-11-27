package com.gxg.alltils.projectallutils.model.user;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.imageloader.ImageLoader;
import com.gxg.alltils.projectallutils.imageloader.ImageLoaderUtil;
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
            KLog.e("sss  已经登陆过"+SharedPreferencesUtils.isLogin(getActivity()));
            layout_user_info.setVisibility(View.VISIBLE);
            layout_login.setVisibility(View.GONE);
            String avatar = SharedPreferencesUtils.get(getActivity(), Contants.IMG_AVATAR, "").toString();
            KLog.e(avatar);
           KLog.e( new File(avatar).exists()+"    "+ new File(avatar).length());
            new ImageLoaderUtil().loadCircleImg(getActivity(),new ImageLoader.Builder().imgView(img_avatar).url(new File(avatar)).errorHolder(R.drawable.ic_me_touxiang).placeHolder(R.drawable.ic_me_touxiang).build(),2,getResources().getColor(R.color.white));
        }else{
            KLog.e("sss  没有登陆过"+SharedPreferencesUtils.isLogin(getActivity()));
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
        layoutParams.width = screenWidth;
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
                toZxing();
                break;
            case R.id.iv_setting:
                openActivity(SettingActivity.class);
                break;
        }
    }
    /**
     * 跳转扫描二维码页
     */
    private void toZxing() {
        permissionsJudgment(new PermissionCallBack() {
            @Override
            public void onSucceed(int requestCode, List<String> grantedPermissions) {
                openActivity(ZxingActivity.class);
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
                AndPermission.defaultSettingDialog(getActivity(), 400)
                        .setTitle("权限申请失败")
                        .setMessage("扫描二维码需要打开相机和散光灯的权限，请在设置中授权！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        },Permission.CAMERA,Permission.STORAGE);
    }



}
