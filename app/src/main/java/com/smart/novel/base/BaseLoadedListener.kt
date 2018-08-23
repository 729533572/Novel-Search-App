package com.smart.novel.base


import com.smart.framework.library.bean.ErrorBean

/**
 * 统一返回数据接口
 */
interface BaseLoadedListener<T> {
    /**
     * 请求接口成功
     * @param event_tag
     * @param data
     */
    fun onSuccess(event_tag: Int, data: T)

    /**
     * 请求接口成功但返回失败
     * @param msg
     */
    fun onError(msg: String)

    /**
     * 请求接口失败
     * @param msg
     */
    fun onException(msg: String)

    /**
     *
     * @param error
     */
    fun onBusinessError(error: ErrorBean)
}
