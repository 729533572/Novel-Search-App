package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.mvp.model.TestModel
import com.smart.novel.mvp.presenter.TestPresenter

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 搜索小说
 */
class FRA_Search : BaseMVPFragment<TestPresenter, TestModel>() {
    override fun showException(error: ErrorBean?) {
    }

    companion object {
        fun getInstance(): FRA_Search {
            val fragment = FRA_Search()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun startEvents() {

    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_search
    }

    override fun showBusinessError(error: ErrorBean?) {
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

    fun tabCheck() {
    }
}