package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.framework.library.base.BaseFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.R
import kotlinx.android.synthetic.main.fra_bookshelf.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 书架
 */
class FRA_BookShelf : BaseFragment() {
    companion object {
        fun getInstance(): FRA_BookShelf {
            val fragment = FRA_BookShelf()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_bookshelf
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
        tv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var l: String? = null
                CommonUtils.makeEventToast(mContext,l,false)
            }
        })
    }


    override fun isBindEventBusHere(): Boolean {
        return false
    }
}