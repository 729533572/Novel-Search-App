package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.mvp.contract.NovelDetailContract
import com.smart.novel.net.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:小说详情页
 */

class NovelDetailPresenter : NovelDetailContract.Presenter() {
    override fun getNovelDetail(id: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getNovelDetail(id), object : RxObserverListener<List<NovelBean>>(mView) {
            override fun onSuccess(result: List<NovelBean>) {
                mView.getNovelDetail(result)
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

    //收藏小说
    override fun doCollect(id: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.doCollect(id), object : RxObserverListener<Any>(mView) {
            override fun onSuccess(result: Any) {
                mView.doCollect(result)
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

    override fun addReadRecord(id: String, chapter_name: String, chapter_number: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.addReadRecord(id, chapter_name, chapter_number), object : RxObserverListener<Any>(mView) {
            override fun onSuccess(result: Any) {
                mView.addReadRecord(result)
            }
        }))
    }
    override fun getNextChapter(book_id: String, chapter_number: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getNextChapter(book_id, chapter_number), object : RxObserverListener<List<ChapterBean>>(mView) {
            override fun onSuccess(result: List<ChapterBean>) {
                mView.getNextChapter(result)
            }
        }))
    }

    override fun getLastChapter(book_id: String, chapter_number: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getLastChapter(book_id,chapter_number), object : RxObserverListener<List<ChapterBean>>(mView) {
            override fun onSuccess(result: List<ChapterBean>) {
                mView.getLastChapter(result)
            }
        }))
    }
}