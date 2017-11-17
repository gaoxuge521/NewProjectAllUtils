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
public class HomeAbzyAdapter extends BaseQuickAdapter<HomeBean.DatalistBean.AbzyBean,BaseViewHolder> {

    public HomeAbzyAdapter(@Nullable List<HomeBean.DatalistBean.AbzyBean> data) {
        super(R.layout.item_abzy_shop, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.DatalistBean.AbzyBean item) {
        helper.setText(R.id.tv_home_abzy,item.getContent_name());
        ImageView imageView = helper.getView(R.id.iv_home_abzy);
        Glide.with(mContext).load(item.getContent_value()).into(imageView);
    }
}
