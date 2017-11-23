package com.gxg.alltils.projectallutils.model.home.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.adapter.TagAdapter;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.utils.Contants;
import com.gxg.alltils.projectallutils.utils.GsonUtil;
import com.gxg.alltils.projectallutils.utils.SharedPreferencesUtils;
import com.gxg.alltils.projectallutils.utils.WeakHandler;
import com.gxg.alltils.projectallutils.widght.ClearEditText;
import com.gxg.alltils.projectallutils.widght.TagFlowLayout;
import com.socks.library.KLog;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


public class SearchActivity extends BaseActivity {

    @Bind(R.id.searchEditText)
    ClearEditText searchEditText;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_hot)
    TextView tvHot;
    @Bind(R.id.fl_hot_words)
    TagFlowLayout flHotWords;
    @Bind(R.id.tv_search_delete)
    TextView tvSearchDelete;
    @Bind(R.id.fl_history_words)
    TagFlowLayout flHistoryWords;
    private TagAdapter<String> adapter;
    private List<String> hotWords ; // 热门标签
    private List<String> historyWords ; // 历史搜索

    public static final String SEARCH_VALUE = "Search_Value";


    //TODO 假数据，倒计时记得删除
    private    String[] hotWord = {"洗车","双立人","家用","服装","水果","茶","赠品"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        hotWords = new ArrayList<>();
        historyWords = new ArrayList<>();
    }

    @Override
    protected void initData() {
        getHotKeyWord();

        getHistoryWord();
    }



    @Override
    protected void initListener() {
        //热门搜索点击
        flHotWords.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                Bundle bundle = new Bundle();
                bundle.putString(SEARCH_VALUE,hotWords.get(position));
                openActivity(SearchGoodResultActivity.class,bundle);

                addHistoryWord(hotWords.get(position));
                return false;
            }
        });
        //历史搜索点击
        flHistoryWords.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Bundle bundle = new Bundle();
                bundle.putString(SEARCH_VALUE,historyWords.get(position));
                openActivity(SearchGoodResultActivity.class,bundle);
                return false;
            }
        });
    }


    /**
     * 获取历史搜索记录
     */
    private void getHistoryWord() {
        //从本地获取就可以了
        Object o = SharedPreferencesUtils.get(SearchActivity.this, Contants.HISTORY_SEARCH, "");
        if(o!=null){
            String histort_search = o.toString();
            if(!TextUtils.isEmpty(histort_search)){
                historyWords = GsonUtil.GsonToList(histort_search,String.class);
                KLog.e("sss222   "+histort_search);

                flHistoryWords.setAdapter(new TagAdapter<String>(historyWords) {
                    @Override
                    public View getView(FlowLayout parent, int position, String tag) {
                        TextView tvTag = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.layout_flow_tag, flHistoryWords, false);
                        tvTag.setText(tag);
                        return tvTag;
                    }
                });
            }

        }
        displayHistoryWords();
    }

    /**
     * 判断历史数据是否为0
     */
    private void displayHistoryWords() {
        if (historyWords.size() > 0) {
            tvSearchDelete.setVisibility(View.VISIBLE);
            flHistoryWords.setVisibility(View.VISIBLE);
        } else {
            tvSearchDelete.setVisibility(View.GONE);
            flHistoryWords.setVisibility(View.GONE);
        }
    }


    /**
     * 获取热门搜索列表
     */
    private void getHotKeyWord() {
        //假数据，应该从网络请求

        hotWords.clear();
        Collections.addAll(hotWords,hotWord);
        KLog.e("sss2222 "+hotWords);
        adapter = new TagAdapter<String>(hotWords) {
            @Override
            public View getView(FlowLayout parent, int position, String tag) {
                TextView tvTag = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.layout_flow_tag, flHotWords, false);
                tvTag.setText(tag);
                return tvTag;
            }
        };
        flHotWords.setAdapter(adapter);
    }
    /**
     * 添加到历史搜索中
     * @param value 名称
     */
    private void addHistoryWord(String value) {
        if (!historyWords.contains(value)) {
            historyWords.add(value);
            SharedPreferencesUtils.put(SearchActivity.this, Contants.HISTORY_SEARCH, GsonUtil.GsonToString(historyWords));
            getHistoryWord();
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_search_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_search_delete:
                historyWords.clear();
                SharedPreferencesUtils.remove(SearchActivity.this,Contants.HISTORY_SEARCH);
                //移除历史数据
                //延迟半秒再获取数据，防止sp移除数据太慢
                new WeakHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getHistoryWord();
                    }
                },500);


                break;
        }
    }
}
