package com.gxg.alltils.projectallutils.http;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.gxg.alltils.projectallutils.application.BaseApplication;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 作者：Administrator on 2017/11/24 13:01
 * 邮箱：android_gaoxuge@163.com
 * 基于okhttp的简单封装，用于retrofit的补充
 */
public class HttpHelper {
    public static final String BASEURL = "https://b2b2c.shopnctest.com/dema/mo_bile/index.php";



    private static HttpHelper httpHelper;
    private Context context;
    private String token, app_version, userId;
    public final static int CONNECT_TIMEOUT = 60;
    private String android_id;
    private Cache mCache;
    File cacheFile;
    private Handler mHandler;

    public static HttpHelper getInstance() {
        if (httpHelper == null) {
            httpHelper = new HttpHelper(BaseApplication.getmInstance());
        }
        return httpHelper;
    }
    public static HttpHelper getInstance(Context context) {
        if (httpHelper == null) {
            httpHelper = new HttpHelper(BaseApplication.getmInstance());
        }
        return httpHelper;
    }
    public HttpHelper(Context context) {
        this.context = context;
        init();
    }
    private void init() {
        try {
            mHandler = new Handler(context.getMainLooper());
            //缓存文件夹
//            KLog.e("context : " + context);
            KLog.e("cacheDir : " + context.getExternalCacheDir());
            if (context.getExternalCacheDir() != null) {
                cacheFile = new File(context.getExternalCacheDir().getAbsolutePath(), "cachess");
                if (!cacheFile.exists()) {
                    try {
                        cacheFile.mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (cacheFile.exists()) {
//                    KLog.d("file--" + cacheFile + "");
                }
                //缓存大小为10M
                int cacheSize = 10 * 1024 * 1024;
                //创建缓存对象
                mCache = new Cache(cacheFile, cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void request(String url) {
        request(0, url, null, null);
    }
    public void request(String url, RequestBody requestBody) {
        request(1, url, requestBody, null);
    }
    public void request(String url, HttpCallBack callBack) {
        request(0, url, null, callBack);
    }

    public void request(String url,Map<String,Object> map, HttpCallBack callBack) {
        request(0, jointURL(HttpHelper.BASEURL,map), null, callBack);
    }
    public void request(String url, RequestBody requestBody, HttpCallBack callBack) {
        request(1, url, requestBody, callBack);
    }
    /**
     * 网络请求
     *
     * @param type        类型 0: GET; 1: POST; 2: DELETE
     * @param url         请求地址
     * @param requestBody 请求参数
     * @param callBack    回调方法
     */
    public void request(int type, final String url, RequestBody requestBody, final HttpCallBack callBack) {
        try {
            if (context != null) {

            }
            if (url.equals("")) {
                return;
            }
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    X509Certificate[] x509Certificates = new X509Certificate[0];
                    return x509Certificates;
                }
            };
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            //创建okHttpClient对象age
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //      .addInterceptor(interceptor)
                    .cache(mCache)
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .hostnameVerifier(DO_NOT_VERIFY)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
            //创建一个Request
            Request tempRequest = new Request.Builder()
                    .url(url)
                    .build();
            switch (type) {
                case 0:
                    tempRequest = new Request.Builder()
                            .url(url)
                            .build();
                    break;
                case 1:
                    if (requestBody != null) {
                        tempRequest = new Request.Builder()
                                .url(url)
                                .post(requestBody)
                                .build();
                    }
                    break;
                case 2:
                    if (requestBody != null) {
                        tempRequest = new Request.Builder()
                                .url(url)
                                .delete(requestBody)
                                .build();
                    }
                    break;
                default:
                    break;
            }
            final Request request = tempRequest;
            //new call
            Call call = okHttpClient.newCall(request);
            //请求加入调度
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final  IOException e) {

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(e.getMessage())) {
                                KLog.e(e.getMessage());
                            }
                            if (callBack != null) {
                                callBack.onFailure(""+e.toString());
                            }
                        }
                    });

                }
                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {

                    try {
                        int code = response.code();
//                        KLog.e("onResponse  code   ==  " + code);
                        if (code == 403) {

                        } else if (code == 404) {
                        } else if (code == 200) {
                           final String result = response.body().string();
                            if (context != null) {
//                                KLog.e("↓↓↓ " + url + " ↓↓↓");
//                                KLog.e(result);
//                                KLog.d("network--" + response.networkResponse());
//                                KLog.d("cache--" + response.cacheResponse());
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (callBack != null) {
                                        callBack.onSuccess(result);
                                    }
                                }
                            });


                        }
                        response.body().close();
                    } catch (final Exception e) {
                        e.printStackTrace();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callBack != null) {
                                    callBack.onError(""+e.toString());
                                }
                            }
                        });

                    }
                }
            });
        } catch (final  Exception e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onError(""+e.toString());
                    }
                }
            });
        }
    }
    public interface HttpCallBack {
        void onSuccess(String result);
        void onFailure(String msg);
        void onError(String msg);
    }


    /**
     * 为HttpGet请求拼接一个参数
     *
     * @author wangsong 2015-10-9
     * @param url
     * @param name
     * @param value
     */
    public static String jointURL(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }

    /**
     * 为HttpGet请求拼接多个参数
     *
     * @author wangsong 2015-10-9
     * @param url
     * @param values
     */
    public static String jointURL(String url, Map<String, Object> values) {
        StringBuffer result = new StringBuffer();
        result.append(url).append("?");
        Set<String> keys = values.keySet();
        for (String key : keys) {
            result.append(key).append("=").append(values.get(key)).append("&");
        }
        return result.toString().substring(0, result.toString().length()-1);
    }



