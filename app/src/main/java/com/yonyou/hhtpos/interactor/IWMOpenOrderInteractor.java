package com.yonyou.hhtpos.interactor;

import com.yonyou.hhtpos.bean.wm.OpenOrderEntity;

/**
 * 作者：liushuofei on 2017/7/15 17:53
 * 邮箱：lsf@yonyou.com
 */
public interface IWMOpenOrderInteractor {
    /**
     * 开单
     * @param bean 开单实体类
     */
    void openOrder(OpenOrderEntity bean);
}