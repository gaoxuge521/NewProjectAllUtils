package com.gxg.alltils.projectallutils.model.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxg.alltils.projectallutils.MainActivity;
import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.application.BaseApplication;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.utils.ButtonUtils;
import com.gxg.alltils.projectallutils.utils.Contants;
import com.gxg.alltils.projectallutils.utils.EditTextUtils;
import com.gxg.alltils.projectallutils.utils.SharedPreferencesUtils;
import com.gxg.alltils.projectallutils.utils.VerifyUtils;
import com.gxg.alltils.projectallutils.widght.CustomDialog;
import com.gxg.alltils.projectallutils.widght.CustomTextWatcher;
import com.socks.library.KLog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Bind(R.id.iv_login_qq)
    ImageView ivLoginQq;
    @Bind(R.id.iv_login_wx)
    ImageView ivLoginWx;
    @Bind(R.id.iv_login_sina)
    ImageView ivLoginSina;
    private List<EditText> editLoginTexts = new ArrayList<>();
    public static final String LOGIN_TYPE = "login";

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
        customLoginTextWatcher = new CustomTextWatcher(editLoginTexts, LOGIN_TYPE, ib_login_biyan, btnLogin);
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
        } else {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void initListener() {
        CustomTextWatcher customLoginTextWatcher = new CustomTextWatcher(editLoginTexts, ib_login_biyan);
        etPsw.addTextChangedListener(customLoginTextWatcher);
    }

    @OnClick({R.id.iv_login_qq, R.id.iv_login_wx, R.id.iv_login_sina,R.id.ll_left, R.id.tv_register, R.id.ib_login_biyan, R.id.tv_pwd_forget, R.id.btn_login})
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
                if (checkRegisterMobile()) {
                    if (!TextUtils.isEmpty(etPsw.getText().toString())) {
                        showToastShort("登陆");
                        login();
                    } else {
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

            case R.id.iv_login_qq://第三方qq登陆
                KLog.e("sss 登陆qq");
                if (ButtonUtils.isFFastDoubleClick()) {
                    UMShareAPI.get(BaseApplication.getmInstance()).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
                }
                break;
            case R.id.iv_login_wx://第三方微信登陆
                KLog.e("sss 登陆微信");
                if (ButtonUtils.isFFastDoubleClick()) {
                    UMShareAPI.get(BaseApplication.getmInstance()).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                }
                break;
            case R.id.iv_login_sina://第三方微博登陆
                UMShareAPI.get(BaseApplication.getmInstance()).deleteOauth(this, SHARE_MEDIA.SINA, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        KLog.e("解除绑定开始");
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        KLog.e("解除绑定完成");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        KLog.e("解除绑定出错");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        KLog.e("解除绑定取消");
                    }
                });
                if (ButtonUtils.isFFastDoubleClick()) {
                    UMShareAPI.get(BaseApplication.getmInstance()).getPlatformInfo(this, SHARE_MEDIA.SINA, umAuthListener);
                }

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
        bundle.putString("sss", "sss");
        SharedPreferencesUtils.putLogin(LoginActivity.this, Contants.TOKEN, "token");
        openActivityAndCloseThis(MainActivity.class, bundle);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {


        @Override
        public void onStart(SHARE_MEDIA share_media) {
            KLog.e("sss"+share_media);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            //获取用户名
            String nickName = filterEmoji(data.get("screen_name"));
            String name = data.get("name");
            //获取token
            String accessToken = data.get("accessToken");
            String access_token = data.get("access_token");
            String openid = data.get("openid");
            //获取头像
            String profile_image_url = data.get("profile_image_url");
            String iconurl = data.get("iconurl");
            KLog.e("sss 登陆"+nickName+"   "+data.toString());
//            socialBean = new SocialBean();
//            socialBean.setUserHead(data.get("iconurl"));
//            socialBean.setUserNick(nickName);

            Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();

            if (platform.toString().equals("QQ")) {
//                thirdType = "tencent";
//                socialBean.setOpenId(data.get("unionid"));
//                socialBean.setUnionId("");
//                socialBean.setLoginType(thirdType);
//                thirdLogin(nickName, data.get("iconurl"), thirdType, data.get("unionid"), "", data.get("accessToken"));
            }
            if (platform.toString().equals("WEIXIN")) {
//                thirdType = "wechat";
//                socialBean.setOpenId(data.get("openid"));
//                socialBean.setUnionId(data.get("unionid"));
//                socialBean.setLoginType(thirdType);
//                thirdLogin(nickName, data.get("iconurl"), thirdType, data.get("openid"), data.get("uid"), data.get("accessToken"));
            }
            if (platform.toString().equals("SINA")) {
//                thirdType = "sina";
//                socialBean.setOpenId(data.get("uid"));
//                socialBean.setUnionId("");
//                socialBean.setLoginType(thirdType);
//                thirdLogin(nickName, data.get("iconurl"), thirdType, data.get("uid"), "", data.get("accessToken"));
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            KLog.e("sss    授权失败"+t.toString());
            showToastShort("授权失败"+t.toString());
            showError(t, platform);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToastShort("取消授权");
        }
    };

    //显示第三方登录的错误
    private void showError(Throwable t, SHARE_MEDIA platform) {
        final CustomDialog customDialog = new CustomDialog(this);
        String typeName = null;
        if (platform.toString().equals("SINA")) {
            typeName = "新浪微博";
        }

        if (platform.toString().equals("WEIXIN")) {
            typeName = "微信";
        }

        if (platform.toString().equals("QQ")) {
            typeName = "QQ";
        }


        if (t.toString().contains("没有安装应用")) {
            String content = "您暂未安装" + typeName + "应用\n" + "请您下载安装后再使用该第三方软件登录";
            customDialog.baseDialogForThirdLogin(this, null, content);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMShareAPI.get(BaseApplication.getmInstance()).onActivityResult(requestCode, resultCode, data);
    }

    //替换emoji表情为#
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("#");
                return source;
            }
            return source;
        }
        return source;
    }

}
