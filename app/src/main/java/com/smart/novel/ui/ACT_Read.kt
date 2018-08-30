package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.mvp.model.BookShelfModel
import com.smart.novel.mvp.presenter.BookShelfPresenter

/**
 * Created by JoJo on 2018/8/24.
 *wechat：18510829974
 *description：抓取网页：https://www.zhuishu.tw/id50438
 */
class ACT_Read : BaseMVPActivity<BookShelfPresenter, BookShelfModel>(), IBaseView {

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_read
    }

    override fun startEvents() {

    }

    override fun showException(error: ErrorBean?) {

    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun getLoadingTargetView(): View? {
        return null
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
}