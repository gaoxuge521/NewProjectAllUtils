package com.hyphenate.easeui.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.utils.FileUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseAlertDialog.AlertDialogUser;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu.ChatInputMenuListener;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView.EaseVoiceRecorderCallback;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EMLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.util.List;

/**
 * you can newer an EaseChatFragment to use or you can inherit it to expand.
 * You need call setArguments to pass chatType and userId 
 * <br/>
 * <br/>
 * you can see ChatActivity in demo for your reference
 *
 */
public class  EaseChatFragment extends EaseBaseFragment implements EMMessageListener {
    protected static final String TAG = "EaseChatFragment";
    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;

    /**
     * params to fragment
     */
    protected Bundle fragmentArgs;
    protected int chatType;
    protected String toChatUsername;
    protected EaseChatMessageList messageList;
    protected EaseChatInputMenu inputMenu;

    protected EMConversation conversation;
    
    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;

    protected Handler handler = new Handler();
    protected File cameraFile;
    protected EaseVoiceRecorderView voiceRecorderView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;

    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected int pagesize = 20;
    protected GroupListener groupListener;
    protected EMMessage contextMenuMessage;
    
    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;
    
    protected int[] itemStrings = { R.string.attach_take_pic, R.string.attach_picture, R.string.attach_location };
    protected int[] itemdrawables = { R.drawable.ease_chat_takepic_selector, R.drawable.ease_chat_image_selector,
            R.drawable.ease_chat_location_selector };
    protected int[] itemIds = { ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_LOCATION };
    private EMChatRoomChangeListener chatRoomChangeListener;
    private boolean isMessageListInited;
    protected MyItemClickListener extendMenuItemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ease_fragment_chat, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        fragmentArgs = getArguments();
        // check if single chat or group chat
        chatType = fragmentArgs.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        // userId you are chat with or group id
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * init view
     */
    protected void initView() {
        // hold to record voice
        //noinspection ConstantConditions
        voiceRecorderView = (EaseVoiceRecorderView) getView().findViewById(R.id.voice_recorder);

        // message list layout
        messageList = (EaseChatMessageList) getView().findViewById(R.id.message_list);
        if(chatType != EaseConstant.CHATTYPE_SINGLE)
            messageList.setShowUserNick(true);
        listView = messageList.getListView();

        extendMenuItemClickListener = new MyItemClickListener();
        inputMenu = (EaseChatInputMenu) getView().findViewById(R.id.input_menu);
        registerExtendMenuItem();
        // init input menu
        inputMenu.init(null);
        /**
         *设置相关事件监听
         */
        inputMenu.setChatInputMenuListener(new ChatInputMenuListener() {
            // 发送文本消息
            @Override
            public void onSendMessage(String content) {
                sendTextMessage(content);
            }
            ////把touch事件传入到EaseVoiceRecorderView 里进行录音
            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderCallback() {
                    // 发送语音消息
                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        sendVoiceMessage(voiceFilePath, voiceTimeLength);
                    }
                });
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
                sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }
        });

        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);

        //输入法
        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //剪贴版
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        //软键盘隐藏
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    protected void setUpView() {
        titleBar.setTitle(toChatUsername);
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            // set title
            if(EaseUserUtils.getUserInfo(toChatUsername) != null){
                EaseUser user = EaseUserUtils.getUserInfo(toChatUsername);
                if (user != null) {
                    titleBar.setTitle(user.getNick());
                }
            }
            titleBar.setRightImageResource(R.drawable.ease_mm_title_remove);
        } else {
        	titleBar.setRightImageResource(R.drawable.ease_to_group_details_normal);
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
                //group chat
                EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
                if (group != null)
                    titleBar.setTitle(group.getGroupName());
                // listen the event that user moved out group or group is dismissed
                groupListener = new GroupListener();
                EMClient.getInstance().groupManager().addGroupChangeListener(groupListener);
            } else {
                onChatRoomViewCreation();
            }

        }
        if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
            onConversationInit();
            onMessageListInit();
        }

        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleBar.setRightLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                    emptyHistory();
                } else {
                    toGroupDetails();
                }
            }
        });

        setRefreshLayoutListener();
        
        // show forward message if the message is not null
        String forward_msg_id = getArguments().getString("forward_msg_id");
        if (forward_msg_id != null) {
            forwardMessage(forward_msg_id);
        }
    }
    
    /**
     * register extend menu, item id need > 3 if you override this method and keep exist item
     */
    protected void registerExtendMenuItem(){
        for(int i = 0; i < itemStrings.length; i++){
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }
    

    //
    protected void onConversationInit(){
        //获取单个会话
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        //指定会话消息未读数清零
        conversation.markAllMessagesAsRead();
        // the number of messages loaded into conversation is getChatOptions().getNumberOfMessagesLoaded
        // you can change this number
        //获取此会话的所有消息
        final List<EMMessage> msgs = conversation.getAllMessages();
        int msgCount = msgs != null ? msgs.size() : 0;
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (msgs != null && msgs.size() > 0) {
                msgId = msgs.get(0).getMsgId();
            }
            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
        }
        
    }
    
    protected void onMessageListInit(){
        messageList.init(toChatUsername, chatType, chatFragmentHelper != null ? 
                chatFragmentHelper.onSetCustomChatRowProvider() : null);
        //设置list item里的控件的点击事件
        setListItemClickListener();
        
        messageList.getListView().setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });
        
        isMessageListInited = true;
    }
    
    protected void setListItemClickListener() {
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {
            
            @Override
            public void onUserAvatarClick(String username) {
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onAvatarClick(username);
                }
            }
            
            @Override
            public void onUserAvatarLongClick(String username) {
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onAvatarLongClick(username);
                }
            }
            
            @Override
            public void onResendClick(final EMMessage message) {
                new EaseAlertDialog(getActivity(), R.string.resend, R.string.confirm_resend, null, new AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (!confirmed) {
                            return;
                        }
                        resendMessage(message);
                    }
                }, true).show();
            }
            
            @Override
            public void onBubbleLongClick(EMMessage message) {
                contextMenuMessage = message;
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onMessageBubbleLongClick(message);
                }
            }
            
            @Override
            public boolean onBubbleClick(EMMessage message) {
                if(chatFragmentHelper == null){
                    return false;
                }
                return chatFragmentHelper.onMessageBubbleClick(message);
            }

        });
    }

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
                            List<EMMessage> messages;
                            try {
                                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                                    messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                } else {
                                    messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                }
                            } catch (Exception e1) {
                                swipeRefreshLayout.setRefreshing(false);
                                return;
                            }
                            if (messages.size() > 0) {
                                messageList.refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }

                            isloading = false;

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_more_messages),
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 400: { // 这个400就是上面defineSettingDialog()的第二个参数。
                // 你可以在这里检查你需要的权限是否被允许，并做相应的操作。
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("sss", "onActivityResult: 00000000000   "+resultCode);
        if (resultCode == Activity.RESULT_OK) {
//            Log.e("sss", "onActivityResult: 11111111111111");
            if (requestCode == REQUEST_CODE_CAMERA) { // capture newer image
//                Log.e("sss", "onActivityResult: 22222222222222" +cameraFile);
//                Log.e("sss", "onActivityResult: 22222222222222  "+cameraFile.exists());
                if (cameraFile != null && cameraFile.exists())
//                    Log.e("sss", "onActivityResult: 3333333333333");
                    sendImageMessage(cameraFile.getAbsolutePath());
            } else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_MAP) { // location
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    sendLocationMessage(latitude, longitude, locationAddress);
                } else {
                    Toast.makeText(getActivity(), R.string.unable_to_get_loaction, Toast.LENGTH_SHORT).show();
                }
                
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(isMessageListInited)
            messageList.refresh();
        EaseUI.getInstance().pushActivity(getActivity());
        // register the event listener when enter the foreground
        EMClient.getInstance().chatManager().addMessageListener(this);
        
        if(chatType == EaseConstant.CHATTYPE_GROUP){
            EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
        }
    }
    
    @Override
    public void onStop() {
        super.onStop();
        // unregister this event listener when this activity enters the
        // background
        EMClient.getInstance().chatManager().removeMessageListener(this);

        // remove activity from foreground activity list
        EaseUI.getInstance().popActivity(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (groupListener != null) {
            EMClient.getInstance().groupManager().removeGroupChangeListener(groupListener);
        }

        if(chatType == EaseConstant.CHATTYPE_CHATROOM){
            EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
        }
        
        if(chatRoomChangeListener != null){
            EMClient.getInstance().chatroomManager().removeChatRoomChangeListener(chatRoomChangeListener);
        }
        
    }

    public void onBackPressed() {
        if (inputMenu.onBackPressed()) {
            getActivity().finish();
            if(chatType == EaseConstant.CHATTYPE_GROUP){
                EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
                EaseAtMessageHelper.get().cleanToAtUserList();
            }
            if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            	EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
            }
        }
    }

    protected void onChatRoomViewCreation() {
        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Joining......");
        EMClient.getInstance().chatroomManager().joinChatRoom(toChatUsername, new EMValueCallBack<EMChatRoom>() {

            @Override
            public void onSuccess(final EMChatRoom value) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(getActivity().isFinishing() || !toChatUsername.equals(value.getId()))
                            return;
                        pd.dismiss();
                        EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(toChatUsername);
                        if (room != null) {
                            titleBar.setTitle(room.getName());
                            EMLog.d(TAG, "join room success : " + room.getName());
                        } else {
                            titleBar.setTitle(toChatUsername);
                        }
                        addChatRoomChangeListenr();
                        onConversationInit();
                        onMessageListInit();
                    }
                });
            }

            @Override
            public void onError(final int error, String errorMsg) {
                // TODO Auto-generated method stub
                EMLog.d(TAG, "join room failure : " + error);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });
                getActivity().finish();
            }
        });
    }
    

    protected void addChatRoomChangeListenr() {
//        chatRoomChangeListener = newer EMChatRoomChangeListener() {
//
//            @Override
//            public void onChatRoomDestroyed(String roomId, String roomName) {
//                if (roomId.equals(toChatUsername)) {
//                    showChatroomToast(" room : " + roomId + " with room name : " + roomName + " was destroyed");
//                    getActivity().finish();
//                }
//            }
//
//            @Override
//            public void onMemberJoined(String roomId, String participant) {
//                showChatroomToast("member : " + participant + " join the room : " + roomId);
//            }
//
//            @Override
//            public void onMemberExited(String roomId, String roomName, String participant) {
//                showChatroomToast("member : " + participant + " leave the room : " + roomId + " room name : " + roomName);
//            }
//
//            @Override
//            public void onRemovedFromChatRoom(String roomId, String roomName, String participant) {
//                if (roomId.equals(toChatUsername)) {
//                    String curUser = EMClient.getInstance().getCurrentUser();
//                    if (curUser.equals(participant)) {
//                    	EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
//                        getActivity().finish();
//                    }else{
//                        showChatroomToast("member : " + participant + " was kicked from the room : " + roomId + " room name : " + roomName);
//                    }
//                }
//            }
//
//        };
//
//        EMClient.getInstance().chatroomManager().addChatRoomChangeListener(chatRoomChangeListener);
    }
    
    protected void showChatroomToast(final String toastContent){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), toastContent, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // implement methods in EMMessageListener
    @Override
    public void onMessageReceived(List<EMMessage> messages) {

        Log.e("sss", "onMessageReceived: 接受消息");

        for (EMMessage message : messages) {

            String muserName = message.getStringAttribute(EaseConstant.USERNAME, "");

            String muserPic = message.getStringAttribute(EaseConstant.USERPHONE, "");
            String hxIdFrom = message.getFrom();
            EaseUser easeUser = new EaseUser(hxIdFrom);
            easeUser.setAvatar(muserPic);
            easeUser.setNickname(muserName);
//            Log.e("sss", "onMessageReceived:111 收到信息"+muserName+"   "+muserPic+"  "+hxIdFrom);

            SharedPreferences user = getActivity().getSharedPreferences(message.getUserName(), Context.MODE_PRIVATE);
            user.edit().putString(EaseConstant.USERNAME,muserName).commit();
            user.edit().putString(EaseConstant.USERPHONE,muserPic).commit();
            String username = null;
            // group message
            if (message.getChatType() == ChatType.GroupChat || message.getChatType() == ChatType.ChatRoom) {
                username = message.getTo();
            } else {
                // single chat message
                username = message.getFrom();
            }

            // if the message is for current conversation
            if (username.equals(toChatUsername) || message.getTo().equals(toChatUsername)) {
                messageList.refreshSelectLast();
                EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
            } else {
                EaseUI.getInstance().getNotifier().onNewMsg(message);
            }
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
//        Log.e("sss", "onCmdMessageReceived:   //收到透传消息");

    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
//        Log.e("sss", "onCmdMessageReceived:   ////收到已读回执");
        if(isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onMessageDelivered(List<EMMessage> messages) {
//        Log.e("sss", "onCmdMessageReceived:   // //收到已送达回执");
        if(isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object change) {
//        Log.e("sss", "onCmdMessageReceived:   ////消息状态变动");
        if(isMessageListInited) {
            messageList.refresh();
        }
    }
    
    
    /**
     * handle the click event for extend menu
     *
     */
    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener{

        @Override
        public void onClick(int itemId, View view) {
            if(chatFragmentHelper != null){
                if(chatFragmentHelper.onExtendMenuItemClick(itemId, view)){
                    return;
                }
            }
            switch (itemId) {
            case ITEM_TAKE_PICTURE:
                AndPermission.with(EaseChatFragment.this)
                        .requestCode(300)
                        .permission(
                                // 申请多个权限组方式：
                                Permission.CAMERA,
                                Permission.STORAGE
                        )
                        .callback(listener)
                        .start();
//                selectPicFromCamera();
                break;
            case ITEM_PICTURE:
                AndPermission.with(EaseChatFragment.this)
                        .requestCode(500)
                        .permission(
                                // 申请多个权限组方式：
                                Permission.CAMERA,
                                Permission.STORAGE
                        )
                        .callback(listener)
                        .start();
//              selectPicFromLocal();
                break;
            case ITEM_LOCATION:

                AndPermission.with(EaseChatFragment.this)
                        .requestCode(200)
                        .permission(
                                // 申请多个权限组方式：
                                Permission.LOCATION
                        )
                        .callback(listener)
                        .start();
//                startActivityForResult(new Intent(getActivity(), EaseBaiduMapActivity.class), REQUEST_CODE_MAP);
                break;

            default:
                break;
            }
        }

    }
    
    /**
     * input @
     * @param username
     */
    protected void inputAtUsername(String username, boolean autoAddAtSymbol){
        if(EMClient.getInstance().getCurrentUser().equals(username) ||
                chatType != EaseConstant.CHATTYPE_GROUP){
            return;
        }
        EaseAtMessageHelper.get().addAtUser(username);
        EaseUser user = EaseUserUtils.getUserInfo(username);
        if (user != null){
            username = user.getNick();
        }
        if(autoAddAtSymbol)
            inputMenu.insertText("@" + username + " ");
        else
            inputMenu.insertText(username + " ");
    }
    
    
    /**
     * input @
     * @param username
     */
    protected void inputAtUsername(String username){
        inputAtUsername(username, true);
    }
    

    //send message
    protected void sendTextMessage(String content) {
        if(EaseAtMessageHelper.get().containsAtUsername(content)){
            sendAtMessage(content);
        }else{
            EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
            sendMessage(message);
        }
    }
    
    /**
     * send @ message, only support group chat message
     * @param content
     */
    @SuppressWarnings("ConstantConditions")
    private void sendAtMessage(String content){
        if(chatType != EaseConstant.CHATTYPE_GROUP){
            EMLog.e(TAG, "only support group chat message");
            return;
        }
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
        if(EMClient.getInstance().getCurrentUser().equals(group.getOwner()) && EaseAtMessageHelper.get().containsAtAll(content)){
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, EaseConstant.MESSAGE_ATTR_VALUE_AT_MSG_ALL);
        }else {
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG,
                    EaseAtMessageHelper.get().atListToJsonArray(EaseAtMessageHelper.get().getAtMessageUsernames(content)));
        }
        sendMessage(message);
        
    }
    
    
    protected void sendBigExpressionMessage(String name, String identityCode){
        EMMessage message = EaseCommonUtils.createExpressionMessage(toChatUsername, name, identityCode);
        sendMessage(message);
    }

    protected void sendVoiceMessage(String filePath, int length) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        sendMessage(message);
    }

    protected void sendImageMessage(String imagePath) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
        sendMessage(message);
    }

    protected void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        sendMessage(message);
    }

    protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        sendMessage(message);
    }

    protected void sendFileMessage(String filePath) {
        EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
        sendMessage(message);
    }
    
    
    protected void sendMessage(EMMessage message){
        if (message == null) {
            return;
        }
        if(chatFragmentHelper != null){
            //set extension
            chatFragmentHelper.onSetMessageAttributes(message);
        }
        if (chatType == EaseConstant.CHATTYPE_GROUP){
            message.setChatType(ChatType.GroupChat);
        }else if(chatType == EaseConstant.CHATTYPE_CHATROOM){
            message.setChatType(ChatType.ChatRoom);
        }
        //send message
        EMClient.getInstance().chatManager().sendMessage(message);
        //refresh ui
        if(isMessageListInited) {
            messageList.refreshSelectLast();
        }
    }
    
    
    public void resendMessage(EMMessage message){
        message.setStatus(EMMessage.Status.CREATE);
        EMClient.getInstance().chatManager().sendMessage(message);
        messageList.refresh();
    }
    
    //===================================================================================
    

    /**
     * send image
     * 
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }
    
    /**
     * send file
     * @param uri
     */
    protected void sendFileByUri(Uri uri){
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = null;

            try {
                cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(getActivity(), R.string.File_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }
        //limit the size < 10M
        if (file.length() > 10 * 1024 * 1024) {
            Toast.makeText(getActivity(), R.string.The_file_is_not_greater_than_10_m, Toast.LENGTH_SHORT).show();
            return;
        }
        sendFileMessage(filePath);
    }

    /**
     * capture newer image
     */
    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(getActivity(), R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

//        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
//                + System.currentTimeMillis() + ".jpg");

        cameraFile = new File(Environment.getExternalStorageDirectory(), EMClient.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        //noinspection ResultOfMethodCallIgnored
        cameraFile.getParentFile().mkdirs();

//        FileUtils.startActionCapture(getActivity(),cameraFile,REQUEST_CODE_CAMERA);
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.getUriForFile(getActivity(),cameraFile)),
                REQUEST_CODE_CAMERA);

//        startActivityForResult(
//                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
//                REQUEST_CODE_CAMERA);
    }

    /**
     * select local image
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }



    /**
     * clear the conversation history
     * 
     */
    protected void emptyHistory() {
        String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
        new EaseAlertDialog(getActivity(),null, msg, null,new AlertDialogUser() {
            
            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if(confirmed){
                    EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
                    messageList.refresh();
                }
            }
        }, true).show();
    }

    /**
     * open group detail
     * 
     */
    protected void toGroupDetails() {
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            if(chatFragmentHelper != null){
                chatFragmentHelper.onEnterToChatDetails();
            }
        }else if(chatType == EaseConstant.CHATTYPE_CHATROOM){
        	if(chatFragmentHelper != null){
        	    chatFragmentHelper.onEnterToChatDetails();
        	}
        }
    }

    /**
     * hide
     */
    protected void hideKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    
    /**
     * forward message
     * 
     * @param forward_msg_id
     */
    protected void forwardMessage(String forward_msg_id) {
        final EMMessage forward_msg = EMClient.getInstance().chatManager().getMessage(forward_msg_id);
        EMMessage.Type type = forward_msg.getType();
        switch (type) {
        case TXT:
            if(forward_msg.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
                sendBigExpressionMessage(((EMTextMessageBody) forward_msg.getBody()).getMessage(),
                        forward_msg.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null));
            }else{
                // get the content and send it
                String content = ((EMTextMessageBody) forward_msg.getBody()).getMessage();
                sendTextMessage(content);
            }
            break;
        case IMAGE:
            // send image
            String filePath = ((EMImageMessageBody) forward_msg.getBody()).getLocalUrl();
            if (filePath != null) {
                File file = new File(filePath);
                if (!file.exists()) {
                    // send thumb nail if original image does not exist
                    filePath = ((EMImageMessageBody) forward_msg.getBody()).thumbnailLocalPath();
                }
                sendImageMessage(filePath);
            }
            break;
        default:
            break;
        }
        
        if(forward_msg.getChatType() == EMMessage.ChatType.ChatRoom){
            EMClient.getInstance().chatroomManager().leaveChatRoom(forward_msg.getTo());
        }
    }

    /**
     * listen the group event
     * 
     */
    class GroupListener extends EaseGroupListener {

        @Override
        public void onUserRemoved(final String groupId, String groupName) {
            getActivity().runOnUiThread(new Runnable() {

                public void run() {
                    if (toChatUsername.equals(groupId)) {
                        Toast.makeText(getActivity(), R.string.you_are_group, Toast.LENGTH_LONG).show();
                        Activity activity = getActivity();
                        if (activity != null && !activity.isFinishing()) {
                            activity.finish();
                        }
                    }
                }
            });
        }

        @Override
        public void onGroupDestroyed(final String groupId, String groupName) {
        	// prompt group is dismissed and finish this activity
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (toChatUsername.equals(groupId)) {
                        Toast.makeText(getActivity(), R.string.the_current_group, Toast.LENGTH_LONG).show();
                        Activity activity = getActivity();
                        if (activity != null && !activity.isFinishing()) {
                            activity.finish();
                        }
                    }
                }
            });
        }

    }
    
   
    protected EaseChatFragmentHelper chatFragmentHelper;
    public void setChatFragmentListener(EaseChatFragmentHelper chatFragmentHelper){
        this.chatFragmentHelper = chatFragmentHelper;
    }
    
    public interface EaseChatFragmentHelper{
        /**
         * set message attribute
         * 设置消息扩展属性
         */
        void onSetMessageAttributes(EMMessage message);

        /**
         * enter to chat detail
         * 进入会话详情
         */
        void onEnterToChatDetails();
        
        /**
         * on avatar clicked
         *  用户头像点击事件
         * @param username
         */
        void onAvatarClick(String username);
        
        /**
         * on avatar long pressed
         * @param username
         */
        void onAvatarLongClick(String username);
        
        /**
         * on message bubble clicked
         */
        boolean onMessageBubbleClick(EMMessage message);
        
        /**
         * on message bubble long pressed
         */
        void onMessageBubbleLongClick(EMMessage message);
        
        /**
         * on extend menu item clicked, return true if you want to override
         * @param view 
         * @param itemId 
         * @return
         */
        boolean onExtendMenuItemClick(int itemId, View view);
        
        /**
         * on set custom chat row provider
         * 设置自定义chatrow提供者
         * @return
         */
        EaseCustomChatRowProvider onSetCustomChatRowProvider();
    }


    /**
     * 权限申请的回调
     */
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if(AndPermission.hasAlwaysDeniedPermission(getActivity(), grantedPermissions)){
//                Log.e("sss","权限申请成功    "+grantedPermissions.toString());
            }
