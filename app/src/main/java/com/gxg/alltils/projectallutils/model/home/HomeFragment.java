package com.gxg.alltils.projectallutils.model.home;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.bean.HomeBean;
import com.gxg.alltils.projectallutils.bean.HomeListBean;
import com.gxg.alltils.projectallutils.http.RetrofitServiceManager;
import com.gxg.alltils.projectallutils.huanxin.controller.HXController;
import com.gxg.alltils.projectallutils.huanxin.ui.ChatActivity;
import com.gxg.alltils.projectallutils.model.LocalImageHolderView;
import com.gxg.alltils.projectallutils.model.ZxingActivity;
import com.gxg.alltils.projectallutils.model.home.activity.SearchActivity;
import com.gxg.alltils.projectallutils.model.home.adapter.HomeAbzyAdapter;
import com.gxg.alltils.projectallutils.model.home.adapter.HomeAdapter;
import com.gxg.alltils.projectallutils.model.home.adapter.HomeShopAdapter;
import com.gxg.alltils.projectallutils.utils.ScreenSizeUtil;
import com.gxg.alltils.projectallutils.widght.CustomDialog;
import com.gxg.alltils.projectallutils.widght.NoticeView;
import com.hyphenate.EMCallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.SettingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import rx.Subscriber;


/**
 * 作者：Administrator on 2017/11/14 17:45
 * 邮箱：android_gaoxuge@163.com
 */
public class HomeFragment extends BaseFragment implements CountdownView.OnCountdownEndListener {


    @Bind(R.id.rv_head)
    RecyclerView rvHead;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.rl_header_left)
    RelativeLayout rlHeaderLeft;
    @Bind(R.id.rl_header_center)
    RelativeLayout rlHeaderCenter;
    @Bind(R.id.rl_header_right)
    RelativeLayout rlHeaderRight;
    @Bind(R.id.top_home)
    LinearLayout top_home;
    @Bind(R.id.iv_back_top)
    ImageView ivBackTop;
    private HomeAdapter homeAdapter;
    private HomeBean mHomeBean;
    private HomeShopAdapter homeShopAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager abzyGridLayoutManager;
    private HomeAbzyAdapter homeAbzyAdapter;

    private int mWidth;//手机屏幕宽度
    private CustomDialog customDialog;

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

