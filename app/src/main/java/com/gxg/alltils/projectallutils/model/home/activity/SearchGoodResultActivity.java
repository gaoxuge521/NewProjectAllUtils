package com.gxg.alltils.projectallutils.model.home.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.socks.library.KLog;
import com.yyydjk.library.DropDownMenu;

import butterknife.Bind;

public class SearchGoodResultActivity extends BaseActivity {

    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @Bind(R.id.rv_search)
    RecyclerView rvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_good_result;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String search_key = getIntent().getStringExtra(SearchActivity.SEARCH_VALUE);
        KLog.e("sss" + search_key);
    }

    @Override
    protected void initListener() {

    }
}
