package com.gxg.alltils.projectallutils.model.find.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.model.find.bean.FindOtherBean;
import com.gxg.alltils.projectallutils.widght.MyGridView;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/24 15:53
 * 邮箱：android_gaoxuge@163.com
 */
public class FindOtherAdapter extends BaseQuickAdapter<FindOtherBean.DatasBean.ClassListBean,BaseViewHolder> {
    public FindOtherAdapter(@Nullable List<FindOtherBean.DatasBean.ClassListBean> data) {
        super(R.layout.item_find_other,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindOtherBean.DatasBean.ClassListBean item) {
        helper.setText(R.id.tv_find_other_title,item.getGc_name());
        MyGridView gridView = helper.getView(R.id.gv_fm_find_other);
        FindOtherGridAdapter findOtherGridAdapter = new FindOtherGridAdapter(item.getChild(),mContext);
        gridView.setAdapter(findOtherGridAdapter);


    }
}
