package com.gxg.alltils.projectallutils.imageloader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.socks.library.KLog;

/**
 * 作者：Administrator on 2017/11/15 14:40
 * 邮箱：android_gaoxuge@163.com
 * 将图片转化为圆形
 * new GlideCircleTransform(mContext,2,mContext.getResources().getColor(R.color.white))) 带边框
 * new GlideCircleTransform(mContext)  不带边框
 */
public class GlideCircleTransform  extends BitmapTransformation {
    private Paint mBorderPaint;
    private float mBorderWidth;

    public GlideCircleTransform(Context context) {
        super(context);
    }

    public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
        super(context);
        mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;

        KLog.e("sss1111");
        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }


    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        KLog.e("sss2222");
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        KLog.e("sss3333");
        if (source == null) return null;
        KLog.e("sss44444");
        int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        KLog.e(mBorderPaint);
        if (mBorderPaint != null) {
            float borderRadius = r - mBorderWidth / 2;
            KLog.e("sss"+"   "+mBorderWidth+"   "+borderRadius);
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
