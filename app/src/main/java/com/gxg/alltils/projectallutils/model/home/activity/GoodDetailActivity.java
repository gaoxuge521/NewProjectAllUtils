package com.gxg.alltils.projectallutils.model.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.umeng.ShareDialog;
import com.gxg.alltils.projectallutils.widght.CustomDialog;
import com.socks.library.KLog;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import butterknife.OnClick;

public class GoodDetailActivity extends BaseActivity {


    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.ll_right)
    LinearLayout llRight;
    private CustomDialog customDialog;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void initView() {
        title.setText("商品详情");
        llRight.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);
        customDialog = new CustomDialog(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.ll_left, R.id.ll_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_right:
                showToastShort("分享");
                share();
                break;
        }
    }

    /**
     * 分享
     */
    private void share() {
        shareDialog = new ShareDialog(this, "https://www.baidu.com", new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                KLog.e("sss  000000000000"+share_media);
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                showToastShort("分享成功啦");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                showToastShort("分享失败啦");
                KLog.e("sss  222222222222222222222"+share_media+"    "+throwable.toString());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                showToastShort("分享取消了");
                KLog.e("sss  333333333333333333"+share_media);
            }
        });
        CustomDialog.showCenterDialog(this, shareDialog);
    }
}
