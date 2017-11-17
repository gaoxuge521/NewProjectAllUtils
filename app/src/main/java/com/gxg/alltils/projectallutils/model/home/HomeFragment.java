package com.gxg.alltils.projectallutils.model.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.bean.HomeListBean;
import com.gxg.alltils.projectallutils.http.RetrofitServiceManager;
import com.gxg.alltils.projectallutils.http.bean.HomeBean;
import com.gxg.alltils.projectallutils.model.LocalImageHolderView;
import com.gxg.alltils.projectallutils.model.home.adapter.HomeAbzyAdapter;
import com.gxg.alltils.projectallutils.model.home.adapter.HomeAdapter;
import com.gxg.alltils.projectallutils.model.home.adapter.HomeShopAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * 作者：Administrator on 2017/11/14 17:45
 * 邮箱：android_gaoxuge@163.com
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rv_head)
    RecyclerView rvHead;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    private View view;
    private HomeAdapter homeAdapter;
    private HomeBean mHomeBean;
    private HomeShopAdapter homeShopAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager abzyGridLayoutManager;
    private HomeAbzyAdapter homeAbzyAdapter;


    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void setupViews(View view) {
        String id = getArguments().getString("id", "");
        KLog.e(id);
        //初始化view
        initView();
        //初始化数据
        initData();
        //设置点击事件
        initListener();
        //请求数据
        getHomeData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        } else {

        }
    }

    private void initListener() {
        homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

              page++;
              getHomeList(page);

            }
        },rvHead);
    }

    private void initData() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(),5);
        abzyGridLayoutManager = new GridLayoutManager(getActivity(),3);
        //首页的主adapter
        rvHead.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(new ArrayList<HomeListBean.DatalistBean.RecommendBean>());
        homeAdapter.addHeaderView(getHeadView());
        rvHead.setAdapter(homeAdapter);


        //商品分类列表的adapter
        rvShopType.setLayoutManager(gridLayoutManager);
        homeShopAdapter = new HomeShopAdapter(new ArrayList<HomeBean.DatalistBean.FastBean>());
        rvShopType.setAdapter(homeShopAdapter);

        //9个功分类
        rvAbzy.setLayoutManager(abzyGridLayoutManager);
        homeAbzyAdapter = new HomeAbzyAdapter(new ArrayList<HomeBean.DatalistBean.AbzyBean>());
        rvAbzy.setAdapter(homeAbzyAdapter);

    }

    private void initView() {

        title.setText("首页");

    }

    public static HomeFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id", id);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取数据，接口通过抓包得到
     */
    private void getHomeData() {
        Map<String, Object> map = new HashMap<>();
        map.put("com", "com_appService");
        map.put("method", "appSev");
        map.put("app_com", "com_app");
        map.put("task", "getindexad");

        RetrofitServiceManager.getInstance().getHomeData(new Subscriber<HomeBean>() {
            @Override
            public void onCompleted() {
                KLog.e("sss结束");
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("sss失败" + e.toString());
            }

            @Override
            public void onNext(HomeBean homeBean) {
                KLog.e("sss成功" + homeBean.toString());

                mHomeBean = homeBean;
                setHeadData();

                getHomeList(page);
            }
        }, map);
    }

    private List<String> imgs = new ArrayList<>();
    private void setHeadData() {
        if(mHomeBean!=null){
            //轮播图
            List<HomeBean.DatalistBean.RollBean> roll = mHomeBean.getDatalist().getRoll();
            if(roll!=null && roll.size()>0){
                imgs.clear();
                for (int i = 0; i < roll.size(); i++) {
                    imgs.add(roll.get(i).getContent_value());
                }
                if(imgs.size()>1){
                    bannerHome.setCanLoop(true);
                }else{
                    bannerHome.setCanLoop(false);
                }
                bannerHome.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                },imgs) //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.drawable.icon_banner_nomal, R.drawable.icon_banner_select});

            }
            //商品分类
            if(mHomeBean.getDatalist().getFast().size()>0){
                rvShopType.setVisibility(View.VISIBLE);
                homeShopAdapter.setNewData(mHomeBean.getDatalist().getFast());
            }else{
                rvShopType.setVisibility(View.GONE);
            }


            //推送消息
            if(mHomeBean.getDatalist().getNews().size()>0){
                tvChange.setVisibility(View.VISIBLE);
                tvChange.setText(mHomeBean.getDatalist().getNews().get(0).getContent_name());
            }else{
                tvChange.setVisibility(View.GONE);
            }

            //9个功能分类
            if(mHomeBean.getDatalist().getAbzy().size()>0){
                rvAbzy.setVisibility(View.VISIBLE);
                homeAbzyAdapter.setNewData(mHomeBean.getDatalist().getAbzy());
            }else{
                rvAbzy.setVisibility(View.GONE);
            }



        }
    }

    private int page = 1;
    /**
     * 获取首页列表数据
     */
    private void getHomeList(final int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("com", "com_appService");
        map.put("method", "appSev");
        map.put("app_com", "com_app");
        map.put("task", "indexRecommend");
        map.put("page", page);

        RetrofitServiceManager.getInstance().getHomeList(new Subscriber<HomeListBean>() {
            @Override
            public void onCompleted() {
                homeAdapter.loadMoreComplete();
            }

            @Override
            public void onError(Throwable e) {
                homeAdapter.loadMoreComplete();
            }

            @Override
            public void onNext(HomeListBean homeBean) {

                if(page==1){
                    homeAdapter.setNewData(homeBean.getDatalist().getRecommend());
                }else{
                    homeAdapter.addData(homeBean.getDatalist().getRecommend());
                }


                if(homeBean.getDatalist().getRecommend().size()==0 || homeBean.getDatalist().getRecommend().size()<10){
                    homeAdapter.setEnableLoadMore(false);
                }
            }
        },map);
    }

    /**
     * --------------------------头布局--------------------------------------------------------------------
     */
    ConvenientBanner bannerHome;
    RecyclerView rvShopType;
    TextView tvChange;
    RecyclerView rvAbzy;
    RecyclerView rvGroup;
    LinearLayout llZdGroup;
    ImageView ivZdOne;
    ImageView ivZdTwo;
    ImageView ivZdThree;
    ImageView ivZdFour;
    LinearLayout llKqGroup;
    ImageView ivKqOne;
    ImageView ivKqTwo;
    ImageView ivKqThree;
    ImageView ivKqFour;
    ImageView ivKqFive;
    ImageView ivKqSix;
    ImageView ivKqSeven;
    LinearLayout llRmGroup;
    ImageView ivRmOne;
    ImageView ivRmTwo;
    ImageView ivRmThree;
    ImageView ivRmFour;
    ImageView ivRmFive;
    ImageView ivRmSix;

    private View getHeadView() {
        View convertView = View.inflate(getActivity(), R.layout.home_head, null);
        bannerHome = (ConvenientBanner) convertView.findViewById(R.id.banner_home);
        rvShopType = (RecyclerView) convertView.findViewById(R.id.rv_shop_type);
        tvChange = (TextView) convertView.findViewById(R.id.tv_change);
        rvAbzy = (RecyclerView) convertView.findViewById(R.id.rv_abzy);
        rvGroup = (RecyclerView) convertView.findViewById(R.id.rv_group);
        llZdGroup = (LinearLayout) convertView.findViewById(R.id.ll_zd_group);
        ivZdOne = (ImageView) convertView.findViewById(R.id.iv_zd_one);
        ivZdTwo = (ImageView) convertView.findViewById(R.id.iv_zd_two);
        ivZdThree = (ImageView) convertView.findViewById(R.id.iv_zd_three);
        ivZdFour = (ImageView) convertView.findViewById(R.id.iv_zd_four);
        llKqGroup = (LinearLayout) convertView.findViewById(R.id.ll_kq_group);
        ivKqOne = (ImageView) convertView.findViewById(R.id.iv_kq_one);
        ivKqTwo = (ImageView) convertView.findViewById(R.id.iv_kq_two);
        ivKqThree = (ImageView) convertView.findViewById(R.id.iv_kq_three);
        ivKqFour = (ImageView) convertView.findViewById(R.id.iv_kq_four);
        ivKqFive = (ImageView) convertView.findViewById(R.id.iv_kq_five);
        ivKqSix = (ImageView) convertView.findViewById(R.id.iv_kq_six);
        ivKqSeven = (ImageView) convertView.findViewById(R.id.iv_kq_seven);
        llRmGroup = (LinearLayout) convertView.findViewById(R.id.ll_rm_group);
        ivRmOne = (ImageView) convertView.findViewById(R.id.iv_rm_one);
        ivRmTwo = (ImageView) convertView.findViewById(R.id.iv_rm_two);
        ivRmThree = (ImageView) convertView.findViewById(R.id.iv_rm_three);
        ivRmFour = (ImageView) convertView.findViewById(R.id.iv_rm_four);
        ivRmFive = (ImageView) convertView.findViewById(R.id.iv_rm_five);
        ivRmSix = (ImageView) convertView.findViewById(R.id.iv_rm_six);

        return convertView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
