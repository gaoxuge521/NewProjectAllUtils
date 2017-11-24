package com.gxg.alltils.projectallutils.model.find.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.http.HttpHelper;
import com.gxg.alltils.projectallutils.model.find.adapter.FindTopAdapter;
import com.gxg.alltils.projectallutils.model.find.bean.FindTopBean;
import com.gxg.alltils.projectallutils.utils.GsonUtil;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * 作者：Administrator on 2017/11/24 14:32
 * 邮箱：android_gaoxuge@163.com
 */
public class FindTopFragment extends BaseFragment {
    @Bind(R.id.gv_fm_find_top)
    GridView gvFmFindTop;
    private FindTopBean findTopBean;

    @Override
    protected int setContentView() {
        return R.layout.fragment_find_top;
    }

    @Override
    public void setupViews(View view) {

        getTypeDataTop();
    }



    public static FindTopFragment newInstance() {

        Bundle args = new Bundle();

        FindTopFragment fragment = new FindTopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取首个fragment的数据
     */
    private void getTypeDataTop() {
        Map<String,Object> map = new HashMap<>();
        map.put("act","brand");
        map.put("op","recommend_list");
        HttpHelper.getInstance().request(HttpHelper.jointURL(HttpHelper.BASEURL,map), new HttpHelper.HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                findTopBean = GsonUtil.GsonToObject(result, FindTopBean.class);
                FindTopAdapter findTopAdapter = new FindTopAdapter(getActivity(),findTopBean.getDatas().getBrand_list());
                gvFmFindTop.setAdapter(findTopAdapter);
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
