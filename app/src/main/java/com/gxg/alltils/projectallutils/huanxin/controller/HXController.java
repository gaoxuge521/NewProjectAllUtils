package com.gxg.alltils.projectallutils.huanxin.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gxg.alltils.projectallutils.huanxin.ui.ChatActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

/**
 * Created by Administrator on 2017/3/9.
 */
public class HXController {

    /**
     * 初始化环信
     *
     * @param context 上下文
     */
    public static void init(final Context context) {
        KLog.e("环信初始化22222222");
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //开启自动登录
        options.setAutoLogin(false);
        //初始化EaseUI
        EaseUI.getInstance().init(context, options);

        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);


        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {

                // TODO: 2017/3/13  根据官方文档建议，此时应该根据username获取该用户的昵称和头像，并封装成EaseUser对象返回，如果未能获取到用户信息，则建议从app后台获取
                // TODO: 2017/3/13  当从后台获取到信息后，应当调用刷新ui的方法，而刷新ui，又会调用getUser方法，从而加载用户信息。
                EaseUser currentUser = new EaseUser(username);

//                LogUtils.e("huanxin","username  "+username+"  "+EMClient.getInstance().getCurrentUser());
//                LogUtils.e("huanxin","username  "+username+"  "+GlobalVar.getUser().getNickName()+"    "+GlobalVar.getUser().getPhoto());
                //如果是自己
                if (TextUtils.equals(username, EMClient.getInstance().getCurrentUser())) {
                    currentUser.setNickname("我自己");
                    currentUser.setAvatar(null);
//                    currentUser.setNickname(GlobalVar.getUser().getNickName());
//                    currentUser.setAvatar(GlobalVar.getUser().getPhoto());
                    //如果是官方客服
                } else if (TextUtils.equals(ChatActivity.JX_SERVER_USERNAME_1, username) || TextUtils.equals(ChatActivity.JX_SERVER_USERNAME_2, username)) {
                    currentUser.setNickname("久囍客服");
                    currentUser.setAvatar("http://images.9xi.com/727c05dc-c4b6-4968-9a0c-fa0f181064b4.png");
                    //其他人
                    // FIXME: 2017/3/21  头像和昵称的处理
                } else {

                    SharedPreferences user = context.getSharedPreferences(username, Context.MODE_PRIVATE);

                    String string = user.getString(EaseConstant.USERPHONE, "");
                    String name = user.getString(EaseConstant.USERNAME, "久囍管家");
                    currentUser.setNickname(name);
                    currentUser.setAvatar(string);

                }


//                LogUtils.e("huanxin",currentUser.toString());
                return currentUser;
            }
        });

        //设置消息提示音
        setSettingConfig();
        //设置消息通知的配置
        setNotificationConfig(context);
        //注册环信的连接监听
        regConnectionListener(context);
        //设置消息监听
        registerMessageListener();
    }


    /**
     * 设置消息通知的配置
     *
     * @param context
     */
    private static void setNotificationConfig(final Context context) {


        EaseUI.getInstance().getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
            @Override
            public String getDisplayedText(EMMessage message) {
            // 设置状态栏的消息提示，可以根据message的类型做相应提示

//                RxBus.getDefault().post(new HXEvent(HXEvent.HX_NEW_MSG, 0));
//                LogUtils.e("sss","收到了新消息");
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, context);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if (user != null) {
                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                } else {
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
//                LogUtils.e("sss","收到了新消息");
                return fromUsersNum + "联系人发来了" + messageNum + "条消息";
            }

            @Override
            public String getTitle(EMMessage message) {
//                LogUtils.e("sss","收到了新消息");
                ////修改标题,
                return "您收到了新消息";
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                ////设置小图标，这里为默认
                return 0;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {

                //设置点击通知栏跳转事件
                Intent intent = new Intent(context, ChatActivity.class);
                EMMessage.ChatType chatType = message.getChatType();
                if (chatType == EMMessage.ChatType.Chat) { // 单聊信息
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, message.getFrom());
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                } else { // 群聊信息
                    // message.getTo()为群聊id
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, message.getTo());
                    if (chatType == EMMessage.ChatType.GroupChat) {
                        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                    } else {
                        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_CHATROOM);
                    }
                }
                return intent;
            }
        });
    }

    /**
     * 消息提醒的配置
     */
    private static void setSettingConfig() {
        EaseUI.getInstance().setSettingsProvider(new EaseUI.EaseSettingsProvider() {
            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isSpeakerOpened() {
                return true;
            }
        });
    }


    /**
     * 注册环信的连接监听
     *
     * @param context
     */
    private static void regConnectionListener(final Context context) {

        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
            @Override
            public void onConnected() {
                // TODO: 2017/3/9  环信链接后的回调 子线程
                // do some things ...
            }

            @Override
            public void onDisconnected(int error) {

                // TODO: 2017/3/9  环信断开链接的回调 子线程

                if (error == EMError.USER_REMOVED) {
                    // 显示帐号已经被移除
//                    LogUtils.e("sss","显示帐号已经被移除");
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                    // 发送消息
//                    Intent intent = new Intent(context, DialogActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                    LogUtils.e("sss","显示帐号在其他设备登录");
                } else {
                    if (NetUtils.hasNetwork(context)) {
                        //连接不到聊天服务器
//                        LogUtils.e("sss","连接不到聊天服务器");
                    } else {
//                        LogUtils.e("sss","当前网络不可用，请检查网络设置");
                        //当前网络不可用，请检查网络设置
                    }
                }
            }
        });
    }

    private static String picturl;
    private static String name;

    /**
     * 设置消息监听
     * Global listener
     * If this event already handled by an activity, you don't need handle it again
     * activityList.size() <= 0 means all activities already in background or not in Activity Stack
     */
    private static void registerMessageListener() {
        EMMessageListener messageListener = new EMMessageListener() {


            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {

//                    LogUtils.e("sss","onMessageReceived id : " + message.getMsgId());
                    //接收处理扩展消息
                    String em_name=message.getStringAttribute("name","");
                    String em_picturl=message.getStringAttribute("picturl","");
//                    LogUtils.e("huanxin",em_name+"  "+em_picturl);


                    //下面是环信客服的扩展消息，本身可以使用和上方一样的方法（扩展参数不同），但是不知道为什么取不到值，因此先取weichat，自己解析扩展消息
                    String hxIdFrom = message.getFrom();
                    EaseUser easeUser = new EaseUser(hxIdFrom);
                    if(null==picturl||picturl.length()==0){
                        try {
                            JSONObject jsonObject = message.getJSONObjectAttribute("weichat");
                            JSONObject jsonObject1=jsonObject.getJSONObject("agent");
                            easeUser.setAvatar("http:"+jsonObject1.getString("avatar"));
                            easeUser.setNickname(jsonObject1.getString("userNickname"));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        easeUser.setAvatar(picturl);
                        easeUser.setNickname(name);
                    }



                    // in background, do not refresh UI, notify it in notification bar
                    //应用在后台，不需要刷新UI,通知栏提示新消息
                    if (!EaseUI.getInstance().hasForegroundActivies()) {
                        EaseUI.getInstance().getNotifier().onNewMsg(message);
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action

//                    LogUtils.e("sss","收到透传消息");
                    //red packet code : 处理红包回执透传消息
//                    if(!EaseUI.getInstance().hasForegroundActivies()){
//                        if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//                            RedPacketUtil.receiveRedPacketAckMessage(message);
//                            broadcastManager.sendBroadcast(newer Intent(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION));
//                        }
//                    }

//                    if (action.equals("__Call_ReqP2P_ConferencePattern")) {
//                        String title = message.getStringAttribute("em_apns_ext", "conference call");
//                        Toast.makeText(context, title, Toast.LENGTH_LONG).show();
//                    }
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
//                LogUtils.e("sss","收到已读回执");
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
//                LogUtils.e("sss","收到已送达回执");
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
//                LogUtils.e("sss","消息状态变动");

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }


    /**
     * 判断当前环信用户是否登录
     *
     * @return true 已经登录，false 未登录。
     */
    public static boolean hXIsLogin() {
        boolean b = EMClient.getInstance().isLoggedInBefore();
        if (b) {
            loadAllGroupsAndConversations();
        }
        return b;
    }


    /**
     * 登录环信
     *
     * @param userId     环信帐号
     * @param pwd        环信密码
     * @param emCallBack 登录结果的回调 ,注意：回调在子线程
     */
    public static void loginHX(@NonNull String userId, @NonNull String pwd, @NonNull final EMCallBack emCallBack) {

//        LogUtils.e("sss",userId +"  "+pwd );

        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(pwd)) {
            EMClient.getInstance().login(userId, pwd, new EMCallBack() {
                @Override
                public void onSuccess() {
                    loadAllGroupsAndConversations();
                    emCallBack.onSuccess();
                }

                @Override
                public void onError(int i, String s) {
                    emCallBack.onError(i, s);
                }

                @Override
                public void onProgress(int i, String s) {
                    emCallBack.onProgress(i, s);
                }
            });

        }
    }


    /**
     * 退出环信登录
     *
     * @param emCallBack 退出环信登录结果回调，注意：回调在子线程
     */
    public static void logoutHX(@NonNull EMCallBack emCallBack) {

        //如果集成了GCM等第三方推送，方法里第一个参数需要设为true
        EMClient.getInstance().logout(true, emCallBack);
    }

    /**
     * 加载所有的群组和会话列表，保证进入主页面后本地会话和群组都 load 完毕。
     */
    private static void loadAllGroupsAndConversations() {
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
    }


}
