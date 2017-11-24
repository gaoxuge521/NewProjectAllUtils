package com.gxg.alltils.projectallutils.model.find.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.model.find.bean.FindOtherBean;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/24 16:03
 * 邮箱：android_gaoxuge@163.com
 */
public class FindOtherGridAdapter extends BaseAdapter {
    private List<FindOtherBean.DatasBean.ClassListBean.ChildBean > childBeanList;
    private Context mContext;

    public FindOtherGridAdapter(List<FindOtherBean.DatasBean.ClassListBean.ChildBean> childBeanList, Context mContext) {
        this.childBeanList = childBeanList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return childBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return childBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_find_other_grid,null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_find_other_grid_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(childBeanList.get(position).getGc_name());
        return convertView;
    }

    static class ViewHolder{
        TextView textView;
    }
}
