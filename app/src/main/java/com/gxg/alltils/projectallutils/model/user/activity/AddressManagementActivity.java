package com.gxg.alltils.projectallutils.model.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.http.HttpHelper;
import com.gxg.alltils.projectallutils.model.user.adapter.AddressListAdapter;
import com.gxg.alltils.projectallutils.model.user.bean.AddressListManagerBean;
import com.gxg.alltils.projectallutils.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class AddressManagementActivity extends BaseActivity {

    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.ll_right)
    LinearLayout llRight;
    @Bind(R.id.rv_address)
    RecyclerView rvAddress;
    @Bind(R.id.btn_add_address)
    Button btn_add_address;
    private AddressListManagerBean addressListManagerBean;
    private AddressListAdapter addressListAdapter;

    public static final String USERNAME="username";
    public static final String USERPHONE="userphone";
    public static final String AREA="area";
    public static final String ADDRESS="address";
    public static final String ISDEFAULT="isdefault";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_management;
    }

    @Override
    protected void initView() {
        //右边的加号显示出来
        llRight.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);
        title.setText("地址管理");

        //设置地址的空数据
        rvAddress.setLayoutManager(new LinearLayoutManager(AddressManagementActivity.this));
        addressListAdapter = new AddressListAdapter(new ArrayList<AddressListManagerBean.DatasBean.AddressListBean>());
        rvAddress.setAdapter(addressListAdapter);





    }

    @Override
    protected void initData() {



        getAddressData();
    }


    /**
     * TODO 获取地址列表信息 假数据 接口出来时删掉
     */
    List<AddressListManagerBean.DatasBean.AddressListBean> addressList = new ArrayList<>();
    public List<AddressListManagerBean.DatasBean.AddressListBean> getAddressList(){
        addressList.clear();
        AddressListManagerBean.DatasBean.AddressListBean bean = new AddressListManagerBean.DatasBean.AddressListBean();
        bean.setAddress("天通中苑23号楼6单元606");
        bean.setTrue_name("高续铬");
        bean.setMob_phone("15735804834");
        bean.setArea_info("北京 北京市 东城区");
        addressList.add(bean);


        AddressListManagerBean.DatasBean.AddressListBean bean2 = new AddressListManagerBean.DatasBean.AddressListBean();
        bean2.setAddress("天通中苑23号楼6单元606哈哈哈哈哈哈和所得税的范范");
        bean2.setTrue_name("高续铬三大傻发发发送方法是");
        bean2.setMob_phone("15735804834");
        bean2.setArea_info("北京 北京市 东城区");
        addressList.add(bean2);

        return addressList;
    }

    /**
     * 获取地址列表信息
     */
    private void getAddressData() {
        Map<String,Object> map = new HashMap<>();
        map.put("act","member_address");
        map.put("op","address_list");

        HttpHelper.getInstance().request(HttpHelper.BASEURL,map, new HttpHelper.HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                addressListManagerBean = GsonUtil.GsonToObject(result, AddressListManagerBean.class);

                addressListAdapter.setNewData(getAddressList());


            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    protected void initListener() {
        addressListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.tv_delete:
                        showToastShort("删除");
                        break;
                    case R.id.tv_edit:
                        showToastShort("编辑");
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_add_address,R.id.ll_left, R.id.ll_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_right:
                toAddAddressActivity();
                break;
            case R.id.btn_add_address:
                toAddAddressActivity();
                break;
        }
    }

    //跳转添加收货地址页的requestCode
    public static final int ADDADDRESS = 0001;
    /**
     * 跳转添加收货地址页
     */
    private void toAddAddressActivity() {
        Intent intent = new Intent(this,AddAddressActivity.class);
        startActivityForResult(intent,ADDADDRESS);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_on);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //添加收货地址
        if(requestCode==ADDADDRESS){
            try {
                String username = data.getStringExtra(USERNAME);
                String userphone = data.getStringExtra(USERPHONE);
                String area = data.getStringExtra(AREA);
                String address = data.getStringExtra(ADDRESS);
                boolean isdefault = data.getBooleanExtra(ISDEFAULT, false);


                AddressListManagerBean.DatasBean.AddressListBean addressListBean = new AddressListManagerBean.DatasBean.AddressListBean();
                addressListBean.setTrue_name(username);
                addressListBean.setTel_phone(userphone);
                addressListBean.setArea_info(area);
                addressListBean.setAddress(address);
                if(isdefault){
                    addressListBean.setIs_default("0");
                }else{
                    addressListBean.setIs_default("1");
                }

                List<AddressListManagerBean.DatasBean.AddressListBean> list = new ArrayList<>();
                list.add(addressListBean);
                addressListAdapter.addData(list);



            } catch (Exception e) {
            }

        }
    }
}
