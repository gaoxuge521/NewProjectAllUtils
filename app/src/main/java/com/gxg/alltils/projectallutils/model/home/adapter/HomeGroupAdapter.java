package com.gxg.alltils.projectallutils.model.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.http.bean.HomeBean;
import com.gxg.alltils.projectallutils.utils.ScreenSizeUtil;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/17 18:55
 * 邮箱：android_gaoxuge@163.com
 */
public class HomeGroupAdapter extends BaseQuickAdapter<HomeBean.DatalistBean.GroupBean, BaseViewHolder> {

    private final int ITEM_DIVIDER;

    public HomeGroupAdapter(@Nullable List<HomeBean.DatalistBean.GroupBean> data) {
        super(R.layout.item_home_group, data);
        ITEM_DIVIDER = (ScreenSizeUtil.getScreenWidth()-ScreenSizeUtil.Dp2Px(20))/3;

    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.DatalistBean.GroupBean item) {

        ImageView imageView = helper.getView(R.id.iv_home_group);
//        LinearLayout ll_item_group = helper.getView(R.id.ll_item_group);
//        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) helper.itemView.getLayoutParams();
//        layoutParams.width = ITEM_DIVIDER;
//        layoutParams.height = ITEM_DIVIDER;
//        imageView.setLayoutParams(layoutParams);
//        layoutParams.setMargins(ScreenSizeUtil.Dp2Px(mContext,5),0,0,0);
        Glide.with(mContext).load(item.getContent_value()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
