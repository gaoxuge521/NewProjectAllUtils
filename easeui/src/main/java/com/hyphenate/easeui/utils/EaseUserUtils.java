package com.hyphenate.easeui.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

public class EaseUserUtils {
    
    static EaseUserProfileProvider userProvider;
    
    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }
    
    /**
     * get EaseUser according username
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);
        
        return null;
    }
    
    /**
     * set user avatar
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	EaseUser user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId).error(R.drawable.new_ic_me_touxiang).placeholder(R.drawable.new_ic_me_touxiang).bitmapTransform(new GlideCircleTransform(context)).into(imageView);

            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(user.getAvatar()).error(R.drawable.new_ic_me_touxiang).bitmapTransform(new GlideCircleTransform(context)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }
        }else{
            Glide.with(context).load(R.drawable.new_ic_me_touxiang).bitmapTransform(new GlideCircleTransform(context)).into(imageView);
        }
    }
    
    /**
     * set user's nickname
     */
    public static void setUserNick(String username,TextView textView){
//        Log.e("sss", "setUserNick: "+username  +getUserInfo(username).getNick());
        if(textView != null){
        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNick() != null){
        		textView.setText(user.getNick());
        	}else{
        		textView.setText(username);
        	}
        }
    }
    
}
