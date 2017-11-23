package com.gxg.alltils.projectallutils.model.user.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.event.ShowHomeEvent;
import com.gxg.alltils.projectallutils.http.utils.RxBus;
import com.gxg.alltils.projectallutils.huanxin.controller.HXController;
import com.gxg.alltils.projectallutils.utils.AppUtil;
import com.gxg.alltils.projectallutils.utils.DataCleanManager;
import com.gxg.alltils.projectallutils.utils.PopupWindowHelper;
import com.gxg.alltils.projectallutils.utils.SharedPreferencesUtils;
import com.gxg.alltils.projectallutils.widght.CustomDialog;
import com.hyphenate.EMCallBack;
import com.socks.library.KLog;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.tv_aboutus)
    TextView tvAboutus;
    @Bind(R.id.tv_third_login)
    TextView tvThirdLogin;
    @Bind(R.id.tv_user_agreement)
    TextView tvUserAgreement;
    @Bind(R.id.tv_usermore_feedback)
    TextView tvUsermoreFeedback;
    @Bind(R.id.tv_usermore_version)
    TextView tvUsermoreVersion;
    @Bind(R.id.tv_usermore_clear_size)
    TextView tvUsermoreClearSize;
    @Bind(R.id.tv_usermore_out)
    TextView tvUsermoreOut;
    @Bind(R.id.ll_usermore_out)
    LinearLayout llUsermoreOut;
    @Bind(R.id.rl_usermore_clear)
    RelativeLayout rlUsermoreClear;
    private PopupWindowHelper popupWindowHelper;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {

        //登陆过
        if(SharedPreferencesUtils.isLogin(SettingActivity.this)){
            llUsermoreOut.setVisibility(View.VISIBLE);
        }else{
            llUsermoreOut.setVisibility(View.GONE);
        }

        //获取缓存大小
        try {
            String cache = DataCleanManager.getTotalCacheSize(getApplicationContext());
            tvUsermoreClearSize.setText(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        customDialog = new CustomDialog(SettingActivity.this);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.rl_usermore_clear,R.id.ll_left, R.id.tv_aboutus, R.id.tv_third_login, R.id.tv_user_agreement, R.id.tv_usermore_feedback, R.id.tv_usermore_version, R.id.ll_usermore_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_aboutus:
                showToastShort("关于我们");
                break;
            case R.id.tv_third_login:
                break;
            case R.id.tv_user_agreement:
                break;
            case R.id.tv_usermore_feedback:
                openActivity(FeedBackActivity.class);
                break;
            case R.id.tv_usermore_version:
                showDialogVersion(view);
                break;
            case R.id.rl_usermore_clear:
                //显示清除缓存删除选项
                showClearView(view);
                break;
            case R.id.ll_usermore_out:
                showoutPopView(view);
                break;
        }
    }

    /**
     * 显示最新版本的dialog
     * @param view
     */
    private void showDialogVersion(View view) {
        //已经是最新版本
        customDialog.showSingleButtonDialog(getString(R.string.app_name) + AppUtil.getVersion(), "当前已是最新版本", null);



        //普通的提示升级
//        customDialog.versionTip(SettingActivity.this, getString(R.string.app_name)+ AppUtil.getVersion(), "当前已是最新版本", new CustomDialog.PositiveOnClick() {
//            @Override
//            public void onPositiveClick() {
//                showToastShort("马上升级");
//            }
//        });


        //强制升级
//        customDialog.versionTip(SettingActivity.this, "sss", "sss", new CustomDialog.PositiveOnClick() {
//            @Override
//            public void onPositiveClick() {
//                showToastShort("强制升级");
//            }
//        }, new CustomDialog.NegativeOnClick() {
//            @Override
//            public void onNegativeClick() {
//                ActivityManage.getAppManager().AppExit();
//            }
//        }, new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
//            }
//        });
    }


    //显示删除选项
    private void showoutPopView(View view) {
        View popView = LayoutInflater.from(this).inflate(R.layout.layout_popupview_dialog_delete, null);
        popupWindowHelper = new PopupWindowHelper(popView);

        Button btnMakeSure = (Button) popView.findViewById(R.id.btn_make_sure);
        Button btnGiveUp = (Button) popView.findViewById(R.id.btn_give_up);
        TextView tvDeleteTitle = (TextView) popView.findViewById(R.id.tv_delet_title);
        tvDeleteTitle.setText("确定要退出么?");

        btnMakeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出登陆清除token
                SharedPreferencesUtils.removeToken(SettingActivity.this);
                //退出时清除保存的各种用户信息
                SharedPreferencesUtils.clearUser(SettingActivity.this);
                //退出登陆环也退出
                HXController.logoutHX(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        KLog.e("环信退出成功");
                    }

                    @Override
                    public void onError(int i, String s) {
                        KLog.e("环信退出失败"+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                popupWindowHelper.dismiss();

                RxBus.getDefault().post(new ShowHomeEvent(ShowHomeEvent.NAME,1));
                showToastShort("您已成功退出");
                finish();
            }
        });

        btnGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowHelper.dismiss();
            }
        });
        popupWindowHelper.showFromBottom(view);
    }


    //显示清除缓存删除选项
    private void showClearView(View view) {
        View popView = LayoutInflater.from(this).inflate(R.layout.layout_popupview_dialog_delete, null);
        popupWindowHelper = new PopupWindowHelper(popView);
        Button btnMakeSure = (Button) popView.findViewById(R.id.btn_make_sure);
        Button btnGiveUp = (Button) popView.findViewById(R.id.btn_give_up);
        TextView tvDeleteTitle = (TextView) popView.findViewById(R.id.tv_delet_title);
        tvDeleteTitle.setText("确定要清除缓存吗?");

        btnMakeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(getApplicationContext());
                tvUsermoreClearSize.setText("0M");
                popupWindowHelper.dismiss();
                showToastShort("缓存已经清理");
            }
        });

        btnGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowHelper.dismiss();
            }
        });
        popupWindowHelper.showFromBottom(view);
    }
}