//    /**
//     * 错误上报接口
//     *
//     * @param error_code 错误码
//     * @param error_desc 错误信息
//     * @param error_url  报错接口
//     */
//    public void uploadInfo(int error_code, String error_desc, String error_url) {
//        try {
//            String url = Constant.UPLOAD_INFO;
//            RequestBody requestBody = new FormBody.Builder()
//                    .add(Constant.UPLOAD_INFO_UPLOAD_TYPE, "2902")
//                    .add(Constant.UPLOAD_INFO_UPLOAD_INFOR, "0")
//                    .add(Constant.UPLOAD_INFO_JSONDATA, getJsonData(error_code == -1 ? "" : (error_code + ""), error_desc, error_url))
//                    .build();
//            request(url, requestBody);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private String getJsonData(String error_code, String error_desc, String error_url) {
//        String date = CommonMethod.getCurrentTime("yyyy-MM-dd HH时");
//        JSONObject jsonObject = new JSONObject();
//        try {
//            if (userId != null) {
//                jsonObject.put("userId", userId);
//            } else {
//                jsonObject.put("userId", "");
//            }
//            if (app_version != null) {
//                jsonObject.put("appVersion", app_version);
//            } else {
//                jsonObject.put("appVersion", "");
//            }
//            if (android_id != null) {
//                jsonObject.put("idfa", android_id);
//            } else {
//                jsonObject.put("idfa", "");
//            }
//            if (error_code != null) {
//                jsonObject.put("error_code", error_code);
//            } else {
//                jsonObject.put("error_code", "");
//            }
//            if (date != null) {
//                jsonObject.put("date", date);
//            } else {
//                jsonObject.put("date", "");
//            }
//            if (error_desc != null) {
//                jsonObject.put("error_desc", error_desc);
//            } else {
//                jsonObject.put("error_desc", "");
//            }
//            if (token != null) {
//                jsonObject.put("token", token);
//            } else {
//                jsonObject.put("token", "");
//            }
//            if (error_url != null) {
//                jsonObject.put("url", error_url);
//            } else {
//                jsonObject.put("url", "");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        KLog.e("jsonObject : " + jsonObject);
//        return jsonObject.toString();
//    }
    public Cache provideCache() {
        mCache = new Cache(context.getCacheDir(), 10240 * 1024);
        return mCache;
    }

}
