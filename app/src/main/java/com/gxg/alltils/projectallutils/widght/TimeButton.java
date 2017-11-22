package com.gxg.alltils.projectallutils.widght;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yuliuyue
 */
public class TimeButton extends AppCompatButton implements OnClickListener {
    private long length = 59 * 1000;// 倒计时长度,这里给了默认60秒
    private String textAfter = "已发送(%d)";
    private String textBefore = "获取验证码";
    private String textAgain = "重获验证码";
    private int bg_after ;
    private int bg_before ;

    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    private boolean enabled = false;
    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText(String.format(textAfter, time / 1000));
            TimeButton.this.setTextColor(Color.parseColor("#f74467"));
            time -= 1000;
            if (time < 0) {
                enabled = false;
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(textAgain);
                changeBackGroundResource(bg_before);
                TimeButton.this.setTextColor(Color.parseColor("#f74467"));
                clearTimer();
            }
        }
    };

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);

    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public void cancel(){
        enabled = false;
        TimeButton.this.setEnabled(true);
        TimeButton.this.setText(textAgain);
        changeBackGroundResource(bg_before);
        TimeButton.this.setTextColor(Color.parseColor("#f74467"));
        clearTimer();
    }

    private void changeBackGroundResource(int bg) {
        int left = TimeButton.this.getPaddingLeft();
        int top = TimeButton.this.getPaddingTop();
        int right = TimeButton.this.getPaddingRight();
        int bottom = TimeButton.this.getPaddingBottom();
        TimeButton.this.setBackgroundResource(bg);
        TimeButton.this.setPadding(left,top,right,bottom);
    }

    private void initTimer() {
        time = length;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };
    }

    private void clearTimer() {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    @Override
    public void onClick(View v) {

        if (mOnclickListener != null)
            mOnclickListener.onClick(v);
        if(!enabled)
            return;
        initTimer();
        this.setText(String.format(textAfter, time / 1000));
        this.setTextColor(Color.parseColor("#f74467"));
        changeBackGroundResource(bg_after);
        this.setEnabled(false);
        t.schedule(tt, 0, 1000);
        // t.scheduleAtFixedRate(task, delay, period);
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        clearTimer();
    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate(Bundle bundle) {

    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate() {

    }

    /**
     * 设置计时时候显示的文本
     */
    public TimeButton setTextAfter(String text1) {
        this.textAfter = text1;
        return this;
    }

    public void enableStarted(boolean enabled){
        this.enabled = enabled;
    }

    /**
     * 设置点击之前的文本
     */
    public TimeButton setTextBefore(String text0) {
        this.textBefore = text0;
        this.setText(textBefore);
        return this;
    }

    /**
     * 设置点击之前的背景
     */
    public TimeButton setBgBefore(int resId) {
        this.bg_before = resId;
        return this;
    }

    /**
     * 设置点击之后的背景
     */
    public TimeButton setBgAfter(int resId) {
        this.bg_after = resId;
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param length 时间 默认毫秒
     * @return
     */
    public TimeButton setLength(long length) {
        this.length = length;
        return this;
    }
}