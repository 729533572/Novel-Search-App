package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.mvp.contract.NovelDetailContract
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:小说详情页
 */

class NovelDetailPresenter : NovelDetailContract.Presenter() {
    override fun getNovelDetail(id: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getNovelDetail(id), object : RxObserverListener<NovelBean>(mView) {
            override fun onSuccess(result: NovelBean) {
                mView.getNovelDetail(result)
            }
        }))
    }

    //收藏小说
    override fun doCollect(id: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.doCollect(id), object : RxObserverListener<Any>(mView) {
            override fun onSuccess(result: Any) {
                mView.doCollect(result)
            }
        }))
    }

    //获取全部章节  latest=1 时，返回最新章节 latest=n 返回全部章节
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