package com.gxg.alltils.projectallutils.widght;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/21 13:53
 * 邮箱：android_gaoxuge@163.com
 */
public class CustomTextWatcher implements TextWatcher {
    private List< ? extends EditText> list;
    private Button button;
    private ImageButton imageButton =null;
    private TextView textView;
    private String type;

    public CustomTextWatcher(List<? extends EditText> list , Button button, ImageButton imageButton) {
        this.list = list;
        this.button = button;
        this.imageButton=imageButton;
    }
    public CustomTextWatcher(List<? extends EditText> list , Button button) {
        this.list = list;
        this.button = button;

    }

    public CustomTextWatcher(List<? extends EditText> list , ImageButton imageButton) {
        this.list = list;
        this.imageButton = imageButton;

    }

    public CustomTextWatcher(List<? extends EditText> list , TextView textView) {
        this.list = list;
        this.textView = textView;

    }


    public CustomTextWatcher(List<? extends EditText> list ,String type, ImageButton imageButton,TextView textview) {
        this.list = list;
        this.imageButton = imageButton;
        this.type =type;
        this.textView =textview;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (button!=null&&type==null){
            checkButtonState();
        }
        if(imageButton!=null&&type==null){
            checkTextStare();
        }
        if(button==null&&imageButton!=null&&type==null){
            checkTextStare();
        }

        if(button==null&&textView!=null&&type==null){
            checkTextViewState();
        }

        if(type!=null){
            checkLoginRegisterViewState();
        }

    }

    private void checkLoginRegisterViewState() {
        if (imageButton!=null){
            if(TextUtils.isEmpty(list.get(0).getText().toString().trim())){
                imageButton.setVisibility(View.INVISIBLE);
            }else {
                imageButton.setVisibility(View.VISIBLE);
            }
        }

        for (EditText editText : list) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                textView.setBackgroundColor(Color.parseColor("#cccccc"));
                return;
            }
        }
        textView.setBackgroundColor(Color.parseColor("#f74467"));


    }

    private void checkTextStare() {
        if(TextUtils.isEmpty(list.get(0).getText().toString().trim())){
            imageButton.setVisibility(View.INVISIBLE);
        }else {
            imageButton.setVisibility(View.VISIBLE);
        }
    }

    private void checkButtonState() {

        for (EditText editText : list) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                button.getBackground().setAlpha(80);
                button.setEnabled(false);
                return;
            }
        }

        button.getBackground().setAlpha(255);
        button.setEnabled(true);

    }

    private void checkTextViewState(){
        for (EditText editText : list) {
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                textView.setBackgroundColor(Color.parseColor("#cccccc"));
                textView.setText("下一步");
                return;
            }
        }

        textView.setBackgroundColor(Color.parseColor("#f74467"));
        textView.setText("确定");
    }
}
