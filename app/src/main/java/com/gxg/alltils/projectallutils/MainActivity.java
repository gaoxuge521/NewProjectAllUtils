package com.gxg.alltils.projectallutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gxg.alltils.projectallutils.adapter.MainViewPagerAdapter;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.model.find.FindFragment;
import com.gxg.alltils.projectallutils.model.home.HomeFragment;
import com.gxg.alltils.projectallutils.model.other.OtherFragment;
import com.gxg.alltils.projectallutils.model.shop.ShopFragment;
import com.gxg.alltils.projectallutils.model.user.UserFragment;
import com.gxg.alltils.projectallutils.utils.BarUtils;
import com.gxg.alltils.projectallutils.utils.PhoneSystemManager;
import com.gxg.alltils.projectallutils.widght.NoSlidingViewPager;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity {

    @Bind(R.id.vp)
    NoSlidingViewPager vp;
    @Bind(R.id.rbOne)
    RadioButton rbOne;
    @Bind(R.id.rbTwo)
    RadioButton rbTwo;
    @Bind(R.id.rbThree)
    RadioButton rbThree;
    @Bind(R.id.rbFour)
    RadioButton rbFour;
    @Bind(R.id.rbFive)
    RadioButton rbFive;
    @Bind(R.id.rg)
    RadioGroup rg;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    List<Fragment> fragments = new ArrayList<>();
    private HomeFragment homeFragment;
    private FindFragment findFragment;
    private ShopFragment shopFragment;
    private OtherFragment otherFragment;
    private UserFragment userFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();

    }


    /**
     * 获取数据
     */
    private void getData() {

        boolean pushStopped = JPushInterface.isPushStopped(this);
    }

    @Override
    protected void initView() {

        String telPhoneSystem = PhoneSystemManager.getTelPhoneSystem();
        KLog.e("现在使用的手机是" + telPhoneSystem);

        if (telPhoneSystem.equals(PhoneSystemManager.SYS_EMUI)) {//华为手机

            int virtualBarHeigh = PhoneSystemManager.AndroidWorkaround.getVirtualBarHeigh(MainActivity.this);
            KLog.e("虚拟导航栏高度11111：" + virtualBarHeigh);
        }
    }

    @Override
    protected void initData() {
        fragments.clear();
        homeFragment = HomeFragment.newInstance("1");
        findFragment = FindFragment.newInstance("2");
        shopFragment = ShopFragment.newInstance("3");
        otherFragment = OtherFragment.newInstance("4");
        userFragment = UserFragment.newInstance("5");

        fragments.add(homeFragment);
        fragments.add(findFragment);
        fragments.add(shopFragment);
        fragments.add(otherFragment);
        fragments.add(userFragment);

        vp.setScanScroll(false);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbOne:
                        vp.setCurrentItem(0);
                        //打开手势滑动
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        BarUtils.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.purpletextColor));
                        break;
                    case R.id.rbTwo:
                        vp.setCurrentItem(1);
                        //禁止手势滑动
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        BarUtils.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.red));
                        break;
                    case R.id.rbThree:
                        vp.setCurrentItem(2);
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        BarUtils.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.blue));
                        break;
                    case R.id.rbFour:
                        vp.setCurrentItem(3);
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        BarUtils.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.orange));
                        break;
                    case R.id.rbFive:
                        vp.setCurrentItem(4);
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        BarUtils.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.c_663333));
                        break;
                }
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        KLog.e("onNewIntent", "onNewIntent");
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
