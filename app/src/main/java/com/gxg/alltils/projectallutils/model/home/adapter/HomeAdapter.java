package com.gxg.alltils.projectallutils.model.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.bean.HomeListBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/17 18:13
 * 邮箱：android_gaoxuge@163.com
 */
public class HomeAdapter extends BaseQuickAdapter<HomeListBean.DatalistBean.RecommendBean,BaseViewHolder> {
    public HomeAdapter(@Nullable List<HomeListBean.DatalistBean.RecommendBean> data) {
        super(R.layout.item_home_rv,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeListBean.DatalistBean.RecommendBean item) {

    }
}