//    private boolean pullingFlag = false;

    private void initListener() {
        homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                page++;
                getHomeList(page);

            }
        }, rvHead);

        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getHomeData();
            }
        });


        refresh.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {
            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
            }

            @Override
            public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {
            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {
            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {
            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }

            @Override
            public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
                //小拉一下，不刷新  PullDownToRefresh   PullDownCanceled  None
                //下拉刷新  PullDownToRefresh  ReleaseToRefresh  Refreshing  RefreshFinish  None

//                PullDownToRefresh  标题栏隐藏   None标题栏显示
                switch (newState) {
                    case None:
//                        KLog.e("sssddd  None");
                        top_home.setVisibility(View.VISIBLE);
                        break;
                    case PullDownToRefresh:
                        top_home.setVisibility(View.GONE);
//                        KLog.e("sssddd PullDownToRefresh 下拉开始刷新");
                        break;
                    case Refreshing:
//                        KLog.e("sssddd  Refreshing正在刷新");
                        break;
                    case ReleaseToRefresh:
//                        KLog.e("sssddd  ReleaseToRefresh释放完立即刷新");
                        break;
                    case RefreshFinish:
//                        KLog.e("sssddd  RefreshFinish");
                        break;
                    case PullDownCanceled:
//                        KLog.e("sssddd  PullDownCanceled");
                        break;

                }
            }
        });

        rvHead.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int position = layoutManager.findFirstVisibleItemPosition();
                        if (position > 0) {
                            top_home.setBackgroundColor(Color.argb(220, 243, 152,
                                    0));
                            ivBackTop.setVisibility(View.VISIBLE);
                        } else {
                            View firstView = layoutManager.findViewByPosition(position);
                            int top = Math.abs(firstView.getTop());

                            int mHeight = getHeight();
                            int mPrecent = top * 220 / mHeight;
                            if (mPrecent <= 220 && mPrecent > 0) {
                                ivBackTop.setVisibility(View.GONE);
                                top_home.setBackgroundColor(Color.argb(mPrecent, 243,
                                        152, 0));
                            } else if (mPrecent <= 0) {
                                ivBackTop.setVisibility(View.GONE);
                                top_home.setBackgroundColor(Color.argb(0, 243, 152, 0));
                            } else {
                                ivBackTop.setVisibility(View.VISIBLE);
                                top_home.setBackgroundColor(Color.argb(220, 243, 152,
                                        0));
                            }

                        }
                    }
                });
            }
        });


        //验证权限并登陆环信
        toHXpermission(false);

    }



    /**
     * 获取顶部view的高度
     */
    private int getHeight() {
        return top_home.getHeight();
    }

    private void initData() {


        //关闭越界回弹
        refresh.setEnableOverScrollBounce(false);


        //获取屏幕的宽度
        mWidth = ScreenSizeUtil.getScreenWidth(getActivity());

        linearLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        abzyGridLayoutManager = new GridLayoutManager(getActivity(), 3);
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
        ll_ms_layoutParams.height = mWidth / 3;
        ll_group.setLayoutParams(ll_ms_layoutParams);


        //设置值得买首张图片的宽度，屏幕的1/3，高度是宽度的2倍
        ViewGroup.LayoutParams layoutParams = ivZdOne.getLayoutParams();
        layoutParams.width = mWidth / 3;
        ivZdOne.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams1 = rl_zd_ivgroup.getLayoutParams();
        layoutParams1.height = mWidth / 3 * 2;
        rl_zd_ivgroup.setLayoutParams(layoutParams1);

        //设置值得买第二张图片的宽高度，屏幕的1/3
        ViewGroup.LayoutParams layoutParams2 = ivZdTwo.getLayoutParams();
        layoutParams2.height = mWidth / 3;
        ivZdTwo.setLayoutParams(layoutParams2);


        //设置口腔相关的宽高
        ViewGroup.LayoutParams kq_top_layoutParams = ll_kq_top.getLayoutParams();
        kq_top_layoutParams.height = mWidth / 2;
        ll_kq_top.setLayoutParams(kq_top_layoutParams);

        ViewGroup.LayoutParams kq_bottom_layoutParams = ll_kq_botton.getLayoutParams();
        kq_bottom_layoutParams.height = mWidth / 3 * 2;
        ll_kq_botton.setLayoutParams(kq_bottom_layoutParams);

        //设置热门相关宽高
        ViewGroup.LayoutParams ll_rmtop_layoutParams = ll_rm_top.getLayoutParams();
        ll_rmtop_layoutParams.height = mWidth / 3;
        ll_rm_top.setLayoutParams(ll_rmtop_layoutParams);
        ViewGroup.LayoutParams ll_rmboton_layoutParams = ll_rm_botton.getLayoutParams();
        ll_rmboton_layoutParams.height = mWidth / 3;
        ll_rm_botton.setLayoutParams(ll_rmboton_layoutParams);

    }



    private void initView() {
        customDialog = new CustomDialog(getActivity());
        refresh.setEnableLoadmore(false);
        refresh.setEnableAutoLoadmore(false);
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
        if (mHomeBean != null) {
            //轮播图
            List<HomeBean.DatalistBean.RollBean> roll = mHomeBean.getDatalist().getRoll();
            if (roll != null && roll.size() > 0) {
                imgs.clear();
                for (int i = 0; i < roll.size(); i++) {
                    imgs.add(roll.get(i).getContent_value());
                }

                bannerHome.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, imgs) //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.drawable.icon_banner_nomal, R.drawable.icon_banner_select})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT).startTurning(3000);
                if (imgs.size() > 1) {
                    bannerHome.setCanLoop(true);
                } else {
                    bannerHome.setCanLoop(false);
                }

                bannerHome.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        showToastShort("点击轮播图"+position);
                    }
                });
            }
            //商品分类
            if (mHomeBean.getDatalist().getFast().size() > 0) {
                rvShopType.setVisibility(View.VISIBLE);
                homeShopAdapter.setNewData(mHomeBean.getDatalist().getFast());
            } else {
                rvShopType.setVisibility(View.GONE);
            }


            //推送消息
            if (mHomeBean.getDatalist().getNews().size() > 0) {
                noticeview.setVisibility(View.VISIBLE);
                noticeview.addNotice(mHomeBean.getDatalist().getNews());
            } else {
                noticeview.setVisibility(View.GONE);
            }

            //9个功能分类
            if (mHomeBean.getDatalist().getAbzy().size() > 0) {
                rvAbzy.setVisibility(View.VISIBLE);
                homeAbzyAdapter.setNewData(mHomeBean.getDatalist().getAbzy());

            } else {
                rvAbzy.setVisibility(View.GONE);
            }


            //手机秒杀，今日特价
            if (mHomeBean.getDatalist().getGroup().size() > 0) {
//                KLog.e("sss  group"+mHomeBean.getDatalist().getGroup());
                countdownview.setTag("test");
//                countdownview.dynamicShow(new DynamicConfig.Builder().setShowDay(true).setShowHour(true).setShowMinute(true).setShowSecond(true).setShowMillisecond(false).build());
                long time = (long) 150 * 24 * 60 * 60 * 1000;
                countdownview.start(60000);


                ll_group.setVisibility(View.VISIBLE);

                Glide.with(getActivity()).load(mHomeBean.getDatalist().getGroup().get(0).getContent_value()).into(ivGroupOne);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getGroup().get(1).getContent_value()).into(ivGroupTwo);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getGroup().get(2).getContent_value()).into(ivGroupThree);
            } else {
                ll_group.setVisibility(View.GONE);
            }

            //值得买
            if (mHomeBean.getDatalist().getScare() != null && mHomeBean.getDatalist().getScare().size() > 0) {
                llZdGroup.setVisibility(View.VISIBLE);

                Glide.with(getActivity()).load(mHomeBean.getDatalist().getScare().get(0).getContent_value()).into(ivZdOne);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getScare().get(1).getContent_value()).into(ivZdTwo);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getScare().get(2).getContent_value()).into(ivZdThree);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getScare().get(3).getContent_value()).into(ivZdFour);
            } else {
                llZdGroup.setVisibility(View.GONE);
            }

            //口腔相关
            if (mHomeBean.getDatalist().getProctg() != null && mHomeBean.getDatalist().getProctg().size() > 0) {
                llKqGroup.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(0).getContent_value()).into(ivKqOne);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(1).getContent_value()).into(ivKqTwo);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(2).getContent_value()).into(ivKqThree);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(3).getContent_value()).into(ivKqFour);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(4).getContent_value()).into(ivKqFive);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(5).getContent_value()).into(ivKqSix);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getProctg().get(6).getContent_value()).into(ivKqSeven);
            } else {
                llKqGroup.setVisibility(View.GONE);
            }

            //热门推荐
            mHomeBean.getDatalist().getRecommend();
            if (mHomeBean.getDatalist().getRecommend() != null && mHomeBean.getDatalist().getRecommend().size() > 0) {
                llRmGroup.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(0).getContent_value()).into(ivRmOne);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(1).getContent_value()).into(ivRmTwo);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(2).getContent_value()).into(ivRmThree);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(3).getContent_value()).into(ivRmFour);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(4).getContent_value()).into(ivRmFive);
                Glide.with(getActivity()).load(mHomeBean.getDatalist().getRecommend().get(5).getContent_value()).into(ivRmSix);
            } else {
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

                if (page == 1) {
                    homeAdapter.setNewData(homeBean.getDatalist().getRecommend());
                } else {
                    homeAdapter.addData(homeBean.getDatalist().getRecommend());
                }


                if (refresh.isRefreshing()) {
                    refresh.finishRefresh();
                }
//                if (homeBean.getDatalist().getRecommend().size() == 0 || homeBean.getDatalist().getRecommend().size() < 10) {
//                    homeAdapter.setEnableLoadMore(false);
//                }
            }
        }, map);
    }

    /**
     * --------------------------头布局--------------------------------------------------------------------
     */
    ConvenientBanner bannerHome;//轮播图
    RecyclerView rvShopType;//商品分类列表
    //    TextView tvChange;//通知消息
    RecyclerView rvAbzy;//9个分类功能
    LinearLayout ll_group;//热卖特价相关
    ImageView ivGroupOne, ivGroupTwo, ivGroupThree;
    LinearLayout llZdGroup;//值得买相关
    LinearLayout ll_zd_other;
    RelativeLayout rl_zd_ivgroup;
    ImageView ivZdOne;
    ImageView ivZdTwo;
    ImageView ivZdThree;
    ImageView ivZdFour;
    LinearLayout llKqGroup;//口腔相关
    LinearLayout ll_kq_top, ll_kq_botton;
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
    LinearLayout ll_rm_top, ll_rm_botton;
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
        ivGroupOne = (ImageView) convertView.findViewById(R.id.iv_group_one);
        ivGroupTwo = (ImageView) convertView.findViewById(R.id.iv_group_two);
        ivGroupThree = (ImageView) convertView.findViewById(R.id.iv_group_three);
        rvShopType = (RecyclerView) convertView.findViewById(R.id.rv_shop_type);
