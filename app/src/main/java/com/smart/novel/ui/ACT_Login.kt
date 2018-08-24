package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.novel.R
import com.smart.novel.base.ACT_Base

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description:
 */
class ACT_Login : ACT_Base() {
    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_login
    }

    override fun getLoadingTargetView(): View? {
        return null
    }

    override fun initViewsAndEvents() {
        //默认不使用StatusBarUtil处理，是黑色背景，白色文字的状态栏。使用的话，就是全屏沉浸式状态栏，setPaddingSmart：设置一个假view盖在statusbar上面的。
        /**
         * StatusBarUtil.immersive(this) //状态栏文字白色
        *   StatusBarUtil.setPaddingSmart(this, title_login)   这样是全屏的，
         */

    }

    override fun toggleOverridePendingTransition(): Boolean {
        return false
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
}