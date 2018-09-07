package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.net.BaseHttpResponse
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description: 小说详情页
 */
class NovelDetailContract {
    interface View : IBaseView {
        fun getChapterList(dataList: List<ChapterBean>)
        //获取小说详情信息
        fun getNovelDetail(novelBean: List<NovelBean>)

        //小说id
        fun doCollect(result: Any)

        fun deleteCollect(result: Any)
        //增加阅读记录
        fun addReadRecord(result: Any)

        //获取上一章节
        fun getLastChapter(dataList: List<ChapterBean>)

        //获取下一章节
        fun getNextChapter(dataList: List<ChapterBean>)
    }

    interface Model : BaseModel {
        fun getChapterList(id: String, type: String, page: String): Observable<BaseHttpResponse<List<ChapterBean>>>
        fun getNovelDetail(id: String): Observable<BaseHttpResponse<List<NovelBean>>>
        fun doCollect(id: String): Observable<BaseHttpResponse<Any>>
        fun deleteCollect(id: String): Observable<BaseHttpResponse<Any>>
        fun addReadRecord(id: String, chapter_name: String, chapter_number: String): Observable<BaseHttpResponse<Any>>
        //获取上一章节
        fun getLastChapter(book_id: String, chapter_number: String): Observable<BaseHttpResponse<List<ChapterBean>>>
        //获取下一章节
        fun getNextChapter(book_id: String, chapter_number: String): Observable<BaseHttpResponse<List<ChapterBean>>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getChapterList(multipleStatusView: MultipleStatusView, id: String, type: String, page: String)
        abstract fun getNovelDetail(id: String)
        abstract fun doCollect(id: String)
        abstract fun deleteCollect(id: String)
        abstract fun addReadRecord(id: String, chapter_name: String, chapter_number: String)
        abstract fun getLastChapter(book_id: String, chapter_number: String)
        abstract fun getNextChapter(book_id: String, chapter_number: String)
    }
}