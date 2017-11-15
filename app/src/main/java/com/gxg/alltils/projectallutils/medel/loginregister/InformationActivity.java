package com.gxg.alltils.projectallutils.medel.loginregister;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.imageloader.GlideCircleTransform;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gxg.alltils.projectallutils.R.id.img_avatar;

/**
 * 个人信息
 */
public class InformationActivity extends TakePhotoActivity {

    @Bind(img_avatar)
    ImageView imgAvatar;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_email)
    TextView tvEmail;
    private Dialog mCameraDialog;//底部弹框

    private TakePhoto mPhoto;
    private int limit = 1;//最多选择几张
    //裁剪时的宽高
    int height = 200;
    int width = 200;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iinformation);

        ButterKnife.bind(this);
        initView();
        initData();
    }



    protected void initView() {
        mPhoto = getTakePhoto();
        Glide.with(this).load(R.drawable.ic_my_avatar).transform(new GlideCircleTransform(this)).into(imgAvatar);
//        Glide.with(this).load(R.drawable.ic_my_avatar).bitmapTransform(new GlideCircleTransform(this, ScreenSizeUtil.Dp2Px(this,30),getResources().getColor(R.color.white))).into(imgAvatar);
    }

    protected void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({img_avatar, R.id.tv_username, R.id.tv_sex, R.id.tv_phone, R.id.tv_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case img_avatar://重新选择头像
                showSelectDialog();
                break;
            case R.id.tv_username:
                break;
            case R.id.tv_sex:
                break;
            case R.id.tv_phone:
                break;
            case R.id.tv_email:
                break;
        }
    }



    /**
     * 从相机进带裁剪
     */
    private void goPhoto() {
        setAllParmeter();
        //从相机进带裁剪
        mPhoto.onPickFromCaptureWithCrop(mImageUri, getCropOptions());
        //从相机进不带裁剪
//        mPhoto.onPickFromCapture(mImageUri);
    }
    /**
     * 进相册
     */
    private void gopicture() {
        setAllParmeter();
        //设置选择的图片的多少和裁剪的属性
        limit = 1;

        if (limit > 1) {
            mPhoto.onPickMultipleWithCrop(limit, getCropOptions());
            return;
        }
        //只设置选择图片的数量，不裁剪
//        mPhoto.onPickMultiple(limit);
//        //从文件夹选择带裁剪
//        mPhoto .onPickFromDocumentsWithCrop(imageUri,getCropOptions());
//        //从文件夹选择不带裁剪
//        mPhoto.onPickFromDocuments();
        //从相册选择带裁剪
        mPhoto.onPickFromGalleryWithCrop(mImageUri, getCropOptions());
        //从相册选择不带裁剪
//        mPhoto.onPickFromGallery();
    }
    //设置公共的属性
    public void setAllParmeter() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        mImageUri = Uri.fromFile(file);
        configCompress(mPhoto);
        configTakePhotoOption(mPhoto);
    }
    //设置
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();

        //使用takephoto自带相册//false使用系统的
        builder.setWithOwnGallery(true);
        //纠正拍照的照片的旋转角度
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }

    //压缩的配置
    private void configCompress(TakePhoto takePhoto) {
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

        takePhoto.onEnableCompress(config, showProgressBar);
    }

    //设置裁剪功能的属性
    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();

//        builder.setAspectX(width).setAspectY(height);//宽*高
        builder.setOutputX(width).setOutputY(height);//宽/高

        builder.setWithOwnCrop(true);//true使用takephoto自带的裁剪工具  false使用系统的裁剪工具
        return builder.create();

    }
    /**
     *
     */
    @Override
    public void takeCancel() {
        super.takeCancel();

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

    private void showImg(ArrayList<TImage> images) {
        KLog.e("sss", images.toString());
        KLog.e("sss", images.size() + "  ");

        if (images.size() == 1) {
            KLog.e("sss", "size==1");
//            new ImageLoaderUtil().loadImage(InformationActivity.this, new ImageLoader.Builder().url(images.get(0))
//                    .imgView(imageView).strategy(ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI).build());
//            mIvImg.setVisibility(View.VISIBLE);
//            mRvPicture.setVisibility(View.GONE);
            Glide.with(InformationActivity.this).load(new File(images.get(0).getCompressPath())).bitmapTransform(new GlideCircleTransform(InformationActivity.this)).into(imgAvatar);

        } else if (images.size() > 1) {
//            mIvImg.setVisibility(View.GONE);
//            mRvPicture.setVisibility(View.VISIBLE);
//            mAdapter.setNewData(images);
            KLog.e("sss", "size>1");
        } else {
            KLog.e("sss", "........");
        }
    }

    /**
     * 弹出dialog
     */
    private void showSelectDialog() {
        mCameraDialog = new Dialog(this, R.style.my_dialog);
        mCameraDialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog不消失是false  ，消失是true
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.layout_camera_dialog, null);
        Button btn_open_camera = (Button) root.findViewById(R.id.btn_open_camera);
        btn_open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto();
                mCameraDialog.dismiss();
            }
        });
        Button btn_choose_img = (Button)  root.findViewById(R.id.btn_choose_img);
        btn_choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gopicture();
                mCameraDialog.dismiss();
            }
        });
        Button btn_cancel = (Button)  root.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraDialog.dismiss();
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }
}
