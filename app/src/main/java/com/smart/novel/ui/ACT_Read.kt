package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.novel.R
import com.smart.novel.base.ACT_Base

/**
 * Created by JoJo on 2018/8/24.
 *wechat：18510829974
 *description：抓取网页：https://www.zhuishu.tw/id50438
 */
class ACT_Read : ACT_Base() {
    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_read
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