//            Log.e("sss","权限申请成功");
            if (requestCode == 200) {//定位
                startActivityForResult(new Intent(getActivity(), EaseBaiduMapActivity.class), REQUEST_CODE_MAP);
            }else if(requestCode==500){//相册
                selectPicFromLocal();
            }else if(requestCode==300){//相机
                selectPicFromCamera();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {//定位权限
                // TODO ...
//                Log.e("sss","权限申请失败" + deniedPermissions.toString());
                // 是否有不再提示并拒绝的权限。
                if (!AndPermission.hasAlwaysDeniedPermission(getActivity(), deniedPermissions)) {
//                    Log.e("sss","权限申请失败222222");
                    // 第一种：用AndPermission默认的提示语。
//                    AndPermission.defaultSettingDialog(getActivity(), 400).show();

//                    // 第二种：用自定义的提示语。
                    AndPermission.defaultSettingDialog(getActivity(), 400)
                            .setTitle("权限申请失败")
                            .setMessage("发送位置信息需要定位权限，请在设置中授权！")
                            .setPositiveButton("好，去设置")
                            .show();

//                    // 第三种：自定义dialog样式。
//                    SettingService settingService = AndPermission.defineSettingDialog(getActivity(), 400);
//                    // 你的dialog点击了确定调用：
//                    settingService.execute();
//                    // 你的dialog点击了取消调用：
//                    settingService.cancel();
                }
            }else if(requestCode==300){//相机权限
//                Log.e("sss","权限申请失败  33333");
                AndPermission.defaultSettingDialog(getActivity(), 400)
                        .setTitle("权限申请失败")
                        .setMessage("发送图片信息需要相机权限，请在设置中授权！")
                        .setPositiveButton("好，去设置")
                        .show();
            }else if(requestCode==500){//相册权限
//                Log.e("sss","权限申请失败  4444444");
                AndPermission.defaultSettingDialog(getActivity(), 400)
                        .setTitle("权限申请失败")
                        .setMessage("发送图片信息需要查看图库的权限，请在设置中授权！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }
    };





}
