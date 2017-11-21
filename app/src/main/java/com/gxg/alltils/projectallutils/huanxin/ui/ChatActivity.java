package com.gxg.alltils.projectallutils.huanxin.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.hyphenate.easeui.EaseConstant;

import butterknife.Bind;


public class ChatActivity extends BaseActivity {

    public static final String JX_SERVER_USERNAME_1 = "sever_001";
    public static final String JX_SERVER_USERNAME_2 = "sever_002";

    public static String ACTION="com.jiuxi.marriage.module.huanxin.ui.ChatActivity";

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_finish)
    ImageView ivFinish;
    @Bind(R.id.iv_avatar)
    ImageView iv_avatar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    private String userId;
    @Override
    public void initView() {
        userId = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        if(JX_SERVER_USERNAME_2.equals(userId)){
            tvTitle.setText("在线客服");
        }else{
            tvTitle.setText("久囍管家");
        }
//        setNickAndAvatar();
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ChatFragment chatFragment = ChatFragment.newInstance(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }


    /**
     * 设置头部的用户名的头像
     */
    public void setNickAndAvatar(){

//        EaseUserUtils.setUserNick(userId,tvTitle);
//        EaseUserUtils.setUserAvatar(mActivity,userId,iv_avatar);
    }
    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }

    /**
     * 打开会话页面 私聊方式
     *
     * @param activity
     * @param chatUserId 会话对象的环信id
     */
    public static void startChatActivity(Activity activity, String chatUserId) {
        Intent intent = new Intent(activity, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        bundle.putString(EaseConstant.EXTRA_USER_ID, chatUserId);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * 打开会话页面 私聊方式
     *
     * @param activity
     */
    public static void startChatActivity(Activity activity) {
        Intent intent = new Intent(activity, ChatActivity.class);
        activity.startActivity(intent);
    }


    /**
     * 打开会话页面 私聊方式
     *
     * @param activity
     */
    public static void startChatActivity(Activity activity,Bundle bundle) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
//    /**
//     * 打开会话页面 私聊方式
//     *
//     * @param activity
//     * @param chatUserId    会话对象的环信id
//     * @param goodsInfoBean 商品详情
//     */
//    public static void startChatActivity(Activity activity, String chatUserId, GoodsInfoBean goodsInfoBean) {
//        Intent intent = new Intent(activity, ChatActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
//        bundle.putString(EaseConstant.EXTRA_USER_ID, chatUserId);
//        bundle.putSerializable(GoodsInfoBean.class.getSimpleName(), goodsInfoBean);
//        intent.putExtras(bundle);
//        activity.startActivity(intent);
//    }


    /**
     * 打开会话页面
     *
     * @param activity
     * @param chatType   会话类型 常量类型，目前仅支持私聊  {@link EaseConstant#CHATTYPE_SINGLE 私聊}，{@link EaseConstant#CHATTYPE_GROUP 群聊}
     * @param chatUserId 会话对象的环信id
     */
    public static void startChatActivity(Activity activity, int chatType, String chatUserId) {
        Intent intent = new Intent(activity, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        bundle.putString(EaseConstant.EXTRA_USER_ID, chatUserId);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * @param activity
     * @param chatType   会话类型 常量类型，目前仅支持私聊  {@link EaseConstant#CHATTYPE_SINGLE 私聊}，{@link EaseConstant#CHATTYPE_GROUP 群聊}
     * @param chatUserId 会话对象的环信 id
     * @return 返回一个可用于跳转的 Intent
     */
    public static Intent makeIntent(Activity activity, int chatType, String chatUserId) {
        Intent intent = new Intent(activity, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        bundle.putString(EaseConstant.EXTRA_USER_ID, chatUserId);
        intent.putExtras(bundle);
        return intent;
    }




}
