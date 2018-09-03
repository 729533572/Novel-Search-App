package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.ChapterBean
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
    }

    interface Model : BaseModel {
        fun getChapterList(id: String, type: String, page: String): Observable<BaseHttpResponse<List<ChapterBean>>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getChapterList(multipleStatusView: MultipleStatusView, id: String, type: String, page: String)
    }
}