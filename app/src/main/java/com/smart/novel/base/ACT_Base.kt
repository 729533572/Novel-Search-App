package com.smart.novel.base

import android.view.ViewGroup
import com.hazz.kotlinmvp.utils.StatusBarUtil
import com.smart.framework.library.base.BaseActivity
import com.smart.framework.library.netstatus.NetUtils
import com.smart.novel.MyApplication.Companion.context

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

    override fun isApplyStatusBarTranslucency(): Boolean {
        return false
    }

    /**
     * 不重写该方法，默认是黑色背景、白色icon白色字的状态栏
     * 下面统一处理成白色背景，深色字体，icon的状态栏
     */
    override fun handleStatusBar() {
        val rootView = (findViewById(android.R.id.content)) as ViewGroup
        val contentView = rootView.getChildAt(0) as ViewGroup
//        //有可能是线性布局和相对布局，也有可能是帧布局，所以在这里面添加一个LinearLayout，保证外层布局是LinearLayout，然后在LinearLayout
//        var fakeTitlleView = View(this)
//        var lp: ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
//        fakeTitlleView.layoutParams = lp
//        fakeTitlleView.setBackgroundColor(Color.WHITE)
//        contentView.addView(fakeTitlleView, 0)
        StatusBarUtil.darkMode(this)//状态栏字体颜色及icon变黑
//        StatusBarUtil.setPaddingSmart(this, fakeTitlleView)
        contentView.setPadding(contentView.paddingLeft, contentView.paddingTop + StatusBarUtil.getStatusBarHeight(context),
                contentView.paddingRight, contentView.paddingBottom)
    }

    override fun toggleOverridePendingTransition(): Boolean {
        return true
    }

    override fun getOverridePendingTransitionMode(): TransitionMode? {
        return TransitionMode.RIGHT
    }
}