package com.gxg.alltils.projectallutils.model.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.utils.DataCleanManager;
import com.gxg.alltils.projectallutils.utils.SharedPreferencesUtils;

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
                break;
            case R.id.tv_usermore_version:
                break;
            case R.id.rl_usermore_clear:
                break;
            case R.id.ll_usermore_out:
                break;
        }
    }
}
