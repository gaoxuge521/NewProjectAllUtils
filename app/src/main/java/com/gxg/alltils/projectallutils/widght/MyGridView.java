package com.gxg.alltils.projectallutils.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 作者：Administrator on 2017/11/24 16:17
 * 邮箱：android_gaoxuge@163.com
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
