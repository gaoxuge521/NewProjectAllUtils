package com.gxg.alltils.projectallutils.medel.shop;

import android.os.Bundle;
import android.view.View;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.utils.BarUtils;
import com.socks.library.KLog;

/**
 * 作者：Administrator on 2017/11/14 17:45
 * 邮箱：android_gaoxuge@163.com
 */
public class ShopFragment extends BaseFragment {
    @Override
    protected int setContentView() {
        return R.layout.fragment_shop;
    }

    @Override
    public void setupViews(View view) {
        String id = getArguments().getString("id", "");
        BarUtils.setStatusBarColor(getActivity(),getResources().getColor(R.color.pinkBuy));
        KLog.e(id);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

        }else{

        }
    }

    public static ShopFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id",id);
        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
