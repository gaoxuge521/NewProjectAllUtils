package com.gxg.alltils.projectallutils.http.service;


import com.gxg.alltils.projectallutils.http.bean.MovieSubject;
import com.gxg.alltils.projectallutils.http.result.HttpResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 作者：Administrator on 2017/11/6 11:26
 * 邮箱：android_gaoxuge@163.com
 */
public interface MovieService {

    /**
     * get请求，配合rxjava
     */
    @GET("top250")
    Observable<HttpResult<MovieSubject>> rxGetData(@QueryMap Map<String, Object> map);

//    @FormUrlEncoded
//    @POST("top250")
//    Observable<HttpResult<MovieSubject>> rxPostData(@FieldMap Map<String,Object> map);

    @FormUrlEncoded
    @POST("top250")
    Observable<MovieSubject> rxPostData(@FieldMap Map<String, Object> map);

























    /**
     * get请求，只用retrofit
     * 定义了一个方法getTop250,使用get请求方式，加上@GET 标签，标签后面是这个接口的 尾址top250
     * ,完整的地址应该是 baseUrl+尾址 ，参数 使用@Query标签，如果参数多的话可以用@QueryMap标签，接收一个Map
     * @param start
     * @param count
     * @return
     */
    @GET("top250")
    Call<MovieSubject> getTop250(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Call<MovieSubject> getTop250ByMap(@QueryMap Map<String, Object> map);


    /**
     *  post请求，只用retrofit
     * 说明：使用POST 请求方式时，只需要更改方法定义的标签，用@POST 标签，参数标签用 @Field 或者@Body或者FieldMap，
     * 注意：使用POST 方式时注意2点，1，必须加上 @FormUrlEncoded标签，否则会抛异常。2，使用POST方式时，必须要有参数，否则会抛异常,
     * @return
     */
    @FormUrlEncoded
    @POST("top250")
    Call<MovieSubject> getTop250ByPost(@Field("start") int start, @Field("count") int count);

    @FormUrlEncoded
    @POST("top250")
    Call<MovieSubject> getTop250ByPostOrMap(@FieldMap Map<String, Object> map);
}
