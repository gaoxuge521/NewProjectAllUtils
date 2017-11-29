package com.gxg.alltils.projectallutils.umeng;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;


/**
 * @author tangyang
 */
public class ShareDialog extends Dialog {
    private static final int[] images =
            {
                    R.drawable.umeng_socialize_qq,
                    R.drawable.umeng_socialize_wechat,
                    R.drawable.umeng_socialize_sina,
                    R.drawable.umeng_socialize_qzone,
                    R.drawable.umeng_socialize_wxcircle
            };
    private String[] shares;
    private LayoutInflater inflater;
    private ShareUtil shareUtil;

    //qq 微信 微博 QQ空间 朋友圈
    public ShareDialog(Context context) {
        super(context);
    }

    public ShareDialog(Context context, String url, UMShareListener listener) {
        super(context);
        shareUtil = new ShareUtil((Activity) context, url, listener);
        inflater = LayoutInflater.from(context);
        shares = context.getResources().getStringArray(R.array.shares);
    }

    public ShareDialog(Context context, String url, String imageUrl, UMShareListener listener) {
        super(context);
        shareUtil = new ShareUtil((Activity) context, url, imageUrl, listener);
        inflater = LayoutInflater.from(context);
        shares = context.getResources().getStringArray(R.array.shares);
    }

    public ShareDialog(Context context, String title, String content, String url, String imageUrl, Integer drawable, UMShareListener listener) {
        super(context);
        shareUtil = new ShareUtil((Activity) context, title, content, url, imageUrl, drawable, listener);
        inflater = LayoutInflater.from(context);
        shares = context.getResources().getStringArray(R.array.shares);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_share);
        GridView mGv = (GridView) findViewById(R.id.gv_share);

        ShareAdapter adapter = new ShareAdapter();
        mGv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mGv.setAdapter(adapter);

        mGv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        shareUtil.toShare(SHARE_MEDIA.QQ);

                        break;
                    case 1:
                        shareUtil.toShare(SHARE_MEDIA.WEIXIN);

                        break;
                    case 2:
                        shareUtil.toShare(SHARE_MEDIA.SINA);
                        break;
                    case 3:
                        shareUtil.toShare(SHARE_MEDIA.QZONE);
                        break;
                    case 4:
                        shareUtil.toShareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                }

                dismiss();
            }
        });
    }

    private class ShareAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (null == convertView) {
                vh = new ViewHolder();
                convertView = inflater.inflate(
                        R.layout.layout_share_dialog, null);
                vh.mIv = (ImageView) convertView
                        .findViewById(R.id.iv_share_dialog);
                vh.mTv = (TextView) convertView
                        .findViewById(R.id.tv_share_dialog);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.mIv.setImageResource(images[position]);
            vh.mTv.setText(shares[position]);
            return convertView;
        }

        class ViewHolder {
            ImageView mIv;
            TextView mTv;
        }
    }
}


