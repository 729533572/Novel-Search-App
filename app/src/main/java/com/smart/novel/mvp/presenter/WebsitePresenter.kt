package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.WebsiteBean
import com.smart.novel.mvp.contract.WebsiteContract
import com.smart.novel.net.RetrofitRxManager

/**
 * Created by JoJo on 2018/9/6.
 * wechat:18510829974
 * description:小说其他网站的小说信息
 */

class WebsitePresenter : WebsiteContract.Presenter() {
    override fun switchWebsite(website_id: String, chapter_number: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.switchWebsite(website_id, chapter_number), object : RxObserverListener<List<ChapterBean>>(mView) {
            override fun onSuccess(result: List<ChapterBean>) {
                mView.switchWebsite(result)
            }
        }))
    }

    override fun getOtherWebsiteList(multipleStatusView: MultipleStatusView?, author: String, book_name: String) {
        if (multipleStatusView != null) multipleStatusView.showLoading()
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getOtherWebsiteList(author, book_name), object : RxObserverListener<List<WebsiteBean>>(mView) {
            override fun onSuccess(result: List<WebsiteBean>) {
                if (multipleStatusView != null) {
                    multipleStatusView.showContent()
                }
                mView.getOtherWebsiteList(result)
            }
        }))
    }
}