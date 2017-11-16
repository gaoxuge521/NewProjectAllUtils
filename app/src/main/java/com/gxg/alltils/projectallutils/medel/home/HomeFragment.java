package com.gxg.alltils.projectallutils.medel.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.utils.BarUtils;
import com.socks.library.KLog;

import butterknife.Bind;

/**
 * 作者：Administrator on 2017/11/14 17:45
 * 邮箱：android_gaoxuge@163.com
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private View view;

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void setupViews(View view) {
        String id = getArguments().getString("id", "");
        KLog.e("sss  setupViews");
        KLog.e(id);
        initView();
        initData();
        initListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       KLog.e("sss  onActivityCreated");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KLog.e("sss  onViewCreated");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        KLog.e("sss  setUserVisibleHint");
        if(isVisibleToUser){

        }else{

        }
    }

    private void initListener() {

    }

    private void initData() {

    }

    private void initView() {

    }

    public static HomeFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id", id);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
