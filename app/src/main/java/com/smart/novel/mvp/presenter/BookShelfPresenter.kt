package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.NovelBean
import com.smart.novel.mvp.contract.BookShelfContract
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:书架模块
 */
class BookShelfPresenter : BookShelfContract.Presenter() {
    override fun getBookShelfData(type: String, page: String, multipleStatusView: MultipleStatusView?) {
        if (multipleStatusView != null) multipleStatusView!!.showLoading()
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getBookShelfData(type, page), object : RxObserverListener<List<NovelBean>>(mView) {
            override fun onSuccess(result: List<NovelBean>) {
                if (multipleStatusView != null) multipleStatusView!!.showContent()
                mView.getBookShelfData(result)
            }
        }))
    }

    //删除阅读记录
    override fun deleteReadRecord(id: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.deleteReadRecord(id), object : RxObserverListener<Any>(mView) {
            override fun onSuccess(result: Any) {
                mView.deleteReadRecord(result)
            }
        }))
    }

    override fun deleteCollect(id: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.deleteCollect(id), object : RxObserverListener<Any>(mView) {
            override fun onSuccess(result: Any) {
                mView.deleteCollect(result)
            }
        }))
    }
}