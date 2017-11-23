package com.gxg.alltils.projectallutils.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;


/**
 * 更多
 */
public class TopLayout extends LinearLayout {

    private TextView tvTitle;
    private TextView tvDesc;
    private ImageView ivMore;

    private String tvTitleString;
    private String tvDescString;

    private OnActionClickListener onClickListener;

    public interface OnActionClickListener {

        /**
         * 点击更多按钮
         */
        void next();
    }

    public TopLayout(Context context) {
        super(context);
        initLayoutView();
    }

    public TopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // 获取自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopLayout);

        this.tvTitleString = typedArray.getString(R.styleable.TopLayout_top_title);
        this.tvDescString = typedArray.getString(R.styleable.TopLayout_top_desc);

        typedArray.recycle();

        initLayoutView();
    }

    /**
     * 初始化topBard的布局
     */
    private void initLayoutView() {

        inflate(getContext(), R.layout.layout_top, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivMore = (ImageView) findViewById(R.id.iv_more);
        tvDesc = (TextView) findViewById(R.id.tv_desc);

        // 设置默认的属性
        tvTitle.setText(tvTitleString);
        tvDesc.setText(tvDescString);

        // 设置事件
        MyClickListener clickListener = new MyClickListener();
        this.setOnClickListener(clickListener);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setTitle(int resId) {
        tvTitle.setText(resId);
    }

    public void setMoreViewVisible(boolean visible) {
        ivMore.setVisibility(visible ? VISIBLE : GONE);
    }


    public void setDesc(String title) {
        tvDesc.setText(title);
    }

    public void setDesc(int resId) {
        tvDesc.setText(resId);
    }


    public void setOnTopBarActionListener(OnActionClickListener mtsclistener) {
        this.onClickListener = mtsclistener;
    }


    private class MyClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.next();
            }
        }
    }
}
