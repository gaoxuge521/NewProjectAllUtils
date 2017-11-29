package com.gxg.alltils.projectallutils.model.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.http.HttpHelper;
import com.gxg.alltils.projectallutils.model.home.adapter.ListDropDownAdapter;
import com.gxg.alltils.projectallutils.model.home.adapter.SearchGoodAdapter;
import com.gxg.alltils.projectallutils.model.home.bean.SearchGoodBean;
import com.gxg.alltils.projectallutils.model.home.bean.SearchGoodStoreCreditBean;
import com.gxg.alltils.projectallutils.utils.GsonUtil;
import com.gxg.alltils.projectallutils.utils.PopupWindowHelper;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class SearchGoodResultActivity extends BaseActivity {


    @Bind(R.id.rv_search)
    RecyclerView rvSearch;
    @Bind(R.id.tv_sorting)
    TextView tvSorting;
    @Bind(R.id.tv_sales)
    TextView tvSales;
    @Bind(R.id.tv_filter)
    TextView tvFilter;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;

    private List<View> popupViews = new ArrayList<>();

    private String citys[] = {"综合排序", "价格从高到低", "价格从低到高", "人气排序"};
    private PopupWindowHelper popupWindowHelper;
    private ListView cityList;
    private ListDropDownAdapter listDropDownAdapter;
    //搜索的三个关键key
    private int curpage = 1;

    private String order = "2";
    private String key;
    private SearchGoodBean searchGoodBean;
    private SearchGoodAdapter searchGoodAdapter;
    private SearchGoodStoreCreditBean searchGoodStoreCreditBean;
    public static final String SEARCH_VALUE = "Search_Value";//从SearchActivity传过来的
    private String search_key;//
    public static final String SEARCH_BRAND_ID = "Search_brand_id";//从分类传过来的
    private String search_brand_id;//
    public static final String SEARCH_GC_ID = "Search_gc_id";//从分类传过来的
    private String search_gc_id;//
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
        title.setText("");

    }

    @Override
    protected void initData() {
        search_key = getIntent().getStringExtra(SEARCH_VALUE);
        KLog.e("sss" + search_key);

        search_brand_id = getIntent().getStringExtra(SEARCH_BRAND_ID);
        KLog.e("sss" + search_brand_id);

        search_gc_id = getIntent().getStringExtra(SEARCH_GC_ID);
        KLog.e("sss" + search_gc_id);
        //添加数据
        rvSearch.setLayoutManager(new LinearLayoutManager(SearchGoodResultActivity.this));
        searchGoodAdapter = new SearchGoodAdapter(new ArrayList<SearchGoodBean.DatasBean.GoodsListBean>());
        rvSearch.setAdapter(searchGoodAdapter);

        // 初始化排序的pop
        initCityPop();

        getSearchData();


    }

    /**
     * 获取搜索的数据
     */
    private void getSearchData() {
        Map<String, Object> map = new HashMap<>();
        map.put("act", "goods");
        map.put("op", "goods_list");

        if(!TextUtils.isEmpty(search_key)){
            map.put("keyword", search_key);
        }else   if(!TextUtils.isEmpty(search_brand_id)){
            map.put("b_id", search_brand_id);
        }else   if(!TextUtils.isEmpty(search_gc_id)){
            map.put("gc_id", search_gc_id);
        }
        map.put("key", key);
        map.put("order", order);
        map.put("gift", "");
        map.put("groupbuy", "");
        map.put("xianshi", "");
        map.put("own_shop", "");
        map.put("price_from", "");
        map.put("price_to", "");
        map.put("area_id", "");
        map.put("ci", "");
        map.put("curpage", curpage);
        map.put("page", 10);
        HttpHelper.getInstance().request(HttpHelper.BASEURL, map, new HttpHelper.HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    searchGoodBean = GsonUtil.GsonToObject(result, SearchGoodBean.class);
                    if (curpage == 1) {

                        searchGoodAdapter.setEnableLoadMore(true);
                        searchGoodAdapter.setNewData(searchGoodBean.getDatas().getGoods_list());
                        if (searchGoodBean.getDatas().getGoods_list().size() == 0 || searchGoodBean.getDatas().getGoods_list().size() < 10) {
                            searchGoodAdapter.setEmptyView(getEmptyView());
                            tvEmptyTitle.setText("没有此类商品");
                            tvEmptyContent.setText("去试试别的关键字吧");
                            btnLookingAround.setVisibility(View.GONE);
                            searchGoodAdapter.setEnableLoadMore(false);
                            searchGoodAdapter.removeAllFooterView();
                        }
                    } else {
                        searchGoodAdapter.addData(searchGoodBean.getDatas().getGoods_list());
                        if (searchGoodBean.getDatas().getGoods_list().size() == 0 || searchGoodBean.getDatas().getGoods_list().size() < 10) {
                            searchGoodAdapter.setEnableLoadMore(false);
                            searchGoodAdapter.addFooterView(getFootView());
                        }
                    }

                    searchGoodAdapter.loadMoreComplete();

                }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    protected void initListener() {

        searchGoodAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rvSearch.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        curpage++;
                        getSearchData();
                    }
                }, 500);
            }
        }, rvSearch);

        rvSearch.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_search_good_store_name:
                        getStoreCredit(searchGoodAdapter.getData().get(position).getStore_id(), position);
                        break;
                    case R.id.ll_store_credit:
                        searchGoodAdapter.setShowStore(null, position, false);
                        break;
                    case R.id.iv_search_good_item:
                    case R.id.ll_goods:
                        Bundle bundle = new Bundle();
                        openActivity(GoodDetailActivity.class,bundle);
                        break;
                }
            }
        });
    }

    /**
     * 获取商店的评价信息
     */
    private void getStoreCredit(final String store_id, final int position) {
        Map<String, Object> map = new HashMap<>();
        map.put("act", "store");
        map.put("op", "store_credit");
        map.put("store_id", store_id);

        HttpHelper.getInstance().request(HttpHelper.BASEURL, map, new HttpHelper.HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    searchGoodStoreCreditBean = GsonUtil.GsonToObject(result, SearchGoodStoreCreditBean.class);
                    searchGoodAdapter.setShowStore(searchGoodStoreCreditBean, position, true);
                }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError(String msg) {

            }
        });


    }

    @OnClick({R.id.ll_left, R.id.tv_sorting, R.id.tv_sales, R.id.tv_filter, R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sorting://排序
                tvSorting.setTextColor(getResources().getColor(R.color.red));
                tvSales.setTextColor(getResources().getColor(R.color.black));
                showCityPop();
                break;
            case R.id.tv_sales://销量
                tvSorting.setText("综合排序");
                listDropDownAdapter.setCheckItem(0);
                tvSales.setTextColor(getResources().getColor(R.color.red));
                tvSorting.setTextColor(getResources().getColor(R.color.black));
                curpage = 1;
                key = "1";
                order = "2";
                getSearchData();

                break;
            case R.id.tv_filter://筛选
                Intent intent = new Intent(SearchGoodResultActivity.this, FilterActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.iv_more://切换视图
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    /**
     * 初始化排序的pop
     */
    private void initCityPop() {
        cityList = new ListView(this);
        popupWindowHelper = new PopupWindowHelper(cityList);
        listDropDownAdapter = new ListDropDownAdapter(this, Arrays.asList(citys));
        cityList.setAdapter(listDropDownAdapter);
    }

    /**
     * 显示排序的pop
     */
    private void showCityPop() {

        popupWindowHelper.showAsDropDown(tvSorting);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToastShort("" + position);
                tvSorting.setText(listDropDownAdapter.getItem(position).toString());
                listDropDownAdapter.setCheckItem(position);
                popupWindowHelper.dismiss();

                switch (position) {
                    case 0:
                        curpage = 1;
                        key = "";
                        order = "2";
                        getSearchData();
                        break;
                    case 1:
                        curpage = 1;
                        key = "3";
                        order = "2";
                        getSearchData();
                        break;
                    case 2:
                        curpage = 1;
                        key = "3";
                        order = "1";
                        getSearchData();
                        break;
                    case 3:
                        curpage = 1;
                        key = "2";
                        order = "2";
                        getSearchData();
                        break;

                }
            }
        });
    }


}
