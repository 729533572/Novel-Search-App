package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.framework.library.base.BaseFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.mvp.model.TestModel
import com.smart.novel.mvp.presenter.TestPresenter

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 排行榜
 */
class FRA_RankingList : BaseFragment<TestPresenter, TestModel>() {

    companion object {
        fun getInstance(): FRA_RankingList {
            val fragment = FRA_RankingList()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun startEvents() {
    }
    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_banklist
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
}