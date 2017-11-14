package com.gxg.alltils.projectallutils;

import android.os.Bundle;

import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.http.HttpTestActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        openActivity(HttpTestActivity.class);
        //设置按两次退出
//        setBackExit(true);
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
