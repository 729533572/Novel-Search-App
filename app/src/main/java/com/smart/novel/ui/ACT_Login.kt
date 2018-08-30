package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.db.bean.ReadHistoryBean
import com.smart.novel.mvp.contract.BookShelfContract
import com.smart.novel.mvp.model.BookShelfModel
import com.smart.novel.mvp.presenter.BookShelfPresenter

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 登录页面
 */
class ACT_Login : BaseMVPActivity<BookShelfPresenter, BookShelfModel>(), BookShelfContract.View{

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_login
    }

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun startEvents() {
    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    @OnClick(R.id.tv_do_search, R.id.et_search_keywords)
    fun onClick(view: View) {
        when (view.id) {

        }
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun showException(error: ErrorBean?) {
    }
    override fun getBookShelfData(dataList: List<ReadHistoryBean>) {

    }
}