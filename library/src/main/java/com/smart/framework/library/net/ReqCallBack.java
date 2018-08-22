package com.smart.framework.library.net;


import com.smart.framework.library.bean.ErrorBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Joe on 2016/12/21.
 * 统一封装的返回数据抽象类
 */
public  abstract class ReqCallBack<T> {

    /**抽象类范型的数据类型*/
    protected Type type;

    public ReqCallBack() {
        Type superClass = getClass().getGenericSuperclass();
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }


    /**
     * 响应成功
     * @param result
     */
    public  abstract  void onReqSuccess(T result);


    /**
     * 请求失败
     * @param result
     */
    public  abstract  void onFailure(String result);

    /**
     * 响应失败
     * @param error
     */
    public abstract void onReqFailed(ErrorBean error);
}
