package com.gxg.alltils.projectallutils.model.loginregister;

import android.app.Dialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.bean.JsonBean;
import com.gxg.alltils.projectallutils.imageloader.ImageLoader;
import com.gxg.alltils.projectallutils.imageloader.ImageLoaderUtil;
import com.gxg.alltils.projectallutils.utils.Contants;
import com.gxg.alltils.projectallutils.utils.GetJsonDataUtil;
import com.gxg.alltils.projectallutils.utils.SharedPreferencesUtils;
import com.gxg.alltils.projectallutils.utils.WeakHandler;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.socks.library.KLog;

import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gxg.alltils.projectallutils.R.id.img_avatar;
import static com.gxg.alltils.projectallutils.utils.Contants.IMG_AVATAR;

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
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_time)
    TextView tvTime;
    private Dialog mCameraDialog;//底部弹框
    private TakePhoto mPhoto;
    private int limit = 1;//最多选择几张
    //裁剪时的宽高
    int height = 200;
    int width = 200;
    private Uri mImageUri;
    private static final int MSG_LOAD_DATA = 0x0001;//获取城市列表的数据
    private Thread thread;
    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what){
                case MSG_LOAD_DATA:
                    if (thread==null){//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }

                    break;
            }

            return true;
        }
    });


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
        String img_path = SharedPreferencesUtils.get(InformationActivity.this, IMG_AVATAR, "").toString();

        new ImageLoaderUtil().loadCircleImg(this,new ImageLoader.Builder().imgView(imgAvatar).url(new File(img_path)).errorHolder(R.drawable.ic_me_touxiang).placeHolder(R.drawable.ic_me_touxiang).build(),2,getResources().getColor(R.color.white));

    }

    protected void initData() {
        initTimePicker();//初始化时间选择器
        handler.sendEmptyMessage(MSG_LOAD_DATA);//获取省市选择列表的数据
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_time,R.id.tv_address,R.id.img_avatar, R.id.tv_username, R.id.tv_sex, R.id.tv_phone, R.id.tv_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address://选择地址
                ShowPickerView();
                break;
            case R.id.tv_time://时间选择
                pvTime.show();
                break;
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
     * -------------------------------------------------------------时间选择相关-------------------------------------------------------------------
     */
    private TimePickerView pvTime;
    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2030, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                tvTime.setText(getTime(date));
//                Button btn = (Button) v;
//                btn.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * -------------------------------------------------------------城市选择相关-------------------------------------------------------------------
     */

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);

                tvAddress.setText(tx);
                Toast.makeText(InformationActivity.this,tx,Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }

    //解析数据
    private void initJsonData() {

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        KLog.e("sss省市列表读取成功");

    }

    //Gson 解析
    public ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            KLog.e("sss转换失败"+e.toString());
        }
        return detail;
    }


    /**
     * -------------------------------------------------------------相机相册相关-------------------------------------------------------------------
     */

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

        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();

        //使用takephoto自带相册//false使用系统的
        builder.setWithOwnGallery(true);
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
            KLog.e("sss", "size==1"+images.get(0).getCompressPath());
            SharedPreferencesUtils.put(InformationActivity.this,Contants.IMG_AVATAR,images.get(0).getCompressPath());
            new ImageLoaderUtil().loadCircleImg(this,new ImageLoader.Builder().imgView(imgAvatar).url(new File(images.get(0).getCompressPath())).errorHolder(R.drawable.ic_me_touxiang).placeHolder(R.drawable.ic_me_touxiang).build(),2,getResources().getColor(R.color.white));
        } else if (images.size() > 1) {
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
