package com.gxg.alltils.projectallutils.model.user.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.model.user.bean.AddressListManagerBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/28 15:35
 * 邮箱：android_gaoxuge@163.com
 * 收货地址管理的adapter
 */
public class AddressListAdapter  extends BaseQuickAdapter<AddressListManagerBean.DatasBean.AddressListBean,BaseViewHolder>{
    public AddressListAdapter(@Nullable List<AddressListManagerBean.DatasBean.AddressListBean> data) {
        super(R.layout.item_address_manager,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressListManagerBean.DatasBean.AddressListBean item) {
        helper.setText(R.id.tv_name,item.getTrue_name());
        helper.setText(R.id.tv_phone,item.getMob_phone());
        helper.setText(R.id.tv_address_info,item.getArea_info()+" "+item.getAddress());
        CheckBox checkBox = helper.getView(R.id.cx_defaule_address);
        if(!TextUtils.isEmpty(item.getIs_default())){
            if(item.getIs_default().equals("0")){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }
        }else{
            checkBox.setChecked(false);
        }


        helper.addOnClickListener(R.id.tv_delete);//删除的点击事件
        helper.addOnClickListener(R.id.tv_edit);//编辑的点击事件
    }
}
