package com.gxg.alltils.projectallutils.model.find.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.model.find.bean.FindTopBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/24 15:03
 * 邮箱：android_gaoxuge@163.com
 */
public class FindTopAdapter extends BaseAdapter {
    private List<FindTopBean.DatasBean.BrandListBean> brandList;
    private Context mContext;

    public FindTopAdapter(Context mContext ,List<FindTopBean.DatasBean.BrandListBean> brandList) {
        this.brandList = brandList;
        this.mContext = mContext;
    }

    public List<FindTopBean.DatasBean.BrandListBean> getBrandList() {
        return brandList;
    }

    @Override
    public int getCount() {
        return brandList.size();
    }

    @Override
    public Object getItem(int position) {
        return brandList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_find_top,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_find_top);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(brandList.get(position).getBrand_pic()).into(viewHolder.imageView);
        return convertView;
    }
    static class ViewHolder{
        ImageView imageView;
    }
}
