package com.gxg.alltils.projectallutils.model.loginregister;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.widght.CustomTextWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.tv_pwd_forget)
    TextView tvPwdForget;
    @Bind(R.id.rl_auto_login)
    RelativeLayout rlAutoLogin;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.ib_login_biyan)
    ImageButton ib_login_biyan;
    public static final String LOGIN_TYPE="login";
    private List<EditText> editLoginTexts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        editLoginTexts.add(etPsw);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        CustomTextWatcher customLoginTextWatcher = new CustomTextWatcher(editLoginTexts,ib_login_biyan);
        etPsw.addTextChangedListener(customLoginTextWatcher);
    }

    @OnClick({ R.id.ib_login_biyan,R.id.tv_pwd_forget, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ib_login_biyan:
                break;
            case R.id.tv_pwd_forget:
                break;
            case R.id.btn_login:
                break;
        }
    }
}
