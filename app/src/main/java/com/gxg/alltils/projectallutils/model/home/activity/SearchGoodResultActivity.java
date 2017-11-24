package com.gxg.alltils.projectallutils.model.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.http.HttpHelper;
import com.gxg.alltils.projectallutils.model.home.adapter.ListDropDownAdapter;
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
    private String search_key;
    private String order;

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
        search_key = getIntent().getStringExtra(SearchActivity.SEARCH_VALUE);
        KLog.e("sss" + search_key);

        // 初始化排序的pop
        initCityPop();

        getSearchData();


    }

    /**
     * 获取搜索的数据
     */
    private void getSearchData() {
        Map<String,Object> map = new HashMap<>();
        map.put("act","goods");
        map.put("op","goods_list");
        map.put("keyword",search_key);
        map.put("key","");
        map.put("order",order);
        map.put("gift","");
        map.put("groupbuy","");
        map.put("xianshi","");
        map.put("own_shop","");
        map.put("price_from","");
        map.put("price_to","");
        map.put("area_id","");
        map.put("ci","");
        map.put("curpage",1);
        map.put("page",10);
        HttpHelper.getInstance().request(HttpHelper.jointURL(HttpHelper.BASEURL,map), new HttpHelper.HttpCallBack() {
            @Override
            public void onSuccess(String result) {

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

    }

    @OnClick({R.id.ll_left,R.id.tv_sorting, R.id.tv_sales, R.id.tv_filter, R.id.iv_more})
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
            }
        });
    }
}
