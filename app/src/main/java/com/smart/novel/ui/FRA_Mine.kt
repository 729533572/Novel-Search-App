package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.framework.library.base.BaseFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 书架
 */
class FRA_Mine : BaseFragment() {
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

    override fun initViewsAndEvents() {

    }


    override fun isBindEventBusHere(): Boolean {
        return false
    }
}