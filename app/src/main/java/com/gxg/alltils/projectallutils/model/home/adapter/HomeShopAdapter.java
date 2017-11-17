package com.gxg.alltils.projectallutils.model.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.http.bean.HomeBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/17 18:55
 * 邮箱：android_gaoxuge@163.com
 */
public class HomeShopAdapter extends BaseQuickAdapter<HomeBean.DatalistBean.FastBean,BaseViewHolder> {

    public HomeShopAdapter( @Nullable List<HomeBean.DatalistBean.FastBean> data) {
        super(R.layout.item_home_shop, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.DatalistBean.FastBean item) {
        helper.setText(R.id.tv_home_shop,item.getContent_txt());
        ImageView imageView = helper.getView(R.id.iv_home_shop);
        Glide.with(mContext).load(item.getContent_value()).into(imageView);
    }
}