//        tvChange = (TextView) convertView.findViewById(R.id.tv_change);
        rvAbzy = (RecyclerView) convertView.findViewById(R.id.rv_abzy);
        ll_group = (LinearLayout) convertView.findViewById(R.id.ll_group);
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
    public void onEnd(CountdownView cv) {
        Object tag = cv.getTag();
        if (tag != null) {
            KLog.e("sss   " + tag.toString());
        }
    }


    @OnClick({R.id.iv_back_top,R.id.rl_header_left, R.id.rl_header_center, R.id.rl_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_top:
                //返回顶部
                rvHead.scrollToPosition(0);
                break;
            case R.id.rl_header_left://扫一扫
               toZxing();
                break;
            case R.id.rl_header_center://搜索
                showToastShort("搜索");
                openActivity(SearchActivity.class);
                break;
            case R.id.rl_header_right://消息
                //跳转环信聊天界面
                toHXpermission(true);
                break;
        }
    }

    /**
     * 跳转扫描二维码页
     */
    private void toZxing() {
        permissionsJudgment(new PermissionCallBack() {
            @Override
            public void onSucceed(int requestCode, List<String> grantedPermissions) {
                openActivity(ZxingActivity.class);
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
              final  SettingService settingService = AndPermission.defineSettingDialog(getActivity(), 400);
                if(customDialog!=null){
                    customDialog.dialogForPermission(getActivity(), "扫描二维码需要打开相机和文件读写的权限，请在设置中授权！", "取消", "去设置", new CustomDialog.NegativeOnClick() {
                        @Override
                        public void onNegativeClick() {
                            settingService.cancel();
                        }
                    }, new CustomDialog.PositiveOnClick() {
                        @Override
                        public void onPositiveClick() {
                            settingService.execute();
                        }
                    });
                }
            }
        },Permission.CAMERA,Permission.STORAGE);
    }


    /**
     * 进入环信单聊验证权限
     * @param isToHx 是否进入环信聊天页面
     */
    private void toHXpermission(final  boolean isToHx) {
        permissionsJudgment(new PermissionCallBack() {
            @Override
            public void onSucceed(int requestCode, List<String> grantedPermissions) {
                HxLogin(isToHx);
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
                // 是否有不再提示并拒绝的权限。
                if (!AndPermission.hasAlwaysDeniedPermission(mActivity, deniedPermissions)) {
                    KLog.e("没有不再提示并拒绝的权限222222");
                    showPermissionDialog();
                }else{
                    // 有不再提示并拒绝的权限。
                    KLog.e("有不再提示并拒绝的权限222222");

                    if(isToHx){
                        showPermissionDialog();
                    }else{
                        showToastLong("当前应用缺少必要权限，可能会影响您的正常使用。请在设置中授权！");
                    }
                }

            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
    }

    /**
     * 显示添加权限的dialog
     */
    private void showPermissionDialog() {
        final SettingService settingService = AndPermission.defineSettingDialog(getActivity(), 400);
        if(customDialog!=null){
            customDialog.dialogForPermission(getActivity(), "当前应用缺少必要权限，请在设置中授权！", "取消", "去设置", new CustomDialog.NegativeOnClick() {
                @Override
                public void onNegativeClick() {
                    settingService.cancel();
                }
            }, new CustomDialog.PositiveOnClick() {
                @Override
                public void onPositiveClick() {
                    settingService.execute();
                }
            });
        }
    }

    /**
     * 登陆环信
     * @param isToHx 是否进聊天页面
     */
    private void HxLogin(final boolean isToHx) {
        HXController.loginHX("15735804834", "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                KLog.e("登陆成功");
                if(isToHx){
                    goHX();
                }
            }

            @Override
            public void onError(int i, String s) {
                KLog.e("登陆失败" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
    /**
     * 跳转环信聊天界面
     */
    private void goHX() {
        if (HXController.hXIsLogin()) {
            ChatActivity.startChatActivity(getActivity(), ChatActivity.JX_SERVER_USERNAME_1);
        } else {
            HXController.loginHX("15735804834", "123456", new EMCallBack() {
                @Override
                public void onSuccess() {
                    KLog.e("登陆成功");
                    ChatActivity.startChatActivity(getActivity(), ChatActivity.JX_SERVER_USERNAME_1);
                }

                @Override
                public void onError(int i, String s) {
                    KLog.e("登陆失败" + s);
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }
}
