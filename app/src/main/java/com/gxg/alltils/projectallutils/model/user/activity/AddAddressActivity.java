package com.gxg.alltils.projectallutils.model.user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.bean.JsonBean;
import com.gxg.alltils.projectallutils.utils.EmojiFilter;
import com.gxg.alltils.projectallutils.utils.GetJsonDataUtil;
import com.gxg.alltils.projectallutils.utils.VerifyUtils;
import com.gxg.alltils.projectallutils.utils.WeakHandler;
import com.socks.library.KLog;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：Administrator on 2017/11/28 16:18
 * 邮箱：android_gaoxuge@163.com
 */
public class AddAddressActivity extends BaseActivity {

    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.et_receiver_name)
    EditText etReceiverName;
    @Bind(R.id.et_receiver_phone)
    EditText etReceiverPhone;
    @Bind(R.id.tv_select_address)
    TextView tvSelectAddress;
    @Bind(R.id.et_receiver_detailed_address)
    EditText etReceiverDetailedAddress;
    @Bind(R.id.cx_defaule_address)
    CheckBox cxDefauleAddress;
    @Bind(R.id.btn_save_address)
    Button btnSaveAddress;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView() {
        //过滤表情符
        etReceiverName.setFilters(new InputFilter[]{new EmojiFilter()});
        etReceiverDetailedAddress.setFilters(new InputFilter[]{new EmojiFilter()});
    }

    @Override
    protected void initData() {
        //获取省市选择列表的数据
        handler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    protected void initListener() {

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
                String tx = options1Items.get(options1).getPickerViewText()+" "+
                        options2Items.get(options1).get(options2)+" "+
                        options3Items.get(options1).get(options2).get(options3);

                tvSelectAddress.setText(tx);
                Toast.makeText(AddAddressActivity.this,tx,Toast.LENGTH_SHORT).show();
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
     * -------------------------------------------------------------城市选择相关-------------------------------------------------------------------
     */


    @OnClick({R.id.ll_left, R.id.tv_select_address, R.id.cx_defaule_address, R.id.btn_save_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left://返回
                finish();
                break;
            case R.id.tv_select_address://选择地址
                closeInputMethod();
                ShowPickerView();
                break;
            case R.id.cx_defaule_address:
                break;
            case R.id.btn_save_address://保存地址
                saveAddress();
                break;
        }
    }

    /**
     * 保存地址
     */
    private void saveAddress() {
        String name = etReceiverName.getText().toString();
        String phone = etReceiverPhone.getText().toString();
        String detaileAddress = etReceiverDetailedAddress.getText().toString();
        String selectAddress = tvSelectAddress.getText().toString();
        if(TextUtils.isEmpty(name)){
            showToastShort("用户名不能为空！");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            showToastShort("手机号码不能为空！");
            return;
        }
        if(TextUtils.isEmpty(selectAddress)){
            showToastShort("地区不能为空！");
            return;
        }
        if(TextUtils.isEmpty(detaileAddress)){
            showToastShort("详细地址不能为空！");     return;
        }
        if(!VerifyUtils.isMobileNO(phone)){
            showToastShort("请正确填写联系手机！");     return;
        }

        //带数据跳回去
        Intent intent = new Intent(this,AddressManagementActivity.class);
        intent.putExtra(AddressManagementActivity.USERNAME,name);
        intent.putExtra(AddressManagementActivity.USERPHONE,phone);
        intent.putExtra(AddressManagementActivity.AREA,selectAddress);
        intent.putExtra(AddressManagementActivity.ADDRESS,detaileAddress);
        intent.putExtra(AddressManagementActivity.ISDEFAULT,cxDefauleAddress.isChecked());

        setResult(500,intent);
        finish();
    }
}
