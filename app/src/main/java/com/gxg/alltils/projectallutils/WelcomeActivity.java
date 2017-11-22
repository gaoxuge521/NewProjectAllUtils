package com.gxg.alltils.projectallutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.gxg.alltils.projectallutils.utils.Contants;
import com.gxg.alltils.projectallutils.utils.SharedPreferencesUtils;
import com.gxg.alltils.projectallutils.utils.WeakHandler;

public class WelcomeActivity extends Activity {
    private int MSG_INIT_OK = 1;
    private ImageView ivWelcome;
    private Button btnJump;
    private WeakHandler mHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_INIT_OK) {
                mHandler.removeCallbacks(timeOutTask);
                Intent intent;
                int count = (int) SharedPreferencesUtils.getLogin(WelcomeActivity.this, Contants.LOGIN_COUNT, 0);

                //是第一次登陆进入导航页
                if (count < 1) {
                    intent = new Intent(WelcomeActivity.this, LoadingViewpageActivity.class);
                } else {
                    //不是第一次进入首页
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                }
                SharedPreferencesUtils.putLogin(WelcomeActivity.this, Contants.LOGIN_COUNT, ++count);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        }
    });
    Runnable timeOutTask = new Runnable() {
        @Override
        public void run() {
            gotoNext();
        }
    };

    private void gotoNext() {
        Message msg = Message.obtain();
        msg.what = MSG_INIT_OK;
        mHandler.sendMessage(msg);
    }

    private CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
        public void onTick(long millisUntilFinished) {
            if (btnJump == null) {
                btnJump = (Button) findViewById(R.id.btn_jump);
            }
            btnJump.setText("跳过" + millisUntilFinished / 1000 + "\'");
        }

        public void onFinish() {
            if (btnJump == null) {
                btnJump = (Button) findViewById(R.id.btn_jump);
            }
            btnJump.setText("跳过" + 0 + "\'");
            gotoNext();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏设置,隐藏窗口所有装饰(包括顶部任务栏)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 标题是属于View的,所以窗口所有的修饰部分隐藏后标题依然有效,需要隐藏掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        initView();
        initSystem();

    }

    private void initView() {
        ivWelcome = (ImageView) findViewById(R.id.iv_welcome);
        btnJump = (Button) findViewById(R.id.btn_jump);

        //计时器
        countDownTimer.start();

        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext();
            }
        });
    }

    private void initSystem() {
        final int outTime = 3000;
        mHandler.postDelayed(timeOutTask, outTime);//

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer !=null){
            countDownTimer.cancel();
        }
    }
}
