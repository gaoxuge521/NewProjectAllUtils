package com.gxg.alltils.projectallutils.http.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 作者：Administrator on 2017/11/6 15:29
 * 邮箱：android_gaoxuge@163.com
 */
public class RxBus {
    private static volatile RxBus defaultInstance;

    private final Subject<Object, Object> bus;
    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }
    // 单例RxBus
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance ;
    }
    // 发送一个新的事件
    public void post (Object o) {
        bus.onNext(o);
    }
    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObserverable (Class<T> eventType) {
        return bus.ofType(eventType);
//        这里感谢小鄧子的提醒: ofType = filter + cast
//        return bus.filter(newer Func1<Object, Boolean>() {
//            @Override
//            public Boolean call(Object o) {
//                return eventType.isInstance(o);
//            }
//        }) .cast(eventType);
    }
}
