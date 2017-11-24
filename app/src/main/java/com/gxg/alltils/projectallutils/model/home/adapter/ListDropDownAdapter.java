package com.gxg.alltils.projectallutils.model.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.socks.library.KLog;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/24 09:01
 * 邮箱：android_gaoxuge@163.com
 */
public class ListDropDownAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public ListDropDownAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = View.inflate(context, R.layout.item_list_drop_down,null);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            holder.iv_check = (ImageButton) convertView.findViewById(R.id.iv_check);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        fillValue(position,holder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.textView.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.textView.setTextColor(context.getResources().getColor(R.color.red));
                viewHolder.iv_check.setVisibility(View.VISIBLE);
            } else {
                viewHolder.textView.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.iv_check.setVisibility(View.GONE);
            }
        }
    }

    public static class ViewHolder{
        TextView textView;
        ImageButton iv_check;
    }
}
