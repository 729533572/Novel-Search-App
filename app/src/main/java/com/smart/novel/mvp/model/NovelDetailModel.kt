package com.smart.novel.mvp.model

import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.mvp.contract.NovelDetailContract
import com.smart.novel.net.BaseHttpResponse
import com.smart.novel.net.RetrofitRxManager
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:小说详情页
 */

class NovelDetailModel : NovelDetailContract.Model {

    override fun getNovelDetail(id: String): Observable<BaseHttpResponse<List<NovelBean>>> {
        return RetrofitRxManager.getRequestService().getNovelDetail(id)
    }

    override fun getChapterList(id: String, type: String, page: String): Observable<BaseHttpResponse<List<ChapterBean>>> {
        return RetrofitRxManager.getRequestService().getChapterList(id, type, page)
    }

    override fun doCollect(id: String): Observable<BaseHttpResponse<Any>> {
        return RetrofitRxManager.getRequestService().doCollect(id)
    }

    override fun deleteCollect(id: String): Observable<BaseHttpResponse<Any>> {
        return RetrofitRxManager.getRequestService().deleteCollect(id)
    }

    override fun addReadRecord(id: String, chapter_name: String, chapter_number: String): Observable<BaseHttpResponse<Any>> {
        return RetrofitRxManager.getRequestService().addReadRecord(id, chapter_name, chapter_number)
    }

    override fun getLastChapter(book_id: String, chapter_number: String): Observable<BaseHttpResponse<List<ChapterBean>>> {
        return RetrofitRxManager.getRequestService().getLastChapter(book_id, chapter_number)
    }

    override fun getNextChapter(book_id: String, chapter_number: String): Observable<BaseHttpResponse<List<ChapterBean>>> {
        return RetrofitRxManager.getRequestService().getNextChapter(book_id, chapter_number)
    }
}