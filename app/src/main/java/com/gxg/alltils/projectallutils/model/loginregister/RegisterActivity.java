package com.gxg.alltils.projectallutils.model.loginregister;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.utils.ButtonUtils;
import com.gxg.alltils.projectallutils.utils.EditTextUtils;
import com.gxg.alltils.projectallutils.utils.VerifyUtils;
import com.gxg.alltils.projectallutils.widght.ClearEditText;
import com.gxg.alltils.projectallutils.widght.CustomTextWatcher;
import com.gxg.alltils.projectallutils.widght.TimeButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.gxg.alltils.projectallutils.R.id.ib_login_biyan;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_input_code)
    EditText etInputCode;
    @Bind(R.id.btn_get_code)
    TimeButton btnGetCode;
    @Bind(R.id.rl_input_code)
    LinearLayout rlInputCode;
    @Bind(R.id.et_psw)
    ClearEditText etPsw;
    @Bind(ib_login_biyan)
    ImageButton ibLoginBiyan;
    @Bind(R.id.tv_account_hava)
    TextView tvAccountHava;
    @Bind(R.id.rl_auto_login)
    RelativeLayout rlAutoLogin;
    @Bind(R.id.btn_register)
    TextView btnRegister;

    public static final String LOGIN_TYPE="login";

    private boolean isLoginHidden = true;//控制密码是否显示
    private List<EditText> editLoginTexts = new ArrayList<>();
    private CustomTextWatcher customLoginTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        title.setText("注册");
        btnGetCode.setBgAfter(R.drawable.login_shape);//设置点击之后的背景
        btnGetCode.setBgBefore(R.drawable.login_shape);//设置点击之前的背景

        editLoginTexts.add(etPsw);
        customLoginTextWatcher = new CustomTextWatcher(editLoginTexts,LOGIN_TYPE,ibLoginBiyan,btnRegister);
        etPsw.addTextChangedListener(customLoginTextWatcher);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    //检查注册手机号格式
    private boolean checkRegisterMobile() {
        String mobile = etName.getText().toString().trim();// 获得输入的手机号
        if ("".equals(mobile)) {
            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        } else if (VerifyUtils.isMobileNO(mobile)) {
            btnGetCode.enableStarted(true);
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



    //检查密码格式
    private boolean checkPassword(ClearEditText cet) {
        String password = cet.getText().toString().trim();//获得输入的密码
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "密码长度不够", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() > 12) {
            Toast.makeText(getApplicationContext(), "最多输入12位数或字母", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!VerifyUtils.IsPassword(password)) {
            Toast.makeText(getApplicationContext(), "密码格式不正确,请设置6~12位数字或字母", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    protected void onDestroy() {
      if(btnGetCode!=null){
          btnGetCode.onDestroy();
      }
        super.onDestroy();
    }

    @OnClick({R.id.btn_get_code,R.id.tv_account_hava,R.id.ll_left, ib_login_biyan, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left://返回
                finish();
                break;

            case R.id.btn_get_code:
                if(checkRegisterMobile()){
                    //获取验证码
                    getMsgCode();
                }
                break;

            case ib_login_biyan://密码可见
                if (isLoginHidden) {
                    //设置EditText文本为可见的
                    etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ibLoginBiyan.setImageResource(R.drawable.icon_yanjing_normal);

                } else {
                    //设置EditText文本为隐藏的
                    etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ibLoginBiyan.setImageResource(R.drawable.icon_yanjing_biyan);
                }
                EditTextUtils.setSpan(etPsw);
                isLoginHidden = !isLoginHidden;
                etPsw.postInvalidate();
                break;
            case R.id.btn_register://注册
                if(ButtonUtils.isFastDoubleClick()){//防止频繁点击
                    if(checkRegisterMobile()){//验证手机号
                        //判断验证码
                        if(!TextUtils.isEmpty(etInputCode.getText().toString())){
                            if(checkPassword(etPsw)){//验证密码
                                register();
                            }
                        }else{
                            showToastShort("验证码不能为空！");
                        }

                    }
                }

                break;
            case R.id.tv_account_hava://登陆
                openActivityAndCloseThis(LoginActivity.class);
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {

    }

    /**
     * 获取验证码
     */
    private void getMsgCode() {

    }
}
