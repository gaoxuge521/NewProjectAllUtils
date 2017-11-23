package com.gxg.alltils.projectallutils.model.user.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.utils.EmojiFilter;
import com.gxg.alltils.projectallutils.utils.LengthFilter;

import butterknife.Bind;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity implements TextWatcher {

    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ll_right)
    LinearLayout llRight;
    @Bind(R.id.et_commit)
    EditText etCommit;
    @Bind(R.id.tv_prompt)
    TextView tv_prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView() {
        llRight.setVisibility(View.VISIBLE);

        //设置过滤表情和200字限制
        etCommit.setFilters(new InputFilter[]{new EmojiFilter(),new LengthFilter(400)});
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        etCommit.addTextChangedListener(this);
    }

    @OnClick({R.id.ll_left, R.id.ll_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_right:
                showToastShort("已经提交成功");
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(etCommit.getText().toString().length()>=200){
            tv_prompt.setVisibility(View.VISIBLE);
        }else{
            tv_prompt.setVisibility(View.GONE);
        }
    }
}
