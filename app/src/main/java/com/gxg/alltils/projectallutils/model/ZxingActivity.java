package com.gxg.alltils.projectallutils.model;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.utils.WeakHandler;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zxing.QRCodeDecoder;
import zxing.QRCodeView;
import zxing.ZXingView;

public class ZxingActivity extends TakePhotoActivity implements QRCodeView.Delegate {

    @Bind(R.id.zxing_view)
    ZXingView zxingView;

    @Bind(R.id.cx_sgd)
    CheckBox cxSgd;
    @Bind(R.id.cx_xc)
    CheckBox cxXc;
    @Bind(R.id.ll_other)
    LinearLayout llOther;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;
    private TakePhoto mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();

    }


    protected void initView() {
        title.setText("二维码扫描");
        mPhoto = getTakePhoto();
        KLog.e("sss  打开后置摄像头开始预览  显示扫描框");
        //打开后置摄像头开始预览，但是并未开始识别
        zxingView.startCamera();
        //显示扫描框
        zxingView.showScanRect();

        zxingView.startSpotAndShowRect();
    }

    protected void initData() {

    }

    protected void initListener() {
        zxingView.setDelegate(this);
    }

    @OnClick({R.id.ll_left,R.id.cx_sgd, R.id.cx_xc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.cx_sgd:
                if (cxSgd.isChecked()) {
                    zxingView.openFlashlight();
                } else {
                    zxingView.closeFlashlight();
                }
                break;
            case R.id.cx_xc:

                //关闭摄像头并停止识别
                zxingView.stopCamera();
                zxingView.stopSpot();
                gopicture();
                break;
        }
    }


    @Override
    protected void onStart() {

        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getRunningActivityName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getRunningActivityName());
        MobclickAgent.onPause(this);
    }
    private String getRunningActivityName() {
        String contextString = this.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

    @Override
    protected void onStop() {

        //关闭摄像头预览，并且隐藏扫描框
        zxingView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        ButterKnife.unbind(this);
        //销毁二维码扫描控件
        if (zxingView != null) {
            zxingView.onDestroy();
        }

        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(ZxingActivity.this, "扫描成功" + result, Toast.LENGTH_SHORT).show();
        vibrate();
        //延迟1.5秒后开始识别
        zxingView.startSpot();
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    @Override
    public void onScanQRCodeOpenCameraError() {

        KLog.e("sss  打开相机出错");
    }


    /**
     * -------------------------------------------------------------相机相册相关-------------------------------------------------------------------
     */


    private int limit = 1;//最多选择几张
    //裁剪时的宽高
    int height = 200;
    int width = 200;
    private Uri mImageUri;
//    /**
//     * 从相机进带裁剪
//     */
//    private void goPhoto() {
//        setAllParmeter();
//        //从相机进带裁剪
//        mPhoto.onPickFromCaptureWithCrop(mImageUri, getCropOptions());
//        //从相机进不带裁剪
////        mPhoto.onPickFromCapture(mImageUri);
//    }

    /**
     * 进相册
     */
    private void gopicture() {
        setAllParmeter();
        //设置选择的图片的多少和裁剪的属性
        limit = 1;

//        if (limit > 1) {
//            mPhoto.onPickMultipleWithCrop(limit, getCropOptions());
//            return;
//        }
        //只设置选择图片的数量，不裁剪
//        mPhoto.onPickMultiple(limit);
//        //从文件夹选择带裁剪
//        mPhoto .onPickFromDocumentsWithCrop(imageUri,getCropOptions());
//        //从文件夹选择不带裁剪
//        mPhoto.onPickFromDocuments();
        //从相册选择带裁剪
//        mPhoto.onPickFromGalleryWithCrop(mImageUri, getCropOptions());
        //从相册选择不带裁剪
        mPhoto.onPickFromGallery();
    }

    //设置公共的属性
    public void setAllParmeter() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        mImageUri = Uri.fromFile(file);

        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();

        //使用takephoto自带相册//false使用系统的
        builder.setWithOwnGallery(false);
        //纠正拍照的照片的旋转角度
        builder.setCorrectImage(true);
        mPhoto.setTakePhotoOptions(builder.create());

        //不压缩
//        takePhoto.onEnableCompress(null,false);
//        return ;
        int maxSize = 102400;
        int width = 800;
        int height = 800;
        //显示压缩进度条
        boolean showProgressBar = true;
        //拍照压缩后是否保存原图
        boolean enableRawFile = true;

//        CompressConfig config;

        //压缩工具使用自带的
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();

        //压缩工具使用LuBan
//        LubanOptions option=new LubanOptions.Builder()
//                .setMaxHeight(height)
//                .setMaxWidth(width)
//                .setMaxSize(maxSize)
//                .create();
//        config=CompressConfig.ofLuban(option);
//        config.enableReserveRaw(enableRawFile);

        mPhoto.onEnableCompress(config, showProgressBar);


    }

//    //设置裁剪功能的属性
//    private CropOptions getCropOptions() {
//        CropOptions.Builder builder = new CropOptions.Builder();
////        builder.setAspectX(width).setAspectY(height);//宽*高
//        builder.setOutputX(width).setOutputY(height);//宽/高
//        builder.setWithOwnCrop(true);//true使用takephoto自带的裁剪工具  false使用系统的裁剪工具
//        return builder.create();
//
//    }

    /**
     *
     */
    @Override
    public void takeCancel() {
        super.takeCancel();
        KLog.e("关闭了相册");
        //延时重新打开摄像头，避免摄像头自动对焦失败
        new WeakHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                zxingView.startCamera();
                //显示扫描框
                zxingView.showScanRect();

                zxingView.startSpotAndShowRect();
            }
        },500);

    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }


    private void showImg(final ArrayList<TImage> images) {
        KLog.e("sss", images.toString());
        KLog.e("sss", images.size() + "  ");

        if (images.size() == 1) {
            KLog.e("sss", "size==1");
                /*
//            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
//            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
             */
            //打开摄像头开始识别
            zxingView.startCamera();
            zxingView.startSpotAndShowRect();

            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(images.get(0).getCompressPath());
                }

                @Override
                protected void onPostExecute(String result) {
                    if (TextUtils.isEmpty(result)) {
                        Toast.makeText(ZxingActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ZxingActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        } else if (images.size() > 1) {
            KLog.e("sss", "size>1");
        } else {
            KLog.e("sss", "........");
        }
    }


}
