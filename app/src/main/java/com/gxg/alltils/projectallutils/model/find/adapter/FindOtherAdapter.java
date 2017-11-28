package com.gxg.alltils.projectallutils.model.find.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.model.find.bean.FindOtherBean;
import com.gxg.alltils.projectallutils.model.home.activity.SearchGoodResultActivity;
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
    protected void convert(BaseViewHolder helper, final FindOtherBean.DatasBean.ClassListBean item) {
        helper.setText(R.id.tv_find_other_title,item.getGc_name());
        MyGridView gridView = helper.getView(R.id.gv_fm_find_other);
        FindOtherGridAdapter findOtherGridAdapter = new FindOtherGridAdapter(item.getChild(),mContext);
        gridView.setAdapter(findOtherGridAdapter);

        helper.addOnClickListener(R.id.tv_find_other_title);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, SearchGoodResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(SearchGoodResultActivity.SEARCH_GC_ID,item.getChild().get(position).getGc_id());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
