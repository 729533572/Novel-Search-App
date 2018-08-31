package com.smart.novel.base

import android.view.View
import android.view.ViewGroup
import com.hazz.kotlinmvp.utils.StatusBarUtil
import com.smart.framework.library.base.BaseActivity
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.netstatus.NetUtils
import com.smart.novel.MyApplication

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 非全屏的开启动画向右的Activity基类
 * 不需要联网的Activity的基类
 */
abstract class ACT_Base : BaseActivity() {
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

    override fun showException(error: ErrorBean?) {

    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun isApplyStatusBarTranslucency(): Boolean {
        return false
    }

    /**
     * 不重写该方法，默认是黑色背景、白色icon白色字的状态栏
     * 下面统一处理成Activity 沉浸式，全屏：白色背景，深色字体，icon的状态栏
     */
    override fun handleStatusBar() {
        val rootView = (findViewById(android.R.id.content)) as ViewGroup
        val contentView = rootView.getChildAt(0) as ViewGroup
        StatusBarUtil.darkMode(this)//状态栏字体颜色及icon变黑
        contentView.setPadding(contentView.paddingLeft, contentView.paddingTop + StatusBarUtil.getStatusBarHeight(MyApplication.context),
                contentView.paddingRight, contentView.paddingBottom)
    }

    /**
     * 沉浸式，全屏：白色icon白色字的状态栏
     */
    fun lightModeStatusBar() {
        val rootView = (findViewById(android.R.id.content)) as ViewGroup
        val contentView = rootView.getChildAt(0) as ViewGroup
        StatusBarUtil.immersive(this)//白色icon白色字的状态栏
        contentView.setPadding(contentView.paddingLeft, contentView.paddingTop,
                contentView.paddingRight, contentView.paddingBottom)
    }

    override fun getLoadingTargetView(): View? {
        return null
    }

    override fun toggleOverridePendingTransition(): Boolean {
        return true
    }

    override fun getOverridePendingTransitionMode(): TransitionMode? {
        return TransitionMode.RIGHT
    }
}