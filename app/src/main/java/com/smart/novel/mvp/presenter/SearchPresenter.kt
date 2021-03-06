package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.HotSearchBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.mvp.contract.SearchContract
import com.smart.novel.net.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:搜小说
 */
class SearchPresenter : SearchContract.Presenter() {
    /**
     * 获取搜索结果列表
     */
    override fun getSearchResultList(multipleStatusView: MultipleStatusView, keywords: String) {
        multipleStatusView.showLoading()
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getSearchResultList(keywords), object : RxObserverListener<List<NovelBean>>(mView) {
            override fun onSuccess(result: List<NovelBean>) {
                multipleStatusView.showContent()
                mView.getSearchResultList(result)
            }
        }))
    }

    /**
     * 获取热门搜索列表
     */
    override fun getHotSearchList() {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getHotSearchList(), object : RxObserverListener<List<HotSearchBean>>(mView) {
            override fun onSuccess(result: List<HotSearchBean>) {
                mView.getHotSearchList(result)
            }
        }))
    }
}