package com.smart.novel.base

import com.smart.framework.library.base.BaseActivity
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.netstatus.NetUtils

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 非全屏的开启动画向右的Activity基类
 */
abstract class ACT_Base : BaseActivity() {
    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun getRefreshTime(): Long {
        return 0
    }

    override fun isApplyKitKatTranslucency(): Boolean {
        return false
    }


    override fun onNetworkConnected(type: NetUtils.NetType?) {
    }

    override fun onNetworkDisConnected() {
    }

    override fun isApplyStatusBarTranslucency(): Boolean {
        return false
    }

    override fun initViewsAndEvents() {
    }

    override fun toggleOverridePendingTransition(): Boolean {
        return true
    }

    override fun getOverridePendingTransitionMode(): TransitionMode? {
        return TransitionMode.RIGHT
    }
}