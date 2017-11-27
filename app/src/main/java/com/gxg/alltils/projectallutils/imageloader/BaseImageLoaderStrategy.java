package com.gxg.alltils.projectallutils.imageloader;

import android.content.Context;

/**
 * Created by Anthony on 2016/3/3.
 * Class Note:
 * abstract class/interface defined to load image
 * (Strategy Pattern used here)
 */
public interface BaseImageLoaderStrategy {
   void loadImage(Context ctx, ImageLoader img);
   void loadCircleImage(Context context,ImageLoader imageLoader,int borderWidth,int borderColor);
   void loadRoundImage(Context ctx,ImageLoader img,int radius);
}
