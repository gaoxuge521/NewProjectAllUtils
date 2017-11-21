package com.gxg.alltils.projectallutils.model.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
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
import com.gxg.alltils.projectallutils.utils.ScreenSizeUtil;
import com.gxg.alltils.projectallutils.widght.NoticeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.iwgang.countdownview.CountdownView;
import rx.Subscriber;

import static com.gxg.alltils.projectallutils.R.id.ll_kq_group;

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
    private HomeAdapter homeAdapter;
    private HomeBean mHomeBean;
    private HomeShopAdapter homeShopAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager abzyGridLayoutManager;
    private HomeAbzyAdapter homeAbzyAdapter;

    private int mWidth;//手机屏幕宽度

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void setupViews(View view) {
        String id = getArguments().getString("id", "");
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

        //获取屏幕的宽度
        mWidth = ScreenSizeUtil.getScreenWidth(getActivity());

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
        rvShopType.setHasFixedSize(true);
        homeShopAdapter = new HomeShopAdapter(new ArrayList<HomeBean.DatalistBean.FastBean>());
        rvShopType.setAdapter(homeShopAdapter);

        //9个功分类
        rvAbzy.setLayoutManager(abzyGridLayoutManager);
        rvAbzy.setHasFixedSize(true);
        homeAbzyAdapter = new HomeAbzyAdapter(new ArrayList<HomeBean.DatalistBean.AbzyBean>());
        rvAbzy.setAdapter(homeAbzyAdapter);

        //手机秒杀设置图片的高度
        ViewGroup.LayoutParams ll_ms_layoutParams = ll_group.getLayoutParams();
        ll_ms_layoutParams.height = mWidth/3;
        ll_group.setLayoutParams(ll_ms_layoutParams);



        //设置值得买首张图片的宽度，屏幕的1/3，高度是宽度的2倍
        ViewGroup.LayoutParams layoutParams = ivZdOne.getLayoutParams();
        layoutParams.width = mWidth/3;
        ivZdOne.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams1 = rl_zd_ivgroup.getLayoutParams();
        layoutParams1.height = mWidth/3*2;
        rl_zd_ivgroup.setLayoutParams(layoutParams1);

        //设置值得买第二张图片的宽高度，屏幕的1/3
        ViewGroup.LayoutParams layoutParams2 = ivZdTwo.getLayoutParams();
        layoutParams2.height = mWidth/3;
        ivZdTwo.setLayoutParams(layoutParams2);


        //设置口腔相关的宽高
        ViewGroup.LayoutParams kq_top_layoutParams = ll_kq_top.getLayoutParams();
        kq_top_layoutParams.height = mWidth/2;
        ll_kq_top.setLayoutParams(kq_top_layoutParams);

        ViewGroup.LayoutParams kq_bottom_layoutParams = ll_kq_botton.getLayoutParams();
        kq_bottom_layoutParams.height = mWidth/3*2;
        ll_kq_botton.setLayoutParams(kq_bottom_layoutParams);

        //设置热门相关宽高
        ViewGroup.LayoutParams ll_rmtop_layoutParams = ll_rm_top.getLayoutParams();
        ll_rmtop_layoutParams.height = mWidth/3;
        ll_rm_top.setLayoutParams(ll_rmtop_layoutParams);
        ViewGroup.LayoutParams ll_rmboton_layoutParams = ll_rm_botton.getLayoutParams();
        ll_rmboton_layoutParams.height = mWidth/3;
        ll_rm_botton.setLayoutParams(ll_rmboton_layoutParams);

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
//                KLog.e("sss结束");
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("sss失败" + e.toString());
            }

            @Override
            public void onNext(HomeBean homeBean) {
//                KLog.e("sss成功" + homeBean.toString());

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
                        .setPageIndicator(new int[]{R.drawable.icon_banner_nomal, R.drawable.icon_banner_select})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
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
                noticeview.setVisibility(View.VISIBLE);
                noticeview.addNotice(mHomeBean.getDatalist().getNews());
            }else{
                noticeview.setVisibility(View.GONE);
            }

            //9个功能分类
            if(mHomeBean.getDatalist().getAbzy().size()>0){
                rvAbzy.setVisibility(View.VISIBLE);
                homeAbzyAdapter.setNewData(mHomeBean.getDatalist().getAbzy());

            }else{
                rvAbzy.setVisibility(View.GONE);
            }


            //手机秒杀，今日特价
            if(mHomeBean.getDatalist().getGroup().size()>0){
//                KLog.e("sss  group"+mHomeBean.getDatalist().getGroup());
//                countdownview.setTag(mHomeBean.getDatalist().getGroup().get(0).getContent_name());
                long time = (long)3* 60 *60* 1000;
                countdownview.start(995550000);
//                countdownview.restart();


//                countdownview.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
//                    @Override
//                    public void onEnd(CountdownView cv) {
//                        KLog.e("sss   倒计时结束");
//                    }
//                });


                ll_group.setVisibility(View.VISIBLE);

                Glide.with(getActivity()).load(mHomeBean.getDatalist().getGroup().get(0).getContent_value()).into(ivGroupOne);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getGroup().get(1).getContent_value()).into(ivGroupTwo);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getGroup().get(2).getContent_value()).into(ivGroupThree);
            }else{
                ll_group.setVisibility(View.GONE);
            }

            //值得买
            if(mHomeBean.getDatalist().getScare()!=null && mHomeBean.getDatalist().getScare().size()>0){
                llZdGroup.setVisibility(View.VISIBLE);

                Glide.with(getActivity()).load(mHomeBean.getDatalist().getScare().get(0).getContent_value()).into(ivZdOne);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getScare().get(1).getContent_value()).into(ivZdTwo);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getScare().get(2).getContent_value()).into(ivZdThree);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getScare().get(3).getContent_value()).into(ivZdFour);
            }else{
                llZdGroup.setVisibility(View.GONE);
            }

            //口腔相关
            if(mHomeBean.getDatalist().getProctg()!=null && mHomeBean.getDatalist().getProctg().size()>0){
                llKqGroup.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(0).getContent_value()).into(ivKqOne);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(1).getContent_value()).into(ivKqTwo);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(2).getContent_value()).into(ivKqThree);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(3).getContent_value()).into(ivKqFour);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(4).getContent_value()).into(ivKqFive);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(5).getContent_value()).into(ivKqSix);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(6).getContent_value()).into(ivKqSeven);
            }else{
                llKqGroup.setVisibility(View.GONE);
            }

            //热门推荐
            mHomeBean.getDatalist().getRecommend();
            if( mHomeBean.getDatalist().getRecommend()!=null &&  mHomeBean.getDatalist().getRecommend().size()>0){
                llRmGroup.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(0).getContent_value()).into(ivRmOne);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(1).getContent_value()).into(ivRmTwo);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(2).getContent_value()).into(ivRmThree);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(3).getContent_value()).into(ivRmFour);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(4).getContent_value()).into(ivRmFive);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(5).getContent_value()).into(ivRmSix);
            }else{
                llRmGroup.setVisibility(View.GONE);
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
    ConvenientBanner bannerHome;//轮播图
    RecyclerView rvShopType;//商品分类列表
//    TextView tvChange;//通知消息
    RecyclerView rvAbzy;//9个分类功能
    LinearLayout ll_group;//热卖特价相关
    ImageView ivGroupOne,ivGroupTwo,ivGroupThree;
    LinearLayout llZdGroup;//值得买相关
    LinearLayout ll_zd_other;
    RelativeLayout rl_zd_ivgroup;
    ImageView ivZdOne;
    ImageView ivZdTwo;
    ImageView ivZdThree;
    ImageView ivZdFour;
    LinearLayout llKqGroup;//口腔相关
    LinearLayout ll_kq_top,ll_kq_botton;
    ImageView ivKqOne;
    ImageView ivKqTwo;
    ImageView ivKqThree;
    ImageView ivKqFour;
    ImageView ivKqFive;
    ImageView ivKqSix;
    ImageView ivKqSeven;
    LinearLayout llRmGroup;//热门相关
    ImageView ivRmOne;
    ImageView ivRmTwo;
    ImageView ivRmThree;
    ImageView ivRmFour;
    ImageView ivRmFive;
    ImageView ivRmSix;
    LinearLayout ll_rm_top,ll_rm_botton;
    NoticeView noticeview;//轮播广告
    CountdownView countdownview;
    private View getHeadView() {
        View convertView = View.inflate(getActivity(), R.layout.home_head, null);
        countdownview = (CountdownView) convertView.findViewById(R.id.countdownview);
        noticeview = (NoticeView) convertView.findViewById(R.id.noticeview);

        ll_kq_top = (LinearLayout) convertView.findViewById(R.id.ll_kq_top);
        ll_rm_top = (LinearLayout) convertView.findViewById(R.id.ll_rm_top);
        ll_rm_botton = (LinearLayout) convertView.findViewById(R.id.ll_rm_botton);
        ll_kq_botton = (LinearLayout) convertView.findViewById(R.id.ll_kq_botton);
        rl_zd_ivgroup = (RelativeLayout) convertView.findViewById(R.id.rl_zd_ivgroup);
        ll_zd_other = (LinearLayout) convertView.findViewById(R.id.ll_zd_other);
        bannerHome = (ConvenientBanner) convertView.findViewById(R.id.banner_home);
        ivGroupOne = (ImageView)convertView. findViewById(R.id.iv_group_one);
        ivGroupTwo = (ImageView) convertView.findViewById(R.id.iv_group_two);
        ivGroupThree = (ImageView)convertView. findViewById(R.id.iv_group_three);
        rvShopType = (RecyclerView) convertView.findViewById(R.id.rv_shop_type);
//        tvChange = (TextView) convertView.findViewById(R.id.tv_change);
        rvAbzy = (RecyclerView) convertView.findViewById(R.id.rv_abzy);
        ll_group = (LinearLayout) convertView.findViewById(R.id.ll_group);
        llZdGroup = (LinearLayout) convertView.findViewById(R.id.ll_zd_group);
        ivZdOne = (ImageView) convertView.findViewById(R.id.iv_zd_one);
        ivZdTwo = (ImageView) convertView.findViewById(R.id.iv_zd_two);
        ivZdThree = (ImageView) convertView.findViewById(R.id.iv_zd_three);
        ivZdFour = (ImageView) convertView.findViewById(R.id.iv_zd_four);
        llKqGroup = (LinearLayout) convertView.findViewById(ll_kq_group);
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


}
