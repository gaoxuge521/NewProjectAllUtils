package com.gxg.alltils.projectallutils.model.shop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.event.ShowHomeEvent;
import com.gxg.alltils.projectallutils.http.utils.RxBus;
import com.gxg.alltils.projectallutils.model.shop.bean.ShopCardBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 作者：Administrator on 2017/11/14 17:45
 * 邮箱：android_gaoxuge@163.com
 */
public class ShopFragment extends BaseFragment {
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ll_right)
    LinearLayout llRight;
    @Bind(R.id.rv_shop)
    RecyclerView rvShop;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    private ShopCardAdapter shopCardAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_shop;
    }

    @Override
    public void setupViews(View view) {
        String id = getArguments().getString("id", "");

        KLog.e(id);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        llLeft.setVisibility(View.GONE);
        title.setText("购物车");
    }


    private void initData() {
        rvShop.setLayoutManager(new LinearLayoutManager(getActivity()));
        shopCardAdapter = new ShopCardAdapter(new ArrayList<ShopCardBean.DatasBean.CartListBean>());
        rvShop.setAdapter(shopCardAdapter);
        shopCardAdapter.setEmptyView(getEmptyView());

    }


    private void initListener() {
        btnLookingAround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToastShort("点击");
                RxBus.getDefault().post(new ShowHomeEvent(ShowHomeEvent.NAME,1));
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        } else {

        }
    }




    public static ShopFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id", id);
        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
