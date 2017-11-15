package com.gxg.alltils.projectallutils.http;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gxg.alltils.projectallutils.R;
import com.gxg.alltils.projectallutils.base.BaseActivity;
import com.gxg.alltils.projectallutils.http.bean.MovieSubject;
import com.gxg.alltils.projectallutils.http.exception.ExceptionHandle;
import com.gxg.alltils.projectallutils.http.subscriber.ProgressSubscriber;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class HttpTestActivity extends BaseActivity {

    @Bind(R.id.rv)
    RecyclerView rv;

    public static final String BASEURL = "https://api.douban.com/v2/movie/";

    Retrofit retrofit;
    private MovieSubject mMovieSubject;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_http_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataByError();

    }



    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    private void getDataByError() {
        Map<String, Object> map = new HashMap<>();
        map.put("start","1");
        map.put("count","20");
        RetrofitServiceManager.getInstance().getMovieData(new ProgressSubscriber<MovieSubject>(HttpTestActivity.this,true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable throwable) {
                int statusCode = throwable.code;
                String message = throwable.message;
                Log.e("sss", "onError: "+message+"   "+statusCode );
                showToastShort("请求失败"+message+"   "+statusCode);
            }

            @Override
            public void onNext(MovieSubject movieSubject) {
                super.onNext(movieSubject);
                showToastShort("请求成功"+movieSubject.getTitle());
                mMovieSubject = movieSubject;
                setData();
            }

            @Override
            public void onCompleted() {
                showToastShort("请求结束");
                super.onCompleted();
            }
        },map);
    }

    private void getDataByNew() {
        Map<String, Object> map = new HashMap<>();
        map.put("start","1");
        map.put("count","20");
        RetrofitServiceManager.getInstance().getMovieData(new Subscriber<MovieSubject>() {
            @Override
            public void onCompleted() {

                showToastShort("请求结束");
            }

            @Override
            public void onError(Throwable throwable) {
                if(throwable instanceof HttpException){
                    //获取对应statusCode和Message
                    HttpException exception = (HttpException)throwable;
                    String message = exception.response().message();
                    int code = exception.response().code();
                    showToastShort("请求失败"+message+"   "+code);
                }

                String message = throwable.getMessage();

                showToastShort("请求失败"+throwable.toString()+"   ");

            }

            @Override
            public void onNext(MovieSubject movieSubject) {
                showToastShort("请求成功"+movieSubject.getTitle());
                mMovieSubject = movieSubject;
                setData();
            }
        }, map);
    }



    @Override
    protected void initListener() {

    }


    /**
     * 给recycleview设置数据
     */
    private void setData() {
        if (mMovieSubject != null) {
            rv.setLayoutManager(new LinearLayoutManager(this));
            SubjectAdapter subjectAdapter = new SubjectAdapter(mMovieSubject.getSubjects());
            rv.setAdapter(subjectAdapter);

        }
    }

}
