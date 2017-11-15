package com.gxg.alltils.projectallutils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/14 18:08
 * 邮箱：android_gaoxuge@163.com
 * 主activity 的viewpager适配器
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
