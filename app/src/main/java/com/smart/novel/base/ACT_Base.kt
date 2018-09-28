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
     * 下面处理的思想：设置Activity根布局的paddingTop空出状态栏的高度，把状态栏单独漏出来，实际上整个Activity是全屏模式的
     * 注意：所有在Activity中使用非沉浸式时，需要指定根布局背景为白色，否则状态栏颜色和内容部分背景会不同意
     */
    override fun handleStatusBar(mode: StatusBarMode) {
        val rootView = (findViewById(android.R.id.content)) as ViewGroup
        val contentView = rootView.getChildAt(0) as ViewGroup
        when (mode) {
            StatusBarMode.DARK_NO_FULLSCREEN -> {
                StatusBarUtil.darkMode(this)//状态栏字体颜色及icon变黑
                contentView.setPadding(contentView.paddingLeft, contentView.paddingTop + StatusBarUtil.getStatusBarHeight(MyApplication.context),
                        contentView.paddingRight, contentView.paddingBottom)
            }
            StatusBarMode.DARK_FULLSCREEN -> {
                StatusBarUtil.darkMode(this)//状态栏字体颜色及icon变黑 沉浸式
                contentView.setPadding(contentView.paddingLeft, contentView.paddingTop,
                        contentView.paddingRight, contentView.paddingBottom)
            }
            StatusBarMode.LIGHT_NO_FULLSCREEN -> {
                StatusBarUtil.immersive(this)//白色icon白色字的状态栏
                contentView.setPadding(contentView.paddingLeft, contentView.paddingTop + StatusBarUtil.getStatusBarHeight(MyApplication.context),
                        contentView.paddingRight, contentView.paddingBottom)
            }
            StatusBarMode.LIGHT_FULLSCREEN -> {
                StatusBarUtil.immersive(this)//白色icon白色字的状态栏 沉浸式
                contentView.setPadding(contentView.paddingLeft, contentView.paddingTop,
                        contentView.paddingRight, contentView.paddingBottom)
            }
        }
    }


    override fun getLoadingTargetView(): View? {
        return null
    }
}