package com.smart.framework.library.net.retrofit;

/**
 * Created by JoJo on 2018/4/1.
 * wechat：18510829974
 * description：
 */
public interface BaseObserverListener<T extends Object> {
    void onNext(T result);
    void onComplete();
    void onError(Throwable e);
}
