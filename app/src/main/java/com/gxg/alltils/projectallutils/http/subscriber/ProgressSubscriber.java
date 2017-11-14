package com.gxg.alltils.projectallutils.http.subscriber;

import android.content.Context;

import com.gxg.alltils.projectallutils.http.exception.ExceptionHandle;
import com.gxg.alltils.projectallutils.http.utils.CustomProgressDialog;

import rx.Subscriber;

//import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.exception.ExceptionHandle;
//import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.progress.ProgressDialogHandler;
//import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.utils.CustomProgressDialog;

/**
 * 作者：Administrator on 2017/11/6 16:18
 * 邮箱：android_gaoxuge@163.com
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> {

    private Context context;

    private boolean isShowDialog;//是否显示加载框，默认不显示

//    private ProgressDialogHandler mProgressDialogHandler;

    private CustomProgressDialog progressDialog;
    public ProgressSubscriber(Context context) {
        this(context,false);
    }

    public ProgressSubscriber(Context context,boolean isShowDialog) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        progressDialog =  CustomProgressDialog.createDialog(context);
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        if(isShowDialog){
            showProgressDialog();
        }
    }
    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        if(isShowDialog){
            dismissProgressDialog();
        }
        onCancelProgress();
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof Exception){
            //访问获得对应的Exception
            onError(ExceptionHandle.handleException(e));
        }else {
            //将Throwable 和 未知错误的status code返回
            onError(new ExceptionHandle.ResponeThrowable(e,ExceptionHandle.ERROR.UNKNOWN));
        }



        //隐藏dialog
        dismissProgressDialog();

    }

    public abstract void onError(ExceptionHandle.ResponeThrowable responeThrowable);

    @Override
    public void onNext(T t) {

    }

    private void showProgressDialog() {
        if (progressDialog != null) {
//            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
//            progressDialog.setMessage("正在加载中......");
            progressDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if ( progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
