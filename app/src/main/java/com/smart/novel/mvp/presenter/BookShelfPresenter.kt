package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.db.bean.ReadHistoryBean
import com.smart.novel.mvp.contract.BookShelfContract
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:书架模块
 */
class BookShelfPresenter : BookShelfContract.Presenter() {
    override fun getBookShelfData(multipleStatusView: MultipleStatusView) {
        multipleStatusView.showLoading()
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getBookShelfData(), object : RxObserverListener<List<ReadHistoryBean>>(mView) {
            override fun onSuccess(result: List<ReadHistoryBean>) {
                mView.getBookShelfData(result)
                multipleStatusView.showContent()
            }
        }))
    }
}