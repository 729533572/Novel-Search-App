package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.ChapterBean
import com.smart.novel.mvp.contract.NovelDetailContract
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:小说详情页
 */

class NovelDetailPresenter : NovelDetailContract.Presenter() {
    override fun getChapterList(multipleStatusView: MultipleStatusView, id: String, type: String, page: String) {
        multipleStatusView.showLoading()
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getChapterList(id, type, page), object : RxObserverListener<List<ChapterBean>>(mView) {
            override fun onSuccess(result: List<ChapterBean>) {
                multipleStatusView.showContent()
                mView.getChapterList(result)
            }
        }))
    }
}