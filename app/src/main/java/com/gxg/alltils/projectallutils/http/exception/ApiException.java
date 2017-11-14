package com.gxg.alltils.projectallutils.http.exception;

/**
 * 作者：Administrator on 2017/11/6 15:46
 * 邮箱：android_gaoxuge@163.com
 */
public class ApiException extends RuntimeException {
    private String code;

    public ApiException(String resultCode, String message) {
        super(message);
        this.code = resultCode;
    }
    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     */
    private static String getApiExceptionMessage(int code) {
        String message;
        switch (code) {
            case 1:
                message = "密码错误";
                break;
            case 50:
                message = "您的账号无效或已在其他设备登录，如非本人操作，请重置密码";
                break;
            case 51:
                message = "userId和token参数不存在";
                break;
            case 100:
                message = "验证码错误";
                break;
            case 102:
                message = "信息为空";
                break;
            case 103:
                message = "用户名或密码错误";
                break;
            case 104:
                message = "您输入的手机号码不存在";
                break;
            case 110:
                message = "您输入的验证码错误";
                break;
            case 111:
                message = "您已注册，可使用该账号和密码登录";
                break;
            case 112:
                message = "手机号格式错误";
                break;
            case 201:
                message = "获取列表失败";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }

    public String getCode() {
        return code;
    }
}
