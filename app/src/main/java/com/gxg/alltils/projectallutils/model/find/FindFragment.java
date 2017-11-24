package com.gxg.alltils.projectallutils.model.find;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseFragment;
import com.gxg.alltils.projectallutils.http.HttpHelper;
import com.gxg.alltils.projectallutils.huanxin.controller.HXController;
import com.gxg.alltils.projectallutils.huanxin.ui.ChatActivity;
import com.gxg.alltils.projectallutils.model.ZxingActivity;
import com.gxg.alltils.projectallutils.model.find.bean.FindTypeBean;
import com.gxg.alltils.projectallutils.model.find.fragment.FindOtherFragment;
import com.gxg.alltils.projectallutils.model.find.fragment.FindTopFragment;
import com.gxg.alltils.projectallutils.utils.GsonUtil;
import com.gxg.alltils.projectallutils.widght.verticaltablayout.VerticalTabLayout;
import com.gxg.alltils.projectallutils.widght.verticaltablayout.adapter.TabAdapter;
import com.gxg.alltils.projectallutils.widght.verticaltablayout.widget.QTabView;
import com.gxg.alltils.projectallutils.widght.verticaltablayout.widget.TabView;
import com.hyphenate.EMCallBack;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 作者：Administrator on 2017/11/14 17:45
 * 邮箱：android_gaoxuge@163.com
 */
public class FindFragment extends BaseFragment {
    @Bind(R.id.vTabLayout)
    VerticalTabLayout vTabLayout;
    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;

    List<Fragment> fragments = new ArrayList<>();
    @Bind(R.id.rl_header_left)
    RelativeLayout rlHeaderLeft;
    @Bind(R.id.rl_header_center)
    RelativeLayout rlHeaderCenter;
    @Bind(R.id.rl_header_right)
    RelativeLayout rlHeaderRight;
    private FindTypeBean findTypeBean;
    private FindTypeBean.DatasBean.ClassListBean topbean;

    @Override
    protected int setContentView() {
        return R.layout.fragment_find;
    }

    @Override
    public void setupViews(View view) {
        String id = getArguments().getString("id", "");


        topbean = new FindTypeBean.DatasBean.ClassListBean();
        topbean.setGc_name("品牌推荐");

        getTypeData();

    }

    private void initFragment() {
        fragments.clear();
        if (findTypeBean != null && findTypeBean.getDatas().getClass_list().size() > 0) {
            //动态添加fragment
            for (int i = 0; i < findTypeBean.getDatas().getClass_list().size(); i++) {
                if (i == 0) {
                    FindTopFragment findTopFragment = FindTopFragment.newInstance();
                    fragments.add(findTopFragment);
                } else {
                    FindOtherFragment findOtherFragment = FindOtherFragment.newInstance(findTypeBean.getDatas().getClass_list().get(i).getGc_id());
                    fragments.add(findOtherFragment);
                }
            }

            vTabLayout.setupWithFragment(getChildFragmentManager(), R.id.fragment_container, fragments, new TabAdapter() {
                @Override
                public int getCount() {
                    return fragments.size();
                }

                @Override
                public TabView.TabBadge getBadge(int position) {
                    return null;
                }

                @Override
                public TabView.TabIcon getIcon(int position) {
                    return null;
                }

                @Override
                public TabView.TabTitle getTitle(int position) {
                    return new QTabView.TabTitle.Builder().setContent(findTypeBean.getDatas().getClass_list().get(position).getGc_name()).setTextColor(0xFF151515, 0xFF151515).setTextSize(15, 18).build();
                }

                @Override
                public int getBackground(int position) {
                    return 0;
                }
            });

        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (fragments.size() == 0 || vTabLayout.getTabCount() == 0) {
                getTypeData();
            }
        }
    }

    /**
     * 获取列表数据
     */
    private void getTypeData() {
        Map<String, Object> map = new ArrayMap<>();
        map.put("act", "goods_class");
        HttpHelper.getInstance().request(HttpHelper.jointURL(HttpHelper.BASEURL, map), new HttpHelper.HttpCallBack() {
            @Override
            public void onSuccess(String result) {
                findTypeBean = GsonUtil.GsonToObject(result, FindTypeBean.class);
                if (findTypeBean.getDatas().getClass_list().size() > 0) {
                    findTypeBean.getDatas().getClass_list().add(0, topbean);
                    KLog.e("sss" + findTypeBean.getDatas().getClass_list().get(0).getGc_name());

                    initFragment();


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

    public static FindFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id", id);
        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @OnClick({R.id.rl_header_left, R.id.rl_header_center, R.id.rl_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_header_left:
              toZxing();
                break;
            case R.id.rl_header_center:
                break;
            case R.id.rl_header_right:
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
                AndPermission.defaultSettingDialog(getActivity(), 400)
                        .setTitle("权限申请失败")
                        .setMessage("扫描二维码需要打开相机和散光灯的权限，请在设置中授权！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        },Permission.CAMERA,Permission.STORAGE);
    }


//    ------------------------------------环信相关----------------------------------------------------------------
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
                AndPermission.defaultSettingDialog(getActivity(), 400)
                        .setTitle("权限申请失败")
                        .setMessage("登陆客服聊天需要读写权限以便保存聊天记录，请在设置中授权！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }, Permission.STORAGE);
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
            ChatActivity.startChatActivity(getActivity(), ChatActivity.JX_SERVER_USERNAME_2);
        } else {
            HXController.loginHX("15735804834", "123456", new EMCallBack() {
                @Override
                public void onSuccess() {
                    KLog.e("登陆成功");
                    ChatActivity.startChatActivity(getActivity(), ChatActivity.JX_SERVER_USERNAME_2);
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

    //    ------------------------------------环信相关----------------------------------------------------------------
}
