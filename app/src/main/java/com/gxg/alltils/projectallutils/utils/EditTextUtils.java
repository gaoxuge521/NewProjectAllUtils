package com.gxg.alltils.projectallutils.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;

import com.gxg.alltils.projectallutils.application.BaseApplication;

/**
 * 富文本编辑
 * @author zhaowen
 *
 */

public class EditTextUtils {
    /**
     * 获取本地图片资源
     */
    private static Html.ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            int id = getInteger(source,0);
            // 根据id从资源文件中获取图片对象
            Drawable d = ContextCompat.getDrawable(BaseApplication.getmInstance(),id);
            // 以此作为标志位，方便外部取出对应的资源id
            d.setState(new int[]{id});
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    };

    public static void setSpan(EditText editText) {
        CharSequence text = editText.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * 将富文本转成CharSequence
     *
     * @param commonStr 普通内容
     * @param bqId      表情图片
     */
    public static CharSequence transferEmoji(String commonStr, int bqId) {
        if (bqId == 0) {
            return Html.fromHtml(commonStr, null, null);
        } else {
            return Html.fromHtml(commonStr + "<img src=\"" + bqId + "\">", imageGetter, null);
        }
    }

    public static int getInteger(Object intValue, int defaultValue) {

        if(intValue == null || "".equals(intValue.toString().trim())){
            return defaultValue;
        }

        try {
            return Integer.valueOf(intValue.toString());
        } catch (Exception e) {

            try{
                return Double.valueOf(intValue.toString()).intValue();
            }catch (Exception eDouble){
                return defaultValue;
            }
        }
    }

}
