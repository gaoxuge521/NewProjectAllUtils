package com.gxg.alltils.projectallutils.model.loginregister;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxg.alltils.projectallutils.MainActivity;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.utils.Contants;
import com.gxg.alltils.projectallutils.utils.EditTextUtils;
import com.gxg.alltils.projectallutils.utils.SharedPreferencesUtils;
import com.gxg.alltils.projectallutils.utils.VerifyUtils;
import com.gxg.alltils.projectallutils.widght.CustomTextWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.ll_left)
    LinearLayout llLeftBack;
    @Bind(R.id.title)
    TextView tvTitle;
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
    @Bind(R.id.tv_register)
    TextView tv_register;
    private List<EditText> editLoginTexts = new ArrayList<>();
    public static final String LOGIN_TYPE="login";

    private boolean isLoginHidden = true;//控制密码是否显示
    private CustomTextWatcher customLoginTextWatcher;

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
        tvTitle.setText("登陆");
        editLoginTexts.add(etPsw);
        customLoginTextWatcher = new CustomTextWatcher(editLoginTexts,LOGIN_TYPE,ib_login_biyan,btnLogin);
        etPsw.addTextChangedListener(customLoginTextWatcher);
    }

    @Override
    protected void initData() {

    }

    //检查注册手机号格式
    private boolean checkRegisterMobile() {
        String mobile = etName.getText().toString().trim();// 获得输入的手机号
        if ("".equals(mobile)) {
            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        } else if (VerifyUtils.isMobileNO(mobile)) {

            //手机号正确
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void initListener() {
        CustomTextWatcher customLoginTextWatcher = new CustomTextWatcher(editLoginTexts,ib_login_biyan);
        etPsw.addTextChangedListener(customLoginTextWatcher);
    }

    @OnClick({R.id.ll_left, R.id.tv_register,R.id.ib_login_biyan,R.id.tv_pwd_forget, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ib_login_biyan://显示密码
                if (isLoginHidden) {
                    //设置EditText文本为可见的
                    etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ib_login_biyan.setImageResource(R.drawable.icon_yanjing_normal);

                } else {
                    //设置EditText文本为隐藏的
                    etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ib_login_biyan.setImageResource(R.drawable.icon_yanjing_biyan);
                }
                EditTextUtils.setSpan(etPsw);
                isLoginHidden = !isLoginHidden;
                etPsw.postInvalidate();
                break;
            case R.id.tv_pwd_forget://忘记密码
                break;
            case R.id.btn_login://登陆
                if(checkRegisterMobile()){
                    if(!TextUtils.isEmpty(etPsw.getText().toString())){
                        showToastShort("登陆");
                        login();
                    }else{
                        showToastShort("请输入密码！");
                    }

                }
                break;
            case R.id.tv_register://注册
                openActivityAndCloseThis(RegisterActivity.class);
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    /**
     * 登陆
     */
    private void login() {
        String phone = etName.getText().toString().trim();
        String pwd = etPsw.getText().toString().trim();


        Bundle bundle = new Bundle();
        bundle.putString("sss","sss");
        SharedPreferencesUtils.putLogin(LoginActivity.this, Contants.TOKEN,"token");
        openActivityAndCloseThis(MainActivity.class,bundle);
    }
}
