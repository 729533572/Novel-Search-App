package com.smart.novel.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.base.ACT_Base

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description:
 */
class ACT_Login : ACT_Base() {
    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_login
    }

    override fun getLoadingTargetView(): View? {
        return null
    }

    override fun initViewsAndEvents() {
//        super.initViewsAndEvents()
        Log.e("TAG","initViewsAndEvents")
    }

    override fun toggleOverridePendingTransition(): Boolean {
        return false
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
}