package com.gxg.alltils.projectallutils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/15 10:55
 * 邮箱：android_gaoxuge@163.com
 * ViewPager和TabLayout 结合的adapter
 */
public class BaseFragmentPagerAdapterWithoutTitle extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> fragments;
    public BaseFragmentPagerAdapterWithoutTitle(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
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
    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
