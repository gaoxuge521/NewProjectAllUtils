package com.gxg.alltils.projectallutils.medel.user;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.imageloader.GlideCircleTransform;
import com.gxg.alltils.projectallutils.medel.loginregister.InformationActivity;
import com.gxg.alltils.projectallutils.medel.loginregister.LoginActivity;
import com.gxg.alltils.projectallutils.utils.ScreenSizeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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



    @Override
    protected int setContentView() {
        return R.layout.fragment_user;
    }

    @Override
    public void setupViews(View view) {
        String id = getArguments().getString("id", "");


        //手动给容器赋值，防止随着头部的下拉而放大
        int screenWidth = ScreenSizeUtil.getScreenWidth(getActivity());
        ViewGroup.LayoutParams layoutParams = layout_count.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = screenWidth-(ScreenSizeUtil.Dp2Px(getActivity(),50));
        layout_count.setLayoutParams(layoutParams);


        Glide.with(getActivity()).load(R.drawable.ic_my_avatar).bitmapTransform(new GlideCircleTransform(getActivity(),ScreenSizeUtil.Dp2Px(getActivity(),40),getResources().getColor(R.color.white))).into(img_avatar);
//        KLog.e(screenWidth);
        setData();
    }

    private void setData() {

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

    @OnClick({R.id.img_avatar, R.id.img_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                Bundle bundle = new Bundle();
                bundle.putString("id","10");
                openActivity( InformationActivity.class,bundle);
                break;
            case R.id.img_register:
                openActivity(LoginActivity.class);
                break;
        }
    }
}
