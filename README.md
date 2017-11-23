

RxBus
    回到首页显示homeFragment
    RxBus.getDefault().post(new ShowHomeEvent(ShowHomeEvent.NAME,1));



-----------------------------------------设置过滤表情和200字限制--------------------------------------------
 //设置过滤表情和200字限制
 etCommit.setFilters(new InputFilter[]{new EmojiFilter(),new LengthFilter(400)});

-----------------------------------------设置过滤表情和200字限制--------------------------------------------



-----------------------------------------第三方类库--------------------------------------------
takephoto_library  TakePhoto是一款用于在Android设备上获取照片（拍照或从相册、文件中选择）、裁剪图片、压缩图片的开源工具库
 https://github.com/crazycodeboy/TakePhoto/

PickerView控件，有时间选择器和选项选择器(一下自定义的选择器也可以使用这个库)
 https://github.com/Bigkoo/Android-PickerView

dropmenulibrary 一个实用的多条件筛选菜单
https://github.com/dongjunkun/DropDownMenu

//倒计时控件
//https://github.com/iwgang/CountdownView

//6.0权限
//https://github.com/yanzhenjie/AndPermission

//二维码扫描
//https://github.com/bingoogolapple/BGAQRCode-Android

//大神的动态改变retrofit的baseurl
//https://github.com/JessYanCoding/RetrofitUrlManager

//轮播图
//https://github.com/saiwu-bigkoo/Android-ConvenientBanner
-----------------------------------------第三方类库--------------------------------------------




-----------------------------------弹框工具类CustomDialog--------------------------------------
确定取消的底部pop都可以使用user/layout/layout_popupview_dialog_delete.xml布局
有标题弹出框中部com/gxg/alltils/projectallutils/widght/CustomDialog  baseDialog   baseDialogForIOS
只有确定取消两个按钮的弹框 CustomDialog.  showTwoButtonDialog
客服功能显示在线咨询和一键拨号的弹框 CustomDialog. baseDialogForJiuxiCustom
未安装第三方应用的提示弹框（您暂未安装qq应用）CustomDialog.baseDialogForThirdLogin
预约成功CustomDialog. showReservationSuccessDialog
完善资料弹出框dialogForUserinfo
只显示一张图片的弹框baseDialogForNewer
自定义中部显示弹框showCenterDialog(Context context, Dialog dialog)
自定义底部显示弹框showBottonDialog(Context context, Dialog dialog,View view)
领取优惠券弹出框showCouponDialog
只有确定按钮的带标题的弹框showSingleButtonDialog
提示性全屏的弹出框showFullScreenButtonDialog(仿支付宝加载成功的控件，圆形旋转转对号)
showSingleButtonDialogForJiuxi
baseDialogForJiuxi
-----------------------------------弹框工具类CustomDialog--------------------------------------