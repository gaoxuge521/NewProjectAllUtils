package com.gxg.alltils.projectallutils.imageloader;

import android.widget.ImageView;

import com.gxg.alltils.projectallutils.R;


/**
 * Created by Anthony on 2016/3/3.
 * Class Note:
 * encapsulation of ImageView,Build Pattern used
 * <p>
             * ImageLoader.Builder builder = new ImageLoader.Builder();
             * ImageLoader img = builder.url(url)
             * .imgView(news_img).strategy(ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI).build();
             * new ImageLoaderUtil().loadImage(mContext, img);
 */
public class ImageLoader {
    private int type;  // (Big,Medium,small)  //类型 (大图，中图，小图)
    private Object url; //url to parse //需要解析的url
    private int placeHolder; //placeholder when fail to load pics  //当没有成功加载的时候显示的图片
    private int errerHolder; //当没有成功加载的时候显示的图片
    private ImageView imgView; //ImageView instantce//ImageView的实例
    private int wifiStrategy;//load strategy ,wheather under wifi //加载策略，是否在wifi下才加载

    public int getErrerHolder() {
        return errerHolder;
    }

    public void setErrerHolder(int errerHolder) {
        this.errerHolder = errerHolder;
    }

    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;

    }

    public int getType() {
        return type;
    }

    public Object getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public static class Builder {
        private int type;
        private Object url;
        private int placeHolder;
        private ImageView imgView;
        private int wifiStrategy;
        private int errerHolder;
        public Builder() {
            this.type = ImageLoaderUtil.PIC_SMALL;
            this.url = null;
            this.placeHolder = R.mipmap.prj_default_pic_big;
            this.errerHolder = R.mipmap.prj_default_pic_big;
            this.imgView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_NORMAL;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }


        public Builder url(Object url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder errorHolder(int errorHolder) {
            this.errerHolder = errorHolder;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }

    }
}
