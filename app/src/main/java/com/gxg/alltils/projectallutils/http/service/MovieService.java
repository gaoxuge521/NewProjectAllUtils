package com.gxg.alltils.projectallutils.http.service;


import com.gxg.alltils.projectallutils.bean.HomeListBean;
import com.gxg.alltils.projectallutils.http.RetrofitServiceManager;
import com.gxg.alltils.projectallutils.bean.HomeBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 作者：Administrator on 2017/11/6 11:26
 * 邮箱：android_gaoxuge@163.com
 */
public interface MovieService {




    /**
     * get请求，获取首页头部数据
     */
    @GET(RetrofitServiceManager.BASEURLTITLE)
    Observable<HomeBean> getHomeData(@QueryMap Map<String, Object> map);


    /**
     * get请求，获取首页列表数据
     */
    @GET(RetrofitServiceManager.BASEURLTITLE)
    Observable<HomeListBean> getHomeList(@QueryMap Map<String, Object> map);




//    /**
//     * get请求，配合rxjava
//     */
//    @GET("top250")
//    Observable<MovieSubject> rxGetData(@QueryMap Map<String, Object> map);
//
////    @FormUrlEncoded
////    @POST("top250")
////    Observable<HttpResult<MovieSubject>> rxPostData(@FieldMap Map<String,Object> map);
//
//    @FormUrlEncoded
//    @POST("top250")
//    Observable<MovieSubject> rxPostData(@FieldMap Map<String, Object> map);
}
