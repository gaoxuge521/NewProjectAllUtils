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
import com.gxg.alltils.projectallutils.event.ShowHomeEvent;
import com.gxg.alltils.projectallutils.http.utils.RxBus;
import com.gxg.alltils.projectallutils.model.find.FindFragment;
import com.gxg.alltils.projectallutils.model.home.HomeFragment;
import com.gxg.alltils.projectallutils.model.shop.ShopFragment;
import com.gxg.alltils.projectallutils.model.user.UserFragment;
import com.gxg.alltils.projectallutils.utils.BarUtils;
import com.gxg.alltils.projectallutils.utils.PhoneSystemManager;
import com.gxg.alltils.projectallutils.widght.NoSlidingViewPager;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import rx.Subscription;
import rx.functions.Action1;


public class MainActivity extends BaseActivity {

    @Bind(R.id.vp)
    NoSlidingViewPager vp;
    @Bind(R.id.rbOne)
    RadioButton rbOne;
    @Bind(R.id.rbTwo)
    RadioButton rbTwo;
    @Bind(R.id.rbThree)
    RadioButton rbThree;
//    @Bind(R.id.rbFour)
//    RadioButton rbFour;
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
//    private OtherFragment otherFragment;
    private UserFragment userFragment;
    private Subscription showHomeSubscribe;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        //注册Rxbus
         registerRxBus();

    }

    private void registerRxBus() {

        //显示homeframg
        showHomeSubscribe = RxBus.getDefault().toObserverable(ShowHomeEvent.class)
                .subscribe(new Action1<ShowHomeEvent>() {
                    @Override
                    public void call(ShowHomeEvent showHomeEvent) {
                        vp.setCurrentItem(0,false);
                        rbOne.setChecked(true);
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        //显示homeframg
        if(showHomeSubscribe!=null && showHomeSubscribe.isUnsubscribed()){
            showHomeSubscribe.unsubscribe();
            showHomeSubscribe =null;
        }
        super.onDestroy();

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
//        KLog.e("现在使用的手机是" + telPhoneSystem);

        if (telPhoneSystem.equals(PhoneSystemManager.SYS_EMUI)) {//华为手机

            int virtualBarHeigh = PhoneSystemManager.AndroidWorkaround.getVirtualBarHeigh(MainActivity.this);
//            KLog.e("虚拟导航栏高度11111：" + virtualBarHeigh);
        }
    }

    @Override
    protected void initData() {
        fragments.clear();
        homeFragment = HomeFragment.newInstance("1");
        findFragment = FindFragment.newInstance("2");
        shopFragment = ShopFragment.newInstance("3");
//        otherFragment = OtherFragment.newInstance("4");
        userFragment = UserFragment.newInstance("5");

        fragments.add(homeFragment);
        fragments.add(findFragment);
        fragments.add(shopFragment);
//        fragments.add(otherFragment);
        fragments.add(userFragment);

        vp.setScanScroll(false);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(fragments.size());

    }
    //setCurrentItem后面添加false  它是直接跳转，没有滑屏效果了。
    @Override
    protected void initListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbOne:
                        vp.setCurrentItem(0,false);
                        //打开手势滑动
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        BarUtils.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.purpletextColor));
                        break;
                    case R.id.rbTwo:
                        vp.setCurrentItem(1,false);
                        //禁止手势滑动
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        BarUtils.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.red));
                        break;
                    case R.id.rbThree:
                        vp.setCurrentItem(2,false);
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        BarUtils.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.blue));
                        break;
//                    case R.id.rbFour:
//                        vp.setCurrentItem(3,false);
//                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                        BarUtils.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.orange));
//                        break;
                    case R.id.rbFive:
                        vp.setCurrentItem(3,false);
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
        if (keyCode == KeyEvent.KEYCODE_BACK /* && isHome */) {
            exitBy2Click();
        }
        return false;
    }
    private  Boolean isExit = false;
    /**
     * 双击返回键退出函数（退出程序）
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
