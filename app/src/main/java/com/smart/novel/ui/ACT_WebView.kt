package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.base.ACT_Base

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description: webview
 */
class ACT_WebView : ACT_Base(){
    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun getContentViewLayoutID(): Int {
       return R.layout.act_web_view
    }

    override fun getLoadingTargetView(): View? {
        return null
    }

    override fun initViewsAndEvents() {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
}