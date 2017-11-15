package com.gxg.alltils.projectallutils.imageloader;

import android.widget.ImageView;

import com.gxg.alltils.projectallutils.R;

import java.io.File;


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
    private int type;  // (Big,Medium,small)
    private String url; //url to parse
    private int placeHolder; //placeholder when fail to load pics
    private ImageView imgView; //ImageView instantce
    private int wifiStrategy;//load strategy ,wheather under wifi
    private File imgFile;

    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
        this.imgFile = builder.imgFile;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
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
        private String url;
        private int placeHolder;
        private ImageView imgView;
        private int wifiStrategy;
        private File imgFile;

        public Builder() {
            this.type = ImageLoaderUtil.PIC_SMALL;
            this.url = "";
            this.placeHolder = R.mipmap.prj_default_pic_big;
            this.imgView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_NORMAL;
            this.imgFile = null;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder file(File file){
            this.imgFile =file;
            return this;
        }
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
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
