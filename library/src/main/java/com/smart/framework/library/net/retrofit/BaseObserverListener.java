package com.smart.framework.library.net.retrofit;

import com.smart.framework.library.bean.ErrorBean;

/**
 * Created by JoJo on 2018/4/1.
 * wechat：18510829974
 * description：
 */
public interface BaseObserverListener<T> {
    void onSuccess(T result);
    void onComplete();
    void onError(Throwable e);
    void onBusinessError(ErrorBean errorBean);//http返回200,但是数据返回是错误的
}
