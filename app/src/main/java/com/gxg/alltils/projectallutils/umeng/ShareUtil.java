package com.gxg.alltils.projectallutils.umeng;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.gxg.alltils.projectallutils.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;
import static com.umeng.socialize.bean.SHARE_MEDIA.QZONE;
import static com.umeng.socialize.bean.SHARE_MEDIA.SINA;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN_CIRCLE;

/**
 * @author tangyang
 */
class ShareUtil {
    private final SHARE_MEDIA[] displayList = new SHARE_MEDIA[]
            {
                    WEIXIN, WEIXIN_CIRCLE, SINA,
                    QQ, QZONE
            };
    private Activity context;
    private String title, content, url;
    private UMShareListener listener;
    private UMImage image;
    private UMWeb web;

    ShareUtil(Activity activity, String url, String imgUrl, UMShareListener listener) {
        this(activity, "", "", url, imgUrl, null, listener);
    }

    ShareUtil(Activity activity, String url, UMShareListener listener) {
        this(activity, "", "", url, null, null, listener);
    }

    ShareUtil(final Activity activity, String title, String content,
                     String url, String imgUrl, Integer drawable, UMShareListener listener) {
        context = activity;
        this.url = url;
        this.listener = listener;

        if (TextUtils.isEmpty(imgUrl)) {
            if (null != drawable) {
                image = new UMImage(context,
                        BitmapFactory.decodeResource(context.getResources(), drawable));
            }else{
                image = new UMImage(context,
                        BitmapFactory.decodeResource(context.getResources(), R.mipmap.ma));}
        } else{
            image = new UMImage(context, imgUrl);}
        image.compressStyle = UMImage.CompressStyle.SCALE;
        if (TextUtils.isEmpty(title))
            this.title = "Et_Shop";
        else
            this.title = title;
        if (TextUtils.isEmpty(content))
            this.content = "我在Et_Shop有新发现,绝对适合你";
        else
            this.content = content;
        //在微博开放平台设置的授权回调URL必须与代码中设置一致REDIRECT_URL
//        // 添加LinkedIn平台
//        UMLinkedInHandler linkedInHandler = newer UMLinkedInHandler(content);
//        linkedInHandler.addToSocialSDK();
        web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(image);//缩略图
        web.setDescription(content);//描述
    }

    //注意在用到分享的activity中要添加以下方法
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
//    }
    void toShare() {
        new ShareAction(context).setDisplayList(displayList)
                .withText(content)
                .withMedia(image)
                .setListenerList(listener)
                .open();
    }

    void toShare(SHARE_MEDIA platform) {

        new ShareAction(context)
                .setPlatform(platform)
                .setCallback(listener)
                .withMedia(web)
                .share();
    }

    void toShareCircle(SHARE_MEDIA platform) {
        new ShareAction(context)
                .setPlatform(platform)
                .setCallback(listener)
                .withMedia(web)
                .share();
    }
}
