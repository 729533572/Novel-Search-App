package com.smart.novel.base

import android.view.View
import com.smart.framework.library.base.BaseFragment
import com.smart.framework.library.bean.ErrorBean

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description:
 */
class FRA_Base :BaseFragment(){
    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun onFirstUserVisible() {
    }

    override fun onUserVisible() {
    }

    override fun onUserInvisible() {
    }

    override fun getLoadingTargetView(): View?{
        return null
    }

    override fun initViewsAndEvents() {

    }

    override fun getContentViewLayoutID(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
}