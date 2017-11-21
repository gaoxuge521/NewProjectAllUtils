package com.gxg.alltils.projectallutils.huanxin.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxg.alltils.projectallutils.MainActivity;
import com.gxg.alltils.projectallutils.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EasyUtils;
import com.socks.library.KLog;

import java.util.List;


/**
 * Create by devin Sun.
 */
public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;
    protected int[] itemStrings = {R.string.attach_picture,R.string.attach_location,R.string.attach_take_pic};
    protected int[] itemDrawables = {R.drawable.icon_photo,R.drawable.icon_location,R.drawable.icon_camero};
    protected int[] itemIds = {ITEM_PICTURE,ITEM_LOCATION,ITEM_TAKE_PICTURE};


    private RelativeLayout rlGoodsInfoCard;
    private ImageView ivGoodsImg;
    private TextView tvGoodsName;
    private TextView tvGoodsSalePrice;
    private TextView tvSendGoodsUrl;

    /**
     * @param chatType   会话类型
     * @param chatUserId 会话对象的环信Id
     * @return
     */
    public static ChatFragment newInstance(int chatType, String chatUserId) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        args.putString(EaseConstant.EXTRA_USER_ID, chatUserId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param chatType      会话类型
     * @param chatUserId    会话对象的环信Id
     * @param goodsInfoBean
     * @return
     */
//    public static ChatFragment newInstance(int chatType, String chatUserId, GoodsInfoBean goodsInfoBean) {
//        ChatFragment fragment = new ChatFragment();
//        Bundle args = new Bundle();
//        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, chatType);
//        args.putString(EaseConstant.EXTRA_USER_ID, chatUserId);
//        args.putSerializable(GoodsInfoBean.class.getSimpleName(), goodsInfoBean);
//        fragment.setArguments(args);
//        return fragment;
//    }

    /**
     * @param bundle 存有会话类型和会话对象id的bundle ,建议使用{@link ChatFragment#newInstance(int chatType, String chatUserId)}
     * @return
     */
    public static ChatFragment newInstance(Bundle bundle) {
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ChatFragment newInstance(String chatUserid){
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, chatUserid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        rlGoodsInfoCard = (RelativeLayout) getView().findViewById(R.id.rl_goods_info_card);
        ivGoodsImg = (ImageView) getView().findViewById(R.id.iv_goods_img);
        tvGoodsName = (TextView) getView().findViewById(R.id.tv_goods_name);
        tvGoodsSalePrice = (TextView) getView().findViewById(R.id.tv_goods_sale_price);
        tvSendGoodsUrl = (TextView) getView().findViewById(R.id.tv_send_goods_url);

//        final GoodsInfoBean goodsInfoBean = (GoodsInfoBean) getArguments().getSerializable(GoodsInfoBean.class.getSimpleName());
//        if (goodsInfoBean == null) {
//            rlGoodsInfoCard.setVisibility(View.GONE);
//        } else {
//            rlGoodsInfoCard.setVisibility(View.VISIBLE);
//            GlideUtil.getInstance().setImage(ivGoodsImg, goodsInfoBean.getCoverImg());
//            tvGoodsName.setText(goodsInfoBean.getGoodsName());
//            tvGoodsSalePrice.setText(goodsInfoBean.getPrice() + "元");
//            tvSendGoodsUrl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    sendTextMessage("商品链接：" + goodsInfoBean.getGoodsUrl());
//                }
//            });
//        }


//        LogUtils.e("sss",GlobalVar.getUser().getEmobusername()+"  "+GlobalVar.getUser().getEmobpassword());
    }

    @Override
    protected void setUpView() {

        KLog.e("sss    "+toChatUsername+"   "+chatType);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        int msgCount = conversation.getAllMsgCount();

//        if (TextUtils.equals(toChatUsername, ChatActivity.JX_SERVER_USERNAME_1)
//                || TextUtils.equals(toChatUsername, ChatActivity.JX_SERVER_USERNAME_2) && msgCount == 0) {
//
//            EMMessage emMessage = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
//            EMTextMessageBody txtBody = newer EMTextMessageBody("您好，有什么可以帮助您的吗？");
//            emMessage.addBody(txtBody);
//            emMessage.setAcked(true);
//            emMessage.setTo(EMClient.getInstance().getCurrentUser());
//            emMessage.setFrom(toChatUsername);
//            emMessage.setUnread(false);
//            emMessage.setDelivered(true);
//            emMessage.setStatus(EMMessage.Status.SUCCESS);
//            //向数据库和缓存中添加消息，消息会添加到队列的尾部
//            conversation.appendMessage(emMessage);
//            //向数据库和缓存中插入消息
////            conversation.insertMessage(emMessage);
//        }

        if ( msgCount == 0) {

            EMMessage emMessage = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
            EMTextMessageBody txtBody = new EMTextMessageBody("您好，有什么可以帮助您的吗？");
            emMessage.addBody(txtBody);
            emMessage.setAcked(false);
            emMessage.setTo(EMClient.getInstance().getCurrentUser());
            emMessage.setFrom(toChatUsername);
            emMessage.setUnread(false);
            emMessage.setDelivered(true);
            emMessage.setStatus(EMMessage.Status.SUCCESS);
            //向数据库和缓存中添加消息，消息会添加到队列的尾部
            conversation.appendMessage(emMessage);
            //向数据库和缓存中插入消息
//            conversation.insertMessage(emMessage);
        }


        super.setUpView();
        setChatFragmentListener(this);

        //隐藏标题栏
        hideTitleBar();
        //显示标题栏
//        showTitleBar();
        // set click listener
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAdded() && EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });

    }

    /**
     * 生成底部操作栏
     */
    @Override
    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemDrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }



    /**
     * 设置消息扩展属性
     */
    @Override
    public void onSetMessageAttributes(EMMessage message) {
        //设置消息扩展属性
//        LogUtils.e("sss",GlobalVar.getUser().getNickName()+GlobalVar.getUser().getPhoto());
//       if(!TextUtils.isEmpty(GlobalVar.getUser().getNickName())){
//           message.setAttribute(EaseConstant.USERNAME, GlobalVar.getUser().getNickName());
//       }else{
//           message.setAttribute(EaseConstant.USERNAME, "用户");
//       }
//
//       if(!TextUtils.isEmpty(GlobalVar.getUser().getPhoto())){
//           message.setAttribute(EaseConstant.USERPHONE,GlobalVar.getUser().getPhoto());
//       }else{
//           message.setAttribute(EaseConstant.USERPHONE,"");
//       }


    }


    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        super.onMessageReceived(messages);
//        LogUtils.e("sss","收到消息"+messages.toString());
    }

    /**
     * 进入会话详情
     */
    @Override
    public void onEnterToChatDetails() {

    }

    /**
     * 用户头像点击事件
     *
     * @param username
     */
    @Override
    public void onAvatarClick(String username) {

    }

    /**
     * 用户头像长按事件
     *
     * @param username
     */
    @Override
    public void onAvatarLongClick(String username) {

    }

    /**
     * 消息气泡框点击事件
     */
    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    /**
     * 消息气泡框长按事件
     */
    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    /**
     * 扩展输入栏item点击事件,如果要覆盖EaseChatFragment已有的点击事件，return true
     *
     * @param view
     * @param itemId
     * @return
     */
    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {

        return false;
    }

    /**
     * 设置自定义chatrow提供者
     *
     * @return
     */
    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }


}
