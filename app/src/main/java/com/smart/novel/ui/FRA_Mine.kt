package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.mvp.model.BookShelfModel
import com.smart.novel.mvp.presenter.BookShelfPresenter

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 我的
 */
class FRA_Mine : BaseMVPFragment<BookShelfPresenter, BookShelfModel>() {

    companion object {
        fun getInstance(): FRA_Mine {
            val fragment = FRA_Mine()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_mine
    }

    override fun startEvents() {
    }

    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun showException(error: ErrorBean?) {

    }

    override fun onFirstUserVisible() {
    }

    override fun onUserVisible() {
    }

    override fun onUserInvisible() {
    }

    override fun getLoadingTargetView(): View? {
        return null
    }


    override fun isBindEventBusHere(): Boolean {
        return false
    }
}