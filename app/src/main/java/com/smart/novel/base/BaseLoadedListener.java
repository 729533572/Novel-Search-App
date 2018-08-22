package com.smart.novel.base;


import com.smart.framework.library.bean.ErrorBean;

/**
 * 统一返回数据接口
 */
public interface BaseLoadedListener<T> {
    /**
     * 请求接口成功
     * @param event_tag
     * @param data
     */
    void onSuccess(int event_tag, T data);

    /**
     * 请求接口成功但返回失败
     * @param msg
     */
    void onError(String msg);

    /**
     * 请求接口失败
     * @param msg
     */
    void onException(String msg);

    /**
     *
     * @param error
     */
    void onBusinessError(ErrorBean error);
}
