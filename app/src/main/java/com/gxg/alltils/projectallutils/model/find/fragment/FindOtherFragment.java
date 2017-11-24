package com.gxg.alltils.projectallutils.model.find.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.http.HttpHelper;
import com.gxg.alltils.projectallutils.model.find.adapter.FindOtherAdapter;
import com.gxg.alltils.projectallutils.model.find.bean.FindOtherBean;
import com.gxg.alltils.projectallutils.utils.GsonUtil;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 作者：Administrator on 2017/11/24 14:32
 * 邮箱：android_gaoxuge@163.com
 */
public class FindOtherFragment extends BaseFragment {
    @Bind(R.id.rv_find_other)
    RecyclerView rvFind_Other;
    public static final String GCID = "gc_id";
    private String gc_id ;
    private FindOtherBean findOtherBean;
    List<FindOtherBean.DatasBean.ClassListBean> findOtherList = new ArrayList<>();
    private FindOtherAdapter findOtherAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_find_other;
    }

    @Override
    public void setupViews(View view) {
        if(getArguments()!=null){
            gc_id =  getArguments().getString(GCID,"");
        }

        initData();

    }

    private void initData() {
        rvFind_Other.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFind_Other.setHasFixedSize(true);
        findOtherAdapter = new FindOtherAdapter(findOtherList);
        rvFind_Other.setAdapter(findOtherAdapter);
        rvFind_Other.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                showToastShort("点击"+position);
            }
        });

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(findOtherAdapter==null || findOtherAdapter.getData().size() ==0){
                getTypeDataTop();
            }

        }else{
        }
    }

    public static FindOtherFragment newInstance(String gcId) {
        Bundle args = new Bundle();
        args.putString(GCID,gcId);
        FindOtherFragment fragment = new FindOtherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取首个fragment的数据
     */
    private void getTypeDataTop() {
        Map<String,Object> map = new HashMap<>();
        map.put("act","goods_class");
        map.put("op","get_child_all");
        map.put("gc_id",gc_id);
        HttpHelper.getInstance().request(HttpHelper.jointURL(HttpHelper.BASEURL,map), new HttpHelper.HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                KLog.e("ssss  onSuccess"+result);
                findOtherBean = GsonUtil.GsonToObject(result, FindOtherBean.class);
                findOtherAdapter.setNewData(findOtherBean.getDatas().getClass_list());

            }

            @Override
            public void onFailure(String msg) {
                KLog.e("ssss  onFail  "+msg);
            }

            @Override
            public void onError(String msg) {
                KLog.e("ssss  onError  "+msg);
            }
        });
    }

}
