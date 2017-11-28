package com.gxg.alltils.projectallutils.model.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 商品筛选，搜索页面
 */
public class FilterActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.ll_right)
    LinearLayout llRight;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_filter;
    }

    @Override
    protected void initView() {
        title.setText("商品筛选");
        llRight.setVisibility(View.VISIBLE);
        tvRight.setText("重置");
        tvRight.setTextColor(getResources().getColor(R.color.red));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.ll_left, R.id.rl_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                break;
            case R.id.rl_title:
                break;
        }
    }
}
