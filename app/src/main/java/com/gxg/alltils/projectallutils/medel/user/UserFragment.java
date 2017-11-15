package com.gxg.alltils.projectallutils.medel.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.medel.loginregister.InformationActivity;
import com.gxg.alltils.projectallutils.medel.loginregister.LoginActivity;
import com.socks.library.KLog;

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



    @Override
    protected int setContentView() {
        return R.layout.fragment_user;
    }

    @Override
    public void setupViews(View view) {
        String id = getArguments().getString("id", "");
        KLog.e(id);
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
