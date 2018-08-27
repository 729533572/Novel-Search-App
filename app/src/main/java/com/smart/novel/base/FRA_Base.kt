package com.smart.novel.base

import com.smart.framework.library.base.BaseLazyFragment
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView

/**
 * Created by JoJo on 2018/8/27.
wechat：18510829974
description：normal不需要联网的Fragment的父类
 */
abstract class FRA_Base : BaseLazyFragment(), IBaseView {
    override fun showDialogLoading(msg: String) {
        toggleShowDialogLoading(msg)
    }

    override fun dismissDialogLoading() {
        dismissDialogLoad()
    }

    override fun showLoading(multipleStatusView: MultipleStatusView, msg: String) {

    }

    override fun hideLoading() {

    }
}