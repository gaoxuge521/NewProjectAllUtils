package com.gxg.alltils.projectallutils.model.shop;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.alltils.projectallutils.model.shop.bean.ShopCardBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/27 09:50
 * 邮箱：android_gaoxuge@163.com
 * 购物车的adapter
 */
public class ShopCardAdapter extends BaseQuickAdapter<ShopCardBean.DatasBean.CartListBean,BaseViewHolder> {

    public ShopCardAdapter(@Nullable List<ShopCardBean.DatasBean.CartListBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCardBean.DatasBean.CartListBean item) {

    }
}
